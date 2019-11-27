package edu.cs3500.spreadsheets.controller;

/**
 * To represent the features that a controller supports. The controller is used in the view and
 * takes in user input (from button clicks or text box input) and then modifies the model
 * accordingly.
 */
public interface Features {
  /**
   * Takes in the new formula (from user input on text box) and modifies the coordinate in the
   * model.
   *
   * @param newFormula new formula to be placed in model
   */
  void submit(String newFormula);

  /**
   * Used when the user clicks the cancel button. If the user clicks the button, the changes are
   * discarded and nothing is modified in the model.
   */
  void cancel();

  /**
   * Adds a row to the model and visible rows in the spreadsheet view.
   */
  void addRow();

  /**
   * Adds a column to the model and visible rows in the spreadsheet view.
   */
  void addCol();

  /**
   * Displays the formula of the model at the cell into the text box.
   * @param row row of cell
   * @param col column of cell
   */
  void displayFormula(int row, int col);

  /**
   * Saves the spreadsheet into a textual view file (the same text format as in HW5/6).
   * @param filename file name of new file
   */
  void save(String filename);

  /**
   * Opens a file and creates the model to be displayed.
   * @param filename file name to be opened
   */
  void open(String filename);

  /**
   * Logs the user's keyboard presses and moves the selected cell accordingly and
   * finds the corresponding cell in the model.
   * @param dir keyboard direction moved
   */
  void move(String dir);

}
