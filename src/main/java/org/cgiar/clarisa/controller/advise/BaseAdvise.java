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
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.utils.AppConstants;
import org.cgiar.clarisa.utils.MultiBodyReadHttpServletRequest;
import org.cgiar.clarisa.utils.auth.AuthHolder;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public abstract class BaseAdvise<T, E extends RuntimeException> {

  private static final Logger LOG = LoggerFactory.getLogger(BaseAdvise.class);

  @Inject
  private ClarisaAuditlogManager clarisaAuditlogManager;

  @Inject
  private AuthHolder authHolder;

  protected String getRequestedUrl(HttpServletRequest request) {
    String url = request.getServletPath();
    String queryString = request.getQueryString();
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(url);
    if (queryString != null) {
      stringBuilder.append("?").append(queryString);
    }

    return stringBuilder.toString();
  }

  abstract APIErrorDTO<T> handleException(E ex, HttpServletRequest request);

  protected void log(APIErrorDTO<T> apiError, E ex, HttpServletRequest request) {
    @SuppressWarnings({"unchecked"})
    Class<? extends ClarisaBaseEntity> clazz =
      (Class<? extends ClarisaBaseEntity>) request.getAttribute(AppConstants.HTTP_ENTITY_CLASS_NAME);
    String httpMethod = request.getMethod();
    HttpMethod method = HttpMethod.resolve(httpMethod);
    // Gets the request body as a JSON for DELETE, PUT and POST request methods
    Map<?, ?> requestAsJson = null;
    switch (method) {
      case POST:
      case DELETE:
      case PUT:
      case PATCH:
        try (Reader requestBody = new MultiBodyReadHttpServletRequest(request).getReader()) {
          requestAsJson = new Gson().fromJson(requestBody, Map.class);
        } catch (IOException e1) {
          LOG.debug(e1.getMessage());
          throw new UncheckedIOException(e1);
        }
        break;
      default:
        break;
    }
    LOG.info("an exception has occurred: {}", ex.getMessage());
    this.clarisaAuditlogManager.registerAuditlog(request, authHolder.getCurrentUser(), clazz, requestAsJson, null, null,
      null, false, ex);
  }
}

