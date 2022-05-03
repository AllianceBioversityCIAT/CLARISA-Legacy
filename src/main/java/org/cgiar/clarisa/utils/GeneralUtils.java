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

  /**
   * Returns an immutable empty list if the argument is null, or the argument itself otherwise.
   * Please remember, as the list returned if the argument is null is immutable, do not use if you need to add elements
   * to it, as you will get an {@link UnsupportedOperationException}
   * 
   * @param <T> the list type
   * @param list the list to be checked
   * @return the argument itself if it is not null; {@link Collections#emptyList()} otherwise
   */
  public static <T> List<T> emptyIfNull(List<T> list) {
    return list != null ? list : Collections.emptyList();
  }

  /**
   * Returns an immutable empty set if the argument is null, or the argument itself otherwise.
   * Please remember, as the set returned if the argument is null is immutable, do not use if you need to add elements
   * to it, as you will get an {@link UnsupportedOperationException}.
   * 
   * @param <T> the set type
   * @param set the set to be checked
   * @return the argument itself if it is not null; {@link Collections#emptySet()} otherwise
   */
  public static <T> Set<T> emptyIfNull(Set<T> set) {
    return set != null ? set : Collections.emptySet();
  }

  /**
   * Returns an empty array if the argument is null, or the argument itself otherwise.
   * 
   * @param <T> the array type
   * @param array the array to be checked
   * @return the argument itself if it is not null; an empty array otherwise
   */
  public static <T> T[] emptyIfNull(T[] array) {
    return array != null ? array : (T[]) new Object[0];
  }

  /**
   * Checks if the {@code list} argument is empty. It takes care of null arguments by returning {@code true}
   * 
   * @param <T> the list type
   * @param list the list to be checked
   * @return {@code true} if the list is null or empty; {@code false} otherwise
   */
  public static <T> boolean isEmpty(List<T> list) {
    return list == null ? true : list.isEmpty();
  }

  /**
   * Checks if the {@code set} argument is empty. It takes care of null arguments by returning {@code true}
   * 
   * @param <T> the set type
   * @param set the set to be checked
   * @return {@code true} if the set is null or empty; {@code false} otherwise
   */
  public static <T> boolean isEmpty(Set<T> set) {
    return set == null ? true : set.isEmpty();
  }

  /**
   * Checks if the {@code array} argument is empty. It takes care of null arguments by returning {@code true}
   * 
   * @param <T> the array type
   * @param array the array to be checked
   * @return {@code true} if the array is null or empty; {@code false} otherwise
   */
  public static <T> boolean isEmpty(T[] array) {
    return array == null ? true : array.length == 0;
  }

  /*
   * Null-safe way to get an object
   * @param <T> the object class
   * @param object the object
   * @param defaultObject the default object to be returned if the object is null
   * @return object if not null, else defaultObject
   * public static <T> T getOrElse(T object, T defaultObject) {
   * return object != null ? object : defaultObject;
   * }
   */

}
