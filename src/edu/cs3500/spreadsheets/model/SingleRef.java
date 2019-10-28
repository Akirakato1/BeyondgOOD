package edu.cs3500.spreadsheets.model;

/**
 * To represent a single cell reference (eg. A1 = B1).
 */
public class SingleRef implements Ref {
  private final Coord refCoord;
  private ISpreadsheetModel ss;

  /**
   * Constructor to make a single cell reference
   *
   * @param coord coordinate of cell
   * @param ss    spreadsheet model
   */
  public SingleRef(Coord coord, ISpreadsheetModel ss) {
    this.refCoord = coord;
    this.ss = ss;
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
