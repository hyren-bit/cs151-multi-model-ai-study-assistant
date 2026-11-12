package View;

import Shared.ModelObserver;
import javax.swing.*;
import java.awt.*;

/**
 * The LLMResponsePanel will display a representation of the output of all three LLMs
 */
public class LLMResponsePanel extends JPanel {
    LLMResponseView model1;
    LLMResponseView model2;
    LLMResponseView model3;
    private final int HEIGHT;
    private final int WIDTH;


    /**
     * Constructor for the LLMResponsePanel.
     * @param modelName1 The canonical name for the first LLM.
     * @param modelName2 The canonical name for the second LLM.
     * @param modelName3 The canonical name for the third LLM.
     * @param w The width of the response panel.
     * @param h The height of the response panel.
     */
    LLMResponsePanel(String modelName1, String modelName2, String modelName3, int w, int h) {
        WIDTH = w;
        HEIGHT = h;

        // Create the LLMResponseViews, initializing their names.
        model1 = new LLMResponseView(modelName1, (int) (WIDTH / 3), HEIGHT);
        model2 = new LLMResponseView(modelName2, (int) (WIDTH / 3), HEIGHT);
        model3 = new LLMResponseView(modelName3, (int) (WIDTH / 3), HEIGHT);

        model1.setPreferredSize(new Dimension(100,400));
        this.add(model1);


        // TODO: set formater and display these views
        //  They should be wrapped in decorator JScrollBar (name?) objects
    } //LLMResponsePanel()


    /**
     *
     */
    public void attachModel() {
        // TODO: pass model from view to these guys
        model1.attachModel();
        model2.attachModel();
        model3.attachModel();
    } //attachModel()


    /**
     *
     * @return
     */
    public ModelObserver[] getObservers() {
        return new ModelObserver[] {model1, model2, model3};
    }
} //LLMResponsePanel