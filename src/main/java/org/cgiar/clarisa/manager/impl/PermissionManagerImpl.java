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

package org.cgiar.clarisa.manager.impl;

import org.cgiar.clarisa.dao.PermissionDAO;
import org.cgiar.clarisa.manager.PermissionManager;
import org.cgiar.clarisa.model.Permission;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class PermissionManagerImpl implements PermissionManager {

  private PermissionDAO permissionDAO;


  @Inject
  public PermissionManagerImpl(PermissionDAO permissionDAO) {
    super();
    this.permissionDAO = permissionDAO;
  }


  @Override
  public JpaRepository<Permission, Long> getDAO() {

    return permissionDAO;
  }

}
