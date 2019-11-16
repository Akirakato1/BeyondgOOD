package edu.cs3500.spreadsheets.model;

import java.util.HashSet;
import java.util.Objects;

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
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
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
    return String.format("%f", value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Num)) {
      return false;
    }

    return this.value == ((Num) o).value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public void validateFormula() {
    // We chose to put validateFormula on the Value class. For valid values,
    // this method will not do anything. It does nothing because if it is
    // a bad formula, then we throw an exception.
  }
}
