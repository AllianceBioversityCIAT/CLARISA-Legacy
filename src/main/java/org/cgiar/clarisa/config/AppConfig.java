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

package org.cgiar.clarisa.config;

import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Named
public class AppConfig {

  // Logging.
  private static final Logger LOG = LoggerFactory.getLogger(AppConfig.class);

  @Autowired
  private ApplicationContext context;

  @Value("${spring.profiles.active}")
  private String profile;

  @Value("${bcrypt.rounds}")
  private Integer bcryptRounds;

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.access.validFor}")
  private Long accessJwtExpirationTime;

  @Value("${jwt.refresh.validFor}")
  private Long refreshJwtExpirationTime;

  @Value("${application.cors.allowedUrls}")
  private String allowedUrlsCrossOrigins;

  @Value("${ldap.active}")
  private Boolean ldap;


  public Long getAccessJwtExpirationTime() {
    if (accessJwtExpirationTime == null) {
      LOG.error("There is no access JWT expiration time configured");
      return null;
    }

    return accessJwtExpirationTime;
  }

  public String getAllowedUrlsCrossOrigins() {
    if (allowedUrlsCrossOrigins == null) {
      LOG.error("There are no URL Cross-Origins configured");
      return null;
    }

    return allowedUrlsCrossOrigins;
  }

  public Integer getBcryptRounds() {
    if (bcryptRounds == null) {
      LOG.error("There is not a number of rounds configured for BCrypt");
      return null;
    }

    return bcryptRounds;
  }

  public ApplicationContext getContext() {
    return context;
  }

  public String getJwtSecret() {
    if (jwtSecret == null) {
      LOG.error("There is no JWT secret configured");
      return null;
    }

    return jwtSecret;
  }

  public Boolean getLdap() {
    return ldap;
  }

  public String getProfile() {
    if (profile == null) {
      LOG.error("There is no Spring profile configured!!!");
      return null;
    }

    return profile;
  }

  public Long getRefreshJwtExpirationTime() {
    if (refreshJwtExpirationTime == null) {
      LOG.error("There is no refresh JWT expiration time configured");
      return null;
    }

    return refreshJwtExpirationTime;
  }

  public boolean isDevelopment() {
    return StringUtils.containsIgnoreCase(this.getProfile(), Profile.SPRING_PROFILE_DEVELOPMENT.getName());
  }

  public boolean isLocal() {
    return StringUtils.containsIgnoreCase(this.getProfile(), Profile.SPRING_PROFILE_LOCAL.getName());
  }

  public boolean isProduction() {
    return StringUtils.containsIgnoreCase(this.getProfile(), Profile.SPRING_PROFILE_PRODUCTION.getName());
  }
}
