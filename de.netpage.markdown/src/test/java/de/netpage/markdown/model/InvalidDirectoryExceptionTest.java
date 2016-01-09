package de.netpage.markdown.model;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testfälle für InvalidDirectoryException.
 */
public class InvalidDirectoryExceptionTest {

    @Test
    public void testInstance() throws Exception {
        assertNotNull(new InvalidDirectoryException("Test"));
    }

}