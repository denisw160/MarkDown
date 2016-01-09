package de.netpage.markdown.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Nimmt die Statistik für alle verarbeiteten Verzeichnisse auf.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class DirectoryStatistic implements Statistic {

    private final File source;

    /**
     * Speicher für die Statisiken des aktuellen Verzeichnisses.
     */
    private final List<FileStatistic> files;

    /**
     * Speicher für die untergeordneten Verzeichnisse.
     */
    private final List<DirectoryStatistic> directories;

    /**
     * Erstellt die Statisitk für die verarbeiteten Verzeichnisse.
     *
     * @param source Quelle der Statsitik
     */
    public DirectoryStatistic(final File source) {
        this.source = source;
        this.files = new ArrayList<FileStatistic>();
        this.directories = new ArrayList<DirectoryStatistic>();
    }

    /**
     * Fügt die Statisitk einer Datei an.
     *
     * @param statistic Statistik der Dateio im aktuellen Ordner
     */
    public void add(final FileStatistic statistic) {
        files.add(statistic);
    }

    /**
     * Fügt eine Statisitk für einen Unterordner hinzu.
     *
     * @param statistic Statistik für einen Unterordner
     */
    public void add(final DirectoryStatistic statistic) {
        directories.add(statistic);
    }

    /**
     * Liefert die Quelle der Statistik zurück.
     * Dies kann entweder eine Datei oder ein Verzeichnis sein.
     *
     * @return Quelldatei
     */
    @Override
    public File getSource() {
        return source;
    }

    /**
     * Liefert die Ausgabedatei zurück.
     *
     * @return Ausgabedatei
     */
    @Override
    public File getOutput() {
        return source;
    }

    /**
     * Wurde die Verarbeitung durchgeführt.
     *
     * @return Verarbeitung durchgeführt.
     */
    @Override
    public boolean isProcessed() {
        return !files.isEmpty();
    }

    /**
     * Liefert die Größe der Dateien zurück.
     *
     * @return Größe der Dateien
     */
    @Override
    public long getSize() {
        long size = 0;
        for (final FileStatistic f : files) {
            if (f.isProcessed())
                size += f.getSize();
        }

        return size;
    }

    /**
     * Liefert die Größe der aller Dateien zurück.
     *
     * @return Größe aller Dateien
     */
    public long getSizeAll() {
        long size = 0;
        size += getSize();

        for (final DirectoryStatistic d : directories) {
            size += d.getSizeAll();
        }

        return size;
    }

    /**
     * Liefert die Anzahl der verarbeiteten Dateien zurück.
     *
     * @return Anzahl der verarbeiteten Dateien
     */
    public int getCount() {
        return files.size();
    }

    /**
     * Liefert die Anzahl aller Dateien zurück.
     *
     * @return alle Dateien
     */
    public int getCountAll() {
        int count = 0;
        count += getCount();

        for (final DirectoryStatistic d : directories) {
            count += d.getCountAll();
        }

        return count;
    }

    /**
     * Liefert eine Liste der verarbeiteten Dateien zurück.
     *
     * @return Liste der Verarbeitung
     */
    @Override
    public List<Statistic> getFiles() {
        return new ArrayList<Statistic>(files);
    }

    /**
     * Liefert alle Dateien zurück, die verarbeitet wurden inkl. untergeordnete
     * Verzeichnisse.
     *
     * @return Alle Dateien
     */
    public List<FileStatistic> getFilesAll() {
        final List<FileStatistic> result = new ArrayList<FileStatistic>();
        result.addAll(files);

        for (final DirectoryStatistic d : directories) {
            result.addAll(d.getFilesAll());
        }

        return result;
    }

    /**
     * Liefert die nicht verarbeiteten Dateien zurück.
     *
     * @return Nicht verarbeitete Dateien
     */
    public List<FileStatistic> getNotProcessedFiles() {
        final List<FileStatistic> result = new ArrayList<FileStatistic>();
        for (final FileStatistic f : files) {
            if (!f.isProcessed()) result.add(f);
        }

        return result;
    }

    /**
     * Liefert die nicht verarbeiteten Dateien zurück,
     * sowie aus allen Unterordnern.
     *
     * @return Nicht verarbeitete Dateien
     */
    public List<FileStatistic> getNotProcessedFilesAll() {
        final List<FileStatistic> result = new ArrayList<FileStatistic>();
        result.addAll(getNotProcessedFiles());

        for (final DirectoryStatistic d : directories) {
            result.addAll(d.getNotProcessedFilesAll());
        }

        return result;
    }

}
