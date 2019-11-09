package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

public enum Error implements Value {
  REF("#REF!"), NAME("#NAME?"), VALUE("#VALUE!");
  private final String message;

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
  public void validateFormula() {}

}
