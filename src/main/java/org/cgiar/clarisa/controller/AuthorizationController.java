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
import org.cgiar.clarisa.dto.RefreshTokenDTO;
import org.cgiar.clarisa.dto.RefreshTokenRequestDTO;
import org.cgiar.clarisa.dto.UserAuthenticationDTO;
import org.cgiar.clarisa.exception.RefreshTokenException;
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.manager.RefreshTokenManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.RoleMapper;
import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.model.RefreshToken;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.AppConstants;
import org.cgiar.clarisa.utils.JwtUtils;
import org.cgiar.clarisa.utils.auth.Authenticator;
import org.cgiar.clarisa.utils.auth.DatabaseAuthenticator;
import org.cgiar.clarisa.utils.auth.LDAPAuthenticator;
import org.cgiar.clarisa.utils.auth.LoginStatus;

import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

  private UserManager userManager;
  private RefreshTokenManager refreshTokenManager;
  private ClarisaAuditlogManager clarisaAuditlogManager;

  private RoleMapper roleMapper;

  private JwtUtils jwtTokenUtils;

  private AppConfig appConfig;

  @Inject
  public AuthorizationController(UserManager userManager, JwtUtils jwtTokenUtils, AppConfig appConfig,
    RoleMapper roleMapper, RefreshTokenManager refreshTokenManager, ClarisaAuditlogManager clarisaAuditlogManager) {
    super();
    this.userManager = userManager;
    this.jwtTokenUtils = jwtTokenUtils;
    this.appConfig = appConfig;
    this.roleMapper = roleMapper;
    this.refreshTokenManager = refreshTokenManager;
    this.clarisaAuditlogManager = clarisaAuditlogManager;
  }

  void addEntityClassToRequest(HttpServletRequest request, Class<? extends ClarisaBaseEntity> clazz) {
    request.setAttribute(AppConstants.HTTP_ENTITY_CLASS_NAME, clazz);
  }

  @PostMapping("/refreshToken")
  public ResponseEntity<RefreshTokenDTO> refreshtoken(HttpServletRequest request,
    @RequestBody RefreshTokenRequestDTO previousTokenObject) {
    Class<RefreshToken> thisClazz = RefreshToken.class;
    this.addEntityClassToRequest(request, RefreshToken.class);
    String previousToken = previousTokenObject.getRefreshToken();
    return refreshTokenManager.findFromToken(previousToken).map(refreshTokenManager::verifyExpiration)
      .map(RefreshToken::getUser).map(user -> {
        String token = jwtTokenUtils.generateJWTToken(user);
        this.clarisaAuditlogManager.registerAuditlog(request, user, thisClazz, previousTokenObject, null, null, true);
        return ResponseEntity.ok(new RefreshTokenDTO(token, previousToken));
      }).orElseThrow(() -> new RefreshTokenException(RefreshTokenException.TOKEN_NOT_FOUND, previousToken));
  }

  @PostMapping(value = "/login")
  public ResponseEntity<UserAuthenticationDTO>
    userAuthentication(@RequestBody NewUserAuthenticationDTO newUserAuthenticationDTO) {
    LoginStatus loginStatus = LoginStatus.NOT_LOGGED;
    String username = newUserAuthenticationDTO.getEmail();
    UserAuthenticationDTO userAutenticationDTO = null;
    HttpStatus status = HttpStatus.OK;
    Authenticator authenticator = null;

    if (!StringUtils.contains(username, "@")) {
      username = this.userManager.getEmailFromUsername(username);
    }

    Optional<User> userOptional = this.userManager.getUserByUsername(username);

    if (appConfig.isLocal()) {
      authenticator = appConfig.getContext().getBean(DatabaseAuthenticator.class);
    } else {
      if (userOptional.isPresent() && userOptional.get().getCgiarUser()) {
        authenticator = appConfig.getContext().getBean(LDAPAuthenticator.class);
      } else {
        authenticator = appConfig.getContext().getBean(DatabaseAuthenticator.class);
      }
    }

    if (userOptional.isPresent()) {
      User user = userOptional.get();
      userAutenticationDTO = new UserAuthenticationDTO();
      userAutenticationDTO.setEmail(user.getEmail());
      userAutenticationDTO.setFirst_name(user.getFirstName());
      userAutenticationDTO.setLast_name(user.getLastName());
      userAutenticationDTO.setActive(user.getActive());
      userAutenticationDTO.setId(user.getId());
      userAutenticationDTO.setRoles(roleMapper.entityListToDtoList(user.getUserRoles()));
      userAutenticationDTO.setRefreshToken(username);
    }

    if (userOptional.isPresent() && userOptional.get().getActive()) {
      loginStatus = authenticator.authenticate(username, newUserAuthenticationDTO.getPassword());
    }

    switch (loginStatus) {
      case LDAP_ERROR:
        status = HttpStatus.INTERNAL_SERVER_ERROR;
        // TODO
        break;
      case LOGGED_SUCCESSFULLY:
        String accessToken = this.jwtTokenUtils.generateJWTToken(userOptional.get());
        RefreshToken refreshToken = this.refreshTokenManager.generateTokenForUser(userOptional.get());
        userAutenticationDTO.setAuthenticated(true);
        userAutenticationDTO.setToken(accessToken);
        userAutenticationDTO.setRefreshToken(refreshToken.getToken());
        userAutenticationDTO.setExpiresIn(this.jwtTokenUtils.getExpirationMilis(accessToken));
        break;
      case NOT_LOGGED:
      case WRONG_CREDENTIALS:
        status = HttpStatus.FORBIDDEN;
        break;
      default:
        LOG.error("Unexpected Error: {}", loginStatus);
        break;
    }

    int statusValue = status.value();
    return Optional.ofNullable(userAutenticationDTO)
      .map(result -> new ResponseEntity<>(result, HttpStatus.resolve(statusValue)))
      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

}
