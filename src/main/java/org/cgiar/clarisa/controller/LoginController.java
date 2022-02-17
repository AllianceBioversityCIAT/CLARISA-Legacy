/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture (CLARISA).
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
import org.cgiar.clarisa.dto.NewUserAuthenticationDTO;
import org.cgiar.clarisa.dto.UserAuthenticationDTO;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.RoleMapper;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.Authenticator;
import org.cgiar.clarisa.utils.DatabaseAuthenticator;
import org.cgiar.clarisa.utils.JwtTokenUtils;
import org.cgiar.clarisa.utils.LDAPAuthenticator;
import org.cgiar.clarisa.utils.LoginStatus;

import java.security.Principal;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class LoginController {

  private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

  private UserManager userManager;

  private RoleMapper roleMapper;

  private JwtTokenUtils jwtTokenUtils;

  private AppConfig appConfig;

  @Inject
  public LoginController(UserManager userManager, JwtTokenUtils jwtTokenUtils, AppConfig appConfig,
    RoleMapper roleMapper) {
    super();
    this.userManager = userManager;
    this.jwtTokenUtils = jwtTokenUtils;
    this.appConfig = appConfig;
    this.roleMapper = roleMapper;
  }

  @RequestMapping("/user")
  public Principal user(Principal user) {
    return user;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<UserAuthenticationDTO>
    userAuthentication(@RequestBody NewUserAuthenticationDTO newUserAuthenticationDTO) {
    LoginStatus loginStatus = LoginStatus.NOT_LOGGED;
    String username = newUserAuthenticationDTO.getEmail();
    UserAuthenticationDTO userAutenticationDTO = null;
    HttpStatus status = HttpStatus.OK;
    Authenticator authenticator = null;

    if (appConfig.isLocal()) {
      authenticator = appConfig.getContext().getBean(DatabaseAuthenticator.class);
    } else {
      authenticator = appConfig.getContext().getBean(LDAPAuthenticator.class);
    }

    if (!StringUtils.contains(username, "@")) {
      username = this.userManager.getEmailFromUsername(username);
    }

    Optional<User> userOptional = this.userManager.getUserByUsername(username);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      userAutenticationDTO = new UserAuthenticationDTO();
      userAutenticationDTO.setEmail(user.getEmail());
      userAutenticationDTO.setFirst_name(user.getFirstName());
      userAutenticationDTO.setLast_name(user.getLastName());
      userAutenticationDTO.setId(user.getId());
      userAutenticationDTO.setRoles(roleMapper.entityListToDtoList(user.getUserRoles()));
    }

    loginStatus = authenticator.authenticate(username, newUserAuthenticationDTO.getPassword());

    switch (loginStatus) {
      case LDAP_ERROR:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        // TODO
        break;
      case LOGGED_SUCCESSFULLY:
        String token = this.jwtTokenUtils.generateJWTToken(userOptional.get());
        userAutenticationDTO.setAuthenticated(true);
        userAutenticationDTO.setToken(token);
        break;
      case NOT_LOGGED:
      case WRONG_CREDENTIALS:
        status = HttpStatus.FORBIDDEN;
        break;
      default:
        LOG.error("Unexpected Error");
        break;
    }

    int statusValue = status.value();
    return Optional.ofNullable(userAutenticationDTO)
      .map(result -> new ResponseEntity<>(result, HttpStatus.resolve(statusValue)))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
