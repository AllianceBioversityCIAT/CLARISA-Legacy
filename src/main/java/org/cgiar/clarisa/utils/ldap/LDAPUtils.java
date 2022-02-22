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

import org.cgiar.ciat.auth.LDAPUser;
import org.cgiar.clarisa.model.User;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Named
public class LDAPUtils {

  // Logger
  public static Logger LOG = LoggerFactory.getLogger(LDAPUtils.class);

  @Autowired
  private LDAPHolder ldapHolder;

  public User findUserByEmail(String email) {
    LDAPUser ldapUser = null;
    try {
      ldapUser = this.ldapHolder.getLdapService().searchUserByEmail(email);
    } catch (Exception ex) {
      LOG.error("An error has ocurred while trying to find the user {}. Cause: {}", email, ex.getMessage());
    }

    return LDAPUserToUserMapper.ldapUserToUser(ldapUser);
  }

  public List<User> findUsersByUsername(String username) {
    List<LDAPUser> ldapUsers = Collections.emptyList();
    try {
      ldapUsers = this.ldapHolder.getLdapService().searchUsers(username);
    } catch (Exception ex) {
      LOG.error("An error has ocurred while trying to find the user {}. Cause: {}", username, ex.getMessage());
    }

    return LDAPUserToUserMapper.ldapUserListToUserList(ldapUsers);
  }
}
