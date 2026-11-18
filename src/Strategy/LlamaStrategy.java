package Strategy;

import Client.OllamaClient;

/** Strategy for the llama3.2:1b Ollama model. */
public class LlamaStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "llama3.2:1b";

    public LlamaStrategy(OllamaClient client) {
        super(client, "Llama", MODEL_NAME);
    }
}
