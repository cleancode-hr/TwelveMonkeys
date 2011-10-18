package com.twelvemonkeys.io.enc;

import com.twelvemonkeys.io.FileUtil;
import com.twelvemonkeys.lang.ObjectAbstractTestCase;
import org.junit.Test;

import java.io.*;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * AbstractDecoderTest
 * <p/>
 *
 * @author <a href="mailto:harald.kuhr@gmail.com">Harald Kuhr</a>
 * @version $Id: //depot/branches/personal/haraldk/twelvemonkeys/release-2/twelvemonkeys-core/src/test/java/com/twelvemonkeys/io/enc/DecoderAbstractTestCase.java#1 $
 */
public abstract class DecoderAbstractTestCase extends ObjectAbstractTestCase {

    public abstract Decoder createDecoder();
    public abstract Encoder createCompatibleEncoder();

    protected Object makeObject() {
        return createDecoder();
    }

    @Test
    public final void testNullDecode() throws IOException {
        Decoder decoder = createDecoder();
        ByteArrayInputStream bytes = new ByteArrayInputStream(new byte[20]);

        try {
            decoder.decode(bytes, null);
            fail("null should throw NullPointerException");
        }
        catch (NullPointerException e) {
        }
    }

    @Test
    public final void testEmptyDecode() throws IOException {
        Decoder decoder = createDecoder();
        ByteArrayInputStream bytes = new ByteArrayInputStream(new byte[0]);

        try {
            int count = decoder.decode(bytes, new byte[2]);
            assertEquals("Should not be able to read any bytes", 0, count);
        }
        catch (EOFException allowed) {
            // Okay
        }
    }

    private byte[] createData(int pLength) throws Exception {
        byte[] bytes = new byte[pLength];
        EncoderAbstractTestCase.RANDOM.nextBytes(bytes);
        return bytes;
    }

    private void runStreamTest(int pLength) throws Exception {
        byte[] data = createData(pLength);

        ByteArrayOutputStream outBytes = new ByteArrayOutputStream();
        OutputStream out = new EncoderStream(outBytes, createCompatibleEncoder(), true);
        out.write(data);
        out.close();
        byte[] encoded = outBytes.toByteArray();

        byte[] decoded = FileUtil.read(new DecoderStream(new ByteArrayInputStream(encoded), createDecoder()));
        assertTrue(Arrays.equals(data, decoded));

        InputStream in = new DecoderStream(new ByteArrayInputStream(encoded), createDecoder());
        outBytes = new ByteArrayOutputStream();
        /*
        byte[] buffer = new byte[3];
        for (int n = in.read(buffer); n > 0; n = in.read(buffer)) {
            outBytes.write(buffer, 0, n);
        }
        */
        FileUtil.copy(in, outBytes);

        outBytes.close();
        in.close();
        decoded = outBytes.toByteArray();
        assertTrue(Arrays.equals(data, decoded));
    }

    @Test
    public final void testStreams() throws Exception {
        for (int i = 0; i < 100; i++) {
            try {
                runStreamTest(i);
            }
            catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage() + ": " + i);
            }
        }

        for (int i = 100; i < 2000; i += 250) {
            try {
                runStreamTest(i);
            }
            catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage() + ": " + i);
            }
        }

        for (int i = 2000; i < 80000; i += 1000) {
            try {
                runStreamTest(i);
            }
            catch (IOException e) {
                e.printStackTrace();
                fail(e.getMessage() + ": " + i);
            }
        }
    }
}
