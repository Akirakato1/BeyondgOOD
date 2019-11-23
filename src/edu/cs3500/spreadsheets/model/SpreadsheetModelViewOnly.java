package edu.cs3500.spreadsheets.model;

import java.util.List;

public class SpreadsheetModelViewOnly implements ISpreadsheetViewOnly{
  ISpreadsheetModel ss;
  
  public SpreadsheetModelViewOnly(ISpreadsheetModel ss) {
    this.ss=ss;
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

  
}
