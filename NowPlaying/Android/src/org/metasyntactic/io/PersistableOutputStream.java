package org.metasyntactic.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Collection;
import java.util.Date;

/** @author cyrusn@google.com (Cyrus Najmabadi) */
public class PersistableOutputStream {
  private final OutputStream out;

  private byte[] bytes;
  private ByteBuffer byteBuffer;
  private CharBuffer charBuffer;


  private void initializeBuffers(int byteCount) {
    bytes = new byte[byteCount];
    byteBuffer = ByteBuffer.wrap(bytes);
    charBuffer = byteBuffer.asCharBuffer();
  }


  public PersistableOutputStream(OutputStream out) {
    this.out = out;
    initializeBuffers(1 << 11);
  }


  public void close() throws IOException {
    flush();
    out.close();
  }


  public void flush() throws IOException {
    out.flush();
  }


  private final byte[] bytes4 = new byte[4];
  private final byte[] bytes8 = new byte[8];
  private final ByteBuffer buffer4 = ByteBuffer.wrap(bytes4);
  private final ByteBuffer buffer8 = ByteBuffer.wrap(bytes8);


  public void writeInt(int i) throws IOException {
    buffer4.putInt(0, i);
    out.write(bytes4);
  }


  public void writeLong(long v) throws IOException {
    buffer8.putLong(0, v);
    out.write(bytes8);
  }


  public void writeDouble(double d) throws IOException {
    buffer8.putDouble(0, d);
    out.write(bytes8);
  }


  public void writeString(String s) throws IOException {
    int charCount = s.length();
    int byteCount = charCount * 2;

    if (byteCount > bytes.length) {
      initializeBuffers(Math.max(byteCount, bytes.length * 2));
    }

    writeInt(charCount);

    byteBuffer.position(0);
    charBuffer.position(0);
    charBuffer.put(s);

    out.write(bytes, 0, byteCount);
  }


  public void writePersistable(Persistable persistable) throws IOException {
    persistable.persistTo(this);
  }


  public <T extends Persistable> void writePersistableCollection(Collection<T> collection) throws IOException {
    writeInt(collection.size());
    for (T t : collection) {
      writePersistable(t);
    }
  }


  public void writeStringCollection(Collection<String> collection) throws IOException {
    writeInt(collection.size());
    for (String string : collection) {
      writeString(string);
    }
  }


  public void writeDate(Date date) throws IOException {
    if (date == null) {
      writeLong(-1);
    } else {
      writeLong(date.getTime());
    }
  }
}