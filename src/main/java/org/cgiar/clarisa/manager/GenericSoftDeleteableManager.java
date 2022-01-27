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

import org.cgiar.clarisa.model.SoftDeleteableEntity;

import java.util.Objects;

/**************
 * Generic manager, but for soft-deleteable entities.
 * 
 * @author German C. Martinez - CIAT/CCAFS
 * @param <T> class targeted by this manager
 * @param <Long> the class of the identifier for the class
 **************/

public interface GenericSoftDeleteableManager<T extends SoftDeleteableEntity> extends GenericManager<T> {

  @Override
  public default void delete(T entity) throws RuntimeException {
    Objects.requireNonNull(entity);
    this.deleteById(entity.getId());
  }

  @Override
  public default void deleteById(Long id) throws RuntimeException {
    this.validateId(id, null);

    T entity = this.findById(id).get();
    entity.setActive(false);

    this.update(entity);
  }
}
