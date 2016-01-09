package de.netpage.markdown.output;

import org.junit.Test;

/**
 * Testfälle für ConsoleOutputWriter.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public class ConsoleOutputWriterTest {

    private static final OutputWriter OUTPUT_WRITER = new ConsoleOutputWriter();

    @Test
    public void testPrintln() throws Exception {
        OUTPUT_WRITER.println();
    }

    @Test
    public void testPrintln1() throws Exception {
        OUTPUT_WRITER.println("Test");
    }

}