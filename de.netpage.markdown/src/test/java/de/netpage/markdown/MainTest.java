package de.netpage.markdown;

import org.junit.Test;

/**
 * Testfall für Main.
 *
 * @author Denis Wirries
 * @version 1.0
 * @since 03.05.15
 */
public class MainTest {

    @Test
    public void testMain() throws Exception {
        Main.main(new String[]{"-d", "./doc"});
    }

    @Test
    public void testMainHelp() throws Exception {
        Main.main(new String[]{"-h"});
    }

    @Test
    public void testMainNoneArgs() throws Exception {
        Main.main(new String[]{});
    }

}