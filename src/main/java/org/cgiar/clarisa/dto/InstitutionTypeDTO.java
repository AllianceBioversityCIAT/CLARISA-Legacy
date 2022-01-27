/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.clarisa.dto;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public class InstitutionTypeDTO extends BaseDTO {

  private RepIndOrganizationTypeDTO organizationTypeDTO;
  private String name;
  private String acronym;
  private Boolean subDepartmentActive;


  public InstitutionTypeDTO() {
  }


  public String getAcronym() {
    return acronym;
  }

  public String getName() {
    return name;
  }

  public RepIndOrganizationTypeDTO getOrganizationTypeDTO() {
    return organizationTypeDTO;
  }

  public Boolean getSubDepartmentActive() {
    return subDepartmentActive;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setOrganizationTypeDTO(RepIndOrganizationTypeDTO organizationTypeDTO) {
    this.organizationTypeDTO = organizationTypeDTO;
  }

  public void setSubDepartmentActive(Boolean subDepartmentActive) {
    this.subDepartmentActive = subDepartmentActive;
  }
}
