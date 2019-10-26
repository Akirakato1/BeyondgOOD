package edu.cs3500.spreadsheets.model;

/**
 * Represents interface of spreadsheet model. 
 * @author Akira Kato
 *
 */
public interface ISpreadsheetModel {
   
  void expProcess(Coord coord);
  
  
  void deleteCell(Coord coord);

  void createCell(int row, int col, String sexp);

  void updateCellSexp(Coord coord, String sexp);
  
}
