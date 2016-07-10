package de.netpage.markdown.statistic;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Testfälle für PrintStreamStatisticPrinter.
 */
public class PrintStreamStatisticPrinterTest {

    private final File sampleDir = new File("./src/test/samples");
    private final File sampleMd = new File("./src/test/samples/simple.md");

    private final File sampleLongDir = new File("./src/test/samples/recursive/longnamesdirectory/longnamesdirectory");
    private final File sampleLongMd = new File("./src/test/samples/recursive/longnamesdirectory/longnamesdirectory/simplelongfilenames.md");
    private final File sampleLong2Md = new File("./src/test/samples/recursive/longnamesdirectory/simplelongfilenamesVeryLongFileNameWithMore.md");
    private final File sampleLongNotProcessedMd = new File("./src/test/samples/recursive/longnamesdirectory/longnamesdirectory/notprocessed.md");

    private DirectoryStatistic processed;
    private DirectoryStatistic processedLong;
    private DirectoryStatistic notProcessed;

    private StatisticPrinter printer;

    @Before
    public void setUp() throws Exception {
        printer = new PrintStreamStatisticPrinter(System.out);

        processed = new DirectoryStatistic(sampleDir);
        processedLong = new DirectoryStatistic(sampleLongDir);
        notProcessed = new DirectoryStatistic(sampleDir);

        final FileStatistic fileProcessed = new FileStatistic(sampleMd, sampleMd);
        final FileStatistic fileNotProcessed = new FileStatistic(sampleMd);
        final FileStatistic fileProcessedLong = new FileStatistic(sampleLongMd, sampleLongMd);
        final FileStatistic fileProcessedLong2 = new FileStatistic(sampleLong2Md, sampleLong2Md);
        final FileStatistic fileNotProcessedLong = new FileStatistic(sampleLongNotProcessedMd);

        processed.add(fileProcessed);
        processed.add(fileNotProcessed);
        processed.add(notProcessed);

        processedLong.add(fileProcessedLong);
        processedLong.add(fileProcessedLong2);
        processedLong.add(fileNotProcessedLong);
    }

    @Test
    public void testPrintStatistic() throws Exception {
        printer.print(processed);
    }

    @Test
    public void testPrintStatisticLong() throws Exception {
        processed.add(processedLong);
        printer.print(processed);
    }

    @Test
    public void testPrintStatisticNotProcessed() throws Exception {
        printer.print(notProcessed);
    }

}