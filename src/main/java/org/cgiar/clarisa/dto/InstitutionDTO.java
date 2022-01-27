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

public class InstitutionDTO extends SimpleDTO {

  private InstitutionTypeDTO institutionType;
  private String acronym;
  private String websiteLink;


  public InstitutionDTO() {
  }


  public String getAcronym() {
    return acronym;
  }

  public InstitutionTypeDTO getInstitutionType() {
    return institutionType;
  }

  public String getWebsiteLink() {
    return websiteLink;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setInstitutionType(InstitutionTypeDTO institutionTypeDTO) {
    this.institutionType = institutionTypeDTO;
  }

  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }
}
