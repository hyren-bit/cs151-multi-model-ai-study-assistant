package View;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * This panel displays the user-input text box and a button for submitting new input.
 */
public class UserPromptPanel extends JPanel {
    private String userPrompt; // FIXME: placeholder
    private final JButton askModelsButton;

    /**
     * The constructor for the UserPromptPanel.
     */
    public UserPromptPanel(int w, int h) {
        askModelsButton = new JButton("Ask Models");
        // put this guy on the panel
        // TODO: create this panel
    } //UserPromptPanel()


    /**
     * Attaches the ActionListener that handles updated user-input to the corresponding button.
     * @param a ActionListener that handles updated user-input
     */
    public void attachUserPromptListener(ActionListener a) {
        askModelsButton.addActionListener(a);
    } //askModelsButton()


    /**
     * Accessor method for the user prompt
     * @return The user prompt for the LLMs.
     */
    public String getPrompt() {
        return userPrompt;
    } //getPrompt()
} //UserPromptPanel