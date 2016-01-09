package de.netpage.markdown.statistic;

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
public class Log4jStatisticPrinter extends AbstractLoggerStatisticPrinter implements StatisticPrinter {

    private static final Logger LOG = Logger.getLogger(Log4jStatisticPrinter.class);

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Klassen-Logger umleitet.
     *
     * @param level Level des Logs
     */
    public Log4jStatisticPrinter(final Level level) {
        this(LOG, level);
    }

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Logger umleitet.
     *
     * @param log   Logger
     * @param level Level des Logs
     */
    public Log4jStatisticPrinter(final Logger log, final Level level) {
        super(new PrintStream(new LoggingOutputStream(log, level), false));
    }

}
