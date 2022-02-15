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

package org.cgiar.clarisa.mapper;

import org.cgiar.clarisa.dto.InstitutionDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.model.Institution;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

@Mapper(componentModel = "jsr330", uses = {InstitutionTypeMapper.class})
public interface InstitutionMapper extends SimpleBaseMapper<Institution, InstitutionDTO> {

  @Override
  @Mappings({@Mapping(target = "name", expression = "java(entity.getComposedName())")})
  public SimpleDTO entityToSimpleDto(Institution entity, @Context Object dummy);
}
