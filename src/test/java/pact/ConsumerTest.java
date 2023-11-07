package pact;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import au.com.dius.pact.core.model.annotations.PactDirectory;
import org.apache.hc.client5.http.fluent.Request;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "ProviderTest", pactVersion = PactSpecVersion.V3)
@PactDirectory("build/pacts/samples")
public class ConsumerTest {

    @Nested
    class Test1 {

    @Pact(provider = "ProviderTest", consumer = "ConsumerTest")
    RequestResponsePact getUserFragment(PactDslWithProvider builder) {
        DslPart body = PactDslJsonArray.arrayMaxLike(5)
                .id("id", Long.parseLong("1"))
                .stringType("userName", "Prudhvi")
                .stringType("email", "prudhvi@test.com")
                .closeObject();
        return builder
                .given("user Prudhvi")
                .uponReceiving("get user of Prudhvi")
                .path("/user/1")
                .method("GET")
                .willRespondWith()
                .status(200)
                .body(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "getUserFragment")
    void runTestForGet(MockServer mockServer) throws IOException {
        assertThat(Request.get(mockServer.getUrl() + "/user/1").execute().returnContent().asString(),
                is("[{\"email\":\"prudhvi@test.com\",\"id\":1,\"userName\":\"Prudhvi\"}]"));
    }

}
}


