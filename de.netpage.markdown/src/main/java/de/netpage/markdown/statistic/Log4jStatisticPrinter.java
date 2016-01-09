package de.netpage.markdown.statistic;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.model.Statistic;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.LoggingOutputStream;

import java.io.PrintStream;

/**
 * Dieser Print übergibt die Ausgabe an den Log4J-Printer.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class Log4jStatisticPrinter implements StatisticPrinter {

    private static final Logger LOG = Logger.getLogger(Log4jStatisticPrinter.class);

    private static final int BYTE_TO_KB = 1024;

    private final PrintStream stream;

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Klassen-Logger umleitet.
     */
    public Log4jStatisticPrinter(final Level level) {
        this(LOG, level);
    }

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Logger umleitet.
     *
     * @param log Logger
     */
    public Log4jStatisticPrinter(final Logger log, final Level level) {
        this.stream = new PrintStream(new LoggingOutputStream(log, level), false);
    }

    /**
     * Liefert ein Ausgabeformat der Statistik zurück.
     */
    @Override
    public void print(final Statistic statistic) {
        final String basePath = statistic.getSource().getAbsolutePath();
        stream.print("Statistic for path: " + basePath);
        stream.flush();

        // Verzeichnis-Angaben
        printSummary(statistic);

        // Datei-Angaben
        printDetail(statistic, basePath);

        IOUtils.closeQuietly(stream);
    }

    /**
     * Gibt die Zusammenfassung aus.
     */
    private void printSummary(final Statistic statistic) {
        if (statistic instanceof DirectoryStatistic) {
            final DirectoryStatistic dstat = (DirectoryStatistic) statistic;

            final String format = "%-20s %5s";
            stream.format(format, "File size (kB):", dstat.getSizeAll() / BYTE_TO_KB);
            stream.flush();
            stream.format(format, "File count:", dstat.getCountAll());
            stream.flush();
            stream.format(format, "Files not processed:", dstat.getNotProcessedFilesAll().size());
            stream.flush();
        }
    }

    /**
     * Gibt die Dateien aus.
     */
    private void printDetail(final Statistic statistic, final String basePath) {
        if (statistic instanceof FileStatistic) {
            printFile((FileStatistic) statistic, basePath);
        } else if (statistic instanceof DirectoryStatistic) {
            printDirectory((DirectoryStatistic) statistic, basePath);
        }
    }

    /**
     * Gibt die Details für die DirectoryStatistic aus.
     */
    private void printDirectory(final DirectoryStatistic statistic, final String basePath) {
        // Header für die Tabelle
        if (statistic.getCountAll() > 0) {
            stream.format("%-20s | %-20s | %10s | %12s", "Input", "Output", "Processed", "Size (kB)");
            stream.flush();
            stream.print("-----------------------------------------------------------------------");
            stream.flush();

            // Ausgabe pro Datei
            for (final FileStatistic f : statistic.getFilesAll()) {
                printFile(f, basePath);
            }

            stream.print("-----------------------------------------------------------------------");
            stream.flush();
        }
    }

    /**
     * Schreibt eine Zeile der Statistik für die Datei.
     */
    private void printFile(final FileStatistic f, final String basePath) {
        stream.format("%-20s | %-20s | %10s | %12s",
                StringUtils.replace(f.getSource().getAbsolutePath(), basePath, ""),
                f.isProcessed() ? f.getOutput().getName() : "",
                f.isProcessed(),
                f.getSize() / BYTE_TO_KB);
        stream.flush();
    }

}
