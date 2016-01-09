package de.netpage.markdown.scanner;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Testfälle für RecursiveFileScanner.
 */
public class RecursiveFileScannerTest {

    private final File baseDir = new File("./src/test/samples");
    private final File fileError = new File("src/test/samples/notexists");

    @Test
    public void testFindBase() throws Exception {
        final Scanner scanner = new RecursiveFileScanner(baseDir, ".txt");
        final List<File> files = scanner.find();
        assertEquals(4, files.size());
        final File file = findFile(files, "lists.txt");
        assertEquals("lists.txt", file.getName());
    }

    @Test
    public void testFind() throws Exception {
        final Scanner scanner = new RecursiveFileScanner(baseDir, ".jpg");
        final List<File> files = scanner.find();
        assertEquals(4, files.size());
        final File file = findFile(files, "example-image.jpg");
        assertEquals("example-image.jpg", file.getName());
    }

    private File findFile(final List<File> files, final String s) {
        for (final File f : files) {
            if (f.getName().equalsIgnoreCase(s)) {
                return f;
            }
        }
        return null;
    }

    @Test
    public void testFindError() throws Exception {
        try {
            final Scanner scanner = new RecursiveFileScanner(fileError, ".jpg");
            final List<File> files = scanner.find();
            fail("Exception nicht ausgelöst für " + files.size());
        } catch (final RuntimeException ignore) {
        }
    }

}