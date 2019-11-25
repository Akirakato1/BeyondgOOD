package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.view.SpreadsheetView;

/**
 * To represent a controller for the spreadsheet model. This controller communicates
 * with the view and the model so if the view has any events happening, the model
 * will get updated as well.
 */
public class SpreadsheetController implements Features {
  private ISpreadsheetModel ss;
  private SpreadsheetView view;
  private int currentCol;
  private int currentRow;
  private boolean cellSelected;
  /**
   * Constructor to create a spreadsheet controller.
   * @param ss spreadsheet model
   * @param view model view
   */
  public SpreadsheetController(ISpreadsheetModel ss, SpreadsheetView view) {
    this.ss = ss;
    this.view = view;
    this.cellSelected = false;
    view.addFeatures(this);
  }

  @Override
  public void submit(String newFormula) {
    if (currentCol > 0 && currentRow > 0 && currentCol <= ss.getCol()
        && currentRow <= ss.getRow()) {
      ss.updateCell(new Coord(currentCol, currentRow), newFormula);
    }
    view.updateCellValue(ss.evaluateCell(new Coord(currentCol, currentRow)).toString(), currentRow,
        currentCol);
  }

  @Override
  public void cancel() {
    if (currentCol > 0 && currentRow > 0 && currentCol <= ss.getCol()
        && currentRow <= ss.getRow()) {
      view.setFormulaDisplay(ss.getFormulaAtCoord(new Coord(currentCol, currentRow)).toString());
    }
  }

  @Override
  public void addRow() {
    ss.addRow();
    view.increaseRow();
  }

  @Override
  public void addCol() {
    ss.addCol();
    view.increaseCol();
  }

  @Override
  public void displayFormula(int row, int col) {
    this.currentCol = col;
    this.currentRow = row;
    if (!(currentCol > 0 && currentRow > 0 && currentCol <= ss.getCol()
        && currentRow <= ss.getRow())) {
      this.cellSelected = false;
      view.setFormulaDisplay("");
      return;
    }
    this.cellSelected = true;
    String formula = ss.getFormulaAtCoord(new Coord(col, row)).toString();
    view.setFormulaDisplay(formula);
  }

}
