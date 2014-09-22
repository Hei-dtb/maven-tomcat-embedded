/**
 * Created by Hicham B.I. on 22/09/14.
 */

import org.junit.Test;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

public class TomcatEmbeddedIT {

    @Test
    public void test_tomcat_launched() throws Exception {
        URL url = new URL("http://localhost:8090");
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            assertThat(connection.getResponseMessage()).isEqualTo("OK");
        } catch (Exception e) {
            assertFalse("Tomcat must be launched to succeed", true);
        }
    }
}
