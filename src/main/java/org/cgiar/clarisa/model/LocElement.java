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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "loc_elements")
@NamedQuery(name = "LocElement.findAll", query = "SELECT loc FROM LocElement loc")
public class LocElement extends ClarisaBaseEntity implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String name;

  private String isoAlpha2;


  private String isoAlpha3;


  private String isoNumeric;


  private LocElement parent;


  private LocElementType locElementType;


  private LocGeoposition locGeoposition;

  // relations
  private List<InstitutionLocation> locElementInstitutions;


  @Column(name = "iso_alpha_2")
  public String getIsoAlpha2() {
    return isoAlpha2;
  }


  @Column(name = "iso_alpha_3")
  public String getIsoAlpha3() {
    return isoAlpha3;
  }

  @Column(name = "iso_numeric")
  public String getIsoNumeric() {
    return isoNumeric;
  }

  @OneToMany(mappedBy = "locElement")
  public List<InstitutionLocation> getLocElementInstitutions() {
    return locElementInstitutions;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "element_type_id")
  public LocElementType getLocElementType() {
    return locElementType;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "geoposition_id")
  public LocGeoposition getLocGeoposition() {
    return locGeoposition;
  }

  @Column
  public String getName() {
    return name;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public LocElement getParent() {
    return parent;
  }


  public void setIsoAlpha2(String isoAlpha2) {
    this.isoAlpha2 = isoAlpha2;
  }


  public void setIsoAlpha3(String isoAlpha3) {
    this.isoAlpha3 = isoAlpha3;
  }


  public void setIsoNumeric(String isoNumeric) {
    this.isoNumeric = isoNumeric;
  }

  public void setLocElementInstitutions(List<InstitutionLocation> locElementInstitutions) {
    this.locElementInstitutions = locElementInstitutions;
  }

  public void setLocElementType(LocElementType locElementType) {
    this.locElementType = locElementType;
  }

  public void setLocGeoposition(LocGeoposition locGeoposition) {
    this.locGeoposition = locGeoposition;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(LocElement parent) {
    this.parent = parent;
  }

}
