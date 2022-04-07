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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "global_units")
@NamedQuery(name = "GlobalUnit.findAll", query = "SELECT g FROM GlobalUnit g")
public class GlobalUnit extends SoftDeleteableEntity implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private GlobalUnitType globalUnitType;


  private String name;


  private String acronym;


  private boolean login;


  private String smoCode;


  private Institution institution;


  private String financialCode;

  @Column
  public String getAcronym() {
    return acronym;
  }

  @Column(name = "financial_code")
  public String getFinancialCode() {
    return financialCode;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "global_unit_type_id")
  public GlobalUnitType getGlobalUnitType() {
    return globalUnitType;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "institution_id")
  public Institution getInstitution() {
    return institution;
  }

  @Column
  public String getName() {
    return name;
  }

  @Column(name = "smo_code")
  public String getSmoCode() {
    return smoCode;
  }

  @Column(name = "login")
  public Boolean isLogin() {
    return login;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setFinancialCode(String financialCode) {
    this.financialCode = financialCode;
  }

  public void setGlobalUnitType(GlobalUnitType globalUnitType) {
    this.globalUnitType = globalUnitType;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setLogin(boolean login) {
    this.login = login;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSmoCode(String smoCode) {
    this.smoCode = smoCode;
  }

}
