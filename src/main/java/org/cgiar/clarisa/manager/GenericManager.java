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

package org.cgiar.clarisa.manager;

import org.cgiar.clarisa.exception.EntityNotFoundException;
import org.cgiar.clarisa.exception.NullEntityIdentifierException;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**************
 * Generic implementation of a manager with some basic operations found on CrudRepository.
 * 
 * @author German C. Martinez - CIAT/CCAFS
 * @param <T> entity class targeted by this manager
 **************/

public interface GenericManager<T extends ClarisaBaseEntity> {

  /**
   * Gets a count of the number of entities in the table
   * 
   * @return the number of entities
   */
  public default Long count() {
    return this.getDAO().count();
  }

  /**
   * Deletes an entity passed as a parameter
   * 
   * @param entity the entity to be removed
   * @throws EntityNotFoundException if the entity does not exist
   * @throws NullEntityIdentifierException if the identifier is null
   */
  public default void delete(T entity) throws EntityNotFoundException, NullEntityIdentifierException {
    Objects.requireNonNull(entity);

    Long id = entity.getId();
    this.validateId(id, entity);

    entity = this.getDAO().findById(id).get();
    this.getDAO().delete(entity);
  }

  /**
   * Deletes an entity by a given ID
   * 
   * @param id the entity's ID
   * @throws EntityNotFoundException if the entity does not exist
   * @throws NullEntityIdentifierException if the identifier is null
   */
  public default void deleteById(Long id) throws EntityNotFoundException, NullEntityIdentifierException {
    this.validateId(id, null);

    T entity = this.getDAO().findById(id).get();
    this.getDAO().delete(entity);
  }

  /**
   * Checks if an entity exists in the database, given an ID
   * 
   * @param id the entity's ID
   * @return {@code true} if an entity with the given ID exists in the database; otherwise {@code false}
   */
  public default boolean existsById(Long id) {
    this.validateId(id, null);

    return this.getDAO().existsById(id);
  }

  /**
   * Get all entities from the database
   * 
   * @return the full list of entities of this class
   */
  public default List<T> findAll() {
    return this.getDAO().findAll();
  };

  /**
   * Gets a page of this entity class, as per a given {@code paginationCriteria}.
   * To create a custom {@code Pageable}, please use the {@link PageRequest#of()} method.
   * 
   * @param paginationCriteria the pagination criteria
   * @return a Page that matches the {@code paginationCriteria}; all the entities collected in a page if the
   *         {@code paginationCriteria} is {@code null}
   */
  public default Page<T> findAllPagedBy(Pageable paginationCriteria) {
    return this.getDAO().findAll(paginationCriteria != null ? paginationCriteria : Pageable.unpaged());
  }

  /**
   * Gets the full list of entities, ordered by the given {@code sortingCriteria}
   * 
   * @param sortingCriteria the sorting criteria
   * @return The ordered list of all entities by the provided {@code sortingCriteria}; the unordered list if the
   *         {@code sortingCriteria} is null
   */
  public default List<T> findAllSortedBy(Sort sortingCriteria) {
    return this.getDAO().findAll(sortingCriteria != null ? sortingCriteria : Sort.unsorted());
  }

  /**
   * Fetches an entity from the database by the given ID
   * 
   * @param id the entity's ID
   * @return the entity, {@link Optional#empty()} if it not exists
   * @throws NullEntityIdentifierException if the identifier is null
   */
  public default Optional<T> findById(Long id) throws NullEntityIdentifierException {
    if (id == null) {
      throw new NullEntityIdentifierException();
    }

    return this.getDAO().findById(id);
  }

  /**
   * Gets this manager's DAO. Convenience getter for implementing common methods in this class
   * 
   * @return the DAO
   */
  public JpaRepository<T, Long> getDAO();

  /**
   * Saves an entity and returns it with the generated ID to be used for further manipulation
   * 
   * @param entity the entity to be saved
   * @return the persisted entity
   */
  public default T save(T entity) {
    Objects.requireNonNull(entity);
    return this.getDAO().save(entity);
  }

  /**
   * Updates the DB reference to the given entity
   * 
   * @param entity the entity to be updated
   * @return the updated entity
   * @throws EntityNotFoundException if the entity does not exist
   * @throws NullEntityIdentifierException if the identifier is null
   */
  public default T update(T entity) throws EntityNotFoundException, NullEntityIdentifierException {
    Objects.requireNonNull(entity);

    Long id = entity.getId();
    this.validateId(id, entity);

    return this.save(entity);
  }

  /**
   * Validates an ID
   * 
   * @param id an entity ID
   * @param entity the entity (if possible) for its class
   * @throws EntityNotFoundException if the entity does not exist
   * @throws NullEntityIdentifierException if the identifier is null
   */
  public default void validateId(Long id, T entity) throws EntityNotFoundException, NullEntityIdentifierException {
    if (id == null) {
      throw new NullEntityIdentifierException();
    }

    if (this.getDAO().existsById(id) == false) {
      if (entity != null) {
        throw new EntityNotFoundException(entity.getClass(), id);
      } else {
        throw new EntityNotFoundException(String.format(EntityNotFoundException.ENTITY_NOT_FOUND, String.valueOf(id)));
      }
    }
  }

  // public void validate(T entity) throws Exception;
}
