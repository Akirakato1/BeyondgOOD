package edu.cs3500.spreadsheets.view;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

public abstract class DefaultView implements SpreadsheetView{
  protected ISpreadsheetViewOnly ss;
  
  public DefaultView(ISpreadsheetViewOnly ss) {
    this.ss=ss;
  }
  
  @Override
  public void addFeatures(Features f) {
    //don't do anything.
  }

  @Override
  public void setFormulaDisplay(String formula) {
    throw new UnsupportedOperationException("setformuladisplay not supported");
  }

  @Override
  public void updateCellValue(String value, int row, int col) {
    throw new UnsupportedOperationException("update cell value not supported");
  }

  @Override
  public void increaseRow() {
    throw new UnsupportedOperationException("increaseRow not supported");
  }

  @Override
  public void increaseCol() {
    throw new UnsupportedOperationException("increaseCol not supported");
  }

  @Override
  public void close() {
    throw new UnsupportedOperationException("Close not supported");
  }
}
