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

import org.cgiar.clarisa.dto.BaseDTO;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import java.util.List;

import org.mapstruct.MapperConfig;

/**
 * Base mapper. It contains the common methods for all the mappers: Entity to DTO, DTO to Entity
 * and their equivalents for lists
 * 
 * @author German C. Martinez - CIAT/CCAFS
 * @param <ENTITY> The Entity class
 * @param <DTO> The DTO class
 */

@MapperConfig
public interface BaseMapper<ENTITY extends ClarisaBaseEntity, DTO extends BaseDTO> {

  public List<ENTITY> dtoListToEntityList(List<DTO> dtoList);

  public ENTITY dtoToEntity(DTO dto);

  public List<DTO> entityListToDtoList(List<ENTITY> entityList);

  public DTO entityToDto(ENTITY entity);
}
