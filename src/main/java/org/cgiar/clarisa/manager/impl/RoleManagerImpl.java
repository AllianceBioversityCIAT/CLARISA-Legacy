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

package org.cgiar.clarisa.manager.impl;

import org.cgiar.clarisa.dao.RoleDAO;
import org.cgiar.clarisa.manager.RoleManager;
import org.cgiar.clarisa.model.Role;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class RoleManagerImpl implements RoleManager {

  private static final Logger LOG = LoggerFactory.getLogger(RoleManagerImpl.class);

  private RoleDAO roleDao;

  @Inject
  public RoleManagerImpl(RoleDAO roleDao) {
    super();
    this.roleDao = roleDao;
  }

  @Override
  public List<Role> findByGlobalUnit(Long globalUnit) {
    return this.roleDao.findByGlobalUnit(globalUnit);
  }

  @Override
  public Role findRoleByAcronym_GlobalUnit(String acronym, Long globalUnitId) {

    return this.roleDao.findRoleByAcronym_GlobalUnit(acronym, globalUnitId);
  }

  @Override
  public JpaRepository<Role, Long> getDAO() {

    return this.roleDao;
  }

}
