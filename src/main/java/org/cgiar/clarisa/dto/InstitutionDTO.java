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

package org.cgiar.clarisa.dto;

import java.util.List;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class InstitutionDTO extends SimpleDTO {

  private InstitutionTypeDTO institutionType;
  private String acronym;
  private String websiteLink;

  private List<LocElementDTO> locations;


  public InstitutionDTO() {
  }


  public String getAcronym() {
    return acronym;
  }


  public InstitutionTypeDTO getInstitutionType() {
    return institutionType;
  }


  public List<LocElementDTO> getLocations() {
    return locations;
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

  public void setLocations(List<LocElementDTO> locations) {
    this.locations = locations;
  }

  public void setWebsiteLink(String websiteLink) {
    this.websiteLink = websiteLink;
  }
}
