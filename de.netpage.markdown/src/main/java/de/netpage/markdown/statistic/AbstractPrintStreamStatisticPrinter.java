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

    private static final String CONSOLE_FORMAT = "| %-40s | %-40s | %9s | %9s |";
    private static final int FILE_LENGTH = 40;

    private static final String LINE = "---------------------------------------------------------------------------------------------------------------";
    private static final String MORE = "...";

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
        final String formatNewLine = format + "%n";
        getStream().format(formatNewLine, args);
    }

    /**
     * Gibt die Zusammenfassung aus.
     *
     * @param statistic Statistic für die Ausgabe
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
     *
     * @param basePath  Basis-Pfad
     * @param statistic Statistic für die Ausgabe
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
     *
     * @param basePath  Basis-Pfad
     * @param statistic Statistic für die Ausgabe
     */
    protected void printDirectory(final DirectoryStatistic statistic, final String basePath) {
        // Header für die Tabelle
        if (statistic.getCountAll() > 0) {
            println();
            println(CONSOLE_FORMAT, "Input", "Output", "Processed", "Size (kB)");
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
     *
     * @param basePath Basis-Pfad
     * @param f        Statistic für die Ausgabe
     */
    protected void printFile(final FileStatistic f, final String basePath) {
        final String input = StringUtils.replace(f.getSource().getAbsolutePath(), basePath, "");
        final String output = f.isProcessed() ? f.getOutput().getName() : "";
        println(CONSOLE_FORMAT,
                reduce(input, FILE_LENGTH, false),
                reduce(output, FILE_LENGTH, true),
                f.isProcessed(),
                f.getSize() / BYTE_TO_KB);
    }

    /**
     * Kürze den angebenen String auf die maximale Länge.
     * Wenn der String zu lang ist, dann wird der Überstand durch
     * ... ersetzt.
     *
     * @param length      Max. Länge des neuen Strings
     * @param reduce      String zum Kürzen
     * @param reduceStart Nur am Start kürzen
     * @return gekürtzer String
     */
    protected String reduce(final String reduce, final int length, final boolean reduceStart) {

        if (reduce.length() <= length) return reduce;

        final String result;
        final int len = length - MORE.length();
        if (reduceStart) {
            result = MORE + StringUtils.right(reduce, len);
        } else {
            final int halfLen = len / 2;
            result = StringUtils.left(reduce, halfLen) + MORE + StringUtils.right(reduce, halfLen);
        }

        return result;
    }

}
