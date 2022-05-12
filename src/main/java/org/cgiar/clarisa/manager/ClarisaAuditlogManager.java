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

package org.cgiar.clarisa.manager;

import org.cgiar.clarisa.dto.BaseDTO;
import org.cgiar.clarisa.model.ClarisaAuditlog;
import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.model.User;

import javax.servlet.http.HttpServletRequest;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public interface ClarisaAuditlogManager extends GenericManager<ClarisaAuditlog> {

  /**
   * Add to the database the Service request information. This method is meant to be used for GET requests
   * 
   * @param request - The HTTP Request
   * @param response - The HTTP Response object
   * @param user - the user trying to make the transaction
   * @param successful - this transaction was successful
   * @return
   */
  public default <ENTITY extends ClarisaBaseEntity> ClarisaAuditlog registerAuditlog(HttpServletRequest request,
    User user, Class<ENTITY> entityClazz, boolean successful) {
    return this.registerAuditlog(request, user, entityClazz, null, null, null, null, successful, null);
  }

  /**
   * Add to the database the Service request information
   * 
   * @param request - The HTTP Request
   * @param response - The HTTP Response object
   * @param user - the user trying to make the transaction
   * @param previous - the previous entity, before any modification
   * @param updated - the modified entity
   * @param id - the id of the entity to be modified
   * @param successful - this transaction was successful?
   */
  public <ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> ClarisaAuditlog registerAuditlog(
    HttpServletRequest request, User user, Class<ENTITY> entityClazz, Object incoming, DTO previous, DTO updated,
    Long id, boolean successful, Exception failingCause);

  /**
   * Add to the database the Service request information. This method is meant to be used for DELETE requests
   * 
   * @param request - The HTTP Request
   * @param response - The HTTP Response object
   * @param user - the user trying to make the transaction
   * @param successful - this transaction was successful
   * @return
   */
  public default <ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> ClarisaAuditlog registerAuditlog(
    HttpServletRequest request, User user, Class<ENTITY> entityClazz, Object incoming, DTO previous, Long id,
    boolean successful) {
    return this.registerAuditlog(request, user, entityClazz, incoming, previous, null, id, successful, null);
  }

}
