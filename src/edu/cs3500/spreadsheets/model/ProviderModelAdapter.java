package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.provider.model.IFormula;
import edu.cs3500.spreadsheets.provider.model.IValue;
import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;

/**
 * This is used to adapt our model into their worksheet model. It uses the adapter pattern and
 * composition so that our interface can be transformed into into their interface. We use this
 * adapter to make sure that our code works with the provider's code.
 */
public class ProviderModelAdapter implements ReadOnlyWorksheetModel {
  ISpreadsheetModel ss;

  /**
   * Constructor for provider model adapter.
   *
   * @param ss Spreadsheet model (our interface).
   */
  public ProviderModelAdapter(ISpreadsheetModel ss) {
    this.ss = ss;
  }

  @Override
  public IValue getValueAt(Coord loc) throws IllegalArgumentException {
    checkNull(loc);
    return new ProviderValueAdapter(ss.evaluateCell(loc));
  }

  @Override
  public IFormula getRawContentsAt(Coord loc) throws IllegalArgumentException {
    checkNull(loc);
    return new ProviderFormulaAdapter(ss.getFormulaAtCoord(loc));
  }

  @Override
  public String getTypedContentsAt(Coord loc) throws IllegalArgumentException {
    checkNull(loc);
    return new ProviderFormulaAdapter(ss.getFormulaAtCoord(loc)).toString();
  }

  @Override
  public int getContentCols() {
    return this.ss.getCol();
  }

  @Override
  public int getContentRows() {
    return this.ss.getRow();
  }

  /**
   * Checks if the object is null and throws an exception if it is.
   *
   * @param o given object
   */
  private void checkNull(Object o) {
    if (o == null) {
      throw new IllegalArgumentException("no null in func");
    }
  }
}
