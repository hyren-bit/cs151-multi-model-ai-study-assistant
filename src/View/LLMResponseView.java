package View;

import Shared.*;
import javax.swing.*;
import java.awt.*;

/**
 * Displays a single response from an LLM.
 */
public class LLMResponseView extends JPanel implements ModelObserver {
    private final String modelName;
    private final JTextArea responseText;
    private final JLabel header; // status + time


    /**
     * The constructor for the LLMResponseView
     * @param modelName The name of the LLM.
     * @param w The width of this individual LLM response view.
     * @param h The height of this individual LLM response view.
     */
    public LLMResponseView(String modelName, int w, int h) {
        this.modelName = modelName;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.WEST;

        int headerHeight = 20;
        c.gridy = 0;
        c.gridx = 0;
        header = new JLabel("");
        header.setPreferredSize(new Dimension(w, headerHeight));
        this.add(header, c);

        c.gridy = 1;
        JScrollBar scrollBox = new JScrollBar(JScrollBar.VERTICAL);
        responseText = new JTextArea("");
        responseText.setEditable(false);

        scrollBox.add(responseText, c);
        scrollBox.setPreferredSize(new Dimension(w, h - headerHeight));
        this.add(scrollBox);

    } // LLMResponseView()

    @Override
    public void modelChanged() {
        //String modelName, ResponseState responseState, String modelOutput, int latency) {
        // read the current response for the modelName from the shared model and refresh this view
        // Requires enum (response state enum), int (response time in ms), String (the model output)
        // TODO: implement this method. Needs model accessors.


        // Update header
        // Status text format 'STATUS - t ms'
        String newText = "";
        header.setText(newText);

        // Update the LLM response text field
        newText = "";
        responseText.setText(newText);
    } // modelChanged()

    @Override
    public boolean hasName(String name) {
        return name.equals(modelName);
    } // hasName()

    public void attachModel(/* model reference */) {
        // TODO: pass a reference to the model from view to here.
    } // attachMode()
} // LLMResponseView
