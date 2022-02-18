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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class LDAPUserToUserMapper {

  public static List<User> ldapUserListToUserList(List<LDAPUser> ldapUsers) {
    if (ldapUsers == null) {
      return null;
    }

    List<User> users = new ArrayList<>(ldapUsers.size());
    User user = null;
    for (LDAPUser ldapUser : ldapUsers) {
      if (ldapUser != null) {
        user = new User();
        user.setCgiarUser(true);
        user.setEmail(StringUtils.trimToEmpty(ldapUser.getEmail()));
        user.setFirstName(StringUtils.trimToEmpty(ldapUser.getFirstName()));
        user.setLastName(StringUtils.trimToEmpty(ldapUser.getLastName()));
        user.setUsername(StringUtils.lowerCase(ldapUser.getLogin()));
        user.setActive(false);
      }

      users.add(user);
    }

    return users;
  }

  public static User ldapUserToUser(LDAPUser ldapUser) {
    User user = null;
    if (ldapUser != null) {
      user = new User();
      user.setCgiarUser(true);
      user.setEmail(StringUtils.trimToEmpty(ldapUser.getEmail()));
      user.setFirstName(StringUtils.trimToEmpty(ldapUser.getFirstName()));
      user.setLastName(StringUtils.trimToEmpty(ldapUser.getLastName()));
      user.setUsername(StringUtils.lowerCase(ldapUser.getLogin()));
      user.setActive(false);
    }

    return user;
  }

}
