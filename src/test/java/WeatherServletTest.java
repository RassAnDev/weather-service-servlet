import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.weatherserviceservlet.App;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class WeatherServletTest {

    private static int port;
    private static String hostname = "localhost";
    private static Tomcat app;
    private static String baseUrl;

    @BeforeAll
    public static void setup() throws IOException, ParseException, LifecycleException {
        app = App.getApp(0);
        app.start();
        port = app.getConnector().getLocalPort();
        baseUrl = "http://" + hostname + ":" + port;
    }

    @Test
    void testGetWeather() throws IOException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet(baseUrl + "/");
        CloseableHttpResponse response = client.execute(request);

        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity);

        assertThat(response.getCode()).isEqualTo(200);
        assertThat(content).contains("Voronezh");
        assertThat(content).contains("472045");
        assertThat(content).doesNotContain("London");
    }

    @AfterAll
    public static void tearDown() throws LifecycleException {
        app.stop();
    }
}
