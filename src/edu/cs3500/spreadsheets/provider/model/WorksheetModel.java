package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * A representation of a spreadsheet that can hold values and formulas at coordinates. Users may
 * query a location for for its literal value, or the expression at that location.
 */
public interface WorksheetModel extends ReadOnlyWorksheetModel {

  /**
   * Deletes the contents of the cell at the given location. If no content exists at the location,
   * the state of the worksheet remains unchanged.
   *
   * @param loc coordinates of the cell, indexed from 1
   * @throws IllegalArgumentException if the given Coord is null
   */
  void clearAt(Coord loc) throws IllegalArgumentException;

  /**
   * Deletes the contents in the rectangular region of cells, whose diagonal corners are at the
   * given locations. The cells at the given coordinates are also cleared. If no content exists at
   * the location, the state of the worksheet remains unchanged.
   *
   * @param corner1 location of one corner of the region
   * @param corner2 location of the diagonal corner of the region
   * @throws IllegalArgumentException if any of the given Coords are null
   */
  void clearAt(Coord corner1, Coord corner2) throws IllegalArgumentException;


  /**
   * Replaces the contents of the given location with the given data. The location does not need to
   * previously have contents in it. Strings starting with = will be treated as formulas. Other
   * strings will be treated as number or booleans if possible, and strings otherwise. String values
   * in a cell should be entered with double quotes.
   *
   * @param loc  location of the cell to edit
   * @param expr the formula to place in the cell
   * @throws IllegalArgumentException if the coord or expr is null
   */
  void replaceContentsAt(Coord loc, String expr) throws IllegalArgumentException;

}
