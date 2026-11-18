package Strategy;

import Client.OllamaClient;
import Client.OllamaClient.InvalidResponseException;
import Model.LLMResponse;
import Shared.ResponseState;

import java.net.http.HttpTimeoutException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

/**
 * Shared implementation for strategies that communicate through Ollama.
 * Concrete strategies provide only their display name and Ollama model tag.
 */
public abstract class OllamaStrategy implements LLMStrategy {

    private final OllamaClient client;
    private final String displayName;
    private final String modelName;

    protected OllamaStrategy(
            OllamaClient client, String displayName, String modelName) {
        this.client = Objects.requireNonNull(client, "client");
        this.displayName = Objects.requireNonNull(displayName, "displayName");
        this.modelName = Objects.requireNonNull(modelName, "modelName");
    }

    @Override
    public final String getDisplayName() {
        return displayName;
    }

    @Override
    public CompletableFuture<LLMResponse> generate(String question) {
        Objects.requireNonNull(question, "question");
        long startedAt = System.nanoTime();

        return client.generate(modelName, question)
                .handle((responseText, error) -> {
                    long elapsedMillis =
                            (System.nanoTime() - startedAt) / 1_000_000L;

                    if (error == null) {
                        return LLMResponse.success(
                                displayName, responseText, elapsedMillis);
                    }

                    Throwable cause = unwrap(error);
                    String message = cause.getMessage() == null
                            ? cause.getClass().getSimpleName()
                            : cause.getMessage();

                    if (cause instanceof HttpTimeoutException
                            || cause instanceof TimeoutException) {
                        return new LLMResponse(
                                displayName, "", ResponseState.TIMEOUT,
                                elapsedMillis, message);
                    }

                    if (cause instanceof InvalidResponseException) {
                        return new LLMResponse(
                                displayName, "", ResponseState.INVALID_RESPONSE,
                                elapsedMillis, message);
                    }

                    return LLMResponse.failure(
                            displayName, message, elapsedMillis);
                });
    }

    private static Throwable unwrap(Throwable error) {
        Throwable current = error;
        while ((current instanceof CompletionException
                || current instanceof ExecutionException)
                && current.getCause() != null) {
            current = current.getCause();
        }
        return current;
    }
}
