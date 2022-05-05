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

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Entity
@Table(name = "clarisa_auditlog")
@NamedQuery(name = "ClarisaAuditlog.findAll", query = "SELECT ca FROM ClarisaAuditlog ca")
public class ClarisaAuditlog extends ClarisaBaseEntity {

  /**
   * 
   */
  private static final long serialVersionUID = 6107910815473323684L;

  private String endpoint;
  private String httpMethod;
  private Long entityId;
  private String entityTable;
  private String previousJson;
  private String updatedJson;
  private Date requestDate;
  private User requestedBy;
  private String modificationJustification;
  private boolean wasSuccessful;


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
    ClarisaAuditlog other = (ClarisaAuditlog) obj;
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
  public String getEndpoint() {
    return endpoint;
  }

  @Column(name = "entity_id")
  public Long getEntityId() {
    return entityId;
  }

  @Column(name = "entity_table")
  public String getEntityTable() {
    return entityTable;
  }

  @Column(name = "http_method")
  public String getHttpMethod() {
    return httpMethod;
  }

  @Column(name = "modification_justification")
  public String getModificationJustification() {
    return modificationJustification;
  }

  @Column(name = "previous_json")
  public String getPreviousJson() {
    return previousJson;
  }

  @Column(name = "request_date")
  public Date getRequestDate() {
    return requestDate;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "requested_by")
  public User getRequestedBy() {
    return requestedBy;
  }

  @Column(name = "updated_json")
  public String getUpdatedJson() {
    return updatedJson;
  }

  @Column(name = "was_successful")
  public boolean getWasSuccessful() {
    return wasSuccessful;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public void setEntityTable(String entityTable) {
    this.entityTable = entityTable;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setPreviousJson(String previousJson) {
    this.previousJson = previousJson;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  public void setRequestedBy(User requestedBy) {
    this.requestedBy = requestedBy;
  }

  public void setUpdatedJson(String updatedJson) {
    this.updatedJson = updatedJson;
  }

  public void setWasSuccessful(boolean wasSuccessful) {
    this.wasSuccessful = wasSuccessful;
  }

  @Override
  public String toString() {
    return "ClarisaAuditlog [id=" + this.getId() + ", endpoint=" + this.endpoint + ", httpMethod=" + this.httpMethod
      + ", entityId=" + this.entityId + ", entityTable=" + this.entityTable + ", requestDate=" + this.requestDate
      + ", modificationJustification=" + this.modificationJustification + ", wasSuccessful=" + this.wasSuccessful + "]";
  }
}
