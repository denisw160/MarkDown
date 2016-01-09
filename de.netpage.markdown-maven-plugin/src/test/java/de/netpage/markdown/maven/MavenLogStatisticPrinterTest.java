package de.netpage.markdown.maven;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.statistic.StatisticPrinter;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Testfälle für MavenLogStatisticPrinter.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 25.05.15
 */
@SuppressWarnings("FieldCanBeLocal")
public class MavenLogStatisticPrinterTest {

    private final File sampleDir = new File("../de.netpage.markdown/src/test/resources/de/netpage/pcloud/service/markdown/sample");
    private final File sampleMd = new File("../de.netpage.markdown/src/test/resources/de/netpage/pcloud/service/markdown/sample/sample.md");

    private DirectoryStatistic processed;
    private DirectoryStatistic notProcessed;

    private FileStatistic fileProcessed;
    private FileStatistic fileNotProcessed;

    private StatisticPrinter printer;

    @Before
    public void setUp() throws Exception {
        printer = new MavenLogStatisticPrinter(getLog());

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

    private Log getLog() {
        return new SystemStreamLog();
    }

}