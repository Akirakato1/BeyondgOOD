package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;

public class WorksheetBuilderImpl implements WorksheetBuilder<ISpreadsheetModel>{
  ISpreadsheetModel ss;
  
  public WorksheetBuilderImpl() {
    ss=new SpreadsheetModel();
  }
  
  @Override
  public WorksheetBuilder<ISpreadsheetModel> createCell(int col, int row, String contents) {
    ss.updateCell(new Coord(col,row), contents);
    return this;
  }

  @Override
  public ISpreadsheetModel createWorksheet() {
    return ss;
  }

}
