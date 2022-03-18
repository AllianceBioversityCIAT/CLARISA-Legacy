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

import org.cgiar.clarisa.dto.InstitutionTypeDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.InstitutionTypeManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.InstitutionTypeMapper;
import org.cgiar.clarisa.model.InstitutionType;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/institution-type")
public class InstitutionTypeController extends GenericController<InstitutionType, InstitutionTypeDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();


  // Manager
  private InstitutionTypeManager manager;

  // Mapper
  private InstitutionTypeMapper mapper;

  @Inject
  public InstitutionTypeController(InstitutionTypeManager manager, InstitutionTypeMapper mapper) {
    super(InstitutionType.class);
    this.manager = manager;
    this.mapper = mapper;
  }

  @Override
  public Logger getClassLogger() {
    return InstitutionTypeController.LOG;
  }

  @Override
  public GenericManager<InstitutionType> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<InstitutionType, InstitutionTypeDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

}
