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


public class UserDTO extends BaseDTO {

  private String firstName;

  private String lastName;

  private String userName;

  private String email;

  private Boolean isActive;


  public String getEmail() {
    return email;
  }


  public String getFirstName() {
    return firstName;
  }


  public Boolean getIsActive() {
    return isActive;
  }


  public String getLastName() {
    return lastName;
  }


  public String getUserName() {
    return userName;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }


  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public void setUserName(String userName) {
    this.userName = userName;
  }
}
