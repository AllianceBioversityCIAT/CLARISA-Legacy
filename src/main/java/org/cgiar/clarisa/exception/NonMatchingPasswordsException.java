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
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class NonMatchingPasswordsException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -8766222977332770298L;

  public static final String WRONG_PASSWORD = "The old password for the user %s is not correct!";

  public NonMatchingPasswordsException() {
    super();
  }

  public NonMatchingPasswordsException(String username) {
    super(String.format(WRONG_PASSWORD, username));
  }
}
