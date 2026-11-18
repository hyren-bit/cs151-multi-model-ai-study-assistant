package studyassistant.strategy;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import studyassistant.client.OllamaClient;
import studyassistant.model.LLMResponse;

/**
 * Shared behavior of the concrete strategies: delegate HTTP communication
 * to OllamaClient and convert the String result into an LLMResponse.
 * Subclasses only supply the display name and the Ollama model name.
 *
 * Failures are reported as an unsuccessful LLMResponse (not an exceptional
 * future), so one failing model never breaks the other models that were
 * asked the same question.
 */
public abstract class OllamaStrategy implements LLMStrategy {

    private final OllamaClient client;
    private final String displayName;
    private final String modelName;

    protected OllamaStrategy(OllamaClient client, String displayName, String modelName) {
        this.client = Objects.requireNonNull(client, "client");
        this.displayName = displayName;
        this.modelName = modelName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param question the user's question; must not be null
     * @throws NullPointerException if question is null
     */
    @Override
    public CompletableFuture<LLMResponse> generate(String question) {
        Objects.requireNonNull(question, "question");
        long start = System.currentTimeMillis();

        return client.generate(modelName, question)
                .handle((text, error) -> {
                    long elapsed = System.currentTimeMillis() - start;
                    if (error != null) {
                        Throwable cause = (error instanceof CompletionException
                                && error.getCause() != null) ? error.getCause() : error;
                        return LLMResponse.failure(displayName, cause.getMessage(), elapsed);
                    }
                    return LLMResponse.success(displayName, text, elapsed);
                });
    }
}
