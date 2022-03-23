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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "institutions_locations")

@NamedQuery(name = "InstitutionLocation.findAll", query = "SELECT il FROM InstitutionLocation il")
public class InstitutionLocation implements java.io.Serializable {

  /**
   * S
   */
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private InstitutionLocationId id;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("institutionId")
  @JoinColumn(name = "institution_id")
  private Institution institution;


  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("locElementId")
  @JoinColumn(name = "loc_element_id")
  private LocElement locElement;


  @Column(name = "is_headquater")
  private Boolean headquarter;

  public InstitutionLocation() {
    super();
  }

  public InstitutionLocation(Institution institution, LocElement locElement, Boolean headquarter) {
    super();
    this.institution = institution;
    this.locElement = locElement;
    this.headquarter = headquarter;
  }


  public Boolean getHeadquarter() {
    return headquarter;
  }


  public Institution getInstitution() {
    return institution;
  }


  public LocElement getLocElement() {
    return locElement;
  }


  public void setHeadquarter(Boolean headquarter) {
    this.headquarter = headquarter;
  }


  public void setInstitution(Institution institution) {
    this.institution = institution;
  }


  public void setLocElement(LocElement locElement) {
    this.locElement = locElement;
  }

}
