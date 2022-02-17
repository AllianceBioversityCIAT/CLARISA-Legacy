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

import org.cgiar.clarisa.config.AppConfig;
import org.cgiar.clarisa.model.User;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

@Component
public class JwtUtils {

  private AppConfig appConfig;

  @Inject
  public JwtUtils(AppConfig appConfig) {
    this.appConfig = appConfig;
  }

  /**
   * Generates a Bearer token from an user
   * 
   * @param user the user to be used to generate the token
   * @return the generated token for the user
   */
  public String generateJWTToken(User user) {
    long now = System.currentTimeMillis();
    // FIXME change the id
    String token = this.getJwtBuilder().setId("test").setSubject(user.getEmail())
      .claim("authorities",
        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
      .setIssuedAt(new Date(now)).setExpiration(new Date(now + this.appConfig.getJwtExpirationTime())).compact();

    return token;
  }

  private Jws<Claims> getClaims(String token) {
    return this.getJwtParserWithSecret().build().parseClaimsJws(token);
  }

  public Long getExpirationMilis(String token) {
    Date expiration = this.getClaims(token).getBody().getExpiration();
    return expiration != null ? expiration.getTime() : 0;
  }

  private final JwtBuilder getJwtBuilder() {
    Key key = Keys.hmacShaKeyFor(appConfig.getJwtSecret().getBytes());
    return Jwts.builder().signWith(key);
  }

  private final JwtParserBuilder getJwtParserWithSecret() {
    return Jwts.parserBuilder().setSigningKey(appConfig.getJwtSecret().getBytes());
  }

  public String getUsername(String token) {
    return this.getClaims(token).getBody().getSubject();
  }

  public boolean validate(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
    SignatureException, IllegalArgumentException {
    this.getJwtParserWithSecret().build().parseClaimsJws(token);
    return true;
  }

}
