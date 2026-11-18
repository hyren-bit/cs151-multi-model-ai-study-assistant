package Strategy;

import Client.OllamaClient;

/** Concrete Strategy that asks the qwen3:1.7b model through OllamaClient. */
public class QwenStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "qwen3:1.7b";

    public QwenStrategy(OllamaClient client) {
        super(client, "Qwen", MODEL_NAME);
    }
}
