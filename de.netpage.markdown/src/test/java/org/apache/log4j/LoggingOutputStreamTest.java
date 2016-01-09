package org.apache.log4j;

import org.junit.Test;

import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.fail;

/**
 * Testfälle für LoggingOutputStream.
 */
public class LoggingOutputStreamTest {

    private static final Logger LOG = Logger.getLogger(LoggingOutputStreamTest.class);

    @Test
    public void testWrite() throws Exception {
        final PrintWriter printWriter = new PrintWriter(new LoggingOutputStream(LOG, Level.INFO), true);
        printWriter.println("Test 1");
        printWriter.println("Test 2");
        printWriter.println("Test 3");
        printWriter.println("Test 4");
        printWriter.flush();
        printWriter.close();
    }

    @Test
    public void testWriteMore() throws Exception {
        final PrintWriter printWriter = new PrintWriter(new LoggingOutputStream(LOG, Level.INFO), true);
        for (int i = 0; i < 3000; i++) {
            printWriter.print("Test");
            printWriter.flush();
        }
        printWriter.close();
    }

    @Test
    public void testInstance() throws Exception {
        try {
            final LoggingOutputStream out = new LoggingOutputStream(null, Level.INFO);
            fail("Exception not thrown on " + out);
        } catch (final IllegalArgumentException ignore) {
        }
        try {
            final LoggingOutputStream out = new LoggingOutputStream(LOG, null);
            fail("Exception not thrown on " + out);
        } catch (final IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testWriteClose() throws Exception {
        final LoggingOutputStream out = new LoggingOutputStream(LOG, Level.INFO);

        out.close();
        try {
            out.write(1);
            fail("Exception not thrown");
        } catch (final IOException ignore) {
        }
    }

    @Test
    public void testWriteNull() throws Exception {
        final LoggingOutputStream out = new LoggingOutputStream(LOG, Level.INFO);
        out.write(0);
        out.close();
    }

}