package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.List;

/**
 * Represents interface of spreadsheet model. A model can be modified through user updates, and this
 * model has methods that allows a user to perform basic CRUD operations on the spreadsheet.
 *
 * @author Akira Kato
 */
public interface ISpreadsheetModel {
  /**
   * Gets the formula used at a certain cell.
   *
   * @param coord coordinate of cell
   * @return formula that user inputted
   */
  Formula getFormulaAtCoord(Coord coord);

  /**
   * Evaluates the given cell and returns a value.
   *
   * @param coord coordinate of cell
   * @return A Value representation of cell input
   */
  Value evaluateCell(Coord coord);

  /**
   * Deletes a cell from the spreadsheet. NOTE: May need more information from future assignments on
   * what it means to delete a cell. For now, we simply add a blank.
   *
   * @param coord coordinate of cell
   */
  void deleteCell(Coord coord);

  /**
   * Updates the value at a given cell.
   *
   * @param coord coordinate of cell
   * @param sexp  new sexp to be inputted at given cell
   */
  void updateCell(Coord coord, String sexp);

  /**
   * Gets the list of error messages generated by this spreadsheet during creation of cells.
   *
   * @return list of error messages
   */
  List<String> errorMessages();

  /**
   * Gets the list of occupied Coords in this spreadsheet.
   *
   * @return list of Coord.
   */
  List<Coord> getOccupiedCoords();

  /**
   * Gets the number of columns of the spreadsheet model.
   *
   * @return column count
   */
  int getCol();

  /**
   * Gets the number of rows of the spreadsheet model.
   *
   * @return row count
   */
  int getRow();

  /**
   * Adds a blank column to the spreadsheet model.
   */
  void addCol();

  /**
   * Adds a blank row to the spreadsheet model.
   */
  void addRow();

  /**
   * Gets the column width of the given header.
   * @param colHeader given header in string form
   * @return column width of the header
   */
  int getColWidth(String colHeader);

  /**
   * Sets all the column headers given a map of the headers
   * @param map given map of header widths to set the model with
   */
  void setColHeaderWidths(HashMap<String, Integer> map);
}
