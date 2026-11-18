package studyassistant.strategy;

import studyassistant.client.OllamaClient;

/** Concrete Strategy that asks the llama3.2:1b model through OllamaClient. */
public class LlamaStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "llama3.2:1b";

    public LlamaStrategy(OllamaClient client) {
        super(client, "Llama", MODEL_NAME);
    }
}
