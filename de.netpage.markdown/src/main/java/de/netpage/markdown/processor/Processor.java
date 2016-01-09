package de.netpage.markdown.processor;

import de.netpage.markdown.model.Statistic;

/**
 * Dies ist das Interface für die MarkDown-Processoren.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 20.08.2014
 */
public interface Processor {

    /**
     * Durchsucht die Verzeichnisse und verarbeitet die gefundenen Dateien.
     *
     * @return Statistic mit den verarbeiteten Dateien
     */
    Statistic process();

}
