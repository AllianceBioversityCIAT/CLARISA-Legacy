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

import org.cgiar.clarisa.utils.MultiBodyReadHttpServletRequest;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Component
public class MultiReadRequestFilter extends OncePerRequestFilter {

  private static Set<String> WRAPPABLE_HTTP_METHODS = new TreeSet<String>() {

    {
      this.add(HttpMethod.POST.name());
      this.add(HttpMethod.PUT.name());
      this.add(HttpMethod.PATCH.name());
      this.add(HttpMethod.DELETE.name());
    }
  };

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    String method = StringUtils.trimToEmpty(request.getMethod());
    if (WRAPPABLE_HTTP_METHODS.contains(method)) {
      filterChain.doFilter(new MultiBodyReadHttpServletRequest(request), response);
      return;
    }

    filterChain.doFilter(request, response);
  }

}
