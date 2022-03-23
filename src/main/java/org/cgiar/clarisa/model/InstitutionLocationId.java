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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstitutionLocationId extends ClarisaBaseEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private Long institutionId;


  private Long locElementId;

  @Column(name = "institution_id")
  public Long getInstitutionId() {
    return institutionId;
  }

  @Column(name = "loc_element_id")
  public Long getLocElementId() {
    return locElementId;
  }


  public void setInstitutionId(Long institutionId) {
    this.institutionId = institutionId;
  }


  public void setLocElementId(Long locElementId) {
    this.locElementId = locElementId;
  }

}
