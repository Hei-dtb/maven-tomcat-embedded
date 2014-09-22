/**
 * Created by Hicham B.I. on 22/09/14.
 */

import org.apache.commons.cli.ParseException;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

public class TomcatCommandLineTest {

    @Test
    public void test_default() throws ParseException {
        TomcatCommandLine tcl = new TomcatCommandLine(new String[]{""});
        tcl.parse();

        assertThat(tcl.getPort()).isEqualTo(8080);
        assertThat(tcl.isDaemon()).isFalse();
    }

    @Test
    public void test_with_arguments() throws ParseException {
        TomcatCommandLine tcl = new TomcatCommandLine(new String[]{"-p", "9999", "-d"});
        tcl.parse();
        assertThat(tcl.getPort()).isEqualTo(9999);
        assertThat(tcl.isDaemon()).isTrue();
    }

    @Test
    public void test_fail_whith_bad_arguments() {
        TomcatCommandLine tcl = new TomcatCommandLine(new String[]{"-n", "noob"});
        try {
            tcl.parse();
            failBecauseExceptionWasNotThrown(ParseException.class);
        } catch (ParseException e) {
            assertThat(e).hasMessage("Unrecognized option: -n");
        }
    }
}
