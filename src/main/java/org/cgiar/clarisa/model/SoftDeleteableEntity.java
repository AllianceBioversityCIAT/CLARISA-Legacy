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

package org.cgiar.clarisa.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.google.gson.annotations.Expose;

/**
 * A base class that all Entities that require soft delete functionality need to extend from. Currently DAOs set
 * the active field to false when the entity is deleted, but this may change in the future as we leverage hibernate's
 * soft delete support.
 * This entity prevents the need to re-define the active field.
 * 
 * @author GrantL
 */
@MappedSuperclass
public abstract class SoftDeleteableEntity extends ClarisaBaseEntity implements Serializable {

  private static final long serialVersionUID = 407298647276207880L;

  // Default to true for all new entities. This gets overriden by hibernate which will call the setActive method.
  @Expose
  private boolean active = true;


  @Column(name = "is_active")
  public boolean getActive() {
    return this.active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }
}
