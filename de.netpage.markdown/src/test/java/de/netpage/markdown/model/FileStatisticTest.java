package de.netpage.markdown.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Testfälle für FileStatistic.
 */
public class FileStatisticTest {

    private final File sampleMd = new File("./src/test/samples/simple.md");

    private Statistic processed;
    private Statistic notProcessed;

    @Before
    public void setUp() throws Exception {
        processed = new FileStatistic(sampleMd, sampleMd);
        notProcessed = new FileStatistic(sampleMd);
    }

    @Test
    public void testGetSource() throws Exception {
        assertEquals(sampleMd.getName(), processed.getSource().getName());
        assertEquals(sampleMd.getName(), notProcessed.getSource().getName());
    }

    @Test
    public void testGetOutput() throws Exception {
        assertEquals(sampleMd.getName(), processed.getOutput().getName());
        assertEquals(null, notProcessed.getOutput());
    }

    @Test
    public void testIsProcessed() throws Exception {
        assertTrue(processed.isProcessed());
        assertFalse(notProcessed.isProcessed());
    }

    @Test
    public void testGetSize() throws Exception {
        assertEquals(3554, processed.getSize());
        assertEquals(-1, notProcessed.getSize());
    }

    @Test
    public void testGetFiles() throws Exception {
        assertSame(processed, processed.getFiles().get(0));
        assertSame(notProcessed, notProcessed.getFiles().get(0));
    }

}