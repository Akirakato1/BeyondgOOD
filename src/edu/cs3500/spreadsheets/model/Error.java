package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * To represent an Error. It can be one of 3 types: 1) REF (eg. A1 = A1) 2) NAME (eg. = A1A1) 3)
 * VALUE (eg. < 4 true). This was to make our spreadsheet more flexible to hold all these different
 * types of errors, rather than just ignoring the user input and not putting it in the hashmap.
 */
public enum Error implements Value {
  REF("#REF!"), NAME("#NAME?"), VALUE("#VALUE!");
  private final String message;

  /**
   * Constructor for error.
   *
   * @param message error message (one of the 3 types)
   */
  Error(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public Value evaluate() {
    throw new IllegalArgumentException(message);
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
    throw new IllegalArgumentException(message);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    throw new IllegalArgumentException(message);
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    throw new IllegalArgumentException(message);
  }

  @Override
  public void validateFormula() {
    // We chose to put validateFormula on the Value class. For valid values,
    // this method will not do anything. It does nothing because if it is
    // a bad formula, then we throw an exception.
  }

  @Override
  public HashSet<Coord> getDependent() {
    // should never reach here
    return new HashSet<Coord>();
  }

  @Override
  public boolean hasColumnRef() {
    return false;
  }
}
