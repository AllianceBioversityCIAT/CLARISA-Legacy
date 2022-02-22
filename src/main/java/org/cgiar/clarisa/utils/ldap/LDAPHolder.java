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

package org.cgiar.clarisa.utils.ldap;

import org.cgiar.ciat.auth.LDAPService;
import org.cgiar.clarisa.config.AppConfig;

import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Named("LDAPHolder")
public class LDAPHolder {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(LDAPHolder.class);

  private LDAPService ldapService;
  private AppConfig appConfig;

  @Inject
  public LDAPHolder(AppConfig appConfig) {
    this.appConfig = appConfig;
    this.tryGetLDAPService();
  }

  public LDAPService getLdapService() {
    return ldapService;
  }

  private void tryGetLDAPService() {
    try {
      LDAPService service = new LDAPService();
      if (appConfig.isProduction()) {
        service.setInternalConnection(false);
      } else {
        service.setInternalConnection(true);
      }

      this.ldapService = service;
    } catch (Exception e) {
      LOG.error("Could not open a new LDAP connection. Cause: {}", e.getMessage());
      this.ldapService = null;
    }
  }
}
