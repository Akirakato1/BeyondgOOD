package edu.cs3500.spreadsheets.view;

/**
 * This class represents the spreadsheet view interface. Currently, there are 2 types of views: 1)
 * TextualView 2) VisualView These classes are used to display the cell values/spreadsheet cells to
 * the user.
 */
public interface SpreadsheetView {

  /**
   * Renders a spreadsheet model into either a GUI or textual file.
   */
  void render();

}
