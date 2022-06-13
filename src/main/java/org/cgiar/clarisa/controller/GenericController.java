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

import org.cgiar.clarisa.dto.BaseDTO;
import org.cgiar.clarisa.exception.EntityNotFoundException;
import org.cgiar.clarisa.manager.ClarisaAuditlogManager;
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.model.User;
import org.cgiar.clarisa.utils.AppConstants;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public abstract class GenericController<ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> {

  private Class<ENTITY> entityClass;

  @Inject
  private ClarisaAuditlogManager clarisaAuditlogManager;

  protected GenericController(Class<ENTITY> entityClass) {
    this.entityClass = Objects.requireNonNull(entityClass);
  }

  protected void addEntityClassToRequest(HttpServletRequest request, Class<ENTITY> clazz) {
    request.setAttribute(AppConstants.HTTP_ENTITY_CLASS_NAME, clazz);
  }

  public DTO applyPatchToDTO(JsonPatch patch, DTO dto) throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(this.getObjectMapper().convertValue(dto, JsonNode.class));
    BaseDTO result = this.getObjectMapper().treeToValue(patched, dto.getClass());
    return (DTO) result;
  }

  public ENTITY applyPatchToEntity(JsonPatch patch, ENTITY entity) throws JsonPatchException, JsonProcessingException {
    JsonNode patched = patch.apply(this.getObjectMapper().convertValue(entity, JsonNode.class));
    ClarisaBaseEntity result = this.getObjectMapper().treeToValue(patched, entity.getClass());
    return (ENTITY) result;
  }

  @GetMapping(value = "/count")
  public ResponseEntity<Long> count(HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    Long count = this.getManager().count();
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, true);
    return ResponseEntity.ok(count);
  }

  @DeleteMapping(value = "/delete")
  public void delete(@RequestBody DTO dto, HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    ENTITY entity = this.getMapper().dtoToEntity(dto);
    DTO toBeRemovedDTO = this.getMapper().entityToDto(entity);
    this.getManager().delete(entity);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, dto, toBeRemovedDTO, entity.getId(), true);
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    ENTITY entity = this.getManager().findById(id).orElse(null);
    DTO toBeRemovedDTO = this.getMapper().entityToDto(entity);
    this.getManager().deleteById(id);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, null, toBeRemovedDTO, id, true);
    return ResponseEntity.ok(!this.getManager().existsById(id));
  }

  @GetMapping(value = "/exists/{id}")
  public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    Boolean exists = this.getManager().existsById(id);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, true);
    return ResponseEntity.ok(exists);
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<DTO>> findAll(HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    List<ENTITY> entitys = this.getManager().findAll();
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, true);
    return ResponseEntity.ok(this.getMapper().entityListToDtoList(entitys));
  }

  @GetMapping(value = "/get/{id}")
  public ResponseEntity<DTO> findById(@PathVariable("id") Long id, HttpServletRequest request,
    HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    ENTITY entity = this.getManager().findById(id).orElseThrow(() -> new EntityNotFoundException(this.entityClass, id));
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, true);
    return ResponseEntity.ok(this.getMapper().entityToDto(entity));
  }

  public abstract Logger getClassLogger();

  public abstract GenericManager<ENTITY> getManager();

  public abstract BaseMapper<ENTITY, DTO> getMapper();

  public abstract ObjectMapper getObjectMapper();

  @PatchMapping(value = "/patch/{id}")
  public ResponseEntity<DTO> patch(@PathVariable("id") Long id, @RequestBody JsonPatch patch,
    HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    Optional<ENTITY> entityOptional = this.getManager().findById(id);
    if (entityOptional.isPresent()) {
      try {
        ENTITY entity = entityOptional.get();
        DTO toBePatchedDTO = this.getMapper().entityToDto(entity);
        entity = this.applyPatchToEntity(patch, entity);
        entity = this.getManager().update(entity);
        DTO patchedDTO = this.getMapper().entityToDto(entity);
        this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, toBePatchedDTO, patchedDTO, id, true);
        return ResponseEntity.ok(this.getMapper().entityToDto(entity));
      } catch (JsonProcessingException | JsonPatchException e) {
        return ResponseEntity.unprocessableEntity().build();
      }
    } else {
      this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, false);
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<DTO> save(@RequestBody DTO dto, HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    ENTITY entity = this.getMapper().dtoToEntity(dto);
    entity = this.getManager().save(entity);
    DTO savedDTO = this.getMapper().entityToDto(entity);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, null, savedDTO, savedDTO.getId(), true);
    return ResponseEntity.ok(savedDTO);
  }

  @PutMapping(value = "/update")
  public ResponseEntity<DTO> update(@RequestBody DTO dto, HttpServletRequest request, HttpServletResponse response,
    @AuthenticationPrincipal User user) {
    this.addEntityClassToRequest(request, this.entityClass);
    ENTITY incoming = this.getMapper().dtoToEntity(dto);
    DTO previousDTO = this.getMapper().entityToDto(this.getManager().findById(incoming.getId()).orElse(null));
    ENTITY updated = this.getManager().update(incoming);
    DTO updatedDTO = this.getMapper().entityToDto(updated);
    this.clarisaAuditlogManager.registerAuditlog(request, user, entityClass, previousDTO, updatedDTO, updated.getId(),
      true);
    return ResponseEntity.ok(updatedDTO);
  }

  @RequestMapping("/user")
  public User user(@AuthenticationPrincipal User user) {
    return user;
  }
}
