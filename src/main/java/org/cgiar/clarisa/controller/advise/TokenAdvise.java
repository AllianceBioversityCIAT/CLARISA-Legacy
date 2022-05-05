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


import org.cgiar.clarisa.dto.APIErrorDTO;
import org.cgiar.clarisa.exception.RefreshTokenException;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class TokenAdvise {

  @ResponseBody
  @ExceptionHandler(value = RefreshTokenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  APIErrorDTO<String> handleTokenRefreshException(RefreshTokenException rte, WebRequest request) {
    return new APIErrorDTO<String>(HttpStatus.FORBIDDEN.value(), new Date(), rte.getMessage(),
      request.getDescription(true));
  }
}
