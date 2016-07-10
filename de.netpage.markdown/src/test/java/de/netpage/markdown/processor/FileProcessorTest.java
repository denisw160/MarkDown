package de.netpage.markdown.processor;

import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.model.OutputFormat;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Testfälle für FileProcessor.
 */
public class FileProcessorTest {

    private final File inputfile = new File("src/test/samples/simple.md");
    private final File outputfileHtml = new File("src/test/samples/simple.html");
    private final File outputfilePdf = new File("src/test/samples/simple.pdf");

    @Before
    public void setUp() throws Exception {
        assertTrue(inputfile.exists());

        if (outputfileHtml.exists()) {
            assertTrue(outputfileHtml.delete());
        }
        assertFalse(outputfileHtml.exists());

        if (outputfilePdf.exists()) {
            assertTrue(outputfilePdf.delete());
        }
        assertFalse(outputfilePdf.exists());
    }

    @Test
    public void testProcessHtml() throws Exception {
        final FileProcessor fileProcessor = new FileProcessor(inputfile);
        final FileStatistic statistic = fileProcessor.process();

        assertTrue(outputfileHtml.exists());
        assertNotNull(statistic);

        assertEquals(inputfile.getParentFile().getAbsolutePath(), statistic.getSource().getParentFile().getAbsolutePath());
        assertEquals(inputfile.getName(), statistic.getSource().getName());
        assertTrue(statistic.isProcessed());
        assertEquals(OutputFormat.HTML, statistic.getOutputFormat());
        assertEquals(outputfileHtml.getName(), statistic.getOutput().getName());
        assertTrue(statistic.getSize() > 4200);
    }

    @Test
    public void testProcessPdf() throws Exception {
        final FileProcessor fileProcessor = new FileProcessor(inputfile, OutputFormat.PDF);
        final FileStatistic statistic = fileProcessor.process();

        assertTrue(outputfilePdf.exists());
        assertNotNull(statistic);

        assertEquals(inputfile.getParentFile().getAbsolutePath(), statistic.getSource().getParentFile().getAbsolutePath());
        assertEquals(inputfile.getName(), statistic.getSource().getName());
        assertTrue(statistic.isProcessed());
        assertEquals(OutputFormat.PDF, statistic.getOutputFormat());
        assertEquals(outputfilePdf.getName(), statistic.getOutput().getName());
        assertTrue(0 < statistic.getSize());
        assertNotNull(statistic.toString());
    }

    @Test
    public void testProcessFailed() throws Exception {
        final File fileMock = Mockito.mock(File.class);
        Mockito.when(fileMock.getParentFile()).thenReturn(inputfile.getParentFile());
        final FileProcessor fileProcessor = new FileProcessor(fileMock);
        final FileStatistic statistic = fileProcessor.process();

        assertFalse(outputfileHtml.exists());
        assertNotNull(statistic);
        assertFalse(statistic.isProcessed());
    }

}