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

import org.cgiar.clarisa.model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Component
public class AuthHolderImpl implements AuthHolder {

  @Override
  public User getCurrentUser() {
    Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return (user instanceof String ? null : (User) user);
  }
}
