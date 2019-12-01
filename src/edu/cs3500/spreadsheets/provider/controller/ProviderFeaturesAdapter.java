package edu.cs3500.spreadsheets.provider.controller;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

public class ProviderFeaturesAdapter implements SpreadsheetFeatures{
  Features controller;
  
  public ProviderFeaturesAdapter(Features f){
    this.controller=f;
  }
  
  @Override
  public void cellSelected(Coord loc) {
    controller.displayFormula(loc.row, loc.col);
  }

  @Override
  public void dataEntered(String data, Coord loc) {
    controller.submit(data);
  }

  @Override
  public void deleteData(Coord loc) {
    controller.submit("");
  }

  @Override
  public void saveTo(String fileName) {
    controller.save(fileName);
  }

  @Override
  public void loadFile(String fileName) {
    // TODO Auto-generated method stub
    
  }

}
