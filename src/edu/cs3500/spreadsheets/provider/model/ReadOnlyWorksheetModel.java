package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A representation of a spreadsheet that only has observer methods; worksheets cannot be mutated
 * through this interface.
 */
public interface ReadOnlyWorksheetModel {

  /**
   * Gives the evaluated value that the cell at the given location holds. If the location is outside
   * of the bounds of the current worksheet, returns a {@code EmptyValue}
   *
   * @param loc location of the cell to evaluate
   * @return the evaluated value
   * @throws IllegalArgumentException if the coordinate is null
   * @throws IllegalStateException    if the contents at the location cannot be evaluated
   */
  IValue getValueAt(Coord loc) throws IllegalArgumentException;

  /**
   * Gives the raw value at the given location, such as the text of the functions. If the location
   * is outside of the bounds of the current worksheet, returns a {@code BlankValue}. If the cell
   * was created with a malformed formula, this returns a StringValue containing what the user typed
   * in.
   *
   * @param loc location of the cell to evaluate
   * @return the evaluated value
   * @throws IllegalArgumentException if the coordinate is null
   */
  IFormula getRawContentsAt(Coord loc) throws IllegalArgumentException;

  /**
   * Returns literally what the user typed at the given location.
   *
   * @param loc location of the cell
   * @return the string of what the user typed at this location
   * @throws IllegalArgumentException if the coord is null
   */
  String getTypedContentsAt(Coord loc) throws IllegalArgumentException;

  /**
   * Gets the (1-indexed) coordinate of the furthest column that has at least one cell with content
   * inside (meaning, the cell does not have an empty value). Cells that are empty but are
   * referenced by other cells do not count as having content. If no columns have contents, returns
   * 0.
   *
   * @return the number of columns in the worksheet
   */
  int getContentCols();


  /**
   * Gets the (1-indexed) coordinate of the furthest rows that has at least one cell with content
   * inside (meaning, the cell does not have an empty value). Cells that are empty but are
   * referenced by other cells do not count as having content. If no rows have contents, returns 0.
   *
   * @return the number of rows in the worksheet
   */
  int getContentRows();
}
