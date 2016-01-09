package de.netpage.markdown.model;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Testfälle für DirectoryStatistic.
 */
@SuppressWarnings("FieldCanBeLocal")
public class DirectoryStatisticTest {

    private final File sampleDir = new File("./src/test/samples");
    private final File sampleMd = new File("./src/test/samples/simple.md");

    private DirectoryStatistic processed;
    private DirectoryStatistic notProcessed;

    private FileStatistic fileProcessed;
    private FileStatistic fileNotProcessed;

    @Before
    public void setUp() throws Exception {
        processed = new DirectoryStatistic(sampleDir);
        notProcessed = new DirectoryStatistic(sampleDir);

        fileProcessed = new FileStatistic(sampleMd, sampleMd);
        fileNotProcessed = new FileStatistic(sampleMd);

        processed.add(fileProcessed);
        processed.add(fileNotProcessed);
        processed.add(notProcessed);
    }

    @Test
    public void testGetSource() throws Exception {
        assertEquals(sampleDir.getName(), processed.getSource().getName());
        assertEquals(sampleDir.getName(), notProcessed.getSource().getName());
    }

    @Test
    public void testGetOutput() throws Exception {
        assertEquals(sampleDir.getName(), processed.getOutput().getName());
        assertEquals(sampleDir.getName(), notProcessed.getOutput().getName());
    }

    @Test
    public void testIsProcessed() throws Exception {
        assertTrue(processed.isProcessed());
        assertFalse(notProcessed.isProcessed());
    }

    @Test
    public void testGetSize() throws Exception {
        assertTrue(3000 < processed.getSize());
        assertEquals(0, notProcessed.getSize());
    }

    @Test
    public void testGetSizeAll() throws Exception {
        assertTrue(3000 < processed.getSizeAll());
        assertEquals(0, notProcessed.getSizeAll());
    }

    @Test
    public void testGetCount() throws Exception {
        assertEquals(2, processed.getCount());
        assertEquals(0, notProcessed.getCount());
    }

    @Test
    public void testGetCountAll() throws Exception {
        assertEquals(2, processed.getCountAll());
        assertEquals(0, notProcessed.getCountAll());
    }

    @Test
    public void testGetNotProcessedFiles() throws Exception {
        assertEquals(1, processed.getNotProcessedFiles().size());
        assertEquals(0, notProcessed.getNotProcessedFiles().size());
    }

    @Test
    public void testGetNotProcessedFilesAll() throws Exception {
        assertEquals(1, processed.getNotProcessedFilesAll().size());
        assertEquals(0, notProcessed.getNotProcessedFilesAll().size());
    }

    @Test
    public void testGetFiles() throws Exception {
        assertSame(fileProcessed, processed.getFiles().get(0));
        assertTrue(notProcessed.getFiles().isEmpty());
    }

}