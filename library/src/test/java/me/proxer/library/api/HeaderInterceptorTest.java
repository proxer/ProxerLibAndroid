package me.proxer.library.api;

import me.proxer.library.BuildConfig;
import me.proxer.library.ProxerTest;
import me.proxer.library.util.ProxerUrls;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ruben Gees
 */
public class HeaderInterceptorTest extends ProxerTest {

    @Test
    public void testApiKey() throws IOException, ProxerException, InterruptedException {
        server.enqueue(new MockResponse().setBody(fromResource("news.json")));
        api.notifications().news().build().execute();

        assertThat(server.takeRequest().getHeaders().get("proxer-api-key")).isEqualTo("mockKey");
    }

    @Test
    public void testDefaultUserAgent() throws IOException, ProxerException, InterruptedException {
        server.enqueue(new MockResponse().setBody(fromResource("news.json")));
        api.notifications().news().build().execute();

        assertThat(server.takeRequest().getHeaders().get("User-Agent"))
                .isEqualTo("ProxerLibJava/" + BuildConfig.VERSION);
    }

    @Test
    public void testCustomUserAgent() throws IOException, ProxerException, InterruptedException {
        api = constructApi()
                .userAgent("test")
                .build();

        server.enqueue(new MockResponse().setBody(fromResource("news.json")));
        api.notifications().news().build().execute();

        assertThat(server.takeRequest().getHeaders().get("User-Agent")).isEqualTo("test");
    }

    @Test
    public void testHeadersOnlyForProxerHost() throws Exception {
        startHttpOnlyServer();

        server.enqueue(new MockResponse());

        //noinspection ConstantConditions
        api.client().newCall(new Request.Builder().url(HttpUrl.parse("http://example.com" + server.getPort() + "test"))
                .build()).execute();

        final Headers headers = server.takeRequest().getHeaders();

        assertThat(headers.get("proxer-api-key")).isNull();
        assertThat(headers.get("User-Agent")).doesNotStartWith("ProxerLibJava/");
    }

    @Test
    public void testCorrectHeadersForCdn() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());
        api.client().newCall(new Request.Builder().url(ProxerUrls.cdnBase()
                .newBuilder()
                .addPathSegment("test")
                .build()).build()).execute();

        final Headers headers = server.takeRequest().getHeaders();

        assertThat(headers.get("proxer-api-key")).isNull();
        assertThat(headers.get("User-Agent")).startsWith("ProxerLibJava/");
    }

    @Test
    public void testCorrectHeadersForStreamServer() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());
        api.client().newCall(new Request.Builder().url("https://stream.proxer.me/test").build()).execute();

        final Headers headers = server.takeRequest().getHeaders();

        assertThat(headers.get("proxer-api-key")).isNull();
        assertThat(headers.get("User-Agent")).startsWith("ProxerLibJava/");
    }

    @Test
    public void testCorrectHeadersForSpecificStreamServer() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());
        api.client().newCall(new Request.Builder().url("https://s3-ps.proxer.me/test").build()).execute();

        final Headers headers = server.takeRequest().getHeaders();

        assertThat(headers.get("proxer-api-key")).isNull();
        assertThat(headers.get("User-Agent")).startsWith("ProxerLibJava/");
    }

    @Test
    public void testCorrectHeadersForMangaServer() throws IOException, InterruptedException {
        server.enqueue(new MockResponse());
        api.client().newCall(new Request.Builder().url("https://manga0.proxer.me/test").build()).execute();

        final Headers headers = server.takeRequest().getHeaders();

        assertThat(headers.get("proxer-api-key")).isNull();
        assertThat(headers.get("User-Agent")).startsWith("ProxerLibJava/");
    }
}
