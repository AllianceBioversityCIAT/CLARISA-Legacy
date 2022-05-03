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

package org.cgiar.clarisa.utils.auth;

import org.cgiar.ciat.auth.ADConexion;
import org.cgiar.ciat.auth.LDAPUser;
import org.cgiar.clarisa.utils.ldap.LDAPHolder;

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

  private LDAPHolder ldapHolder;

  public LDAPAuthenticator(LDAPHolder ldapHolder) {
    this.ldapHolder = ldapHolder;
  }

  @Override
  public LoginStatus authenticate(String email, String password) {
    LoginStatus loginStatus = LoginStatus.NOT_LOGGED;

    try {
      ADConexion con = null;

      // nullpointer if a connection to the active directory could not be established
      LDAPUser ldapUser = this.ldapHolder.getLdapService().searchUserByEmail(email);
      con = this.ldapHolder.getLdapService().authenticateUser(ldapUser.getLogin(), password);

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
