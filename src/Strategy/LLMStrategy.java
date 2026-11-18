package Strategy;

import Model.LLMResponse;
import java.util.concurrent.CompletableFuture;

/**
 * Strategy interface shared by all supported language models.
 * The Controller depends on this interface rather than concrete strategies.
 */
public interface LLMStrategy {

    /** @return the model's display name used by the application */
    String getDisplayName();

    /**
     * Starts an asynchronous generation request.
     * This method does not update the Model or GUI.
     *
     * @param question user's question
     * @return future completed with the model's response
     */
    CompletableFuture<LLMResponse> generate(String question);
}
