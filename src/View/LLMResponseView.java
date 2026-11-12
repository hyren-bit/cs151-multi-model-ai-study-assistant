package View;
import Shared.*;
import javax.swing.*;
import java.awt.*;

/**
 * Displays a single response from an LLM.
 */
public class LLMResponseView extends JPanel implements ModelObserver {
    private final String modelName;
    private JTextArea textArea;
    private final int WIDTH;
    private final int HEIGHT;


    /**
     * The constructor for the LLMResponseView
     * @param modelName The name of the LLM.
     * @param w The width of this individual LLM response view.
     * @param h The height of this individual LLM response view.
     */
    public LLMResponseView(String modelName, int w, int h) {
        this.modelName = modelName;
        this.WIDTH = w;
        this.HEIGHT = h;

        // TODO: add model name display
        // TODO: add border
        // TODO: add header (time in ms, and state)
        int headerHeight = Math.max((int) (0.1 * HEIGHT), 20);

        JScrollBar scrollBox = new JScrollBar(JScrollBar.VERTICAL);
        JTextArea textArea = new JTextArea("");
        textArea.setEditable(false);

        scrollBox.add(textArea);
        scrollBox.setPreferredSize(new Dimension(WIDTH, HEIGHT - headerHeight));

    }

    @Override
    public void modelChanged() {
        //String modelName, ResponseState responseState, String modelOutput, int latency) {
        // read the current response for the modelName from the shared model and refresh this view
        // Requires enum (response state enum), int (response time in ms), String (the model output)

        String newText = "";

        textArea.setText(newText);

        // update other info for this field
    }

    public void attachModel() {
        // TODO: pass a reference to the model from view to here.
    }
}
