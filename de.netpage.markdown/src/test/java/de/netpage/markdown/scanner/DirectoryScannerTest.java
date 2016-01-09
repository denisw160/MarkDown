package de.netpage.markdown.scanner;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Testfälle für DirectoryScanner.
 */
public class DirectoryScannerTest {

    private final File baseDir = new File("./src/test/samples");
    private final File noSubDir = new File("./src/test/samples/full");
    private final File fileError = new File("./src/test/samples/notexists");
    private final File fileError2 = new File("./src/test/samples/example-image.jpg");

    @Test
    public void testFindBase() throws Exception {
        final Scanner scanner = new DirectoryScanner(baseDir);
        final List<File> files = scanner.find();
        assertEquals(3, files.size());
        final File file = findFile(files, "full");
        assertEquals("full", file.getName());
        assertTrue(file.isDirectory());
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
    public void testFindEmpty() throws Exception {
        final Scanner scanner = new DirectoryScanner(noSubDir);
        final List<File> files = scanner.find();
        assertEquals(0, files.size());
    }

    @Test
    public void testFindError() throws Exception {
        try {
            final Scanner scanner = new DirectoryScanner(fileError);
            final List<File> files = scanner.find();
            fail("Exception nicht ausgelöst für " + files.size());
        } catch (final RuntimeException ignore) {
        }
    }

    @Test
    public void testFindError2() throws Exception {
        try {
            final Scanner scanner = new DirectoryScanner(fileError2);
            final List<File> files = scanner.find();
            fail("Exception nicht ausgelöst für " + files.size());
        } catch (final RuntimeException ignore) {
        }
    }

}