package de.netpage.markdown.output;

import org.apache.log4j.Logger;

/**
 * OutputWriter auf Log4j-Basis.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public class Log4jOutputWriter implements OutputWriter {

    /**
     * Level auf dem die Ausgabe ausgegeben wird.
     */
    public enum Level {
        /**
         * Debug-Level.
         */
        DEBUG,

        /**
         * Info-Level.
         */
        INFO,

        /**
         * Warning-Level.
         */
        WARN,

        /**
         * Error-Level.
         */
        ERROR
    }

    private final Logger logger;
    private final Level level;

    /**
     * Erstellt den Log4jOutputWriter. Der Logger wird
     * aus dem angebenen Namen erstellt. Das Level gibt die
     * Kategorie für die Ausgabe an.
     *
     * @param loggerName Name des Logger
     * @param level      Level der Log4j-Ausgabe
     */
    public Log4jOutputWriter(final String loggerName, final Level level) {
        this.logger = Logger.getLogger(loggerName);
        this.level = level;
    }

    @Override
    public void println() {
        println("");
    }

    @Override
    public void println(final String line) {
        switch (level) {
            case ERROR:
                logger.error(line);
                break;
            case WARN:
                logger.warn(line);
                break;
            case INFO:
                logger.info(line);
                break;
            case DEBUG:
                logger.debug(line);
                break;
            default:
                logger.trace(line);
        }
    }
}
