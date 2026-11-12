package View;

/*
TODO:
Section Author: John Bechtold
responsibility Java Swing GUI
Question input and Ask Models
3 response panels
view / observer implementation
gui integration with controller
 */

import javax.swing.*;

/**
 * @author John Bechtold
 * @version 0.1
 * Updated: Nov 11, 2026
 */
public class View extends JFrame {
  final LLMResponsePanel responsePanel;
  final UserPromptPanel promptPanel;

  public View() {
    responsePanel = new LLMResponsePanel();
    promptPanel = new UserPromptPanel();
  } //View()

} //MainView
