/*****************************************************************
 * This file is part of CGIAR Level Agricultural Results
 * Interoperable System Architecture Platform (CLARISA).
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

/**************
 * @author Diego Perez - Alliance Bioversity/CIAT
 **************/

package org.cgiar.clarisa.controller;

import org.cgiar.clarisa.dto.PermissionDTO;
import org.cgiar.clarisa.dto.RoleDTO;
import org.cgiar.clarisa.dto.RolePermissionDTO;
import org.cgiar.clarisa.exception.UserNotFoundException;
import org.cgiar.clarisa.manager.RoleManager;
import org.cgiar.clarisa.manager.UserManager;
import org.cgiar.clarisa.mapper.PermissionMapper;
import org.cgiar.clarisa.mapper.RoleMapper;
import org.cgiar.clarisa.model.Permission;
import org.cgiar.clarisa.model.Role;
import org.cgiar.clarisa.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rolepermissions")
public class RolePermissionsController {

  private UserManager userManager;

  private RoleManager roleManager;

  private RoleMapper roleMapper;

  private PermissionMapper permissionMapper;


  @Inject
  public RolePermissionsController(UserManager userManager, RoleManager roleManager, PermissionMapper permissionMapper,
    RoleMapper roleMapper) {
    super();
    this.userManager = userManager;
    this.roleManager = roleManager;
    this.permissionMapper = permissionMapper;
    this.roleMapper = roleMapper;
  }


  @GetMapping(value = "/rolePermission/{username}")
  public ResponseEntity<List<RolePermissionDTO>> getRolePermission(@PathVariable("username") String username) {
    List<RolePermissionDTO> rolePermissionDTO = new ArrayList<>();
    User user = userManager.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    if (user != null) {
      List<Role> roles = user.getUserRoles();
      for (Role role : roles) {
        Optional<Role> objRole = roleManager.findById(role.getId());
        RolePermissionDTO rolePermission = new RolePermissionDTO();
        RoleDTO roleDTO = roleMapper.entityToDto(objRole.get());
        rolePermission.setRole(roleDTO);
        List<Permission> permissions = role.getRolePermissions();
        if (!permissions.isEmpty()) {
          List<PermissionDTO> permissionsListDTO = permissionMapper.entityListToDtoList(permissions);
          rolePermission.setPermissions(permissionsListDTO);
        }
        rolePermissionDTO.add(rolePermission);
      }
    }
    return ResponseEntity.ok(rolePermissionDTO);
  }

}
