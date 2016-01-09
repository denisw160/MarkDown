package de.netpage.markdown.processor;

import de.netpage.markdown.model.DirectoryStatistic;
import de.netpage.markdown.model.InvalidDirectoryException;
import de.netpage.markdown.model.OutputFormat;
import de.netpage.markdown.scanner.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.List;

/**
 * Processor verarbeitet ein / mehrere Verzeichnisse und konvertiert die gefundenen
 * Markdown-Dateien mit dem FileProcessor.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 09.08.2014
 */
public class DirectoryProcessor implements Processor {

    private static final Logger LOG = Logger.getLogger(DirectoryProcessor.class);

    private final File directory;
    private final String suffix;
    private final OutputFormat outputFormat;
    private final boolean recursive;

    /**
     * Vereinfachter Constructor. Recursiv wird mit false belegt.
     *
     * @param directory Verzeichnis
     * @param suffix    Endung
     */
    public DirectoryProcessor(final File directory,
                              final String suffix) {
        this(directory, suffix, OutputFormat.HTML, false);
    }

    /**
     * Constructor erstellt den Processor für das angegebene Verzeichnis.
     * Alle Dateien in diesem Verzeichnis werden gefunden, die zur passenden Endung gehören.
     * Ist rekurisiv angegeben, so werden auch alle Unterordner durchsucht und bearbeitet.
     *
     * @param directory Verzeichnis
     * @param suffix    Endung
     * @param recursive Unterordner mit einschließen
     */
    public DirectoryProcessor(final File directory,
                              final String suffix,
                              final OutputFormat outputFormat,
                              final boolean recursive) {

        this.recursive = recursive;
        this.directory = directory;
        this.outputFormat = outputFormat;
        this.suffix = suffix;

        if (!directory.isDirectory()) {
            throw new InvalidDirectoryException("Directory " + directory + " not exists or is not a directory");
        }
    }

    /**
     * Durchsucht die Verzeichnisse und verarbeitet die gefundenen Dateien.
     *
     * @return Statistic mit den verarbeiteten Dateien
     */
    @Override
    public DirectoryStatistic process() {
        LOG.debug("Processing directory " + directory);
        final DirectoryStatistic stat = new DirectoryStatistic(directory);

        final Scanner scanner = buildFileScanner(directory, suffix, false);
        final List<File> files = scanner.find();

        LOG.debug("Found " + files.size() + " files for processing");
        for (final File f : files) {
            final FileProcessor p = new FileProcessor(f, outputFormat);
            stat.add(p.process());
        }

        if (recursive) {
            LOG.debug("Start recursive for " + directory);
            final Scanner directoryScanner = buildDirectoryScanner(directory, false);
            final List<File> subDirectories = directoryScanner.find();
            for (final File d : subDirectories) {
                final DirectoryProcessor p = new DirectoryProcessor(d, suffix, outputFormat, true);

                stat.add(p.process());
            }
        }

        return stat;
    }

    /**
     * Baut den Scanner für Dateien zusammen.
     *
     * @param directory Verzeichnis
     * @param suffix    Dateiendung
     * @param recursive Rekursiv
     * @return Scanner
     */
    private Scanner buildFileScanner(final File directory, final String suffix, final boolean recursive) {
        if (recursive)
            return new RecursiveFileScanner(directory, suffix);
        return new FileScanner(directory, suffix);
    }

    /**
     * Baut den Scanner für Dateien zusammen.
     *
     * @param directory Verzeichnis
     * @param recursive Rekursiv
     * @return Scanner
     */
    private Scanner buildDirectoryScanner(final File directory, final boolean recursive) {
        if (recursive)
            return new RecursiveDirectoryScanner(directory);
        return new DirectoryScanner(directory);
    }

}
