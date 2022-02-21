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

package org.cgiar.clarisa.controller.advise;

import org.cgiar.clarisa.exception.NonMatchingPasswordsException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@ControllerAdvice
public class WrongPasswordAdvise {

  @ResponseBody
  @ExceptionHandler(NonMatchingPasswordsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String notMatchingHandler(NonMatchingPasswordsException enfe) {
    return enfe.getMessage();
  }
}
