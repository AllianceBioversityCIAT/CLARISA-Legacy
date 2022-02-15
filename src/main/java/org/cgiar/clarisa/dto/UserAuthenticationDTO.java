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

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.clarisa.dto;

public class UserAuthenticationDTO extends BaseDTO {


  public String first_name;


  public String last_name;


  public String email;


  public boolean authenticated;

  public String getEmail() {
    return email;
  }

  public String getFirst_name() {
    return first_name;
  }


  public String getLast_name() {
    return last_name;
  }

  public boolean isAuthenticated() {
    return authenticated;
  }

  public void setAuthenticated(boolean authenticated) {
    this.authenticated = authenticated;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }


  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

}
