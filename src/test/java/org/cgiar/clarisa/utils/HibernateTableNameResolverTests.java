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

import org.cgiar.clarisa.model.ClarisaBaseEntity;
import org.cgiar.clarisa.model.RefreshToken;
import org.cgiar.clarisa.model.User;

import org.hibernate.MappingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@SpringBootTest
@TestPropertySource({"classpath:config/clarisa-${spring.profiles.active:local}.properties"})
public class HibernateTableNameResolverTests {

  @Autowired
  private HibernateTableNameResolver tableNameResolver;

  @Test
  public void testResolver() {
    assertNotNull(tableNameResolver, "resolver should not be null");

    String userTableName = tableNameResolver.extractTableName(User.class);
    assertEquals(userTableName, "users");

    String refreshTableName = tableNameResolver.extractTableName(RefreshToken.class);
    assertEquals(refreshTableName, "refresh_tokens");

    // not being explicitly caught and indirectly thrown by hibernate. the root cause is MappingException, but when
    // i tried to catch it and wrapping it in an IllegalArgumentException, hibernate re-wrapped it on a different
    // exception
    assertThrows(MappingException.class, () -> tableNameResolver.extractTableName(ClarisaBaseEntity.class));
  }
}
