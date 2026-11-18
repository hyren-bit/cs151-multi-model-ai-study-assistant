package studyassistant.strategy;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import studyassistant.client.OllamaClient;
import studyassistant.model.LLMResponse;

/**
 * Tests the three concrete strategies against a fake Ollama server
 * on localhost:11434 (so no real Ollama or model is needed).
 * The fake server answers "answer from <model>" and returns HTTP 500
 * for gemma3:1b to simulate a failing model.
 */
class OllamaStrategyTest {

    private static HttpServer server;
    private static final OllamaClient CLIENT = new OllamaClient();

    @BeforeAll
    static void startFakeOllama() throws IOException {
        server = HttpServer.create(new InetSocketAddress(11434), 0);
        server.createContext("/api/generate", exchange -> {
            String body = new String(exchange.getRequestBody().readAllBytes(),
                    StandardCharsets.UTF_8);
            int status = 200;
            String reply;
            if (body.contains("\\"model\\":\\"gemma3:1b\\"")) {
                status = 500;
                reply = "error";
            } else {
                String model = body.replaceAll(".*\\"model\\":\\"([^\\"]+)\\".*", "$1");
                reply = "{\\"model\\":\\"" + model + "\\",\\"response\\":\\"answer from "
                        + model + "\\",\\"done\\":true}";
            }
            byte[] bytes = reply.getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(status, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        });
        server.start();
    }

    @AfterAll
    static void stopFakeOllama() {
        if (server != null) {
            server.stop(0);
        }
    }

    private static LLMResponse await(CompletableFuture<LLMResponse> future) throws Exception {
        return future.get(5, TimeUnit.SECONDS);
    }

    @Test
    void displayNamesAreCorrect() {
        assertEquals("Llama", new LlamaStrategy(CLIENT).getDisplayName());
        assertEquals("Gemma", new GemmaStrategy(CLIENT).getDisplayName());
        assertEquals("Qwen", new QwenStrategy(CLIENT).getDisplayName());
    }

    @Test
    void llamaUsesLlamaModel() throws Exception {
        LLMResponse r = await(new LlamaStrategy(CLIENT).generate("What is OOP?"));
        assertTrue(r.isSuccess());
        assertEquals("answer from llama3.2:1b", r.getText());
        assertEquals("Llama", r.getModelName());
    }

    @Test
    void qwenUsesQwenModel() throws Exception {
        LLMResponse r = await(new QwenStrategy(CLIENT).generate("What is OOP?"));
        assertTrue(r.isSuccess());
        assertEquals("answer from qwen3:1.7b", r.getText());
    }

    @Test
    void httpErrorBecomesFailedResponse() throws Exception {
        LLMResponse r = await(new GemmaStrategy(CLIENT).generate("What is OOP?"));
        assertFalse(r.isSuccess());
        assertNull(r.getText());
        assertTrue(r.getErrorMessage().contains("500"));
    }

    @Test
    void oneFailingStrategyDoesNotAffectTheOthers() throws Exception {
        // Controller-style use: only the LLMStrategy interface is visible.
        List<LLMStrategy> strategies = List.of(
                new LlamaStrategy(CLIENT), new GemmaStrategy(CLIENT), new QwenStrategy(CLIENT));

        List<CompletableFuture<LLMResponse>> futures =
                strategies.stream().map(s -> s.generate("What is OOP?")).toList();

        assertTrue(await(futures.get(0)).isSuccess());   // Llama
        assertFalse(await(futures.get(1)).isSuccess());  // Gemma
        assertTrue(await(futures.get(2)).isSuccess());   // Qwen
    }

    @Test
    void elapsedTimeIsRecorded() throws Exception {
        LLMResponse r = await(new LlamaStrategy(CLIENT).generate("Hi"));
        assertTrue(r.getElapsedMillis() >= 0);
    }

    @Test
    void nullQuestionIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new LlamaStrategy(CLIENT).generate(null));
    }

    @Test
    void nullClientIsRejected() {
        assertThrows(NullPointerException.class, () -> new QwenStrategy(null));
    }
}
