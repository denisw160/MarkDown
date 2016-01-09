package de.netpage.markdown.scanner;

import de.netpage.markdown.model.InvalidDirectoryException;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dieser Scanner sucht in dem angegeben Verzeichnis nach allen Dateien mit der angegebenen Endung.
 * Dabei untersucht er auch alle untergeordneten Verzeichnisse.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class RecursiveFileScanner implements Scanner {

    private final File directory;
    private final String suffix;

    /**
     * Erstellt den RecursiveFileScanner und konfiguriert das Verzeichnis
     * und die Endung der zu suchenden Dateien.
     *
     * @param directory Verzeichnis für die Suche
     * @param suffix    Endung der Datei
     */
    public RecursiveFileScanner(final File directory, final String suffix) {
        this.directory = directory;
        this.suffix = suffix;

        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory " + directory + " not exists or is not a directory");
        }
    }

    /**
     * Sucht in dem Verzeichnis nach allen Dateien, die auf die angebene Endung enden.
     * Ebenso werden auch die untergeordneten Verzeichnisse durchsucht.
     *
     * @return Liste der passenden Dateien.
     */
    @Override
    public List<File> find() {
        return findRecurive(directory, suffix);
    }

    /**
     * Führt die Suche rekursive aus.
     */
    private List<File> findRecurive(final File directory, final String suffix) {
        final List<File> result = new ArrayList<File>();

        // 1. Schritt alle Dateien in diesem Verzeichnis ermitteln
        result.addAll(getFiles(directory, suffix));

        // 2. Schritt rekursive die Unterordner durchsuchen
        final File[] subdirectories = getSubDirectories(directory);
        for (final File dir : subdirectories) {
            result.addAll(findRecurive(dir, suffix));
        }

        return result;
    }

    /**
     * Liefert alle Dateien zurück.
     */
    private List<File> getFiles(final File directory, final String suffix) {
        final File[] currentFiles = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(suffix);
            }
        });

        return (currentFiles == null) ? null : Arrays.asList(currentFiles);
    }

    /**
     * Liefert alle Subdirectories zurück.
     */
    private File[] getSubDirectories(final File directory) {
        return directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isDirectory();
            }
        });
    }

}
