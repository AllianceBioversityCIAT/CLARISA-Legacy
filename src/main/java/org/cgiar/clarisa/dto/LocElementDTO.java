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

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.clarisa.dto;


public class LocElementDTO extends SimpleDTO {

  private String isoAlpha2;


  private String isoAlpha3;


  private String isoNumeric;


  public String getIsoAlpha2() {
    return isoAlpha2;
  }


  public String getIsoAlpha3() {
    return isoAlpha3;
  }


  public String getIsoNumeric() {
    return isoNumeric;
  }


  public void setIsoAlpha2(String isoAlpha2) {
    this.isoAlpha2 = isoAlpha2;
  }


  public void setIsoAlpha3(String isoAlpha3) {
    this.isoAlpha3 = isoAlpha3;
  }


  public void setIsoNumeric(String isoNumeric) {
    this.isoNumeric = isoNumeric;
  }

}
