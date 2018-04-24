package org.openhr.common.util.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public final class StreamUtil {
  public static byte[] createFile(final byte[] bytes) throws IOException {
    try {
      final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      byteArrayOutputStream.write(bytes);
      return byteArrayOutputStream.toByteArray();
    } catch (final IOException e) {
      e.printStackTrace();
      throw e;
    }
  }
}
