package edu.cs3500.spreadsheets.model;

/**
 * Represents interface of spreadsheet model. 
 * @author Akira Kato
 *
 */
public interface ISpreadsheetModel {
  
  Formula getFormulaAtCoord(Coord coord);
  
  Value evaluateCell(Coord coord);
  
  void deleteCell(Coord coord);

  void updateCell(Coord coord, String sexp);
  
}
