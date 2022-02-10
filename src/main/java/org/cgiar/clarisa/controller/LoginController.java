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


import org.cgiar.ciat.auth.ADConexion;
import org.cgiar.ciat.auth.LDAPService;
import org.cgiar.ciat.auth.LDAPUser;
import org.cgiar.clarisa.dto.NewUserAuthenticationDTO;
import org.cgiar.clarisa.dto.UserAuthenticationDTO;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.MD5Convert;

import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userAuthentication")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class LoginController {

  private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

  private UserManager userManager;
  // private ConfigurableEnvironment env;
  @Value("${spring.profiles.active}")
  private String profile;

  @Inject
  public LoginController(UserManager userManager) {
    super();
    this.userManager = userManager;
  }

  @PostMapping(value = "/login")
  public ResponseEntity<UserAuthenticationDTO>
    userAuthentication(@RequestBody NewUserAuthenticationDTO newUserAuthenticationDTO) {
    UserAuthenticationDTO userAutenticationDTO = null;
    User userlogged = userManager.getUserByEmail(newUserAuthenticationDTO.getEmail());
    if (userlogged != null) {
      String userEmail = userlogged.getEmail().trim().toLowerCase();
      String md5Pass = MD5Convert.stringToMD5(newUserAuthenticationDTO.getPassword());
      userAutenticationDTO = new UserAuthenticationDTO();
      userAutenticationDTO.setEmail(userEmail);
      userAutenticationDTO.setFirst_name(userlogged.getFirstName());
      userAutenticationDTO.setLast_name(userlogged.getLastName());
      userAutenticationDTO.setId(userlogged.getId());
      if (!userlogged.getCgiarUser() && userlogged.getPassword().equals(md5Pass)) {
        userAutenticationDTO.setAuthenticated(true);
      } else {
        userAutenticationDTO.setAuthenticated(false);
        if (userlogged.getCgiarUser()) {
          // try LDPA authentication
          try {
            LOG.info("Trying Authentication...");
            ADConexion con = null;
            LDAPService service = null;
            LDAPUser ldapUser = null;
            // TODO change to validate if you are on production.
            if (profile.equals("local")) {
              service = new LDAPService(true);
            } else {
              service = new LDAPService(false);
            }
            ldapUser = service.searchUserByEmail(userEmail);
            con = service.authenticateUser(ldapUser.getLogin(), newUserAuthenticationDTO.getPassword());
            if (con != null) {
              if (con.getLogin() != null) {
                userAutenticationDTO.setAuthenticated(true);
              } else {
                LOG.error("Authentication error  {}", con.getAuthenticationMessage());
              }
              con.closeContext();
            } else {
              LOG.error("connection error  {}", ldapUser);
            }
          } catch (Exception e) {
            LOG.error("Exception raised trying to log in the user '{}' against the active directory. ",
              newUserAuthenticationDTO.getEmail(), e.getMessage());
          }
        }
      }
    }
    return Optional.ofNullable(userAutenticationDTO).map(result -> new ResponseEntity<>(result, HttpStatus.OK))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }


}
