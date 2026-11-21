package View;

import Model.Model;
import Model.LLMResponse;
import Shared.*;
import javax.swing.*;
import java.awt.*;

/**
 * Displays the responses from one of the LLMs.
 */
public class LLMResponseView extends JPanel implements ModelObserver {
    /// The canonical name of the LLM this Panel is observing.
    private final String modelName;
    /// The response from the LLM.
    private final JTextArea responseText;
    /// The header for the response (the current status of the LLM + response time)
    private final JLabel header;
    /// A reference to the Model governing all the LLMs
    private Model model;


    /**
     * The constructor for the LLMResponseView
     * @param modelName The name of the LLM.
     * @param w The width of this individual LLM response view.
     * @param h The height of this individual LLM response view.
     */
    public LLMResponseView(String modelName, int w, int h) {
        this.modelName = modelName;
        this.model = null;

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1.0;

        // place the header at the top of the response view (eg. "Success - 100ms")
        int headerHeight = 10;
        c.gridy = 0;
        c.gridx = 0;
        header = new JLabel(ResponseState.WAITING.toString());
        header.setPreferredSize(new Dimension(w, headerHeight));
        this.add(header, c);

        // place the response text under the header.
        c.gridy = 1;
        c.weighty = 1.0;
        responseText = new JTextArea("");
        responseText.setEditable(false);

        // put the response text in a scrollPane decorator class
        JScrollPane scrollBox = new JScrollPane(responseText);
        scrollBox.setPreferredSize(new Dimension(w, h - headerHeight));
        this.add(scrollBox, c);

    } // LLMResponseView()

    @Override
    public void modelChanged() {
        // get the response from Model
        LLMResponse modelStatus = model.getLLMResponse(modelName);

        // update the header with the current status
        String newText = modelStatus.getStatus().toString();
        if (modelStatus.getStatus() == ResponseState.SUCCESS) {
            newText += " - " + modelStatus.getResponseTimeMs() + "ms";
        }
        header.setText(newText);

        // Update the LLM response text field
        newText = modelStatus.getText();
        responseText.setText(newText);
    } // modelChanged()

    @Override
    public boolean hasName(String name) {
        return name.equals(modelName);
    } // hasName()

    /**
     * Attaches the Model to this Panel
     * @param model The Model class governing all the LLM responses.
     */
    public void attachModel(Model model) {
        this.model = model;
    } // attachMode()
} // LLMResponseView
