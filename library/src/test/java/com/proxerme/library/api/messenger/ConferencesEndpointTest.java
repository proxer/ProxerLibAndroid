package com.proxerme.library.api.messenger;

import com.proxerme.library.ProxerTest;
import com.proxerme.library.api.ProxerException;
import com.proxerme.library.entitiy.messenger.Conference;
import com.proxerme.library.enums.ConferenceType;
import okhttp3.mockwebserver.MockResponse;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Ruben Gees
 */
public class ConferencesEndpointTest extends ProxerTest {

    @Test
    public void testDefault() throws ProxerException, IOException {
        server.enqueue(new MockResponse().setBody(fromResource("conferences.json")));

        final List<Conference> result = api.messenger()
                .conferences()
                .build()
                .execute();

        assertThat(result).first().isEqualTo(buildTestConference());
    }

    @Test
    public void testPath() throws ProxerException, IOException, InterruptedException {
        server.enqueue(new MockResponse().setBody(fromResource("conferences.json")));

        api.messenger().conferences()
                .page(0)
                .type(ConferenceType.GROUP)
                .build()
                .execute();

        Assertions.assertThat(server.takeRequest().getPath())
                .isEqualTo("/api/v1/messenger/conferences?p=0&type=group");
    }

    private Conference buildTestConference() {
        return new Conference("133663", "Novus4K", "", 2, "avatar",
                "513596_5DoIo2.png", false, true, new Date(1488810726L * 1000),
                0, "0");
    }
}
