package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * To represent a single cell reference (eg. A1 = B1).
 */
class SingleRef implements Ref {
  private final Coord refCoord;
  private ISpreadsheetModel ss;

  /**
   * Constructor to make a single cell reference
   *
   * @param coord coordinate of cell
   * @param ss spreadsheet model
   */
  public SingleRef(Coord coord, ISpreadsheetModel ss) {
    this.refCoord = coord;
    this.ss = ss;
  }

  @Override
  public Value evaluate() {
    return ss.getFormulaAtCoord(refCoord).evaluate();
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    return refCoord.equals(currentCoord)
        || ss.getFormulaAtCoord(refCoord).cyclePresent(currentCoord);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
  
  @Override
  public String toString() {
    return refCoord.toString();
  }

}
