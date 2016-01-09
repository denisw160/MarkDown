package de.netpage.markdown.statistic;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.model.Statistic;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;

/**
 * Printer für die Ausgabe mittels eines PrintStreams wie z.B. System.out.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class PrintStreamStatisticPrinter implements StatisticPrinter {

    public static final int BYTE_TO_KB = 1024;

    private final PrintStream stream;

    /**
     * Erstellt den Printer, der die Ausgabe in den angegebenen Stream umleitet.
     */
    public PrintStreamStatisticPrinter(final PrintStream stream) {
        this.stream = stream;
    }

    /**
     * Liefert ein Ausgabeformat der Statistik zurück.
     */
    @Override
    public void print(final Statistic statistic) {
        final String basePath = statistic.getSource().getAbsolutePath();
        stream.println("Statistic for path: " + basePath);
        stream.println();

        // Verzeichnis-Angaben
        printSummary(statistic);

        // Datei-Angaben
        printDetail(statistic, basePath);
    }

    /**
     * Gibt die Zusammenfassung aus.
     */
    private void printSummary(final Statistic statistic) {
        if (statistic instanceof DirectoryStatistic) {
            final DirectoryStatistic dstat = (DirectoryStatistic) statistic;

            final String format = "%-20s %5s %n";
            stream.format(format, "File size (kB):", dstat.getSizeAll() / BYTE_TO_KB);
            stream.format(format, "File count:", dstat.getCountAll());
            stream.format(format, "Files not processed:", dstat.getNotProcessedFilesAll().size());
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
            stream.println();
            stream.format("| %-20s | %-20s | %10s | %12s |%n", "Input", "Output", "Processed", "Size (kB)");
            stream.println("---------------------------------------------------------------------------");

            // Ausgabe pro Datei
            for (final FileStatistic f : statistic.getFilesAll()) {
                printFile(f, basePath);
            }

            stream.println("---------------------------------------------------------------------------");
        }
    }

    /**
     * Schreibt eine Zeile der Statistik für die Datei.
     */
    private void printFile(final FileStatistic f, final String basePath) {
        // TODO Kürze zu lange Namen
        stream.format("| %-20s | %-20s | %10s | %12s |%n",
                StringUtils.replace(f.getSource().getAbsolutePath(), basePath, ""),
                f.isProcessed() ? f.getOutput().getName() : "",
                f.isProcessed(),
                f.getSize() / BYTE_TO_KB);
    }

}
