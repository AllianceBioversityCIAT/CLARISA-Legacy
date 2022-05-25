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

package org.cgiar.clarisa.controller;

import org.cgiar.clarisa.config.AppConfig;
import org.cgiar.clarisa.config.LegacyPasswordEncoder;
import org.cgiar.clarisa.dto.PasswordChangeDTO;
import org.cgiar.clarisa.exception.UserNotFoundException;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.model.User;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@RestController
@RequestMapping("/admin")
public class AdminController {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(AdminController.class);

  // private LDAPUtils ldapUtils;
  private AppConfig appConfig;
  private UserManager userManager;

  @Inject
  public AdminController(/* LDAPUtils ldapUtils, */AppConfig appConfig, UserManager userManager) {
    // this.ldapUtils = ldapUtils;
    this.appConfig = appConfig;
    this.userManager = userManager;
  }

  @PostMapping(value = "/passwordChange")
  public ResponseEntity<Boolean> userChangePassword(@RequestBody PasswordChangeDTO passwordChangeDTO) {
    if (passwordChangeDTO == null || StringUtils.isEmpty(passwordChangeDTO.getNewPassword())
      || StringUtils.isEmpty(passwordChangeDTO.getUsername())) {
      return ResponseEntity.badRequest().body(false);
    }

    User previousUser = this.userManager.getUserByUsername(passwordChangeDTO.getUsername())
      .orElseThrow(() -> new UserNotFoundException());

    LegacyPasswordEncoder encoder = appConfig.getContext().getBean(LegacyPasswordEncoder.class);
    String newPassword = encoder.encode(passwordChangeDTO.getNewPassword());

    this.userManager.changePassword(newPassword, previousUser.getUsername());

    return ResponseEntity.ok(true);
  }
}
