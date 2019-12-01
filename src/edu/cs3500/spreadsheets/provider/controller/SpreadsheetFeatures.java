package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Responds to the different events that can occur when a user is editing their spreadsheet.
 */
public interface SpreadsheetFeatures {

  /**
   * Select a cell based on the location.
   *
   * @param loc location of cell to select
   */
  void cellSelected(Coord loc);

  /**
   * Respond to the user entering a string at a certain location.
   *
   * @param data the string the user typed
   * @param loc the cell location the user typed at
   */
  void dataEntered(String data, Coord loc);

  /** TODO: Don't forget to test this future us
   * Deletes any data from the cell at the given location.
   *
   * @param loc the cell location to delete the data at
   */
  void deleteData(Coord loc);

  /**
   * Saves the current model to the file with the given name.
   *
   * @param fileName name of the file to save to.
   */
  void saveTo(String fileName);

  /**
   * Loads the given file to display on the view.
   *
   * @param fileName name of the file to load from.
   */
  void loadFile(String fileName);

}
