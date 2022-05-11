/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture Platform (CLARISA).
 * CLARISA is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CLARISA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CLARISA. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

/**************
 * @author Diego Perez - Alliance Bioversity/CIAT
 **************/

package org.cgiar.clarisa.controller;

import org.cgiar.clarisa.config.AppConfig;
import org.cgiar.clarisa.dto.PasswordChangeDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.dto.UserDTO;
import org.cgiar.clarisa.exception.UserNotFoundException;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.UserMapper;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.ldap.LDAPUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController extends GenericController<User, UserDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);


  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();

  // Manager
  private UserManager manager;

  // Mapper
  private UserMapper mapper;

  private LDAPUtils ldapUtils;
  private AppConfig appConfig;

  @Inject
  public UserController(UserManager manager, UserMapper mapper, LDAPUtils ldapUtils, AppConfig appConfig) {
    super(User.class);
    this.manager = manager;
    this.mapper = mapper;
    this.ldapUtils = ldapUtils;
    this.appConfig = appConfig;
  }

  @GetMapping(value = "/simple")
  public ResponseEntity<List<SimpleDTO>> findAllSimple() {
    List<User> resultList = this.manager.findAll();
    return ResponseEntity.ok(this.mapper.entityListToSimpleDtoList(resultList));
  }

  @GetMapping(value = "/get/username/{username}")
  public ResponseEntity<List<UserDTO>> findUser(@PathVariable("username") String username) {
    List<User> users = this.findUsers(username, null);
    return ResponseEntity.ok(mapper.entityListToDtoList(users));
  }

  private List<User> findUsers(String username, Boolean isCgiar) {
    String toFind = StringUtils.trimToEmpty(username);
    List<User> users = Collections.emptyList();
    if (isCgiar == null) {
      Optional<User> userDb = manager.getUserByUsername(toFind);
      if (!userDb.isPresent()) {
        if (StringUtils.contains(toFind, '@')) {
          users = Collections.singletonList(this.ldapUtils.findUserByEmail(toFind));
        } else {
          users = this.ldapUtils.findUsersByUsername(toFind);
        }
      } else {
        users = Collections.singletonList(manager.getUserByUsername(toFind).orElse(null));
      }
    } else {
      if (isCgiar) {
        if (StringUtils.contains(toFind, '@')) {
          users = Collections.singletonList(this.ldapUtils.findUserByEmail(toFind));
        } else {
          users = this.ldapUtils.findUsersByUsername(toFind);
        }
      } else {
        users = Collections.singletonList(manager.getUserByUsername(toFind).orElse(null));
      }
    }

    return users;
  }

  @Override
  public Logger getClassLogger() {
    return UserController.LOG;
  }

  @Override
  public GenericManager<User> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<User, UserDTO> getMapper() {

    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

  @Override
  public ResponseEntity<UserDTO> save(@RequestBody UserDTO dto, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    BCryptPasswordEncoder encoder = appConfig.getContext().getBean(BCryptPasswordEncoder.class);
    dto.setPassword(encoder.encode(dto.getPassword()));
    return super.save(dto, request, response, user);
  }

  @Override
  public ResponseEntity<UserDTO> update(@RequestBody UserDTO dto, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    User previousUser = this.manager.findById(dto.getId()).orElseThrow(() -> new UserNotFoundException());
    if (StringUtils.isEmpty(dto.getPassword())) {
      dto.setPassword(previousUser.getPassword());
    } else {
      BCryptPasswordEncoder encoder = appConfig.getContext().getBean(BCryptPasswordEncoder.class);
      dto.setPassword(encoder.encode(dto.getPassword()));
    }
    return super.update(dto, request, response, user);
  }

  @PostMapping(value = "/passwordChange")
  public ResponseEntity<Boolean> userChangePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
    if (passwordChangeDTO == null || StringUtils.isEmpty(passwordChangeDTO.getNewPassword())
      || StringUtils.isEmpty(passwordChangeDTO.getUsername())) {
      return ResponseEntity.badRequest().body(false);
    }

    User previousUser =
      this.manager.getUserByUsername(passwordChangeDTO.getUsername()).orElseThrow(() -> new UserNotFoundException());

    BCryptPasswordEncoder encoder = appConfig.getContext().getBean(BCryptPasswordEncoder.class);
    String newPassword = encoder.encode(passwordChangeDTO.getNewPassword());

    this.manager.changePassword(newPassword, previousUser.getUsername());

    return ResponseEntity.ok(true);
  }
}
