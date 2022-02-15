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
import org.cgiar.clarisa.manager.GenericManager;
import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.slf4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public abstract class GenericController<ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> {

  private Class<ENTITY> entityClass;

  protected GenericController(Class<ENTITY> entityClass) {
    this.entityClass = Objects.requireNonNull(entityClass);
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
  public ResponseEntity<Long> count() {
    return ResponseEntity.ok(this.getManager().count());
  }

  @DeleteMapping(value = "/delete")
  public void delete(@RequestBody DTO dto) {
    ENTITY entity = this.getMapper().dtoToEntity(dto);
    this.getManager().delete(entity);
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<Boolean> deleteById(@PathVariable("id") Long id) {
    this.getManager().deleteById(id);
    return ResponseEntity.ok(!this.getManager().existsById(id));
  }

  @GetMapping(value = "/exists/{id}")
  public ResponseEntity<Boolean> existsById(@PathVariable("id") Long id) {
    Boolean exists = this.getManager().existsById(id);
    return ResponseEntity.ok(exists);
  }

  @GetMapping(value = "/all")
  public ResponseEntity<List<DTO>> findAll() {
    List<ENTITY> entitys = this.getManager().findAll();
    return ResponseEntity.ok(this.getMapper().entityListToDtoList(entitys));
  }

  @GetMapping(value = "/get/{id}")
  public ResponseEntity<DTO> findById(@PathVariable("id") Long id) {
    ENTITY entity = this.getManager().findById(id).orElseThrow(() -> new EntityNotFoundException(this.entityClass, id));
    return ResponseEntity.ok(this.getMapper().entityToDto(entity));
  }

  public abstract Logger getClassLogger();

  public abstract GenericManager<ENTITY> getManager();

  public abstract BaseMapper<ENTITY, DTO> getMapper();

  public abstract ObjectMapper getObjectMapper();

  @PatchMapping(value = "/patch/{id}")
  public ResponseEntity<DTO> patch(@PathVariable("id") Long id, @RequestBody JsonPatch patch) {
    Optional<ENTITY> entityOptional = this.getManager().findById(id);
    if (entityOptional.isPresent()) {
      try {
        ENTITY entity = entityOptional.get();
        entity = this.applyPatchToEntity(patch, entity);
        entity = this.getManager().update(entity);
        return ResponseEntity.ok(this.getMapper().entityToDto(entity));
      } catch (JsonProcessingException | JsonPatchException e) {
        return ResponseEntity.unprocessableEntity().build();
      }
    } else {
      return ResponseEntity.badRequest().build();
    }
  }

  @PostMapping(value = "/save")
  public ResponseEntity<DTO> save(@RequestBody DTO dto) {
    ENTITY entity = this.getMapper().dtoToEntity(dto);
    entity = this.getManager().save(entity);
    return ResponseEntity.ok(this.getMapper().entityToDto(entity));
  }

  @PutMapping(value = "/update")
  public ResponseEntity<DTO> update(@RequestBody DTO dto) {
    ENTITY entity = this.getMapper().dtoToEntity(dto);
    entity = this.getManager().update(entity);
    return ResponseEntity.ok(this.getMapper().entityToDto(entity));
  }
}
