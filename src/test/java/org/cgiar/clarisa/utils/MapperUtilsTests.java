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
import org.cgiar.clarisa.mapper.UserMapper;
import org.cgiar.clarisa.model.User;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@SpringBootTest
@TestPropertySource({"classpath:config/clarisa-${spring.profiles.active:local}.properties"})
public class MapperUtilsTests {

  @Test
  public void testMapperResolver() {
    BaseMapper<User, ?> userMapper = Mappers.getMapper(UserMapper.class);
    BaseMapper<User, ?> userMapperInferred = MapperUtils.getMapper(User.class);
    assertEquals(userMapper.getClass(), userMapperInferred.getClass());
  }
}
