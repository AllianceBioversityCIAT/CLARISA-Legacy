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

package org.cgiar.clarisa.exception;


/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public class EntityNotFoundException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -6938852241310730779L;

  public static final String CLASS_ENTITY_NOT_FOUND = "%s with id %s could not be found on the database!";
  public static final String ENTITY_NOT_FOUND = "Entity with id %s could not be found in the database!";

  public EntityNotFoundException() {
    super();
  }

  public EntityNotFoundException(Class<?> clazz, Long id) {
    this(String.format(CLASS_ENTITY_NOT_FOUND, clazz.getSimpleName(), id));
  }

  public EntityNotFoundException(String message) {
    super(message);
  }

  public EntityNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public EntityNotFoundException(Throwable cause) {
    super(cause);
  }

}
