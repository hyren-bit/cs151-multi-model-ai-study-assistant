package View;

import javax.swing.*;
import java.util.EventListener;

public class UserPromptPanel extends JPanel {
    private String userPrompt; // FIXME: placeholder

    public UserPromptPanel() {
        // TODO: create this class
    } //UserPromptPanel()

    public void attachUserPromptListener(EventListener e) {
        // TODO: attach this listener to the corresponding button
    }

    /**
     * Accessor method for the user prompt
     * @return The user prompt for the LLMs.
     */
    public String getPrompt() {
        return userPrompt;
    } //getPrompt()
} //UserPromptPanel