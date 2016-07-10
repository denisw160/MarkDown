package de.netpage.markdown.maven;

import de.netpage.markdown.statistic.AbstractLoggerStatisticPrinter;
import de.netpage.markdown.statistic.StatisticPrinter;
import org.apache.maven.plugin.logging.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Dieser Print übergibt die Ausgabe an den Maven Logger.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 25.05.2014
 */
public class MavenLogStatisticPrinter extends AbstractLoggerStatisticPrinter implements StatisticPrinter {

    /**
     * Erstellt einen neuen Printer, der die Ausgabe in den Logger umleitet.
     *
     * @param log Logger
     */
    public MavenLogStatisticPrinter(final Log log) {
        super(new PrintStream(new LogOutputStream(log), false));
    }

    /**
     * Stream für die Ausgabe des Maven Logs.
     */
    private static final class LogOutputStream extends ByteArrayOutputStream {

        private final Log log;

        /**
         * Creates a new byte array output stream. The buffer capacity is
         * initially 32 bytes, though its size increases if necessary.
         */
        public LogOutputStream(final Log log) {
            this.log = log;
        }

        @Override
        public void flush() throws IOException {
            log.info(this.toString());
            this.reset();
            super.flush();
        }
    }

}
