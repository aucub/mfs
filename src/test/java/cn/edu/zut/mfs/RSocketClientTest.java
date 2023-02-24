package cn.edu.zut.mfs;

import cn.edu.zut.mfs.model.Message;
import io.rsocket.metadata.WellKnownMimeType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeType;

import java.net.URI;

public class RSocketClientTest {
    private static RSocketRequester rsocketRequester;

    @BeforeAll
    public static void setUp() throws Exception {
        RSocketStrategies rSocketStrategies = RSocketStrategies.builder()
                .encoder(new Jackson2JsonEncoder())
                .decoder(new Jackson2JsonDecoder())
                .build();
        rsocketRequester = RSocketRequester.builder()
                .dataMimeType(MimeType.valueOf(WellKnownMimeType.APPLICATION_JSON.getString()))
                .metadataMimeType(MimeType.valueOf(WellKnownMimeType.MESSAGE_RSOCKET_COMPOSITE_METADATA.getString()))
                .rsocketStrategies(rSocketStrategies)
                .connectWebSocket(URI.create("ws://127.0.0.1:9898/rsocket"))
                .block();
    }

    @AfterAll
    public static void tearDown() {
        rsocketRequester.rsocket().dispose();
    }

    @Test
    public void testRequestResponse() {
        Message json = rsocketRequester.route("message.request-response").data("test").retrieveMono(Message.class).block();
        System.out.println(json);
    }
}
