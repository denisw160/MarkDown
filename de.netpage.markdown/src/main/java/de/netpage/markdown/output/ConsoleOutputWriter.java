package de.netpage.markdown.output;

/**
 * Standard-Implementierung des OutputWriter.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public class ConsoleOutputWriter implements OutputWriter {

    @Override
    public void println() {
        System.out.println();
    }

    @Override
    public void println(final String line) {
        System.out.println(line);
    }

}
