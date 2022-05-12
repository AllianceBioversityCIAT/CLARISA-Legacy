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

package org.cgiar.clarisa.manager.impl;

import org.cgiar.clarisa.dao.ClarisaAuditlogDAO;
import org.cgiar.clarisa.dto.BaseDTO;
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.ClarisaAuditlog;
import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.HibernateTableNameResolver;

import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.MappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpMethod;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Service
@Scope("singleton")
public class ClarisaAuditlogManagerImpl implements ClarisaAuditlogManager {

  private static final Logger LOG = LoggerFactory.getLogger(ClarisaAuditlogManagerImpl.class);

  // DAOs
  private ClarisaAuditlogDAO clarisaAuditlogDAO;

  // Managers
  @Inject
  protected UserManager userManager;

  // Variables
  @Inject
  protected HibernateTableNameResolver tableNameResolver;
  private Gson gson;

  @Autowired
  public ClarisaAuditlogManagerImpl(ClarisaAuditlogDAO clarisaAuditlogDAO,
    HibernateTableNameResolver tableNameResolver) {
    super();
    this.clarisaAuditlogDAO = clarisaAuditlogDAO;
    this.tableNameResolver = tableNameResolver;
    this.gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
  }

  @Override
  public JpaRepository<ClarisaAuditlog, Long> getDAO() {
    return clarisaAuditlogDAO;
  }

  @Override
  public <ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> ClarisaAuditlog registerAuditlog(
    HttpServletRequest request, User user, Class<ENTITY> entityClazz, Object incoming, DTO previous, DTO updated,
    Long id, boolean successful, Exception failingCause) {


    // Get the Http request parameters
    Enumeration<String> enumeration = request.getParameterNames();
    while (enumeration.hasMoreElements()) {
      String param = request.getParameter(enumeration.nextElement().toString());
      if (param != null) {
        // TODO try to get all the parameters (POST) specially the JSON structure
      }
    }

    // Get the HTTP method with which this request was made, for example, GET, POST, or PUT.
    String httpMethod = request.getMethod();
    HttpMethod method = HttpMethod.resolve(httpMethod);

    // Gets the request body as a JSON for DELETE, PUT and POST request methods
    /*
     * String requestAsJson = null;
     * switch (method) {
     * case POST:
     * case DELETE:
     * case PUT:
     * case PATCH:
     * try (Reader requestBody = new ContentCachingRequestWrapper(request).getReader()) {
     * requestAsJson = new Gson().fromJson(requestBody, new TypeToken<Map<String, String>>() {
     * }.getType());
     * } catch (IOException e1) {
     * LOG.debug(e1.getMessage());
     * throw new UncheckedIOException(e1);
     * }
     * break;
     * default:
     * break;
     * }
     */

    // Get the user
    Optional<User> userOptional = Optional.ofNullable(user).flatMap(u -> userManager.findById(u.getId()));

    String dbTableName = null;
    try {
      dbTableName = this.tableNameResolver.extractTableName(entityClazz);
    } catch (Exception e) {
      if (!(e instanceof IllegalArgumentException || e instanceof MappingException
        || e instanceof JpaSystemException)) {
        // not related to the table name extraction, nothing we can do
        throw e;
      }
    }

    // New clarisa monitoring Object to add in the database
    ClarisaAuditlog monitoring = new ClarisaAuditlog();

    monitoring.setEndpoint(request.getRequestURL().toString());
    monitoring.setEntityId(id != null ? id : (previous != null ? previous.getId() : null));
    monitoring.setEntityTable(dbTableName);
    monitoring.setHttpMethod(httpMethod);
    monitoring.setIncomingJson(incoming != null ? gson.toJson(incoming) : null);
    monitoring.setPreviousJson(previous != null ? gson.toJson(previous) : null);
    monitoring.setRequestDate(new Date());
    monitoring.setRequestedBy(userOptional.orElse(null));
    monitoring.setUpdatedJson(updated != null ? gson.toJson(updated, entityClazz) : null);

    monitoring.setWasSuccessful(successful);
    monitoring
      .setFailingCause(successful ? (failingCause != null ? ExceptionUtils.getStackTrace(failingCause) : null) : null);

    // Save the information to Clarisa Monitoring Table
    monitoring = this.save(monitoring);

    return monitoring;
  }

}
