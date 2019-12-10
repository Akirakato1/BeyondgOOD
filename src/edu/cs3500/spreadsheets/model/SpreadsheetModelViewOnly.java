package edu.cs3500.spreadsheets.model;

import java.util.List;

/**
 * To represent a view only model. None of the methods in this interface can modify/mutate the
 * model.
 */
public class SpreadsheetModelViewOnly implements ISpreadsheetViewOnly {
  ISpreadsheetModel ss;

  /**
   * Constructor for a view only spreadsheet.
   *
   * @param ss spreadsheet model
   */
  public SpreadsheetModelViewOnly(ISpreadsheetModel ss) {
    this.ss = ss;
  }

  @Override
  public Formula getFormulaAtCoord(Coord coord) {
    return ss.getFormulaAtCoord(coord);
  }

  @Override
  public Value evaluateCell(Coord coord) {
    return ss.evaluateCell(coord);
  }

  @Override
  public List<String> errorMessages() {
    return ss.errorMessages();
  }

  @Override
  public List<Coord> getOccupiedCoords() {
    return ss.getOccupiedCoords();
  }

  @Override
  public int getCol() {
    return ss.getCol();
  }

  @Override
  public int getRow() {
    return ss.getRow();
  }
  
  @Override
  public int getColWidth(String colHeader) {
    return ss.getColWidth(colHeader);
  }


}
