package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;

/**
 * Our class to adapt the provider's controller. This uses the adapter pattern
 * so we could turn their code into our interface. We adapted the controller
 * so its functionality works with our code.
 */
public class ProviderFeaturesAdapter implements SpreadsheetFeatures {
  private Features controller;

  /**
   * Constructor for a provide features adapter.
   * @param f controller features
   */
  public ProviderFeaturesAdapter(Features f) {
    this.controller = f;
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
    // Did not implement their extra credit.

  }

}
