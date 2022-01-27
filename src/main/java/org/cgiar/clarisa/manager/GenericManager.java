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

package org.cgiar.clarisa.manager;

import org.cgiar.clarisa.exception.DetachedEntityException;
import org.cgiar.clarisa.exception.EntityNotFoundException;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

/**************
 * Generic implementation of a manager with some basic operations found on CrudRepository.
 * 
 * @author German C. Martinez - CIAT/CCAFS
 * @param <T> entity class targeted by this manager
 * @param <Long> the entity class of the identifier for the class
 **************/

public interface GenericManager<T extends ClarisaBaseEntity> {

  public default Long count() {
    return this.getDAO().count();
  }

  public default void delete(T entity) throws RuntimeException {
    Objects.requireNonNull(entity);

    Long id = entity.getId();
    this.validateId(id, entity);

    entity = this.getDAO().findById(id).get();
    this.getDAO().delete(entity);
  }

  public default void deleteById(Long id) throws RuntimeException {
    this.validateId(id, null);

    T entity = this.getDAO().findById(id).get();
    this.getDAO().delete(entity);
  }

  public default boolean existsById(Long id) {
    return this.findById(id).isPresent();
  }

  public default List<T> findAll() {
    return this.getDAO().findAll();
  };

  public default Optional<T> findById(Long id) {
    if (StringUtils.isBlank(String.valueOf(id))) {
      throw new DetachedEntityException();
    }

    return this.getDAO().findById(id);
  }

  public JpaRepository<T, Long> getDAO();

  public default T save(T entity) throws RuntimeException {
    Objects.requireNonNull(entity);
    return this.getDAO().save(entity);
  }

  public default T update(T entity) throws RuntimeException {
    Objects.requireNonNull(entity);

    Long id = entity.getId();
    this.validateId(id, entity);

    return this.save(entity);
  }

  public default void validateId(Long id, T entity) throws RuntimeException {
    if (StringUtils.isBlank(String.valueOf(id))) {
      throw new DetachedEntityException();
    }

    if (this.getDAO().existsById(id) == false) {
      if (entity != null) {
        throw new EntityNotFoundException(entity.getClass(), String.valueOf(id));
      } else {
        throw new EntityNotFoundException(String.format(EntityNotFoundException.ENTITY_NOT_FOUND, String.valueOf(id)));
      }
    }
  }

  // public void validate(T entity) throws Exception;
}
