package studyassistant.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Sends prompts to the local Ollama server
 * and returns the generated response text.
 */
public class OllamaClient {

    private final HttpClient client = HttpClient.newHttpClient();

    public CompletableFuture<String> generate(String modelName, String prompt) {
        String json =
                "{\"model\":\"" + modelName
                + "\",\"prompt\":\"" + prompt
                + "\",\"stream\":false}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:11434/api/generate"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException(
                                "Ollama HTTP error " + response.statusCode());
                    }
                    return getResponseText(response.body());
                });
    }

    private String getResponseText(String json) {
        String marker = "\"response\":\"";
        int start = json.indexOf(marker) + marker.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }
}
