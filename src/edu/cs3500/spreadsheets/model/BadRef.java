package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

/**
 * To represent a bad reference that the user inputted. This could be something like A1A1. This was
 * made so the user could make a mistake and then fix it later, rather than just not allowing the
 * user input this value in the first place.
 */
public class BadRef implements Ref {
  String refName;

  /**
   * Constructor for a bad reference.
   *
   * @param name bad reference name
   */
  public BadRef(String name) {
    refName = name;
  }

  @Override
  public Value evaluate() {
    throw new IllegalArgumentException(
            "Something was wrong with BadRef " + refName);
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> visited) {
    return false;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    throw new IllegalArgumentException(
            "Something was wrong with BadRef " + refName);
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
