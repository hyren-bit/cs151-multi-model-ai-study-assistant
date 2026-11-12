package View;
import Shared.*;
import javax.swing.*;

public class LLMResponseView extends JPanel implements ModelObserver {
    private final String modelName;

    public LLMResponseView(String modelName) {
        this.modelName = modelName;

    }

    @Override
    public void modelChanged() {
        //String modelName, ResponseState responseState, String modelOutput, int latency) {
        // read the current response for the modelName from the shared model and refresh this view
        // Requires enum (response state enum), int (response time in ms), String (the model output)
    }
}
