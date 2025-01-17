package edu.cs3500.spreadsheets.model;

import java.util.HashSet;
import java.util.Objects;

/**
 * To represent a boolean value (true/false). Booleans are usually used to represent the result of
 * functions like LESSTHAN.
 */
public class Bool implements Value {
  private final boolean value;

  /**
   * Constructor for boolean.
   *
   * @param bool boolean to represent true/false
   */
  public Bool(boolean bool) {
    this.value = bool;
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
    return visitor.visitBool(value);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Bool)) {
      return false;
    }
    return this.value == ((Bool) o).value;
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value + "";
  }

  @Override
  public void validateFormula() {
    // We chose to put validateFormula on the Value class. For valid values,
    // this method will not do anything. It does nothing because if it is
    // a bad formula, then we throw an exception.
  }

  @Override
  public HashSet<Coord> getDependent() {
    return new HashSet<Coord>();
  }

  @Override
  public boolean hasColumnRef() {
    return false;
  }


}
