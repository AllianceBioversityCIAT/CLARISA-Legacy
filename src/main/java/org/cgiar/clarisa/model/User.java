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

package org.cgiar.clarisa.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang3.BooleanUtils;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends ClarisaAuditableEntity implements java.io.Serializable, AuditLog<Long> {

  private static final long serialVersionUID = 3674438945983473335L;

  @Expose
  private String firstName;

  @Expose
  private String lastName;

  @Expose
  private String username;

  @Expose
  private String email;

  @Expose
  private String password;

  @Expose
  private boolean cgiarUser;

  private boolean autoSave;

  private Boolean agreeTerms;

  private Date lastLogin;

  // private Set<UserRole> userRoles = new HashSet<UserRole>(0);

  public User() {
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
    User other = (User) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }

  public Boolean getAgreeTerms() {
    return BooleanUtils.isTrue(agreeTerms);
  }

  /**
   * This method returns the user's full name.
   * 
   * @return a String that represents the user's full name.
   *         e.g. Héctor Tobón
   */
  @Transient
  public String getComposedCompleteName() {
    return this.firstName + " " + this.lastName;
  }

  @Transient
  public String getComposedID() {
    String composedId = this.email.split("@")[0] + "-" + this.getId();
    return composedId;
  }

  /**
   * This method returns a composed way to show a User.
   * 
   * @return a String that represents a User.
   *         e.g. Tobón, Héctor <h.f.tobon@cgiar.org>
   */
  @Transient
  public String getComposedName() {
    if (this.getId() == null || this.getId() == -1) {
      return "";
    }
    return this.getLastName() + ", " + this.getFirstName() + " <" + this.getEmail() + ">";
  }

  @Transient
  public String getComposedNameWithoutEmail() {
    if (this.getId() == null || this.getId() == -1) {
      return "";
    }
    return this.getLastName() + ", " + this.getFirstName();
  }

  public String getEmail() {
    return this.email;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public Date getLastLogin() {
    return this.lastLogin;
  }

  public String getLastName() {
    return this.lastName;
  }

  public String getPassword() {
    return this.password;
  }

  public String getUsername() {
    return this.username;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.getId() == null) ? 0 : this.getId().hashCode());
    return result;
  }

  public boolean isAutoSave() {
    return autoSave;
  }

  public boolean isCgiarUser() {
    return cgiarUser;
  }


  public void setAgreeTerms(Boolean agreeTerms) {
    this.agreeTerms = agreeTerms;
  }

  public void setAutoSave(boolean autoSave) {
    this.autoSave = autoSave;
  }

  public void setCgiarUser(boolean cgiarUser) {
    this.cgiarUser = cgiarUser;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "User [id=" + this.getId() + ", firstName=" + firstName + ", lastName=" + lastName + ", username=" + username
      + ", email=" + email + ", password=" + password + ", cgiarUser=" + cgiarUser + ", isActive=" + this.getActive()
      + ", lastLogin=" + lastLogin + "]";
  }
}