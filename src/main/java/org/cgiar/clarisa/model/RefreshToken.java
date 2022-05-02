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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.google.gson.annotations.Expose;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Entity
@Table(name = "refresh_tokens")
@NamedQuery(name = "RefreshToken.findAll", query = "SELECT rt FROM RefreshToken rt")
public class RefreshToken extends ClarisaBaseEntity implements java.io.Serializable {

  private static final long serialVersionUID = 1766253272111917794L;

  @Expose
  private String token;

  @Expose
  private Date expirationDate;

  @Expose
  private User user;

  public RefreshToken() {
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
    RefreshToken other = (RefreshToken) obj;
    if (this.getId() == null) {
      if (other.getId() != null) {
        return false;
      }
    } else if (!this.getId().equals(other.getId())) {
      return false;
    }
    return true;
  }

  @Column(name = "expiration_date")
  public Date getExpirationDate() {
    return expirationDate;
  }

  @Column
  public String getToken() {
    return token;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User getUser() {
    return user;
  }


  public void setExpirationDate(Date expirationDate) {
    this.expirationDate = expirationDate;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "RefreshToken [id=" + this.getId() + ", token=" + token + ", expirationDate=" + expirationDate + "]";
  }

}
