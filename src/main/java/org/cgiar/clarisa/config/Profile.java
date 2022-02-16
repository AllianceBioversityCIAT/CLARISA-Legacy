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

package org.cgiar.clarisa.config;

import org.apache.commons.lang3.StringUtils;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public enum Profile {

  SPRING_PROFILE_DEVELOPMENT("dev"), SPRING_PROFILE_LOCAL("local"), SPRING_PROFILE_PRODUCTION("prod");

  public static Profile getFromName(String name) {
    for (Profile profile : Profile.values()) {
      if (StringUtils.equalsIgnoreCase(name, profile.getName())) {
        return profile;
      }
    }

    return null;
  }

  private final String name;

  private Profile(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
