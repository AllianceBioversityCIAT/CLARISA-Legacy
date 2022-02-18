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

import org.cgiar.clarisa.dto.SearchUserDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.dto.UserDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.UserMapper;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.ldap.LDAPUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
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

  @Inject
  public UserController(UserManager manager, UserMapper mapper, LDAPUtils ldapUtils) {
    super(User.class);
    this.manager = manager;
    this.mapper = mapper;
    this.ldapUtils = ldapUtils;
  }

  @PostMapping(value = "/create")
  public ResponseEntity<UserDTO> createUser(UserDTO newUser) {


    return ResponseEntity.ok(null);
  }

  @GetMapping(value = "/simple")
  public ResponseEntity<List<SimpleDTO>> findAllSimple() {
    List<User> resultList = this.manager.findAll();
    return ResponseEntity.ok(this.mapper.entityListToSimpleDtoList(resultList));
  }

  @PostMapping(value = "/get/username")
  public ResponseEntity<List<UserDTO>> findUser(@RequestBody SearchUserDTO searchUser) {
    HttpStatus httpStatus = HttpStatus.OK;
    List<User> users = Collections.emptyList();
    if (searchUser != null) {
      String toFind = StringUtils.trimToEmpty(searchUser.getUsername());
      if (searchUser.getIsCgiarUser()) {
        if (StringUtils.contains(toFind, '@')) {
          users = Collections.singletonList(this.ldapUtils.findUserByEmail(toFind));
        } else {
          users = this.ldapUtils.findUsersByUsername(toFind);
        }
      } else {
        users = Collections.singletonList(manager.getUserByUsername(toFind).orElse(null));
      }
    }

    return ResponseEntity.status(httpStatus).body(mapper.entityListToDtoList(users));
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
  public ResponseEntity<UserDTO> save(UserDTO dto) {
    return this.createUser(dto);
  }
}
