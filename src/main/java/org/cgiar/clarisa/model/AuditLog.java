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

package org.cgiar.clarisa.model;

import java.io.Serializable;

public interface AuditLog<ID extends Serializable> {

  public final String PRINCIPAL = "PRINCIPAL";

  public final String ENTITY = "entity";

  public final String RELATION_NAME = "relationName";

  public final String SAVED = "Saved";

  public final String UPDATED = "Updated";

  public final String DELETED = "Deleted";

  public default boolean getActive() {
    return true;
  }

  /**
   * This method get the Entity id that is the primary key
   * 
   * @return a Object model id
   */
  public ID getId();

  public String getModificationJustification();

  /**
   * This method gets the user that insert or update the information to the database.
   * 
   * @return a User object that has data changes
   */
  public User getModifiedBy();

  /**
   * This method get a entity information detail to identify in the audit.
   * 
   * @return a Model class log Detail
   */
  public default String logDetail() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }

  public void setActive(boolean active);

  public void setModificationJustification(String modificationJustification);

  public void setModifiedBy(User modifiedBy);
}