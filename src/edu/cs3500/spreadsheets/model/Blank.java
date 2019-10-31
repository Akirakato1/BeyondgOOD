package edu.cs3500.spreadsheets.model;

import java.util.Objects;

/**
 * To represent a blank cell, which is also a type of value.
 */
class Blank implements Value{
  
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
    return visitor.visitBlank();
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
  
  @Override
  public String toString() {
    return "";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof Blank;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this);
  }

}
