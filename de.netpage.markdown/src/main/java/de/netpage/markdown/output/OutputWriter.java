package de.netpage.markdown.output;

/**
 * Dieses Interface definiert die Schnittstelle für das Schreiben der
 * Ausgabe der Anwendung.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public interface OutputWriter {

    /**
     * Schreibt eine leere Zeile.
     */
    void println();

    /**
     * Schreibt eine Zeile.
     *
     * @param line Zeile
     */
    void println(String line);

}
