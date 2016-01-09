package de.netpage.markdown.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testfälle für OutputFormat.
 */
public class OutputFormatTest {

    @Test
    public void testInstance() throws Exception {
        assertNotNull(OutputFormat.HTML);
        assertNotNull(OutputFormat.PDF);
    }

}