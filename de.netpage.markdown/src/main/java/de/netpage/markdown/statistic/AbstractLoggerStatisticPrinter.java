package de.netpage.markdown.statistic;

import java.io.PrintStream;

/**
 * Dies ist die gemeinesame Implementierung für eine Implementierung,
 * die auf einem Logger basiert.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 25.05.2015
 */
public abstract class AbstractLoggerStatisticPrinter extends AbstractPrintStreamStatisticPrinter implements StatisticPrinter {

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Klassen-Logger umleitet.
     *
     * @param stream Ausgabe
     */
    protected AbstractLoggerStatisticPrinter(final PrintStream stream) {
        super(stream);
    }

    /**
     * Schreibt die Zeile.
     *
     * @param line Zeile
     */
    @Override
    protected void println(final String line) {
        getStream().print(line);
        getStream().flush();
    }

    /**
     * Schreibt die Zeile im Format.
     *
     * @param format Format der Zeile
     * @param args   Ausgabeparameter
     */
    @Override
    protected void println(final String format, final Object... args) {
        getStream().format(format, args);
        getStream().flush();
    }

}
