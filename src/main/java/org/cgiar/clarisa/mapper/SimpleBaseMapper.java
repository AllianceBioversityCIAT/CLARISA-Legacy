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

package org.cgiar.clarisa.mapper;

import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import java.util.ArrayList;
import java.util.List;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

public interface SimpleBaseMapper<ENTITY extends ClarisaBaseEntity, SIMPLE_DTO extends SimpleDTO>
  extends BaseMapper<ENTITY, SIMPLE_DTO> {

  public default List<SimpleDTO> entityListToSimpleDtoList(List<ENTITY> entityList) {
    if (entityList == null) {
      return null;
    }

    List<SimpleDTO> list = new ArrayList<>(entityList.size());
    for (ENTITY entity : entityList) {
      list.add(this.entityToSimpleDto(entity, Long.SIZE));
    }

    return list;
  }

  /**
   * TODO Find some kind of workaround to remove the dummy parameter
   * 
   * @param entity
   * @param dummy a dummy object to help MapsStruct not to be confused
   * @return
   */
  public SimpleDTO entityToSimpleDto(ENTITY entity, Object dummy);

}
