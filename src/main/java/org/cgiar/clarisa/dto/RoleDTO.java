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

package org.cgiar.clarisa.dto;

import java.util.List;

public class RoleDTO extends SimpleDTO {


  private String description;


  private String acronym;


  private GlobalUnitDTO globalUnit;

  private List<PermissionDTO> rolePermissions;


  public String getAcronym() {
    return acronym;
  }


  public String getDescription() {
    return description;
  }


  public GlobalUnitDTO getGlobalUnit() {
    return globalUnit;
  }


  public List<PermissionDTO> getRolePermissions() {
    return rolePermissions;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public void setGlobalUnit(GlobalUnitDTO globalUnit) {
    this.globalUnit = globalUnit;
  }


  public void setRolePermissions(List<PermissionDTO> rolePermissions) {
    this.rolePermissions = rolePermissions;
  }


}
