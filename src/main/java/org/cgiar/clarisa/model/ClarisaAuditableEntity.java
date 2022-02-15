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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.google.gson.annotations.Expose;

/**
 * An abstract class that standardizes how the Audit columns are handled in CLARISA.
 * 
 * @author GrantL
 */
@MappedSuperclass
public abstract class ClarisaAuditableEntity extends SoftDeleteableEntity implements Serializable {

  private static final long serialVersionUID = 9094997494834647630L;

  @Expose
  private User createdBy;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;

  @Expose
  private User modifiedBy;

  @Column(name = "active_since", insertable = false, updatable = false)
  public Date getActiveSince() {
    return this.activeSince;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "created_by")
  public User getCreatedBy() {
    return this.createdBy;
  }

  @Column(name = "modification_justification")
  public String getModificationJustification() {
    return this.modificationJustification;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modified_by")
  public User getModifiedBy() {
    return this.modifiedBy;
  }


  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince == null ? new Date() : activeSince;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }
}