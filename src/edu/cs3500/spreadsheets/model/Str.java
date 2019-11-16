package edu.cs3500.spreadsheets.model;

import java.util.HashSet;
import java.util.Objects;

/**
 * To represent a string value of a single cell. Used when we translate SExp into values.
 */
public class Str implements Value {
  private final String value;

  /**
   * Constructor to create a string value.
   *
   * @param val string to be stored
   */
  public Str(String val) {
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
    return visitor.visitStr(value);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Str)) {
      return false;
    }
    return this.value.equals(((Str) o).value);
  }

  @Override
  public String toString() {
    String output = this.value;
    for (int i = 0; i < output.length(); i++) {
      if (output.charAt(i) == '\\' || output.charAt(i) == '\"') {
        output = output.substring(0, i) + "\\" + output.substring(i, output.length());
        i++;
      }
    }
    return "\"" + output + "\"";
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
