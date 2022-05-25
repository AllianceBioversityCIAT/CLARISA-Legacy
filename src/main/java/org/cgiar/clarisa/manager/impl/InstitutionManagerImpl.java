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

package org.cgiar.clarisa.manager.impl;

import org.cgiar.clarisa.dao.InstitutionDAO;
import org.cgiar.clarisa.manager.InstitutionManager;
import org.cgiar.clarisa.model.Institution;

import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

@Service
@Scope("singleton")
public class InstitutionManagerImpl implements InstitutionManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(InstitutionManagerImpl.class);

  // DAO
  private InstitutionDAO institutionDAO;

  @Inject
  public InstitutionManagerImpl(InstitutionDAO institutionDAO) {
    this.institutionDAO = institutionDAO;
  }

  @Override
  public JpaRepository<Institution, Long> getDAO() {
    return this.institutionDAO;
  }

  @Override
  public List<Institution> searchChildInstitutions(Long parent) {
    return this.institutionDAO.searchChildInstitutions(parent);
  }

  @Override
  public List<Institution> searchInstitution(String searchValue) {
    return this.institutionDAO.searchInstitution(StringUtils.strip(searchValue));
  }

  @Override
  public List<Institution> searchInstitutionParent(Long parent) {
    return this.institutionDAO.searchInstitutionParent(parent);
  }


}
