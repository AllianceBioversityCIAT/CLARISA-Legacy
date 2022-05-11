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

package org.cgiar.clarisa.utils;

import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.MappingException;
import org.hibernate.metamodel.internal.MetamodelImpl;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.stereotype.Component;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Component
public class HibernateTableNameResolver {

  @PersistenceContext
  private EntityManager entityManager;

  /**
   * Gets the name of the table linked to the {@code modelClazz}. Thanks to Antoine Meyer on
   * {@link https://stackoverflow.com/a/66802699}
   * 
   * @param modelClazz the class to be used for the lookup
   * @return a {@code String} representing the name of the table in the database of the argument
   * @throws IllegalArgumentException if the {@code modelClazz} does not represent an individual table
   *         (e.g. a JoinTable entity)
   * @throws MappingException if the {@code modelClazz} does not represent an object mapped by Hibernate
   */
  public String extractTableName(final Class<?> modelClazz) throws IllegalArgumentException, MappingException {
    Objects.requireNonNull(modelClazz);
    final MetamodelImpl metamodel = (MetamodelImpl) entityManager.getMetamodel();
    final EntityPersister entityPersister = metamodel.entityPersister(modelClazz);
    if (entityPersister instanceof SingleTableEntityPersister) {
      return ((SingleTableEntityPersister) entityPersister).getTableName();
    } else {
      throw new IllegalArgumentException(modelClazz + " does not map to a single table.");
    }
  }
}
