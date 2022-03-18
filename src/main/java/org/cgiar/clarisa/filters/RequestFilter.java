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

package org.cgiar.clarisa.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Component
public class RequestFilter extends OncePerRequestFilter {

  private final Logger LOG = LoggerFactory.getLogger(RequestFilter.class);

  @Inject
  public RequestFilter() {

  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {
    String url = request.getRequestURL().toString();
    String queryString = request.getQueryString();

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(url);
    if (queryString != null) {
      stringBuilder.append("?").append(queryString);
    }

    String requestUrl = stringBuilder.toString();

    LOG.debug("Finished [{}] request for resource: {}", request.getMethod(), requestUrl);

    chain.doFilter(request, response);
  }
}
