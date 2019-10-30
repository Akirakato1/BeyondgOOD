package edu.cs3500.spreadsheets.model;

/**
 * To represent a number value inside a spreadsheet cell.
 */
public class Num implements Value {
  private final double value;

  /**
   * Constructor to make a number.
   * 
   * @param val value of cell
   */
  public Num(double val) {
    this.value = val;
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    return false;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitNum(value);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

  @Override
  public String toString() {
    return value + "";
  }
}
