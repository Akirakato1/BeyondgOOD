package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * To represent a single cell reference (eg. A1 = B1).
 */
class SingleRef implements Ref {
  private final Coord refCoord;
  private final ISpreadsheetModel ss;

  /**
   * Constructor to make a single cell reference.
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

    return ss.evaluateCell(refCoord);
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> noCycle, HashSet<Coord> hasCycle) {
    if (noCycle.contains(refCoord)) {
      return false;
    } else if (hasCycle.contains(refCoord)) {
      return true;
    } else {
      boolean isCyclePresent = refCoord.equals(currentCoord)
          || ss.getFormulaAtCoord(refCoord).cyclePresent(currentCoord, noCycle, hasCycle);
      if (isCyclePresent) {
        hasCycle.add(refCoord);
        return true;
      } else {
        noCycle.add(refCoord);
        return false;
      }
    }
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
