package de.netpage.markdown.output;

import org.junit.Test;

/**
 * Testfälle für Log4jOutputWriter.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public class Log4jOutputWriterTest {

    @Test
    public void testPrintln() throws Exception {
        final OutputWriter writer = new Log4jOutputWriter("Test", Log4jOutputWriter.Level.DEBUG);
        writer.println();
    }

    @Test
    public void testPrintln1() throws Exception {
        OutputWriter writer = new Log4jOutputWriter("Test", Log4jOutputWriter.Level.DEBUG);
        writer.println("Test Debug");

        writer = new Log4jOutputWriter("Test", Log4jOutputWriter.Level.INFO);
        writer.println("Test Info");

        writer = new Log4jOutputWriter("Test", Log4jOutputWriter.Level.WARN);
        writer.println("Test Warn");

        writer = new Log4jOutputWriter("Test", Log4jOutputWriter.Level.ERROR);
        writer.println("Test Error");
    }
}