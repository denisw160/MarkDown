package de.netpage.markdown.scanner;

import de.netpage.markdown.model.InvalidDirectoryException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dieser Scanner sucht in dem angegeben Verzeichnis nach allen Unterordnern,
 * sowie deren Unterordnern.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class RecursiveDirectoryScanner implements Scanner {

    private final File directory;

    /**
     * Erstellt den DirectoryScanner und konfiguriert das Verzeichnis.
     *
     * @param directory Verzeichnis für die Suche
     */
    public RecursiveDirectoryScanner(final File directory) {
        this.directory = directory;

        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory " + directory + " not exists or is not a directory");
        }
    }

    /**
     * Liefert eine Liste der Unterordner, sowie dessen Unterordnern zurück,
     * die im Ordner des Scanners existieren.
     *
     * @return Liste mit Unterordnern.
     */
    @Override
    public List<File> find() {
        return findRecurive(directory);
    }

    /**
     * Führt die Suche rekursive aus.
     */
    private List<File> findRecurive(final File directory) {
        final List<File> result = new ArrayList<File>();

        // 1. Schritt alle Unterordner in diesem Verzeichnis ermitteln
        final List<File> subDirectories = getSubDirectories(directory);
        result.addAll(subDirectories);

        // 2. Schritt rekursive die Unterordner abarbeiten
        for (final File dir : subDirectories) {
            result.addAll(findRecurive(dir));
        }

        return result;
    }

    /**
     * Liefert alle Subdirectories zurück.
     */
    private List<File> getSubDirectories(final File directory) {
        final File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory();
            }
        });

        return (files == null) ? null : Arrays.asList(files);
    }

}
