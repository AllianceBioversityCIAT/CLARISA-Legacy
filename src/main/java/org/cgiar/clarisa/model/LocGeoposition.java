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

package org.cgiar.clarisa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "loc_geopositions")
@NamedQuery(name = "LocGeoposition.findAll", query = "SELECT locgeo FROM LocGeoposition locgeo")
public class LocGeoposition extends ClarisaBaseEntity implements java.io.Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Double latitude;

  private Double longitude;

  private LocGeoposition locGeoposition;


  @Column
  public Double getLatitude() {
    return latitude;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public LocGeoposition getLocGeoposition() {
    return locGeoposition;
  }

  @Column
  public Double getLongitude() {
    return longitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }


  public void setLocGeoposition(LocGeoposition locGeoposition) {
    this.locGeoposition = locGeoposition;
  }


  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

}
