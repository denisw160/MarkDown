package de.netpage.markdown.statistic;

import java.io.PrintStream;

/**
 * Printer für die Ausgabe mittels eines PrintStreams wie z.B. System.out.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class PrintStreamStatisticPrinter extends AbstractPrintStreamStatisticPrinter implements StatisticPrinter {

    /**
     * Erstellt den Printer, der die Ausgabe in den angegebenen Stream umleitet.
     *
     * @param stream Stream für die Ausgabe
     */
    public PrintStreamStatisticPrinter(final PrintStream stream) {
        super(stream);
    }

}
