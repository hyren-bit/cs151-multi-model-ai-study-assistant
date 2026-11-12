package View;
import javax.swing.*;
import java.util.EventListener;
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
 * @author John Bechtold
 * @version 0.1
 * Updated: Nov 11, 2026
 */
public class View extends JFrame {
  final LLMResponsePanel responsePanel;
  final UserPromptPanel promptPanel;

  /**
   * The constructor for the View class requires the names of the three models.
   * @param modelName1 The first model name.
   * @param modelName2 The second model name.
   * @param modelName3 The third model name.
   */
  public View(String modelName1, String modelName2, String modelName3) {
    responsePanel = new LLMResponsePanel(modelName1, modelName2, modelName3);
    promptPanel = new UserPromptPanel();

    //TODO: build the view
    // add all components to the frame.
    // set size, repack, close settings etc..
  } //View()


  /**
   * This should be called by Controller.
   * @param e The listener for the user input button.
   */
  public void attachUserPromptListener(EventListener e) {
    // TODO: attach this listener to the corresponding button.
    promptPanel.attachUserPromptListener(e);
  } //attachUserPromptListener()

  /**
   * TODO: This will need to take a reference to the model object.
   * The model reference will be required by the LLMResponseView objects in order to access the model accessors
   * Whenever the ModelObserver.modelChanged() method is called.
   */
  public void attachModel() {

  }

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
   * @return 3 ModelObserver objects to be updated in the Model
   */
  public ModelObserver[] getModelObservers() {
    return responsePanel.getObservers();
  } //getModelObservers()
} //MainView
