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

/**************
 * @author Diego Perez - Alliance Bioversity/CIAT
 **************/

package org.cgiar.clarisa.mapper;

import org.cgiar.clarisa.dto.GlobalUnitDTO;
import org.cgiar.clarisa.dto.RoleDTO;
import org.cgiar.clarisa.dto.SimpleDTO;
import org.cgiar.clarisa.model.GlobalUnit;
import org.cgiar.clarisa.model.Role;
import org.cgiar.clarisa.model.UserRole;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "jsr330")
public interface RoleMapper extends SimpleBaseMapper<Role, RoleDTO> {


  @Override
  @Mappings({@Mapping(target = "name", expression = "java(entity.getComposedName())"),
    @Mapping(target = "globalUnit", expression = "java(this.globalUnitToGlobalUnitDto(entity.getGlobalUnit()))")})
  public RoleDTO entityToDto(Role entity);

  @Override
  @Mappings({@Mapping(target = "name", expression = "java(entity.getComposedName())")})
  public SimpleDTO entityToSimpleDto(Role entity, @Context Object dummy);


  @Mappings({@Mapping(target = "officialCode", source = "smoCode")})
  public GlobalUnitDTO globalUnitToGlobalUnitDto(GlobalUnit entity);

  public default RoleDTO userRoleToRoleDTO(UserRole userRole) {
    if (userRole == null) {
      return null;
    }

    RoleDTO roleDTO = new RoleDTO();
    roleDTO.setId(userRole.getRole().getId());
    roleDTO.setDescription(userRole.getRole().getDescription());
    roleDTO.setAcronym(userRole.getRole().getAcronym());
    roleDTO.setGlobalUnit(this.globalUnitToGlobalUnitDto(userRole.getRole().getGlobalUnit()));
    return roleDTO;

  }


}
