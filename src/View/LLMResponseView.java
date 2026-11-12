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
    private JLabel textLabel; // status + time
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

        // TODO: add header (time in ms, and state)
        int headerHeight = 20;
        JLabel textLabel = new JLabel("");
        this.add(textLabel);

        JScrollBar scrollBox = new JScrollBar(JScrollBar.VERTICAL);
        JTextArea textArea = new JTextArea("");
        textArea.setEditable(false);

        scrollBox.add(textArea);
        scrollBox.setPreferredSize(new Dimension(WIDTH, HEIGHT - headerHeight));
        this.add(scrollBox);

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
