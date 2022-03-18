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

import org.cgiar.clarisa.dto.InstitutionDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.InstitutionManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.InstitutionMapper;
import org.cgiar.clarisa.model.Institution;

import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

@RestController
@RequestMapping("/institution")
public class InstitutionController extends GenericController<Institution, InstitutionDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(InstitutionController.class);

  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();

  // Manager
  private InstitutionManager manager;

  // Mapper
  private InstitutionMapper mapper;

  @Inject
  public InstitutionController(InstitutionManager manager, InstitutionMapper mapper) {
    super(Institution.class);
    this.manager = manager;
    this.mapper = mapper;
  }

  @GetMapping(value = "/simple")
  public ResponseEntity<List<SimpleDTO>> findAllSimple() {
    List<Institution> resultList = this.manager.findAll();
    return ResponseEntity.ok(this.mapper.entityListToSimpleDtoList(resultList));
  }

  @Override
  public Logger getClassLogger() {
    return InstitutionController.LOG;
  }

  @Override
  public GenericManager<Institution> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<Institution, InstitutionDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

}