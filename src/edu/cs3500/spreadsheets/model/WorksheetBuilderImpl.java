package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;

/**
 * Implementation of the worksheet builder. Contains methods that builds and creates the worksheet
 * (which is an ISpreadsheetModel) and the cells.
 */
public class WorksheetBuilderImpl implements WorksheetBuilder<ISpreadsheetModel> {
  private ISpreadsheetModel ss;

  /**
   * Constructor to create a worksheet builder.
   */
  public WorksheetBuilderImpl() {
    ss = new SpreadsheetModel();
  }

  @Override
  public WorksheetBuilder<ISpreadsheetModel> createCell(int col, int row, String contents) {
    ss.updateCell(new Coord(col, row), contents);
    return this;
  }

  @Override
  public ISpreadsheetModel createWorksheet() {
    return ss;
  }

  @Override
  public WorksheetBuilder<ISpreadsheetModel> setColumnWidths(HashMap<String, Integer> colWidths) {
    this.ss.setColHeaderWidths(colWidths);
    return this;
  }

}
