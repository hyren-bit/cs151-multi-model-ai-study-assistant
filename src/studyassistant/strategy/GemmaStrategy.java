package studyassistant.strategy;

import studyassistant.client.OllamaClient;

/** Concrete Strategy that asks the gemma3:1b model through OllamaClient. */
public class GemmaStrategy extends OllamaStrategy {

    private static final String MODEL_NAME = "gemma3:1b";

    public GemmaStrategy(OllamaClient client) {
        super(client, "Gemma", MODEL_NAME);
    }
}
