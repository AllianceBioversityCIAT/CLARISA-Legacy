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

package org.cgiar.clarisa.dao;

import org.cgiar.clarisa.model.User;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserDAO extends JpaRepository<User, Long> {

  @Modifying(flushAutomatically = true)
  @Transactional
  @Query("update User u set u.password = ?1 where u.email = ?2 or u.username = ?2")
  public void changePassword(String password, String username);

  @Query("select u.email from User u where u.username = ?1")
  public String getEmailFromUsername(String username);

  @Query("select u from User u where u.email = ?1 or u.username = ?1")
  public Optional<User> getUserByUsername(String username);
}
