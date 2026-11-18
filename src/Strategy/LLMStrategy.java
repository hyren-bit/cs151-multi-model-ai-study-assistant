package Strategy;

import java.util.concurrent.CompletableFuture;
import Model.LLMResponse;

/**
 * Strategy interface: the common operation shared by all LLM strategies.
 * Application logic (the Controller) depends on this interface, never on
 * a concrete strategy.
 */
public interface LLMStrategy {

    /**
     * Returns the name of the LLM shown in the GUI.
     *
     * @return the display name, e.g. "Llama"
     */
    String getDisplayName();

    /**
     * Starts an asynchronous request to the LLM represented by this strategy.
     * Does not update the Model or the GUI; it only returns the future result.
     *
     * @param question the user's question
     * @return a future that completes with the LLM's response
     */
    CompletableFuture<LLMResponse> generate(String question);
}
