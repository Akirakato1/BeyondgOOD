package edu.cs3500.spreadsheets.controller;

/**
 * To represent the functions the controller can perform on the view.
 * Since there will be 4 buttons, we have a submit, cancel, addRow, and addCol feature.
 * Lastly, we have displayFormula.
 */
public interface Features {
  /**
   * Submits the formula from the text box and edits the model.
   * @param newFormula String version of the text in the text box
   */
  void submit(String newFormula);

  /**
   * Undos any edits in the text box and does nothing to the model.
   */
  void cancel();

  /**
   * Adds a row to the model.
   */
  void addRow();

  /**
   * Adds a column to the model.
   */
  void addCol();

  /**
   * Displays the formula at the current row and column (used in addFeatures).
   * This allows the view to communicate with the model and get the correct information.
   * @param row row of spreadsheet
   * @param col column of spreadsheet
   */
  void displayFormula(int row, int col);

}
