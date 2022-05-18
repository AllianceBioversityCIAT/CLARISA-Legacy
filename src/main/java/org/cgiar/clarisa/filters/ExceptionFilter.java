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

import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.utils.auth.AuthHolder;

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
public class ExceptionFilter extends OncePerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(ExceptionFilter.class);

  @Inject
  private ClarisaAuditlogManager clarisaAuditlogManager;

  @Inject
  private AuthHolder authHolder;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
    filterChain.doFilter(request, response);
    /*
     * try {
     * filterChain.doFilter(request, response);
     * } catch (Exception e) {
     * if (e instanceof NestedServletException) {
     * Throwable cause = e.getCause();
     * if (cause instanceof RuntimeException) {
     * @SuppressWarnings({"unchecked"})
     * Class<? extends ClarisaBaseEntity> clazz =
     * (Class<? extends ClarisaBaseEntity>) request.getAttribute(AppConstants.HTTP_ENTITY_CLASS_NAME);
     * String httpMethod = request.getMethod();
     * HttpMethod method = HttpMethod.resolve(httpMethod);
     * // Gets the request body as a JSON for DELETE, PUT and POST request methods
     * Map<?, ?> requestAsJson = null;
     * switch (method) {
     * case POST:
     * case DELETE:
     * case PUT:
     * case PATCH:
     * try (Reader requestBody = request.getReader()) {
     * requestAsJson = new Gson().fromJson(requestBody, Map.class);
     * } catch (IOException e1) {
     * LOG.debug(e1.getMessage());
     * throw new UncheckedIOException(e1);
     * }
     * break;
     * default:
     * break;
     * }
     * LOG.info("an exception has occurred: {}", e.getMessage());
     * this.clarisaAuditlogManager.registerAuditlog(request, authHolder.getCurrentUser(), clazz, requestAsJson, null,
     * null, null, false, e);
     * }
     * throw e;
     * }
     * }
     */
  }
}