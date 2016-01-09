package de.netpage.markdown.statistic;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.model.Statistic;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintStream;

/**
 * Allgemeiner StatisticPrinter, der auf einer Implementierung eines
 * PrintStreams basiert.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 25.05.2015
 */
public abstract class AbstractPrintStreamStatisticPrinter implements StatisticPrinter {

    private static final int BYTE_TO_KB = 1024;
    private static final String LINE = "------------------------------------------------------------------------";

    private final PrintStream stream;

    /**
     * Erstellt den Printer, der die Ausgabe in den angegebenen Stream umleitet.
     *
     * @param stream Stream für die Ausgabe
     */
    protected AbstractPrintStreamStatisticPrinter(final PrintStream stream) {
        this.stream = stream;
    }

    /**
     * Liefert ein Ausgabeformat der Statistik zurück.
     *
     * @param statistic Statistik
     */
    @Override
    public void print(final Statistic statistic) {
        final String basePath = statistic.getSource().getAbsolutePath();
        println("Statistic for path: " + basePath);
        println();

        // Verzeichnis-Angaben
        printSummary(statistic);

        // Datei-Angaben
        printDetail(statistic, basePath);
        println();
    }

    /**
     * Liefert den PrintStream zurück.
     *
     * @return PrintStream
     */
    protected PrintStream getStream() {
        return stream;
    }

    /**
     * Schreibt eine leere Zeile.
     */
    protected void println() {
        println(" ");
    }

    /**
     * Schreibt die Zeile.
     *
     * @param line Zeile
     */
    protected void println(final String line) {
        getStream().println(line);
    }

    /**
     * Schreibt die Zeile im Format.
     *
     * @param format Format der Zeile
     * @param args   Ausgabeparameter
     */
    protected void println(final String format, final Object... args) {
        getStream().format(format + "%n", args);
    }

    /**
     * Gibt die Zusammenfassung aus.
     */
    protected void printSummary(final Statistic statistic) {
        if (statistic instanceof DirectoryStatistic) {
            final DirectoryStatistic dstat = (DirectoryStatistic) statistic;

            final String format = "%-20s %5s";
            println(format, "File size (kB):", dstat.getSizeAll() / BYTE_TO_KB);
            println(format, "File count:", dstat.getCountAll());
            println(format, "Files not processed:", dstat.getNotProcessedFilesAll().size());
        }
    }

    /**
     * Gibt die Dateien aus.
     */
    protected void printDetail(final Statistic statistic, final String basePath) {
        if (statistic instanceof FileStatistic) {
            printFile((FileStatistic) statistic, basePath);
        } else if (statistic instanceof DirectoryStatistic) {
            printDirectory((DirectoryStatistic) statistic, basePath);
        }
    }

    /**
     * Gibt die Details für die DirectoryStatistic aus.
     */
    protected void printDirectory(final DirectoryStatistic statistic, final String basePath) {
        // Header für die Tabelle
        if (statistic.getCountAll() > 0) {
            println();
            // TODO Kürze zu lange Namen
            println("| %-20s | %-20s | %10s | %12s |", "Input", "Output", "Processed", "Size (kB)");
            println(LINE);

            // Ausgabe pro Datei
            for (final FileStatistic f : statistic.getFilesAll()) {
                printFile(f, basePath);
            }

            println(LINE);
        }
    }

    /**
     * Schreibt eine Zeile der Statistik für die Datei.
     */
    protected void printFile(final FileStatistic f, final String basePath) {
        // TODO Kürze zu lange Namen
        println("| %-20s | %-20s | %10s | %12s |",
                StringUtils.replace(f.getSource().getAbsolutePath(), basePath, ""),
                f.isProcessed() ? f.getOutput().getName() : "",
                f.isProcessed(),
                f.getSize() / BYTE_TO_KB);
    }

}
