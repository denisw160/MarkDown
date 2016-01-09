package de.netpage.markdown.processor;

import com.lowagie.text.DocumentException;
import de.netpage.markdown.model.FileStatistic;
import de.netpage.markdown.model.OutputFormat;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.markdown4j.Markdown4jProcessor;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;

/**
 * Dieser Processor verarbeitet genau eine Datei.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 09.08.2014
 */
public class FileProcessor implements Processor {

    private static final Logger LOG = Logger.getLogger(FileProcessor.class);

    private final File inputFile;
    private final OutputFormat outputFormat;

    private File outputFile;
    private boolean processed;

    /**
     * Erstellt den Processor für die angegebene Datei.
     * Als Format wird HTML verwendet.
     *
     * @param inputFile Eingabe
     */
    public FileProcessor(final File inputFile) {
        this(inputFile, OutputFormat.HTML);
    }

    /**
     * Erstellt den Processor für die angebene Datei und das Format.
     *
     * @param inputFile    Eingabe
     * @param outputFormat Format
     */
    public FileProcessor(final File inputFile, final OutputFormat outputFormat) {
        this.inputFile = inputFile;
        this.outputFile = null;
        this.outputFormat = outputFormat;
        this.processed = false;

        LOG.trace("FileProcessor for " + inputFile + " created - OutputFormat: " + outputFormat);
    }

    /**
     * Verarbeitet die Datei und liefert eine Statistik zurück.
     *
     * @return Statisitk über die verarbeitete Datei
     */
    @Override
    public FileStatistic process() {
        LOG.debug("Processing file " + inputFile);

        try {
            final String html = generateHtml();
            final String outputPath = inputFile.getParentFile().getAbsolutePath();
            final String outputName = extractOutputName(inputFile.getName());

            if (outputFormat == OutputFormat.PDF) {
                writePdf(outputPath, outputName, html);
            } else {
                writeHtml(outputPath, outputName, html);
            }

        } catch (final Exception e) {
            LOG.error("File " + inputFile + " cannot process", e);
        }

        return createStatistic();
    }

    /**
     * Extrahiert den Namen aus dem Eingangsdateinamen (ohne Endung).
     *
     * @param inputFileName Eingangsname
     * @return Ausgangsname
     */
    private String extractOutputName(final String inputFileName) {
        return FilenameUtils.removeExtension(inputFileName);
    }

    /**
     * Erstellt das HTML aus der MarkDown-Datei.
     *
     * @return HTML-Code der MarkDown-Datei
     * @throws IOException Fehler bei der Verarbeitung
     */
    private String generateHtml() throws IOException {
        //noinspection StringBufferReplaceableByString
        final StringBuilder sb = new StringBuilder();

        // Füge Header hinzu
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">\n");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        sb.append("<head>");
        sb.append("<style type=\"text/css\">");
        sb.append("pre {background-color:#C4C4C4;}");
        sb.append("</style>");
        sb.append("</head>");
        sb.append("<body>");

        // Inhalt hinzufügen
        sb.append(new Markdown4jProcessor().process(inputFile));

        // Füge Abschluss hinzu
        sb.append("</body>\n");
        sb.append("</html>");

        return sb.toString();
    }

    /**
     * Schreibt die Datei auf die Festplatte im Format HTML.
     *
     * @param outputPath Ausgabeverzeichnis
     * @param outputName Name der Zieldatei
     * @param content    Inhalt der Datei
     * @throws java.io.IOException Fehler beim Schreiben der Datei
     */
    private void writeHtml(final String outputPath,
                           final String outputName,
                           final String content) throws IOException {

        outputFile = new File(outputPath + File.separator + outputName + ".html");
        LOG.debug("Write to html " + outputFile);

        if (!outputFile.exists()) {
            final boolean exists = outputFile.createNewFile();
            LOG.debug("File created: " + exists);
        }

        final OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8");
        final PrintWriter out = new PrintWriter(writer);
        out.println(content);

        LOG.trace("Closing PrintWriter");
        out.close();

        processed = true;
    }

    /**
     * Schreint das PDF auf die Festplatte.
     *
     * @param outputPath Ausgabeverzeichnis
     * @param outputName Name der Zieldatei
     * @param content    Inhalt der Datei
     * @throws IOException       Fehler beim Schreiben der Datei
     * @throws DocumentException Fehler beim Schreiben des PDFs
     */
    private void writePdf(final String outputPath,
                          final String outputName,
                          final String content) throws IOException, DocumentException {

        outputFile = new File(outputPath + File.separator + outputName + ".pdf");
        LOG.debug("Write to pdf " + outputFile);

        if (!outputFile.exists()) {
            final boolean exists = outputFile.createNewFile();
            LOG.debug("File created: " + exists);
        }

        final ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(content, "file:///" + outputPath + File.separator);
        renderer.layout();

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputFile);
            renderer.createPDF(outputStream);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                LOG.trace("Closing Streams");
                outputStream.close();
            }
        }

        processed = true;
    }

    /**
     * Erstellt eine Statistik über die verarbeite Datei.
     *
     * @return Statistik
     */
    private FileStatistic createStatistic() {
        final FileStatistic stat;

        if (processed) {
            stat = new FileStatistic(inputFile, outputFile);
        } else {
            stat = new FileStatistic(inputFile);
        }

        return stat;
    }

}
