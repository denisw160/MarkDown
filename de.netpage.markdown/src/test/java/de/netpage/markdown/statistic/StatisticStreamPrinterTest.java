package de.netpage.markdown.statistic;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Testfälle für PrintStreamStatisticPrinter.
 */
@SuppressWarnings("FieldCanBeLocal")
public class StatisticStreamPrinterTest {

    private final File sampleDir = new File("./src/test/resources/de/netpage/pcloud/service/markdown/sample");
    private final File sampleMd = new File("./src/test/resources/de/netpage/pcloud/service/markdown/sample/sample.md");

    private DirectoryStatistic processed;
    private DirectoryStatistic notProcessed;

    private FileStatistic fileProcessed;
    private FileStatistic fileNotProcessed;

    private StatisticPrinter printer;

    @Before
    public void setUp() throws Exception {
        printer = new PrintStreamStatisticPrinter(System.out);

        processed = new DirectoryStatistic(sampleDir);
        notProcessed = new DirectoryStatistic(sampleDir);

        fileProcessed = new FileStatistic(sampleMd, sampleMd);
        fileNotProcessed = new FileStatistic(sampleMd);

        processed.add(fileProcessed);
        processed.add(fileNotProcessed);
        processed.add(notProcessed);
    }

    @Test
    public void testPrintStatistic() throws Exception {
        printer.print(processed);
    }

    @Test
    public void testPrintStatisticNotProcessed() throws Exception {
        printer.print(notProcessed);
    }

}