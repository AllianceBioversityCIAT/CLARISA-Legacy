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

import org.cgiar.clarisa.dao.GlobalUnitDAO;
import org.cgiar.clarisa.manager.GlobalUnitManager;
import org.cgiar.clarisa.model.GlobalUnit;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class GlobalUnitManagerImpl implements GlobalUnitManager {

  private static final Logger LOG = LoggerFactory.getLogger(GlobalUnitManagerImpl.class);

  private GlobalUnitDAO globalUnitDao;

  @Inject
  public GlobalUnitManagerImpl(GlobalUnitDAO globalUnitDao) {
    super();
    this.globalUnitDao = globalUnitDao;
  }


  @Override
  public JpaRepository<GlobalUnit, Long> getDAO() {
    return this.globalUnitDao;
  }

}
