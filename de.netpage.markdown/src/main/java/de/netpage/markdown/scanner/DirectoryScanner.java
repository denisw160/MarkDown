package de.netpage.markdown.scanner;

import de.netpage.markdown.model.InvalidDirectoryException;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Dieser Scanner sucht in dem angegeben Verzeichnis nach allen Unterordnern.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class DirectoryScanner implements Scanner {

    private final File directory;

    /**
     * Erstellt den DirectoryScanner und konfiguriert das Verzeichnis.
     *
     * @param directory Verzeichnis für die Suche
     */
    public DirectoryScanner(final File directory) {
        this.directory = directory;

        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory " + directory + " not exists or is not a directory");
        }
    }

    /**
     * Liefert eine Liste der Unterordner zurück,
     * die im Ordner des Scanners existieren.
     *
     * @return Liste mit Unterordnern.
     */
    @Override
    public List<File> find() {
        final File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory();
            }
        });

        return (files == null) ? null : Arrays.asList(files);
    }

}
