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

import org.cgiar.clarisa.dto.GlobalUnitDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.GlobalUnitManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.GlobalUnitMapper;
import org.cgiar.clarisa.model.GlobalUnit;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/globalUnits")
@CrossOrigin(origins = {"http://localhost:4200", "http://127.0.0.1:4200"})
public class GlobalUnitController extends GenericController<GlobalUnit, GlobalUnitDTO> {


  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(GlobalUnitController.class);

  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();


  // Manager
  private GlobalUnitManager manager;


  // Mapper
  private GlobalUnitMapper mapper;

  @Inject
  public GlobalUnitController(GlobalUnitManager manager, GlobalUnitMapper mapper) {
    super(GlobalUnit.class);
    this.manager = manager;
    this.mapper = mapper;
  }


  @Override
  public Logger getClassLogger() {

    return GlobalUnitController.LOG;
  }

  @Override
  public GenericManager<GlobalUnit> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<GlobalUnit, GlobalUnitDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {

    return this.objectMapper;
  }

}
