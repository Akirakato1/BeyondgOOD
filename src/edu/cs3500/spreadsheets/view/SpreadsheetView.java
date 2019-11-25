package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;

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

  /**
   * Adds a Feature to the view button. This allows the view to communicate/get information from
   * the spreadsheet model.
   * @param f Features controller
   */
  void addFeatures(Features f);

  /**
   * Repaints or refreshes the whole spreadsheet.
   */
  void refresh();

  /**
   * Sets the given formula to be visible in the view.
   * @param formula
   */
  void setFormulaDisplay(String formula);

  /**
   * "Repaints" a given cell with an updated value.
   * @param value new value to display
   * @param row row of cell
   * @param col row of column
   */
  void updateCellValue(String value, int row, int col);

  /**
   * Increases the number of visible rows seen in the view.
   * DOES NOT MUTATE MODEL.
   */
  void increaseRow();

  /**
   * Increases the number of visible columns seen in the view.
   * DOES NOT MUTATE MODEL.
   */
  void increaseCol();

}
