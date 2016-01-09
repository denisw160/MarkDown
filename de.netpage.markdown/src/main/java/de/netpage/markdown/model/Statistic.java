package de.netpage.markdown.model;

import java.io.File;
import java.util.List;

/**
 * Dieses Interface definiert die Methoden und Eigenschaften
 * für die Statistik, die nach der Verarbeitung angezeigt wird.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public interface Statistic {

    /**
     * Liefert die Quelle der Statistik zurück.
     * Dies kann entweder eine Datei oder ein Verzeichnis sein.
     *
     * @return Quelldatei
     */
    File getSource();

    /**
     * Liefert die Ausgabedatei zurück.
     *
     * @return Ausgabedatei
     */
    File getOutput();

    /**
     * Wurde die Verarbeitung durchgeführt.
     *
     * @return Verarbeitung durchgeführt.
     */
    boolean isProcessed();

    /**
     * Liefert die Größe der Datei zurück.
     *
     * @return Größe der Datei
     */
    long getSize();

    /**
     * Liefert eine Liste der verarbeiteten Verzeichnisse und
     * Dateien zurück.
     *
     * @return Liste der Verarbeitung
     */
    List<Statistic> getFiles();

}
