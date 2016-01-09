package de.netpage.markdown.scanner;

import de.netpage.markdown.model.InvalidDirectoryException;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Dieser Scanner sucht in dem angegeben Verzeichnis nach allen Dateien mit der angegebenen Endung.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 10.08.2014
 */
public class FileScanner implements Scanner {

    private final File directory;
    private final String suffix;

    /**
     * Erstellt den FileScanner und konfiguriert das Verzeichnis
     * und die Endung der zu suchenden Dateien.
     *
     * @param directory Verzeichnis für die Suche
     * @param suffix    Endung der Datei
     */
    public FileScanner(final File directory, final String suffix) {
        this.directory = directory;
        this.suffix = suffix;

        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory " + directory + " not exists or is not a directory");
        }
    }

    /**
     * Sucht in dem Verzeichnis nach allen Dateien, die auf die angebene Endung enden.
     *
     * @return Liste der passenden Dateien.
     */
    @Override
    public List<File> find() {
        final File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(final File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(suffix);
            }
        });

        return (files == null) ? null : Arrays.asList(files);
    }

}
