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

package org.cgiar.clarisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "loc_element_types")
@NamedQuery(name = "LocElementType.findAll", query = "SELECT let FROM LocElementType let")
public class LocElementType extends ClarisaBaseEntity implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String name;
  private Boolean isScope;


  @Column(name = "is_scope")
  public Boolean getIsScope() {
    return isScope;
  }

  @Column
  public String getName() {
    return name;
  }

  public void setIsScope(Boolean isScope) {
    this.isScope = isScope;
  }

  @Column
  public void setName(String name) {
    this.name = name;
  }


}
