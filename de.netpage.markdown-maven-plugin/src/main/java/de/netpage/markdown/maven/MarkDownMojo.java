package de.netpage.markdown.maven;

import de.netpage.markdown.model.OutputFormat;
import de.netpage.markdown.model.Statistic;
import de.netpage.markdown.processor.DirectoryProcessor;
import de.netpage.markdown.statistic.StatisticPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Dies ist das Maven-Plugin für den Aufruf der MarkDown Bibliothek.
 * Es reagiert auf das Goal "markdown:markdown". Standardmäßig wird
 * es in der Phase "package" ausgeführt.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 17.05.15
 */
@Mojo(name = "markdown", defaultPhase = LifecyclePhase.PACKAGE)
public class MarkDownMojo extends AbstractMojo {

    private static final String BLANK = "";
    private static final String LINE = "------------------------------------------------------------------------";

    /**
     * Parameter für das zu verarbeitende Verzeichnis.
     */
    @Parameter(name = "directory", required = true)
    private String directory;
    private File directoryFile;

    /**
     * Parameter für die Endung der MarkDown-Dateien.
     * Standard = .md
     */
    @Parameter(name = "suffix", defaultValue = ".md")
    private String suffix;

    /**
     * Parameter für das Ausgabeformat (HTML oder PDF).
     * Standard = HTML
     */
    @Parameter(name = "outputFormat", defaultValue = "HTML")
    private String outputFormat;
    private OutputFormat outputFormatEnum;

    /**
     * Parameter für die Verarbeitung von untergeordneten Verzeichnissen.
     * true = mit untergeordneten Verzeichnissen.
     * false = nur angegebenes Verzeichnis.
     * Standard = false
     */
    @Parameter(name = "recursive", defaultValue = "false")
    private String recursive;
    private boolean recursiveBool;

    // ***** Setter *****

    public void setDirectory(final String directory) {
        this.directory = directory;
    }

    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }

    public void setOutputFormat(final String outputFormat) {
        this.outputFormat = outputFormat;
    }

    public void setRecursive(final String recursive) {
        this.recursive = recursive;
    }

    // ***** Methods *****

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        displayHeader();
        checkParameter();

        final DirectoryProcessor p = new DirectoryProcessor(directoryFile, suffix, outputFormatEnum, recursiveBool);
        printStatistics(p.process());
    }

    /**
     * Zeigt den Header für die Anwendung an.
     */
    private void displayHeader() {
        getLog().info(BLANK);
        getLog().info(LINE);
        getLog().info("MarkDown-Maven-Plugin");
        getLog().info(LINE);
        getLog().info(BLANK);
    }

    /**
     * Überprüft die Parameter.
     * Sollte Argumente fehlen wird eine Fehlermeldung ausgegeben.
     *
     * @throws MojoExecutionException Parameter sind nicht richtig gefüllt.
     */
    private void checkParameter() throws MojoExecutionException {
        if (StringUtils.isBlank(directory)) {
            final String message = "Parameter directory is required";
            getLog().error(message);
            throw new MojoExecutionException(message);
        } else if (StringUtils.isBlank(suffix)) {
            final String message = "Parameter suffix is required";
            getLog().error(message);
            throw new MojoExecutionException(message);
        } else if (StringUtils.isBlank(outputFormat)) {
            final String message = "Parameter outputformat is required";
            getLog().error(message);
            throw new MojoExecutionException(message);
        } else if (StringUtils.isBlank(recursive)) {
            final String message = "Parameter recursive is required";
            getLog().error(message);
            throw new MojoExecutionException(message);
        }

        directoryFile = new File(directory);
        if (!directoryFile.isDirectory()) {
            final String message = "Parameter directory is not vaild: " + directoryFile.getAbsoluteFile();
            getLog().error(message);
            throw new MojoExecutionException(message);
        }

        recursiveBool = Boolean.valueOf(recursive);

        try {
            outputFormatEnum = OutputFormat.valueOf(outputFormat);
        } catch (final Exception e) {
            final String message = "Parameter outputformat is invalid";
            getLog().error(message);
            throw new MojoExecutionException(message, e);
        }
    }

    /**
     * Gibt die Zusammenfassung zurück.
     *
     * @param statistic Statistik
     */
    private void printStatistics(final Statistic statistic) {
        final StatisticPrinter printer = new MavenLogStatisticPrinter(getLog());
        printer.print(statistic);
    }

}
