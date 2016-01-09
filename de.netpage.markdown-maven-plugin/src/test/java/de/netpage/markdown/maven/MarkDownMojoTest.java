package de.netpage.markdown.maven;

import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Testfälle für MarkDownMojo.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 17.05.15
 */
public class MarkDownMojoTest {

    // Check Parameter-Testfälle

    @Test(expected = MojoExecutionException.class)
    public void testExecuteNoDirectory() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteInvalidDirectory() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc/notexists");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteNotDirectory() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc/usage-plugin.md");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteNoSuffix() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc");
        mojo.setSuffix("");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteNoOutputFormat() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc");
        mojo.setSuffix(".md");
        mojo.setOutputFormat("");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteInvalidOutputFormat() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc");
        mojo.setSuffix(".md");
        mojo.setOutputFormat("XXX");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test(expected = MojoExecutionException.class)
    public void testExecuteNoRecusive() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc");
        mojo.setSuffix(".md");
        mojo.setOutputFormat("HTML");
        mojo.setRecursive("");
        assertNotNull(mojo);
        mojo.execute();
    }

    @Test()
    public void testExecuteValid() throws Exception {
        final MarkDownMojo mojo = new MarkDownMojo();
        mojo.setDirectory("./doc");
        mojo.setSuffix(".md");
        mojo.setOutputFormat("HTML");
        mojo.setRecursive("true");
        assertNotNull(mojo);
        mojo.execute();
    }

}