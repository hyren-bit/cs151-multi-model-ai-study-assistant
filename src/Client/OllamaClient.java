
package Client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class OllamaClient {

    private final HttpClient client;
    private final URI endpoint;
    private final Gson gson;

    // Default constructor: connect to local Ollama
    public OllamaClient() {
        this(URI.create("http://localhost:11434/api/chat"));
    }

    // Allow tests to provide their own local server endpoint
    public OllamaClient(URI endpoint) {
        this.client = HttpClient.newHttpClient();
        this.endpoint = Objects.requireNonNull(endpoint, "endpoint");
        this.gson = new Gson();
    }

    public CompletableFuture<String> generate(String modelName, String prompt) {

        Objects.requireNonNull(modelName, "modelName");
        Objects.requireNonNull(prompt, "prompt");

        // Build the messages array
        JsonObject userMessage = new JsonObject();
        userMessage.addProperty("role", "user");
        userMessage.addProperty("content", prompt);

        JsonArray messages = new JsonArray();
        messages.add(userMessage);

        // Build the request JSON
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", modelName);
        requestBody.add("messages", messages);
        requestBody.addProperty("stream", false);

        String json = gson.toJson(requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpoint)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.sendAsync(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                )
                .thenApply(response -> {

                    // Handle HTTP errors
                    if (response.statusCode() != 200) {
                        throw new RuntimeException(
                                "Ollama HTTP error "
                                        + response.statusCode()
                                        + ": "
                                        + response.body()
                        );
                    }

                    // Parse the response JSON
                    return getResponseText(response.body());
                });
    }

    private String getResponseText(String json) {

        JsonElement root;

        try {
            root = JsonParser.parseString(json);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid JSON response from Ollama", e
            );
        }

        if (!root.isJsonObject()) {
            throw new RuntimeException(
                    "Invalid Ollama response: expected a JSON object"
            );
        }

        JsonObject responseObject = root.getAsJsonObject();

        if (!responseObject.has("message")
                || !responseObject.get("message").isJsonObject()) {
            throw new RuntimeException(
                    "Invalid Ollama response: missing message object"
            );
        }

        JsonObject message = responseObject.getAsJsonObject("message");

        if (!message.has("content")
                || !message.get("content").isJsonPrimitive()
                || !message.get("content").getAsJsonPrimitive().isString()) {
            throw new RuntimeException(
                    "Invalid Ollama response: missing message.content"
            );
        }

        String content = message.get("content").getAsString();

        if (content.isBlank()) {
            throw new RuntimeException(
                    "Invalid Ollama response: empty message.content"
            );
        }

        return content;
    }
}
