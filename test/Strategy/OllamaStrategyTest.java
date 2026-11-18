package Strategy;

import static org.junit.jupiter.api.Assertions.*;

import Client.OllamaClient;
import Model.LLMResponse;
import Shared.ResponseState;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests the Strategy Layer against a local fake Ollama HTTP server.
 * The server uses an ephemeral port, so these tests do not conflict with
 * a real Ollama process and do not require downloading any models.
 */
class OllamaStrategyTest {

    private static HttpServer server;
    private static OllamaClient client;

    @BeforeAll
    static void startFakeOllama() throws IOException {
        server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
        server.createContext("/api/generate", exchange -> {
            String requestBody = new String(
                    exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8);

            int status = 200;
            String responseBody;

            if (requestBody.contains("gemma3:1b")) {
                status = 500;
                responseBody = "{\"error\":\"simulated server error\"}";
            } else if (requestBody.contains("__invalid_response__")) {
                responseBody = "{\"response\":42,\"done\":true}";
            } else {
                String model = findModel(requestBody);
                String answer = "answer from " + model
                        + " with quote: \"hello\" and newline:\nnext line";
                responseBody = "{\"model\":\"" + model
                        + "\",\"response\":\"" + escapeJson(answer)
                        + "\",\"done\":true}";
            }

            byte[] bytes = responseBody.getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add(
                    "Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });
        server.start();

        int port = server.getAddress().getPort();
        client = new OllamaClient(
                URI.create("http://127.0.0.1:" + port + "/api/generate"));
    }

    @AfterAll
    static void stopFakeOllama() {
        if (server != null) {
            server.stop(0);
        }
    }

    private static String findModel(String body) {
        if (body.contains("llama3.2:1b")) {
            return "llama3.2:1b";
        }
        if (body.contains("qwen3:1.7b")) {
            return "qwen3:1.7b";
        }
        return "unknown";
    }

    private static String escapeJson(String value) {
        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static LLMResponse await(
            CompletableFuture<LLMResponse> future) throws Exception {
        return future.get(5, TimeUnit.SECONDS);
    }

    @Test
    void concreteStrategiesExposeExpectedDisplayNames() {
        assertEquals("Llama", new LlamaStrategy(client).getDisplayName());
        assertEquals("Gemma", new GemmaStrategy(client).getDisplayName());
        assertEquals("Qwen", new QwenStrategy(client).getDisplayName());
    }

    @Test
    void llamaStrategyUsesCorrectModelAndDecodesJsonEscapes() throws Exception {
        LLMResponse response = await(
                new LlamaStrategy(client).generate("What is OOP?"));

        assertTrue(response.isSuccess());
        assertEquals("Llama", response.getModelName());
        assertEquals(
                "answer from llama3.2:1b with quote: \"hello\" and newline:\nnext line",
                response.getText());
        assertEquals(ResponseState.SUCCESS, response.getStatus());
    }

    @Test
    void qwenStrategyUsesCorrectModel() throws Exception {
        LLMResponse response = await(
                new QwenStrategy(client).generate("What is OOP?"));

        assertTrue(response.isSuccess());
        assertTrue(response.getText().contains("qwen3:1.7b"));
    }

    @Test
    void httpErrorBecomesFailedResponse() throws Exception {
        LLMResponse response = await(
                new GemmaStrategy(client).generate("What is OOP?"));

        assertFalse(response.isSuccess());
        assertEquals(ResponseState.FAILED, response.getStatus());
        assertTrue(response.getErrorMessage().contains("500"));
    }

    @Test
    void malformedOllamaResponseBecomesInvalidResponse() throws Exception {
        LLMResponse response = await(
                new LlamaStrategy(client).generate("__invalid_response__"));

        assertEquals(ResponseState.INVALID_RESPONSE, response.getStatus());
        assertFalse(response.isSuccess());
        assertTrue(response.getErrorMessage().contains("not a JSON string"));
    }

    @Test
    void strategiesCanRunIndependentlyThroughTheInterface() throws Exception {
        List<LLMStrategy> strategies = List.of(
                new LlamaStrategy(client),
                new GemmaStrategy(client),
                new QwenStrategy(client));

        List<CompletableFuture<LLMResponse>> futures = strategies.stream()
                .map(strategy -> strategy.generate("What is OOP?"))
                .toList();

        assertTrue(await(futures.get(0)).isSuccess());
        assertFalse(await(futures.get(1)).isSuccess());
        assertTrue(await(futures.get(2)).isSuccess());
    }

    @Test
    void elapsedTimeIsRecorded() throws Exception {
        LLMResponse response = await(
                new LlamaStrategy(client).generate("Hi"));

        assertTrue(response.getElapsedMillis() >= 0);
    }

    @Test
    void nullQuestionIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new LlamaStrategy(client).generate(null));
    }

    @Test
    void nullClientIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new QwenStrategy(null));
    }
}
