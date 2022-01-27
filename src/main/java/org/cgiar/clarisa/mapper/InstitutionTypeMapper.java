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

import org.cgiar.clarisa.dto.InstitutionTypeDTO;
import org.cgiar.clarisa.model.InstitutionType;

import org.mapstruct.Mapper;

/**************
 * @author German C. Martinez - CIAT/CCAFS
 **************/

@Mapper(componentModel = "jsr330", uses = {RepIndOrganizationTypeMapper.class})
public interface InstitutionTypeMapper extends BaseMapper<InstitutionType, InstitutionTypeDTO> {

}