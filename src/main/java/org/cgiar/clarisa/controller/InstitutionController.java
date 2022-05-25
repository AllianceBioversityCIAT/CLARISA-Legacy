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

import org.cgiar.clarisa.dto.InstitutionChildDTO;
import org.cgiar.clarisa.dto.InstitutionDTO;
import org.cgiar.clarisa.dto.InstitutionLocationDTO;
import org.cgiar.clarisa.dto.InstitutionParentDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.manager.InstitutionLocationManager;
import org.cgiar.clarisa.manager.InstitutionManager;
import org.cgiar.clarisa.manager.InstitutionTypeManager;
import org.cgiar.clarisa.manager.LocElementManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.mapper.InstitutionMapper;
import org.cgiar.clarisa.mapper.InstitutionTypeMapper;
import org.cgiar.clarisa.model.Institution;
import org.cgiar.clarisa.model.InstitutionLocation;
import org.cgiar.clarisa.model.InstitutionType;
import org.cgiar.clarisa.model.LocElement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  private LocElementManager locElementManager;
  private InstitutionTypeManager insTypeManager;
  private InstitutionLocationManager insLocManager;


  // Mapper
  private InstitutionMapper mapper;
  private InstitutionTypeMapper insTypeMapper;

  @Inject
  public InstitutionController(InstitutionManager manager, InstitutionMapper mapper,
    InstitutionTypeMapper insTypeMapper, LocElementManager locElementManager, InstitutionTypeManager insTypeManager,
    InstitutionLocationManager insLocManager) {
    super(Institution.class);
    this.manager = manager;
    this.mapper = mapper;
    this.insTypeMapper = insTypeMapper;
    this.locElementManager = locElementManager;
    this.insTypeManager = insTypeManager;
    this.insLocManager = insLocManager;
  }

  @Override
  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
    Institution institution = manager.findById(id).orElse(null);
    if (institution != null) {
      List<InstitutionLocation> locationList = insLocManager.searchInstitutionLocation(institution.getId());
      for (InstitutionLocation location : locationList) {
        insLocManager.deleteById(location.getId());
      }
      manager.deleteById(institution.getId());
    } else {
      return null;
    }
    return ResponseEntity.ok(true);
  }

  @GetMapping(value = "/public/all")
  public ResponseEntity<List<SimpleDTO>> findAllSimple() {
    List<Institution> resultList = this.manager.findAll();
    return ResponseEntity.ok(this.mapper.entityListToSimpleDtoList(resultList));
  }

  @GetMapping(value = "/childInstitutions/{id}")
  public ResponseEntity<List<InstitutionDTO>> getChildnstitutions(@PathVariable("id") Long id) {
    List<Institution> relatedList = manager.searchChildInstitutions(id);
    if (relatedList != null) {
      return ResponseEntity.ok(mapper.entityListToDtoList(relatedList));
    }
    return ResponseEntity.ok(null);
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

  @GetMapping(value = "/institutionParent/{institutionId}")
  public ResponseEntity<List<InstitutionDTO>> getRelatedInstitutions(@PathVariable("institutionId") Long id) {
    List<Institution> relatedList = manager.searchInstitutionParent(id);
    if (relatedList != null) {
      return ResponseEntity.ok(mapper.entityListToDtoList(relatedList));
    }
    return ResponseEntity.ok(null);
  }

  @Override
  @PostMapping(value = "/save")
  public ResponseEntity<InstitutionDTO> save(@RequestBody InstitutionDTO dto) {
    Institution institution = new Institution();
    institution.setAcronym(dto.getAcronym());
    InstitutionType insType = insTypeManager.findById(dto.getInstitutionType().getId()).orElse(null);
    institution.setInstitutionType(insType);
    institution.setName(dto.getName());
    institution.setWebsiteLink(dto.getWebsiteLink());

    institution = manager.save(institution);
    List<InstitutionLocation> institutionLocationList = new ArrayList<InstitutionLocation>();
    if (dto.getLocations() != null) {
      for (InstitutionLocationDTO locationDTO : dto.getLocations()) {
        LocElement loc = locElementManager.findById(locationDTO.getLocation().getId()).orElse(null);
        InstitutionLocation newInstitutionLocation = new InstitutionLocation();
        newInstitutionLocation.setHeadquarter(locationDTO.getHeadquarter());
        newInstitutionLocation.setInstitution(institution);
        newInstitutionLocation.setLocElement(loc);
        newInstitutionLocation = insLocManager.save(newInstitutionLocation);
        institutionLocationList.add(newInstitutionLocation);
      }
    }
    institution.setInstitutionLocations(institutionLocationList);
    return ResponseEntity.ok(mapper.entityToDto(institution));
  }

  @Override
  @PutMapping(value = "/update")
  public ResponseEntity<InstitutionDTO> update(@RequestBody InstitutionDTO dto) {

    Institution institution = manager.findById(dto.getId()).orElse(null);
    if (institution != null) {
      if (dto.getLocations() != null) {
        List<InstitutionLocation> locationList = insLocManager.searchInstitutionLocation(institution.getId());
        for (InstitutionLocationDTO locationDTO : dto.getLocations()) {
          boolean found = false;
          for (InstitutionLocation locations : locationList) {
            if (locationDTO.getLocation().getId().equals(locations.getLocElement().getId())) {
              found = true;
            }
          }
          if (!found) {
            LocElement loc = locElementManager.findById(locationDTO.getLocation().getId()).orElse(null);
            InstitutionLocation newInstitutionLocation = new InstitutionLocation();
            newInstitutionLocation.setHeadquarter(locationDTO.getHeadquarter());
            newInstitutionLocation.setInstitution(institution);
            newInstitutionLocation.setLocElement(loc);
            insLocManager.save(newInstitutionLocation);
          }
        }
        // search for Locations deleted
        List<InstitutionLocation> locationstoBeDeleted = new ArrayList<InstitutionLocation>();
        for (InstitutionLocation locations : locationList) {
          boolean found = true;
          for (InstitutionLocationDTO locationDTO : dto.getLocations()) {
            if (locationDTO.getLocation().getId().equals(locations.getLocElement().getId())) {
              found = false;
            }
          }
          if (found) {
            locationstoBeDeleted.add(locations);
          }
        }
        for (InstitutionLocation locations : locationstoBeDeleted) {
          insLocManager.delete(locations);
        }
      }
      institution.setAcronym(dto.getAcronym());
      InstitutionType insType = insTypeManager.findById(dto.getInstitutionType().getId()).orElse(null);
      institution.setInstitutionType(insType);
      institution.setName(dto.getName());
      institution.setWebsiteLink(dto.getWebsiteLink());
      institution = manager.save(institution);
    } else {
      return null;
    }
    return ResponseEntity.ok(mapper.entityToDto(institution));
  }

  @PostMapping(value = "/updateparent")
  public ResponseEntity<InstitutionParentDTO> updateParent(@RequestBody InstitutionParentDTO dto) {
    if (dto.getInstitutionId() != null) {
      InstitutionChildDTO dtoChild;
      InstitutionParentDTO parent;
      List<InstitutionChildDTO> dtoChildList = new ArrayList<InstitutionChildDTO>();
      Institution institution = manager.findById(dto.getInstitutionId()).orElse(null);
      List<Institution> institutionChildsToBeDeleted = new ArrayList<>();
      if (institution != null) {
        List<Institution> institutionChilds = manager.searchInstitutionParent(dto.getInstitutionId());
        // disable the existing ones as child from parent
        for (InstitutionChildDTO childDTO : dto.getInstitutionChildList()) {
          Institution institutionchild = manager.findById(childDTO.getInstitutionId()).orElse(null);
          if (institutionchild != null) {
            boolean found = false;
            for (Institution child : institutionChilds) {
              if (child.getId().longValue() == institutionchild.getId().longValue()) {
                found = true;
              }
            }
            if (!found) {
              institutionchild.setParent(false);
              institutionchild.setInstitutionParent(institution);
              institutionchild = manager.save(institutionchild);
            }
          }
          if (!institutionchild.getParent()) {
            dtoChild = new InstitutionChildDTO();
            dtoChild.setInstitutionId(institutionchild.getId());
            dtoChild.setInstitutionName(institutionchild.getName());
            dtoChild.setInstitutionWebsite(institutionchild.getWebsiteLink());
            dtoChild.setActive(true);
            dtoChildList.add(dtoChild);
          }
        }

        for (Institution child : institutionChilds) {
          boolean found = false;
          for (InstitutionChildDTO childDTO : dto.getInstitutionChildList()) {
            if (child.getId().longValue() == childDTO.getInstitutionId().longValue()) {
              found = true;
            }
          }
          if (!found) {
            institutionChildsToBeDeleted.add(child);
            dtoChild = new InstitutionChildDTO();
            dtoChild.setInstitutionId(child.getId());
            dtoChild.setInstitutionName(child.getName());
            dtoChild.setInstitutionWebsite(child.getWebsiteLink());
            dtoChild.setActive(false);
            dtoChildList.add(dtoChild);
          }
        }
        for (Institution inst : institutionChildsToBeDeleted) {
          Institution institutionchild = manager.findById(inst.getId()).orElse(null);
          institutionchild.setInstitutionParent(null);
          institutionchild.setParent(null);
          institutionchild = manager.save(institutionchild);
        }

        parent = new InstitutionParentDTO();
        parent.setInstitutionId(institution.getId());
        parent.setInstitutionName(institution.getName());
        parent.setInstitutionChildList(dtoChildList);
        return ResponseEntity.ok(parent);
      }

    } else {
      return null;
    }
    return null;
  }

}