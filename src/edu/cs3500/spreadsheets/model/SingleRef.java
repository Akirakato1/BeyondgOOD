package edu.cs3500.spreadsheets.model;

public class SingleRef implements Ref {
  Coord refCoord;
  ISpreadsheetModel ss;
  
  public SingleRef(Coord coord,ISpreadsheetModel ss) {
    this.refCoord=coord;
    this.ss=ss;
  }

  @Override
  public Value evaluate() {
    //detect cycle
    return ss.getFormulaAtCoord(refCoord).evaluate();
  }

  @Override
  public String getType() {
    return "SingleRef";
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    return refCoord.equals(currentCoord);
  }
  
}
