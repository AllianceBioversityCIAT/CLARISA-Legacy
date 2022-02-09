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

package org.cgiar.clarisa.model;
// Generated May 17, 2016 9:53:46 AM by Hibernate Tools 4.3.1.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends ClarisaBaseEntity implements java.io.Serializable {

  private static final long serialVersionUID = 8679238437361759448L;

  @Expose
  private String description;

  @Expose
  private String acronym;

  @Expose
  private Integer order;


  public Role() {
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }

    Role other = (Role) obj;
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

  @Transient
  public String getComposedName() {
    if (this.getId() == null || this.getId() == -1) {
      return "";
    }
    return this.getAcronym() + ", " + this.getDescription();
  }

  @Column
  public String getDescription() {
    return this.description;
  }


  @Column
  public Integer getOrder() {
    return order;
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

  public void setDescription(String description) {
    this.description = description;
  }


  public void setOrder(Integer order) {
    this.order = order;
  }

  @Override
  public String toString() {
    return "Role [id=" + this.getId() + ", description=" + description + ", acronym=" + acronym + ", order=" + order
      + "]";
  }
}