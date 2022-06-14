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

public class UserDTO extends SimpleDTO {

  private String firstName;

  private String lastName;

  private String username;

  private String password;

  private String email;

  private boolean isActive;

  private boolean cgiarUser;

  private List<RoleDTO> roleList;


  public boolean getCgiarUser() {
    return cgiarUser;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getPassword() {
    return password;
  }

  public List<RoleDTO> getRoleList() {
    return roleList;
  }

  public String getUsername() {
    return username;
  }

  public boolean isActive() {
    return isActive;
  }


  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setCgiarUser(boolean isCgiarUser) {
    this.cgiarUser = isCgiarUser;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoleList(List<RoleDTO> roleList) {
    this.roleList = roleList;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
