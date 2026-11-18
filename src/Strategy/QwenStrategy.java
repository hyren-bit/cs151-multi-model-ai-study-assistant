package Strategy;

import Client.OllamaClient;

/** Strategy for the qwen3:1.7b Ollama model. */
public class QwenStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "qwen3:1.7b";

    public QwenStrategy(OllamaClient client) {
        super(client, "Qwen", MODEL_NAME);
    }
}
