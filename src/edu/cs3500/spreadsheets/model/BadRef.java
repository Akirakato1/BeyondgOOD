package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

public class BadRef implements Ref {
  String refName;

  public BadRef(String name) {
    refName = name;
  }

  @Override
  public Value evaluate() {
    throw new IllegalArgumentException(
        "Somethign wrong in badref " + refName + " should not reach here");
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
    return false;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    throw new IllegalArgumentException(
        "Somethign wrong in badref " + refName + " should not reach here");
  }

  @Override
  public void validateFormula() {
    throw new IllegalArgumentException(Error.NAME.toString());
  }

  @Override
  public String toString() {
    return refName;
  }

}
