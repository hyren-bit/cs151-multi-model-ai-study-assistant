package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * This panel displays the user-input text box and a button for submitting new input.
 */
public class UserPromptPanel extends JPanel {
    private final JTextField userPrompt;
    private final JButton askModelsButton;

    /**
     * The constructor for the UserPromptPanel.
     */
    public UserPromptPanel(int w, int h) {
        this.setPreferredSize(new Dimension(w, h));
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 9;
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Question:"), c);

        c.gridy = 1;
        userPrompt = new JTextField("");
        this.add(userPrompt, c);

        c.gridy = 2;
        c.gridx = 8;
        askModelsButton = new JButton("Ask Models");
        this.add(askModelsButton, c);
    } //UserPromptPanel()


    /**
     * Attaches the ActionListener that handles updated user-input to the corresponding button.
     * @param a ActionListener that handles updated user-input
     */
    public void attachUserPromptListener(ActionListener a) {
        askModelsButton.addActionListener(a); // listens for the ask button press
        userPrompt.addActionListener(a); // listens for enter key press
    } //askModelsButton()


    /**
     * Accessor method for the user prompt
     * @return The user prompt for the LLMs.
     */
    public String getPrompt() {
        return userPrompt.getText();
    } //getPrompt()
} //UserPromptPanel