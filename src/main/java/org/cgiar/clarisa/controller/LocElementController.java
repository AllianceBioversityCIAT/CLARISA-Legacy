/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

/**************
 * @author Diego Perez - CIAT/CCAFS
 **************/

package org.cgiar.clarisa.controller;


import org.cgiar.clarisa.dto.LocElementDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.LocElementManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.LocElementMapper;
import org.cgiar.clarisa.model.LocElement;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Locations")
public class LocElementController extends GenericController<LocElement, LocElementDTO> {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(LocElementController.class);

  // ObjectMapper
  private final ObjectMapper objectMapper = new ObjectMapper();

  // Manager
  private LocElementManager manager;


  // Mapper
  private LocElementMapper mapper;


  @Inject
  public LocElementController(LocElementManager manager, LocElementMapper mapper) {
    super(LocElement.class);
    this.manager = manager;
    this.mapper = mapper;
  }


  @Override
  public Logger getClassLogger() {
    return LocElementController.LOG;
  }

  @Override
  public GenericManager<LocElement> getManager() {
    return this.manager;
  }

  @Override
  public BaseMapper<LocElement, LocElementDTO> getMapper() {
    return this.mapper;
  }

  @Override
  public ObjectMapper getObjectMapper() {
    return this.objectMapper;
  }

}
