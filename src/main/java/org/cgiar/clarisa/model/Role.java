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
// Generated May 17, 2016 9:53:46 AM by Hibernate Tools 4.3.1.Final


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

/**
 * Roles generated by hbm2java
 */
@Entity
@Table(name = "roles")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
public class Role extends SoftDeleteableEntity implements java.io.Serializable {

  private static final long serialVersionUID = 8679238437361759448L;

  @Expose
  private String description;

  @Expose
  private String acronym;

  @Expose
  private Integer order;

  private GlobalUnit globalUnit;

  private List<Permission> rolePermissions;


  // relations
  private List<User> roleUsers;


  public Role() {
  }


  public void addPermission(Permission permission) {
    this.rolePermissions.add(permission);
    permission.getPermissionRoles().add(this);
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "global_unit_id")
  public GlobalUnit getGlobalUnit() {
    return globalUnit;
  }


  @Column(insertable = false, updatable = false)
  public Integer getOrder() {
    return order;
  }


  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"),
    inverseJoinColumns = @JoinColumn(name = "permission_id"))
  public List<Permission> getRolePermissions() {
    return rolePermissions;
  }

  @ManyToMany(mappedBy = "userRoles")
  public List<User> getRoleUsers() {
    return roleUsers;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public void removePermission(Permission permission) {
    this.rolePermissions.remove(permission);
    permission.getPermissionRoles().remove(this);
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setDescription(String description) {
    this.description = description;
  }

  public void setGlobalUnit(GlobalUnit globalUnit) {
    this.globalUnit = globalUnit;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public void setRolePermissions(List<Permission> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }


  public void setRoleUsers(List<User> roleUsers) {
    this.roleUsers = roleUsers;
  }


  @Override
  public String toString() {
    return "Role [id=" + this.getId() + ", description=" + description + ", acronym=" + acronym + ", order=" + order
      + "]";
  }
}