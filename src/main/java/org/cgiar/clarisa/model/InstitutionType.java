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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

@Entity
@Table(name = "institution_types")
@NamedQuery(name = "InstitutionType.findAll", query = "SELECT it FROM InstitutionType it")
public class InstitutionType extends ClarisaBaseEntity implements java.io.Serializable {

  static final long serialVersionUID = -943657365260109270L;

  @Expose
  private String name;

  @Expose
  private String acronym;

  @Expose
  private Boolean subDepartmentActive;

  @Expose
  private RepIndOrganizationType repIndOrganizationType;


  public InstitutionType() {
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    InstitutionType other = (InstitutionType) obj;
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
  public String getName() {
    return this.name;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rep_ind_organization_type_id")
  public RepIndOrganizationType getRepIndOrganizationType() {
    return repIndOrganizationType;
  }


  @Column(name = "sub_department_active")
  public Boolean getSubDepartmentActive() {
    return subDepartmentActive;
  }


  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRepIndOrganizationType(RepIndOrganizationType repIndOrganizationType) {
    this.repIndOrganizationType = repIndOrganizationType;
  }

  public void setSubDepartmentActive(Boolean subDepartmentActive) {
    this.subDepartmentActive = subDepartmentActive;
  }

  @Override
  public String toString() {
    return "InstitutionType [id=" + this.getId() + ", name=" + name + ", acronym=" + acronym + ", subDepartmentActive="
      + subDepartmentActive + ", repIndOrganizationType=" + repIndOrganizationType + "]";
  }
}