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

package org.cgiar.clarisa.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;
import org.springframework.security.crypto.password.PasswordEncoder;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class LegacyPasswordEncoderImpl implements LegacyPasswordEncoder {

  private static final Logger LOG = LoggerFactory.getLogger(LegacyPasswordEncoderImpl.class);

  private PasswordEncoder secureEncoder;

  public LegacyPasswordEncoderImpl(int strength) {
    this.secureEncoder = new BCryptPasswordEncoder(strength);
  }

  @Override
  public String encode(CharSequence rawPassword) {
    return this.secureEncoder.encode(rawPassword);
  }

  @Override
  public boolean isLegacyPassword(String encodedPassword) {
    return !StringUtils.startsWithAny(encodedPassword, BCryptVersion.$2A.getVersion(), BCryptVersion.$2B.getVersion(),
      BCryptVersion.$2Y.getVersion());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return this.isLegacyPassword(encodedPassword) ? this.md5Matches(rawPassword, encodedPassword)
      : this.secureEncoder.matches(rawPassword, encodedPassword);
  }

  private boolean md5Matches(CharSequence rawPassword, String encodedPassword) {
    try {
      MessageDigest md;
      md = MessageDigest.getInstance("MD5");
      md.update((rawPassword == null ? "" : rawPassword.toString()).getBytes());
      byte[] digest = md.digest();
      String md5HashedPassword = DatatypeConverter.printHexBinary(digest);

      return StringUtils.equalsIgnoreCase(encodedPassword, md5HashedPassword);
    } catch (NoSuchAlgorithmException e) {
      LOG.warn(e.getMessage());
      return false;
    }
  }

}
