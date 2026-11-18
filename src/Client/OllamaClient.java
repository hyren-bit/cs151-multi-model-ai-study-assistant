package Client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * Sends non-streaming generation requests to a local Ollama server.
 *
 * The endpoint constructor also makes the client easy to test against a
 * temporary HTTP server without requiring a real Ollama installation.
 */
public class OllamaClient {

    private static final URI DEFAULT_ENDPOINT =
            URI.create("http://localhost:11434/api/generate");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(120);

    private final HttpClient httpClient;
    private final URI endpoint;

    /** Creates a client that uses the default local Ollama endpoint. */
    public OllamaClient() {
        this(HttpClient.newHttpClient(), DEFAULT_ENDPOINT);
    }

    /** Creates a client that sends requests to the supplied endpoint. */
    public OllamaClient(URI endpoint) {
        this(HttpClient.newHttpClient(), endpoint);
    }

    /** Constructor for dependency injection and focused testing. */
    public OllamaClient(HttpClient httpClient, URI endpoint) {
        this.httpClient = Objects.requireNonNull(httpClient, "httpClient");
        this.endpoint = Objects.requireNonNull(endpoint, "endpoint");
    }

    /**
     * Generates a response asynchronously.
     *
     * @param modelName Ollama model tag, for example "llama3.2:1b"
     * @param prompt user's prompt
     * @return future containing the generated response text
     */
    public CompletableFuture<String> generate(String modelName, String prompt) {
        Objects.requireNonNull(modelName, "modelName");
        Objects.requireNonNull(prompt, "prompt");

        String requestBody = "{"
                + "\"model\":\"" + escapeJson(modelName) + "\","
                + "\"prompt\":\"" + escapeJson(prompt) + "\","
                + "\"stream\":false"
                + "}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(endpoint)
                .timeout(REQUEST_TIMEOUT)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new OllamaHttpException(
                                response.statusCode(), response.body());
                    }
                    return extractResponseText(response.body());
                });
    }

    /** Escapes a Java string for use as a JSON string value. */
    private static String escapeJson(String value) {
        StringBuilder escaped = new StringBuilder();
        for (int i = 0; i < value.length(); i++) {
            char ch = value.charAt(i);
            switch (ch) {
                case '"': escaped.append("\\\""); break;
                case '\\': escaped.append("\\\\"); break;
                case '\b': escaped.append("\\b"); break;
                case '\f': escaped.append("\\f"); break;
                case '\n': escaped.append("\\n"); break;
                case '\r': escaped.append("\\r"); break;
                case '\t': escaped.append("\\t"); break;
                default:
                    if (ch < 0x20) {
                        escaped.append(String.format("\\u%04x", (int) ch));
                    } else {
                        escaped.append(ch);
                    }
            }
        }
        return escaped.toString();
    }

    /**
     * Reads the top-level JSON "response" string and decodes JSON escapes.
     * This intentionally avoids adding a third-party JSON dependency.
     */
    private static String extractResponseText(String json) {
        if (json == null) {
            throw new InvalidResponseException("Ollama returned an empty body.");
        }

        int key = json.indexOf("\"response\"");
        if (key < 0) {
            throw new InvalidResponseException(
                    "Ollama response does not contain a response field.");
        }

        int colon = json.indexOf(':', key + "\"response\"".length());
        if (colon < 0) {
            throw new InvalidResponseException(
                    "Ollama response field is malformed.");
        }

        int index = colon + 1;
        while (index < json.length() && Character.isWhitespace(json.charAt(index))) {
            index++;
        }
        if (index >= json.length() || json.charAt(index) != '"') {
            throw new InvalidResponseException(
                    "Ollama response field is not a JSON string.");
        }

        StringBuilder value = new StringBuilder();
        index++;
        while (index < json.length()) {
            char ch = json.charAt(index++);
            if (ch == '"') {
                return value.toString();
            }
            if (ch != '\\') {
                value.append(ch);
                continue;
            }
            if (index >= json.length()) {
                throw new InvalidResponseException(
                        "Ollama response contains an incomplete escape sequence.");
            }

            char escaped = json.charAt(index++);
            switch (escaped) {
                case '"': value.append('"'); break;
                case '\\': value.append('\\'); break;
                case '/': value.append('/'); break;
                case 'b': value.append('\b'); break;
                case 'f': value.append('\f'); break;
                case 'n': value.append('\n'); break;
                case 'r': value.append('\r'); break;
                case 't': value.append('\t'); break;
                case 'u':
                    if (index + 4 > json.length()) {
                        throw new InvalidResponseException(
                                "Ollama response contains an invalid Unicode escape.");
                    }
                    try {
                        value.append((char) Integer.parseInt(
                                json.substring(index, index + 4), 16));
                    } catch (NumberFormatException exception) {
                        throw new InvalidResponseException(
                                "Ollama response contains an invalid Unicode escape.");
                    }
                    index += 4;
                    break;
                default:
                    throw new InvalidResponseException(
                            "Ollama response contains an invalid JSON escape.");
            }
        }

        throw new InvalidResponseException(
                "Ollama response contains an unterminated response string.");
    }

    /** Indicates that Ollama returned a non-200 HTTP status. */
    public static class OllamaHttpException extends RuntimeException {
        private final int statusCode;

        public OllamaHttpException(int statusCode, String responseBody) {
            super("Ollama HTTP error " + statusCode
                    + (responseBody == null || responseBody.isBlank()
                    ? "" : ": " + responseBody));
            this.statusCode = statusCode;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }

    /** Indicates that Ollama returned malformed or unexpected JSON. */
    public static class InvalidResponseException extends RuntimeException {
        public InvalidResponseException(String message) {
            super(message);
        }
    }
}
