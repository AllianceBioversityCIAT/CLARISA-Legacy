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

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "institutions")
@NamedQuery(name = "Institution.findAll", query = "SELECT i FROM Institution i")
public class Institution extends ClarisaBaseEntity implements java.io.Serializable {

  private static final long serialVersionUID = 3635585962414755020L;

  @Expose
  private InstitutionType institutionType;

  @Expose
  private String name;

  @Expose
  private String acronym;

  @Expose
  private String websiteLink;

  @Expose
  private Date added;

  // relations
  private List<LocElement> institutionLocations;


  public Institution() {
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    Institution other = (Institution) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }


  @Column
  public String getAcronym() {
    return this.acronym;
  }


  @Column
  public Date getAdded() {
    return this.added;
  }

  @Transient
  public String getComposedName() {
    return (this.acronym != null ? (StringUtils.trim(this.acronym) + " - ") : "") + StringUtils.trim(this.getName());
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "institutions_locations", joinColumns = @JoinColumn(name = "institution_id"),
    inverseJoinColumns = @JoinColumn(name = "loc_element_id"))
  public List<LocElement> getInstitutionLocations() {
    return institutionLocations;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "institution_type_id")
  public InstitutionType getInstitutionType() {
    return institutionType;
  }

  @Column
  public String getName() {
    return this.name;
  }

  @Column(name = "website_link")
  public String getWebsiteLink() {
    return this.websiteLink;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setAdded(Date added) {
    this.added = added;
  }

  public void setInstitutionLocations(List<LocElement> institutionLocations) {
    this.institutionLocations = institutionLocations;
  }

  public void setInstitutionType(InstitutionType institutionType) {
    this.institutionType = institutionType;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }

  @Override
  public String toString() {
    return "Institution [id=" + this.getId() + ", institutionType=" + institutionType + ", name=" + name + ", acronym="
      + acronym + "]";
  }
}