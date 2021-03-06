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


public class GlobalUnitDTO extends SimpleDTO {

  private String name;

  private String acronym;

  private String officialCode;


  public String getAcronym() {
    return acronym;
  }


  @Override
  public String getName() {
    return name;
  }


  public String getOfficialCode() {
    return officialCode;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  @Override
  public void setName(String name) {
    this.name = name;
  }


  public void setOfficialCode(String officialCode) {
    this.officialCode = officialCode;
  }

}
