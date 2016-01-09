package de.netpage.markdown;

import de.netpage.markdown.model.OutputFormat;
import de.netpage.markdown.model.Statistic;
import de.netpage.markdown.output.ConsoleOutputWriter;
import de.netpage.markdown.output.OutputWriter;
import de.netpage.markdown.processor.DirectoryProcessor;
import de.netpage.markdown.statistic.PrintStreamStatisticPrinter;
import de.netpage.markdown.statistic.StatisticPrinter;
import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.PrintWriter;

/**
 * Dies ist die Hauptklasse der Anwenung. Sie startet die
 * Verarbeitung der MarkDown-Dateien.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 09.08.2014
 */
public final class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    private static final String APPLICATION_NAME = "markdown";

    private static final int ROW_WIDTH = 80;
    private static final int SPACES_BEFORE_OPTION = 3;
    private static final int SPACES_BEFORE_OPTION_DESCRIPTION = 5;

    private static final Options OPTIONS = new Options();
    private static final OutputWriter writer = new ConsoleOutputWriter();

    private static File directory;
    private static String suffix = ".md";
    private static OutputFormat outputFormat = OutputFormat.HTML;
    private static boolean recursive = false;

    private static boolean exit = false;

    static {
        OPTIONS.addOption("h", "help", false, "Display help")
                .addOption("d", "directory", true, "Directory for processing")
                .addOption("s", "suffix", true, "Suffix for files to processed")
                .addOption("f", "format", true, "Format for output [html|pdf]")
                .addOption("r", "recursive", false, "Process with subdirectories");
        OPTIONS.getOption("d").setRequired(true);
    }

    private Main() {
        // nur statische Methoden.
    }

    /**
     * Startet das Programm.
     *
     * @param commandLineArguments Commmand-line arguments
     */
    public static void main(final String[] commandLineArguments) {
        displayHeader();

        if (commandLineArguments.length < 1) {
            writer.println("-- USAGE --");
            printUsage();
            return;
        }

        displayProvidedCommandLineArguments(commandLineArguments);
        parse(commandLineArguments);

        if (!exit) {
            final DirectoryProcessor p = new DirectoryProcessor(directory, suffix, outputFormat, recursive);
            printStatistics(p.process());
        }
    }


    /**
     * Überführt die Command-line arguments in die Paramter der Klasse.
     *
     * @param commandLineArguments Command-line arguments für die Verarbeitung
     */
    private static void parse(final String[] commandLineArguments) {
        try {
            final CommandLineParser cmdLinePosixParser = new PosixParser();
            final CommandLine commandLine = cmdLinePosixParser.parse(OPTIONS, commandLineArguments);
            checkParameter(commandLine);
            setupParameter(commandLine);

        } catch (final ParseException parseException) {
            System.err.println("Encountered exception while parsing using PosixParser:\n" + parseException.getMessage());
            printHelp("MARKDOWN HELP", "", ROW_WIDTH, SPACES_BEFORE_OPTION, SPACES_BEFORE_OPTION_DESCRIPTION, true);
            exit = true;
        }
    }

    /**
     * Überprüft die Parameter.
     * Sollte Argumente fehlen wird eine Fehlermeldung ausgegeben.
     *
     * @param commandLine CommandLine
     */
    private static void checkParameter(final CommandLine commandLine) {
        if (commandLine.hasOption("help")) {
            printHelp("MARKDOWN HELP", "", ROW_WIDTH, SPACES_BEFORE_OPTION, SPACES_BEFORE_OPTION_DESCRIPTION, true);
            exit = true;
        }
        if (!commandLine.hasOption("directory")) {
            printHelp("MARKDOWN HELP", "", ROW_WIDTH, SPACES_BEFORE_OPTION, SPACES_BEFORE_OPTION_DESCRIPTION, true);
            exit = true;
        }
    }

    /**
     * Prüft die angegeben Parameter.
     *
     * @param commandLine CommandLine
     */
    private static void setupParameter(final CommandLine commandLine) {
        if (commandLine.hasOption("directory")) {
            directory = new File(commandLine.getOptionValue("directory"));
        }
        if (commandLine.hasOption("suffix")) {
            suffix = commandLine.getOptionValue("suffix");
        }
        if (commandLine.hasOption("format") &&
                OutputFormat.PDF.toString().equalsIgnoreCase(commandLine.getOptionValue("format"))) {
            outputFormat = OutputFormat.PDF;
        }
        if (commandLine.hasOption("recursive")) {
            recursive = true;
        }
    }

    /**
     * Zeigt den Header für die Anwendung an.
     */
    private static void displayHeader() {
        final String header = "MarkDown - Bibliothek zum Umwandeln von MD-Dateien in HTML oder PDF";
        writer.println(header);
        displayBlankLines(1);
    }

    /**
     * Zeigt die Command-line arguments an.
     *
     * @param commandLineArguments Command-line arguments für die Ausgage
     */
    private static void displayProvidedCommandLineArguments(final String[] commandLineArguments) {
        final StringBuilder buffer = new StringBuilder();
        for (final String argument : commandLineArguments) {
            buffer.append(argument).append(" ");
        }
        LOG.debug("CommandLine: " + buffer.toString());
        displayBlankLines(1);
    }

    /**
     * Schreibe eine leere Zeile in die Ausgabe.
     *
     * @param numberBlankLines Anzahl der leeren Zeilen
     */
    private static void displayBlankLines(final int numberBlankLines) {
        for (int i = 0; i < numberBlankLines; ++i) {
            writer.println();
        }
    }

    /**
     * Gibt die Zusammenfassung zurück.
     *
     * @param statistic Statistik
     */
    private static void printStatistics(final Statistic statistic) {
        final StatisticPrinter printer = new PrintStreamStatisticPrinter(System.out);
        printer.print(statistic);
    }


    /**
     * Gibt die Bedingungsanleitung aus.
     */
    private static void printUsage() {
        final PrintWriter writer = new PrintWriter(System.out);
        final HelpFormatter usageFormatter = new HelpFormatter();
        usageFormatter.printUsage(writer, ROW_WIDTH, APPLICATION_NAME, OPTIONS);
        writer.flush();
    }

    /**
     * Schreibt die Hilfe in die Ausgabe.
     */
    private static void printHelp(final String header,
                                  final String footer,
                                  final int printedRowWidth,
                                  final int spacesBeforeOption,
                                  final int spacesBeforeOptionDescription,
                                  final boolean displayUsage) {

        final String commandLineSyntax = "java -jar markdown.jar";
        final PrintWriter writer = new PrintWriter(System.out);
        final HelpFormatter helpFormatter = new HelpFormatter();

        helpFormatter.printHelp(
                writer,
                printedRowWidth,
                commandLineSyntax,
                header,
                OPTIONS,
                spacesBeforeOption,
                spacesBeforeOptionDescription,
                footer,
                displayUsage);

        writer.flush();
    }

}
