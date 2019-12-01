package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.controller.ProviderFeaturesAdapter;
import edu.cs3500.spreadsheets.view.SpreadsheetView;

public class OurViewAdapter implements SpreadsheetView{
  WorksheetView ws;
  
  public OurViewAdapter(WorksheetView ws) {
    this.ws=ws;
  }

  @Override
  public void render() {
    throw new UnsupportedOperationException("no render in provider view");
  }

  @Override
  public void addFeatures(Features f) {
    ws.addSpreadsheetFeatures(new ProviderFeaturesAdapter(f));
  }

  @Override
  public void setFormulaDisplay(String formula) {
    ws.displayMsg(formula);
  }

  @Override
  public void updateCellValue(String value, int row, int col) {
    ws.refresh();
  }

  @Override
  public void increaseRow() {
    throw new UnsupportedOperationException("no inreaserow in provider view");
  }

  @Override
  public void increaseCol() {
    throw new UnsupportedOperationException("no increasecol in provider view");
    
  }

  @Override
  public void close() {
    throw new UnsupportedOperationException("no close in provider view");
  }

  @Override
  public void setHighlight(int row, int col) {
    ws.highlightAt(new Coord(col, row));
    ws.refresh();
  }

}
