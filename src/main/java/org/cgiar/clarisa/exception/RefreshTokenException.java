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

public class RefreshTokenException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 4311586124225844509L;

  public static final String TOKEN_EXPIRED = "Refresh token %s is expired. Please make a new log-in request!";
  public static final String TOKEN_NOT_FOUND = "Refresh token %s does not exist!";

  public RefreshTokenException() {
    super();
  }

  public RefreshTokenException(String token) {
    this(TOKEN_EXPIRED, token);
  }

  public RefreshTokenException(String message, String token) {
    super(String.format(message, token));
  }

  public RefreshTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public RefreshTokenException(Throwable cause) {
    super(cause);
  }
}
