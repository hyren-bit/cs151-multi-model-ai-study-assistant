package View;
import javax.swing.*;
import java.awt.event.ActionListener;
import Shared.ModelObserver;

/*
TODO: This whole thing.
Section Author: John Bechtold
responsibility Java Swing GUI
Question input and Ask Models
3 response panels
view / observer implementation
gui integration with controller
 */

/**
 * The View component of the MVC pattern. This class is responsible for displaying the user-interface
 * as well as accepting user-input and notifying the controller via a listener when user-input updates.
 *
 *
 * @author John Bechtold
 * @version 0.2
 * Updated: Nov 12, 2026
 */
public class View extends JFrame {
  final LLMResponsePanel responsePanel;
  final UserPromptPanel promptPanel;
  private final int WIDTH = 800;
  private final int HEIGHT = 600;


  /**
   * The constructor for the View class requires the names of the three models.
   * @param modelName1 The first model name.
   * @param modelName2 The second model name.
   * @param modelName3 The third model name.
   */
  public View(String modelName1, String modelName2, String modelName3) {
    int responsePanelHeight = Math.max(100, (int) (0.8 * HEIGHT));
    responsePanel = new LLMResponsePanel(modelName1, modelName2, modelName3, WIDTH, responsePanelHeight);
    promptPanel = new UserPromptPanel();

    //TODO: build the view
    // add all components to the frame.
    // set size, repack, close settings etc..
  } //View()


  /**
   * This should be called by Controller.
   * @param a The listener for the user input button.
   */
  public void attachUserPromptListener(ActionListener a) {
    // TODO: attach this listener to the corresponding button.
    promptPanel.attachUserPromptListener(a);
  } //attachUserPromptListener()


  /**
   * TODO: This will need to take a reference to the model object.
   * The model reference will be required by the LLMResponseView objects in order to access the model accessors
   * Whenever the ModelObserver.modelChanged() method is called.
   */
  public void attachModel() {
    // TODO: pass the model down to the response panel.
    responsePanel.attachModel();
  } //attachModel()


  /**
   * This should be called by the controller when the listener event triggers
   * @return The user's prompt for the LLMs.
   */
  public String getUserPrompt() {
    return promptPanel.getPrompt();
  } //getUserPrompt()


  /**
   * Resets the text fields in anticipation for a new set of responses from the LLMs
   */
  private void clearResponses() {

  } //clearResponses()


  /**
   *
   * @return Three ModelObserver objects to be updated in the Model
   */
  public ModelObserver[] getModelObservers() {
    return responsePanel.getObservers();
  } //getModelObservers()
} //MainView
