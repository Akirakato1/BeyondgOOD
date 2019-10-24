package edu.cs3500.spreadsheets.model;

/**
 * Represents interface of spreadsheet model. 
 * @author Akira Kato
 *
 */
public interface ISpreadsheetModel {
   
  double computeValue(Coord coord);
  
  
  void deleteCell(Coord coord);

  void createCell(int row, int col, String sexp);

  void updateCellSexp(Coord coord, String sexp);
  
}
