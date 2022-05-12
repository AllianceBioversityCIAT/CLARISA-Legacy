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

import org.cgiar.clarisa.mapper.BaseMapper;
import org.cgiar.clarisa.model.ClarisaBaseEntity;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class MapperUtils {

  private static final String MAPPER_DIR = "org.cgiar.clarisa.mapper.%sMapper";

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MapperUtils.class);

  @SuppressWarnings("unchecked")
  public static <ENTITY extends ClarisaBaseEntity> BaseMapper<ENTITY, ?> getMapper(Class<ENTITY> entityClazz) {
    BaseMapper<ENTITY, ?> mapperFound = null;
    Class<BaseMapper<ENTITY, ?>> mapperClass = null;
    try {
      mapperClass =
        (Class<BaseMapper<ENTITY, ?>>) Class.forName(String.format(MAPPER_DIR, entityClazz.getSimpleName()));
      mapperFound = Mappers.getMapper(mapperClass);
    } catch (ClassNotFoundException cnfe) {
      LOG.warn("Could not find a mapper for the class {}", entityClazz.getName());
    }

    return mapperFound;
  }

}
