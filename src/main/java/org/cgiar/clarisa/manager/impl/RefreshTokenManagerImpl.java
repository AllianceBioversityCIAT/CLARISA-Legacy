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

import org.cgiar.clarisa.config.AppConfig;
import org.cgiar.clarisa.dao.RefreshTokenDAO;
import org.cgiar.clarisa.exception.RefreshTokenException;
import org.cgiar.clarisa.manager.RefreshTokenManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.RefreshToken;
import org.cgiar.clarisa.model.User;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Service
@Scope("singleton")
public class RefreshTokenManagerImpl implements RefreshTokenManager {

  private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenManagerImpl.class);

  // DAOs
  private RefreshTokenDAO refreshTokenDAO;

  // Managers
  private UserManager userManager;

  // Variables
  private AppConfig appConfig;

  @Autowired
  public RefreshTokenManagerImpl(RefreshTokenDAO refreshTokenDAO, UserManager userManager, AppConfig appConfig) {
    super();
    this.refreshTokenDAO = refreshTokenDAO;
    this.userManager = userManager;
    this.appConfig = appConfig;
  }

  @Override
  public Optional<RefreshToken> findFromToken(String token) {
    return this.refreshTokenDAO.findFromToken(token);
  }

  @Override
  public RefreshToken generateTokenForUser(User user) {
    long currentTime = System.currentTimeMillis();
    RefreshToken refreshToken = new RefreshToken();

    refreshToken.setUser(user);
    refreshToken.setExpirationDate(new Date(currentTime + appConfig.getRefreshJwtExpirationTime()));
    refreshToken.setToken(UUID.randomUUID().toString());

    refreshToken = this.refreshTokenDAO.save(refreshToken);

    return refreshToken;
  }

  @Override
  public JpaRepository<RefreshToken, Long> getDAO() {
    return this.refreshTokenDAO;
  }

  @Override
  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpirationDate().compareTo(new Date()) < 0) {
      this.refreshTokenDAO.delete(token);
      throw new RefreshTokenException(token.getToken());
    }

    return token;
  }

}
