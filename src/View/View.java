package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import Model.Model;
import Shared.ModelObserver;

/*
Section Author: John Bechtold
responsibilities:
  * Java Swing GUI
  * Question input and Ask Models
  * 3 response panels
  * view / observer implementation
  * gui integration with controller
*/

/**
 * The View component of the MVC pattern.<br>
 * This class is responsible for:<br>
 *    * Displaying the user-interface<br>
 *    * Accepting user-input and notifying the controller via a listener.<br>
 *    * Updating the GUI when the model changes. (model notifies observers)<p>
 *
 *
 * @author John Bechtold
 * @version 0.2<br>
 * Updated: Nov 12, 2026
 */
public class View extends JFrame {
  /// the panel that will display the responses from the LLMs.
  private final LLMResponsePanel responsePanel;
  /// The panel that will display the user-input.
  private final UserPromptPanel promptPanel;


  /**
   * The constructor for the View class requires the names of the three models.
   * @param modelName1 The first model name.
   * @param modelName2 The second model name.
   * @param modelName3 The third model name.
   */
  public View(String modelName1, String modelName2, String modelName3) {
    // Initialize Frame settings.
    final int WIDTH = 800;
    final int HEIGHT = 600;
    this.setTitle("Multi-Model AI Study Assistant");
    this.setSize(WIDTH, HEIGHT);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Calculate Panel heights.
    //    promptPanel prefers 20% frame height, with a minimum of 50 and a maximum of 100.
    //    the rest of the height of the frame is allocated to the responsePanel
    final int MIN_PROMPTPANEL_HEIGHT = 50;
    final int MAX_PROMPTPANEL_HEIGHT = 100;
    final double PREF_PROMPTPANEL_PROPORTION = 0.2;

    int promptPanelHeight = Math.max(MIN_PROMPTPANEL_HEIGHT, (int) (PREF_PROMPTPANEL_PROPORTION * HEIGHT));
    promptPanelHeight = Math.min(promptPanelHeight, MAX_PROMPTPANEL_HEIGHT);

    int responsePanelHeight = HEIGHT - promptPanelHeight;


    // Add JComponents
    promptPanel = new UserPromptPanel(WIDTH, promptPanelHeight);
    this.add(promptPanel, BorderLayout.NORTH);

    responsePanel = new LLMResponsePanel(modelName1, modelName2, modelName3, WIDTH, responsePanelHeight);
    this.add(responsePanel, BorderLayout.CENTER);

    // Pack and display the view.
    this.pack();
    this.setVisible(true);
  } //View()


  /**
   * This should be called by Controller.
   * @param a The listener for the user input button.
   */
  public void attachUserPromptListener(ActionListener a) {
    promptPanel.attachUserPromptListener(a);
  } //attachUserPromptListener()


  /**
   * The model reference will be required by the LLMResponseView objects in order to access the model accessors
   * Whenever the ModelObserver.modelChanged() method is called.
   */
  public void attachModel(Model model) {
    responsePanel.attachModel(model);
  } //attachModel()


  /**
   * This is an accessor method to receive the user-prompt for the LLMs.
   * @return The user's prompt for the LLMs.
   */
  public String getUserPrompt() {
    return promptPanel.getPrompt();
  } //getUserPrompt()


  /**
   *  An accessor method to retrieve the ModelObservers responsible for displaying the state of the LLMs in the GUI.
   * @return Three ModelObserver objects to be updated in the Model
   */
  public ModelObserver[] getModelObservers() {
    return responsePanel.getObservers();
  } //getModelObservers()
} //MainView
