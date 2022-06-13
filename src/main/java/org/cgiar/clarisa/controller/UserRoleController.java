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

import org.cgiar.clarisa.dto.RoleDTO;
import org.cgiar.clarisa.dto.UserRoleDTO;
import org.cgiar.clarisa.exception.EntityNotFoundException;
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.manager.RoleManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.RoleMapper;
import org.cgiar.clarisa.model.Role;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.model.UserRole;
import org.cgiar.clarisa.utils.AppConstants;
import org.cgiar.clarisa.utils.GeneralUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {

  private final Class<UserRole> entityClass = UserRole.class;

  private UserManager userManager;
  private RoleManager roleManager;
  private ClarisaAuditlogManager clarisaAuditlogManager;

  private RoleMapper roleMapper;

  @Inject
  public UserRoleController(UserManager userManager, RoleManager roleManager, RoleMapper roleMapper,
    ClarisaAuditlogManager clarisaAuditlogManager) {
    this.userManager = userManager;
    this.roleMapper = roleMapper;
    this.roleManager = roleManager;
    this.clarisaAuditlogManager = clarisaAuditlogManager;
  }

  protected void addEntityClassToRequest(HttpServletRequest request, Class<UserRole> clazz) {
    request.setAttribute(AppConstants.HTTP_ENTITY_CLASS_NAME, clazz);
  }

  @DeleteMapping(value = "/delete")
  public void delete(@RequestBody UserRoleDTO dto, HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    User userToBeModified = this.userManager.findById(dto.getUserId())
      .orElseThrow(() -> new EntityNotFoundException(User.class, dto.getUserId()));
    Role toBeRemoved = this.roleManager.findById(dto.getRoleId())
      .orElseThrow(() -> new EntityNotFoundException(Role.class, dto.getRoleId()));
    List<Role> userRoles = GeneralUtils.emptyIfNull(userToBeModified.getUserRoles());
    userRoles.remove(toBeRemoved);
    userToBeModified.setUserRoles(userRoles);
    this.userManager.update(userToBeModified);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, dto, null, dto.getRoleId(), true);
  }

  @GetMapping(value = "/by-user/{userId}")
  public ResponseEntity<List<RoleDTO>> findRolesByUser(@PathVariable("userId") Long userId, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    List<Role> resultList = this.userManager.findById(userId).map(User::getUserRoles).orElse(Collections.emptyList());
    return ResponseEntity.ok(this.roleMapper.entityListToDtoList(resultList));
  }

  @PostMapping(value = "/save")
  public ResponseEntity<UserRoleDTO> save(@RequestBody UserRoleDTO dto, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    User userToBeModified = this.userManager.findById(dto.getUserId())
      .orElseThrow(() -> new EntityNotFoundException(User.class, dto.getUserId()));
    Role toBeAdded = this.roleManager.findById(dto.getRoleId())
      .orElseThrow(() -> new EntityNotFoundException(Role.class, dto.getRoleId()));
    List<Role> userRoles = GeneralUtils.emptyIfNull(userToBeModified.getUserRoles());
    userRoles.add(toBeAdded);
    userToBeModified.setUserRoles(userRoles);
    this.userManager.update(userToBeModified);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, dto, null, dto.getRoleId(), true);
    return ResponseEntity.ok(dto);
  }
}
