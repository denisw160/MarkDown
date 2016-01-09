package de.netpage.markdown.scanner;

import java.io.File;
import java.util.List;

/**
 * Dies ist das allgemeine Interface für Scanner.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public interface Scanner {

    /**
     * Liefert alle Dateien zurück, die zur Definition des Scanners passen.
     *
     * @return Liste der passenden Dateien.
     */
    List<File> find();

}
