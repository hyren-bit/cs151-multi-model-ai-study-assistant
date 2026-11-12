package View;
import Shared.ModelObserver;

import javax.swing.*;

public class LLMResponsePanel extends JPanel {
    LLMResponseView model1;
    LLMResponseView model2;
    LLMResponseView model3;

    LLMResponsePanel(String modelName1, String modelName2, String modelName3) {
        model1 = new LLMResponseView(modelName1);
        model2 = new LLMResponseView(modelName2);
        model3 = new LLMResponseView(modelName3);

        // TODO: set formater and display these views
        //  They should be wrapped in decorator JScrollBar (name?) objects
    }

    public ModelObserver[] getObservers() {
        return new ModelObserver[] {model1, model2, model3};
    }
} //LLMResponsePanel