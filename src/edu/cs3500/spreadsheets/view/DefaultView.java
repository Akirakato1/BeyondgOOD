package edu.cs3500.spreadsheets.view;

import java.util.HashMap;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

/**
 * To represent a default view. This class is primarily used for textual views to reduce any
 * duplicated code (espiecally in throwing the unsporrted operation exceptions).
 */
public abstract class DefaultView implements SpreadsheetView {
  protected ISpreadsheetViewOnly ss;

  /**
   * Constructor for a default view.
   *
   * @param ss spreadsheet model
   */
  public DefaultView(ISpreadsheetViewOnly ss) {
    this.ss = ss;
  }

  @Override
  public void addFeatures(Features f) {
    /**
     * Textual views do not have buttons/views so we did not allow it to have a controller.
     */
  }

  @Override
  public void setFormulaDisplay(String formula) {
    throw new UnsupportedOperationException("setFormulaDisplay not supported");
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

  @Override
  public void setHighlight(int row, int col) {
    // Do nothing because jTable already does the highlighting for us. This
    // was placed here to more easily adapt the provider's code.
  }

  @Override
  public HashMap<String, Integer> getColumnWidths() {
    throw new UnsupportedOperationException("getColumnWidths not supported");
  }
}
