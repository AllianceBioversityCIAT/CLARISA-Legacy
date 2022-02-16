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

package org.cgiar.clarisa.utils;

import org.cgiar.ciat.auth.ADConexion;
import org.cgiar.ciat.auth.LDAPService;
import org.cgiar.clarisa.config.AppConfig;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Named("LDAPAuth")
public class LDAPAuthenticator implements Authenticator {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(LDAPAuthenticator.class);

  private AppConfig appConfig;

  public LDAPAuthenticator(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  @Override
  public LoginStatus authenticate(String email, String password) {
    LoginStatus loginStatus = LoginStatus.NOT_LOGGED;

    try {
      ADConexion con = null;
      LDAPService service = new LDAPService();
      if (appConfig.isProduction()) {
        service.setInternalConnection(false);
      } else {
        service.setInternalConnection(true);
      }

      con = service.authenticateUser(email, password);

      if (con != null) {
        if (con.getLogin() != null) {
          loginStatus = LoginStatus.LOGGED_SUCCESSFULLY;
        } else {
          loginStatus = LoginStatus.WRONG_CREDENTIALS;
          LOG.error("Authentication error  {}", con.getAuthenticationMessage());
        }
        con.closeContext();
      } else {
        LOG.error("Authentication error ERROR_LOGON_FAILURE");
      }
    } catch (Exception e) {
      loginStatus = LoginStatus.LDAP_ERROR;
      LOG.error("Exception raised trying to log in the user '{}' against the active directory. ", email,
        e.getMessage());
    }

    return loginStatus;
  }

}
