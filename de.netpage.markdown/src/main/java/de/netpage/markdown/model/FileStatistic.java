package de.netpage.markdown.model;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Dies ist die Zusammenfassung für eine Verarbeitung einer
 * MarkDown-Datei.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 18.08.2014
 */
public class FileStatistic implements Statistic {

    private final File source;
    private final File output;

    /**
     * Erstellt eine Statistik für die angegebene Datei.
     *
     * @param source Quelle
     */
    public FileStatistic(final File source) {
        this(source, null);
    }

    /**
     * Erstellt eine Statistik für die angegebene Datei.
     *
     * @param source Quelle
     * @param output Ausgabedatei
     */
    public FileStatistic(final File source, final File output) {
        this.source = source;
        this.output = output;
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
        return output;
    }

    /**
     * Liefert das Output-Format zurück.
     *
     * @return Output
     */
    public OutputFormat getOutputFormat() {
        if (!isProcessed()) return null;
        if (getOutput() == null) return OutputFormat.HTML;

        final String extension = FilenameUtils.getExtension(getOutput().getName());
        if (OutputFormat.PDF.name().equalsIgnoreCase(extension)) return OutputFormat.PDF;

        return OutputFormat.HTML;
    }

    /**
     * Wurde die Verarbeitung durchgeführt.
     *
     * @return Verarbeitung durchgeführt.
     */
    @Override
    public boolean isProcessed() {
        return output != null && output.exists();
    }

    /**
     * Liefert die Größe der Datei zurück.
     *
     * @return Größe der Datei
     */
    @Override
    public long getSize() {
        return (isProcessed()) ? output.length() : -1;
    }

    /**
     * Liefert eine Liste der verarbeiteten Verzeichnisse und
     * Dateien zurück.
     *
     * @return Liste der Verarbeitung
     */
    @Override
    public List<Statistic> getFiles() {
        return new ArrayList<Statistic>(Arrays.asList(this));
    }

}
