/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.clarisa.manager.impl;

import org.cgiar.clarisa.dao.InstitutionLocationDAO;
import org.cgiar.clarisa.manager.InstitutionLocationManager;
import org.cgiar.clarisa.model.InstitutionLocation;

import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class InstitutionLocationManagerImpl implements InstitutionLocationManager {

  private InstitutionLocationDAO institutionLocationDAO;

  @Inject
  public InstitutionLocationManagerImpl(InstitutionLocationDAO institutionLocationDAO) {
    super();
    this.institutionLocationDAO = institutionLocationDAO;
  }


  @Override
  public JpaRepository<InstitutionLocation, Long> getDAO() {

    return institutionLocationDAO;
  }


  @Override
  public List<InstitutionLocation> searchInstitutionLocation(Long institutionId) {
    return institutionLocationDAO.searchInstitutionLocation(institutionId);
  }

}