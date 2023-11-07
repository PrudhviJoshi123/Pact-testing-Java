package pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import groovy.json.JsonOutput;
import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.common.ContentTypes.APPLICATION_JSON;
import static com.google.common.net.HttpHeaders.CONTENT_TYPE;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProviderTestPost", pactVersion = PactSpecVersion.V3)
@PactDirectory("build/pacts/samples")
public class ConsumerTestPost {

    @Nested
    class Test1 {


        @Pact(provider = "ProviderTestPost", consumer = "ConsumerTestPost")
        RequestResponsePact createFragment1(PactDslWithProvider builder) {
            return builder
                    .given("User Prudhvi")
                    .uponReceiving("a post call for Prudhvi")
                    .path("/users/prudhvi")
                    .method("POST")
                    .body("{\n" +
                            "  \"firstName\": \"Prudhvi\",\n" +
                            "  \"lastName\": \"Raj\"\n" +
                            "}")
                    .willRespondWith()
                    .status(200)
                    .toPact();
        }

        @Test
        void runTestPostPrudhvi(MockServer mockServer) throws IOException {
            ClassicHttpResponse postResponse = (ClassicHttpResponse) Request.post(mockServer.getUrl() + "/users/prudhvi")
                    .bodyString("{\n" +
                            "  \"firstName\": \"Prudhvi\",\n" +
                            "  \"lastName\": \"Raj\"\n" +
                            "}", ContentType.APPLICATION_JSON)
                    .execute().returnResponse();

            Assertions.assertEquals(postResponse.getCode(), 200);

        }
    }
}
