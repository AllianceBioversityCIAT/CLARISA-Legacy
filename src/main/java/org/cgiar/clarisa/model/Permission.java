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

/**************
 * @author Diego Perez - Alliance Bioversity/CIAT
 **************/

package org.cgiar.clarisa.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "permissions")
@NamedQuery(name = "Permission.findAll", query = "SELECT p FROM Permission p")
public class Permission extends ClarisaBaseEntity implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String name;

  private List<Role> permissionRoles;

  public void addRole(Role role) {
    this.getPermissionRoles().add(role);
    role.getRolePermissions().add(this);
  }


  @Column
  public String getName() {
    return name;
  }


  @ManyToMany(mappedBy = "rolePermissions")
  public List<Role> getPermissionRoles() {
    return permissionRoles;
  }


  public void removeRole(Role role) {
    this.getPermissionRoles().remove(role);
    role.getRolePermissions().remove(this);
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setPermissionRoles(List<Role> permissionRoles) {
    this.permissionRoles = permissionRoles;
  }
}
