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

package org.cgiar.clarisa.utils;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class GeneralUtils {

  public static <T> List<T> emptyIfNull(List<T> list) {
    return list != null ? list : Collections.emptyList();
  }

  public static <T> Set<T> emptyIfNull(Set<T> set) {
    return set != null ? set : Collections.emptySet();
  }

  /**
   * Null-safe way to get an object
   * 
   * @param <T> the object class
   * @param object the object
   * @param defaultObject the default object to be returned if the object is null
   * @return object if not null, else defaultObject
   */
  public static <T> T getOrElse(T object, T defaultObject) {
    return object != null ? object : defaultObject;
  }

}
