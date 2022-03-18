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

/**************
 * @author Diego Perez - Alliance Bioversity/CIAT
 **************/

package org.cgiar.clarisa.controller;

import org.cgiar.clarisa.dto.RoleDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.RoleManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.RoleMapper;
import org.cgiar.clarisa.model.Role;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController extends GenericController<Role, RoleDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);


  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();


  // Manager
  private RoleManager manager;

  // Mapper
  private RoleMapper mapper;

  @Inject
  public RoleController(RoleManager manager, RoleMapper mapper) {
    super(Role.class);
    this.manager = manager;
    this.mapper = mapper;
  }

  @GetMapping(value = "/RoleByCGIAREntity/{cgiarEntity}")
  public ResponseEntity<List<RoleDTO>> findAllSimple(@PathVariable Long cgiarEntity) {
    List<Role> resultList = this.manager.findByGlobalUnit(cgiarEntity);
    return ResponseEntity.ok(this.mapper.entityListToDtoList(resultList));
  }

  @Override
  public Logger getClassLogger() {
    return RoleController.LOG;
  }

  @Override
  public GenericManager<Role> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<Role, RoleDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

}
