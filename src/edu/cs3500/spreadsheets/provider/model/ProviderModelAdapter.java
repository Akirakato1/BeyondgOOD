package edu.cs3500.spreadsheets.provider.model;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

public class ProviderModelAdapter implements ReadOnlyWorksheetModel{
  ISpreadsheetModel ss;
  
  public ProviderModelAdapter(ISpreadsheetModel ss) {
    this.ss=ss;
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
    return ss.getFormulaAtCoord(loc).toString();
  }

  @Override
  public int getContentCols() {
    return this.ss.getCol();
  }

  @Override
  public int getContentRows() {
    return this.ss.getRow();
  }

  private void checkNull(Object o) {
    if(o==null) {
      throw new IllegalArgumentException("no null in func");
    }
  }
}
