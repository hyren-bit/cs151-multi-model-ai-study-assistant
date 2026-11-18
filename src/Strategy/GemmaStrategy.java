package Strategy;

import Client.OllamaClient;

/** Strategy for the gemma3:1b Ollama model. */
public class GemmaStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "gemma3:1b";

    public GemmaStrategy(OllamaClient client) {
        super(client, "Gemma", MODEL_NAME);
    }
}
