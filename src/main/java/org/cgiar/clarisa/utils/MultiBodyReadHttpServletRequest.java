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

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.google.common.io.ByteStreams;

/**************
 * @author German C. Martinez - Alliance Bioversity/CIAT
 **************/

public class MultiBodyReadHttpServletRequest extends HttpServletRequestWrapper {

  private static class ServletInputStreamImpl extends ServletInputStream {

    private InputStream is;

    public ServletInputStreamImpl(InputStream is) {
      this.is = is;
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public synchronized void mark(int i) {
      throw new RuntimeException(new IOException("mark/reset not supported"));
    }

    @Override
    public boolean markSupported() {
      return false;
    }

    @Override
    public int read() throws IOException {
      return is.read();
    }

    @Override
    public synchronized void reset() throws IOException {
      throw new IOException("mark/reset not supported");
    }

    @Override
    public void setReadListener(ReadListener listener) {
      throw new UnsupportedOperationException();
    }

  }

  private byte[] body;

  public MultiBodyReadHttpServletRequest(HttpServletRequest request) throws IOException {
    super(request);
    this.body = ByteStreams.toByteArray(super.getInputStream());
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    return new ServletInputStreamImpl(new ByteArrayInputStream(body));
  }

  @Override
  public BufferedReader getReader() throws IOException {
    String enc = this.getCharacterEncoding();
    if (enc == null) {
      enc = "UTF-8";
    }
    return new BufferedReader(new InputStreamReader(this.getInputStream(), enc));
  }

}
