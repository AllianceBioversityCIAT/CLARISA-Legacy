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

import org.cgiar.clarisa.dao.ClarisaAuditlogDAO;
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.model.ClarisaAuditlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Service
@Scope("singleton")
public class ClarisaAuditlogManagerImpl implements ClarisaAuditlogManager {

  private static final Logger LOG = LoggerFactory.getLogger(ClarisaAuditlogManagerImpl.class);

  // DAOs
  private ClarisaAuditlogDAO clarisaAuditlogDAO;

  @Autowired
  public ClarisaAuditlogManagerImpl(ClarisaAuditlogDAO clarisaAuditlogDAO) {
    super();
    this.clarisaAuditlogDAO = clarisaAuditlogDAO;
  }

  @Override
  public JpaRepository<ClarisaAuditlog, Long> getDAO() {
    return clarisaAuditlogDAO;
  }

}
