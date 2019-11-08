package edu.cs3500.spreadsheets.model;

import java.util.HashSet;
import java.util.Objects;

/**
 * To represent a boolean value (true/false). Booleans are usually used to represent the result of
 * functions like LESSTHAN.
 */
public class Bool implements Value {
  private final boolean value;

  public Bool(boolean bool) {
    this.value = bool;
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> noCycle, HashSet<Coord> hasCycle) {
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


}
