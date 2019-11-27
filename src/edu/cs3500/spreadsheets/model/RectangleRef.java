package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * To represent a rectangle of references (used when user inputs something like A1:B3).
 */
class RectangleRef implements Ref {
  private final Coord first;
  private final Coord second;
  private ISpreadsheetModel ss;


  /**
   * Constructor for a rectangle of references.
   *
   * @param first  coordinate of first cell
   * @param second coordinate of last cell
   * @param ss     spreadsheet model
   */
  public RectangleRef(Coord first, Coord second, ISpreadsheetModel ss) {
    if (!(first.col <= second.col && first.row <= second.row)) {
      throw new IllegalArgumentException(Error.NAME.toString());
    }

    this.first = first;
    this.second = second;
    this.ss = ss;
  }

  @Override
  public Value evaluate() {
    return new Blank();
  }

  public Formula[] expand() {
    Formula[] expanded = new Formula[(second.col - first.col + 1) * (second.row - first.row + 1)];
    int k = 0;
    for (int i = first.row; i <= second.row; i++) {
      for (int j = first.col; j <= second.col; j++) {
        expanded[k] = new SingleRef(new Coord(j, i), ss);
        k++;
      }
    }
    return expanded;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
    Formula[] refs = this.expand();
    boolean output = false;
    for (Formula r : refs) {
      output = output || r.cyclePresent(currentCoord, (HashSet<Coord>) visited.clone());
    }
    return output;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitRectangleRef(this);
  }

  @Override
  public String toString() {
    return first.toString() + ":" + second.toString();
  }

  @Override
  public void validateFormula() {
    // We chose to put validateFormula on the Value class. For valid values,
    // this method will not do anything. It does nothing because if it is
    // a bad formula, then we throw an exception.
  }

  @Override
  public HashSet<Coord> getDependent() {
    Formula[] expanded = this.expand();
    HashSet<Coord> output = new HashSet<>();
    for (Formula r : expanded) {
      output.addAll(r.getDependent());
    }
    return output;
  }

}
