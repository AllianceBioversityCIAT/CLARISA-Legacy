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
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.PermissionManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.PermissionMapper;
import org.cgiar.clarisa.model.Permission;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permissions")
public class PermissionController extends GenericController<Permission, PermissionDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PermissionController.class);


  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();

  // Manager
  private PermissionManager manager;


  // Mapper
  private PermissionMapper mapper;

  @Inject
  public PermissionController(PermissionManager manager, PermissionMapper mapper) {
    super(Permission.class);
    this.manager = manager;
    this.mapper = mapper;
  }

  @Override
  public Logger getClassLogger() {
    return PermissionController.LOG;
  }

  @Override
  public GenericManager<Permission> getManager() {

    return this.manager;
  }

  @Override
  public BaseMapper<Permission, PermissionDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

}
