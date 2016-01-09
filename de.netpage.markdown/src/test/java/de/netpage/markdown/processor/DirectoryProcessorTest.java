package de.netpage.markdown.processor;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.OutputFormat;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Testfälle für ProcessorDirectoryFileBased.
 */
public class DirectoryProcessorTest {


    private final File baseDir = new File("./src/test/samples/recursive");
    private final File fileError = new File("./src/test/samples/error.md");

    @Test
    public void testProcessRecursiveHtml() throws Exception {
        final DirectoryProcessor p = new DirectoryProcessor(baseDir, ".md", OutputFormat.HTML, true);

        final DirectoryStatistic statistic = p.process();
        assertNotNull(statistic);

        assertNotNull(statistic.getFilesAll());
        assertEquals(5, statistic.getFilesAll().size());

        assertNotNull(statistic.getNotProcessedFilesAll());
        assertEquals(0, statistic.getNotProcessedFilesAll().size());

        assertTrue("Size was " + statistic.getSizeAll(), statistic.getSizeAll() > 20500);
    }

    @Test
    public void testProcessRecursivePdf() throws Exception {
        final DirectoryProcessor p = new DirectoryProcessor(baseDir, ".md", OutputFormat.PDF, true);

        final DirectoryStatistic statistic = p.process();
        assertNotNull(statistic);
        assertEquals(5, statistic.getFilesAll().size());
        assertEquals(0, statistic.getNotProcessedFilesAll().size());
        assertTrue(statistic.getSizeAll() > 28600);
    }

    @Test
    public void testProcessNonRecursive() throws Exception {
        final DirectoryProcessor p = new DirectoryProcessor(baseDir, ".md");

        final DirectoryStatistic statistic = p.process();
        assertNotNull(statistic);
        assertEquals(1, statistic.getFilesAll().size());
        assertEquals(0, statistic.getNotProcessedFilesAll().size());
        assertTrue("Size was " + statistic.getSizeAll(), statistic.getSizeAll() > 4100);
    }

    @Test
    public void testProcessSubDirectory() throws Exception {
        final DirectoryProcessor p = new DirectoryProcessor(baseDir, ".txt", OutputFormat.PDF, true);

        final DirectoryStatistic statistic = p.process();
        assertNotNull(statistic);
        assertEquals(0, statistic.getFilesAll().size());
        assertEquals(0, statistic.getNotProcessedFilesAll().size());
        assertEquals(0, statistic.getSizeAll());
    }

    @Test
    public void testProcessNonDirectory() throws Exception {
        try {
            final DirectoryProcessor p = new DirectoryProcessor(fileError, ".md");
            fail("Exception für " + p + " nicht ausgelöst");
        } catch (final Exception ignore) {
        }
    }

    @Test
    public void testProcessAllHtml() throws Exception {
        DirectoryProcessor p = new DirectoryProcessor(baseDir, ".md", OutputFormat.HTML, true);

        assertNotNull(p.process());
        p = new DirectoryProcessor(baseDir, ".txt", OutputFormat.HTML, true);
        assertNotNull(p.process());
        p = new DirectoryProcessor(baseDir, ".text", OutputFormat.HTML, true);
        assertNotNull(p.process());
    }

    @Test
    public void testProcessAllPdf() throws Exception {
        DirectoryProcessor p = new DirectoryProcessor(baseDir, ".md", OutputFormat.PDF, true);

        assertNotNull(p.process());
        p = new DirectoryProcessor(baseDir, ".txt", OutputFormat.PDF, true);
        assertNotNull(p.process());
        p = new DirectoryProcessor(baseDir, ".text", OutputFormat.PDF, true);
        assertNotNull(p.process());
    }

}