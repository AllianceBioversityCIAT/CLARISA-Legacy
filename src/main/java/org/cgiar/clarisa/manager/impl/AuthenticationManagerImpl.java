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
import org.cgiar.clarisa.manager.AuthenticationManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.LoginStatus;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Service
@Scope("singleton")
public class AuthenticationManagerImpl implements AuthenticationManager {

  private UserManager userManager;
  private AppConfig appConfig;

  @Inject
  public AuthenticationManagerImpl(UserManager userManager, AppConfig appConfig) {
    super();
    this.userManager = userManager;
    this.appConfig = appConfig;
  }

  @Override
  public LoginStatus verifyCredentials(String username, String password) {
    LoginStatus loginStatus = LoginStatus.NOT_LOGGED;
    Optional<User> userOptional = this.userManager.getUserByUsername(username);
    if (userOptional.isPresent()) {
      BCryptPasswordEncoder encoder = appConfig.getContext().getBean(BCryptPasswordEncoder.class);
      if (encoder.matches(password, userOptional.get().getPassword())) {
        loginStatus = LoginStatus.LOGGED_SUCCESSFULLY;
      } else {
        loginStatus = LoginStatus.WRONG_CREDENTIALS;
      }
    } else {
      loginStatus = LoginStatus.WRONG_CREDENTIALS;
    }

    return loginStatus;
  }

}
