package View;

import Model.Model;
import Shared.ModelObserver;
import javax.swing.*;
import java.awt.*;

/**
 * The LLMResponsePanel will display a representation of the output of all three LLMs
 */
public class LLMResponsePanel extends JPanel {
    private final LLMResponseView model1;
    private final LLMResponseView model2;
    private final LLMResponseView model3;


    /**
     * Constructor for the LLMResponsePanel.
     * @param modelName1 The canonical name for the first LLM.
     * @param modelName2 The canonical name for the second LLM.
     * @param modelName3 The canonical name for the third LLM.
     * @param w The width of the response panel.
     * @param h The height of the response panel.
     */
    LLMResponsePanel(String modelName1, String modelName2, String modelName3, int w, int h) {

        // Create the LLMResponseViews, initializing their names and dimensions.
        int modelWidth = (int) (w / 3);
        model1 = new LLMResponseView(modelName1, modelWidth, h);
        model2 = new LLMResponseView(modelName2, modelWidth, h);
        model3 = new LLMResponseView(modelName3, modelWidth, h);

        this.setLayout(new BorderLayout());

        model1.setPreferredSize(new Dimension(modelWidth,h));
        model1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), modelName1));
        this.add(model1, BorderLayout.WEST);

        model2.setPreferredSize(new Dimension(modelWidth,h));
        model2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), modelName2));
        this.add(model2, BorderLayout.CENTER);

        model3.setPreferredSize(new Dimension(modelWidth,h));
        model3.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), modelName3));
        this.add(model3, BorderLayout.EAST);
    } //LLMResponsePanel()


    /**
     *
     */
    public void attachModel(Model model) {
        model1.attachModel(model);
        model2.attachModel(model);
        model3.attachModel(model);
    } //attachModel()


    /**
     *
     * @return Returns the set of observers to be notified when the models change. (All three should be notified whenever a model updates.)
     */
    public ModelObserver[] getObservers() {
        return new ModelObserver[] {model1, model2, model3};
    }
} //LLMResponsePanel