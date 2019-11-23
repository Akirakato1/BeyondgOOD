package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.view.SpreadsheetView;

public class SpreadsheetController implements Features {
  ISpreadsheetModel ss;
  SpreadsheetView view;
  int currentCol;
  int currentRow;
  boolean cellSelected;

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
    System.out.println("addrow" + ss.getRow());
    ss.addRow();
    view.increaseRow();
  }

  @Override
  public void addCol() {
    System.out.println("addcol" + ss.getCol());
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
    // might have index issue, check later
    System.out.println(new Coord(col, row));
    String formula = ss.getFormulaAtCoord(new Coord(col, row)).toString();
    view.setFormulaDisplay(formula);
  }

}
