package de.netpage.markdown.model;

/**
 * Es handelt sich nicht um ein gültiges Vezeichnis.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 10.08.2014
 */
public class InvalidDirectoryException extends RuntimeException {

    private static final long serialVersionUID = -5628592625200416612L;

    /**
     * Erstellt die Exception.
     *
     * @param message Nachricht
     */
    public InvalidDirectoryException(final String message) {
        super(message);
    }

}
