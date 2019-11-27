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
   * @param ss    spreadsheet model
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
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
    if (visited.contains(refCoord)) {
      return true;
    } else {
      visited.add(refCoord);
      return ss.getFormulaAtCoord(refCoord).cyclePresent(currentCoord, visited);
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

  @Override
  public void validateFormula() {
    // We chose to put validateFormula on the Value class. For valid values,
    // this method will not do anything. It does nothing because if it is
    // a bad formula, then we throw an exception.
  }

  @Override
  public HashSet<Coord> getDependent() {
    HashSet<Coord> output = new HashSet<>();
    output.add(refCoord);
    return output;
  }
}
