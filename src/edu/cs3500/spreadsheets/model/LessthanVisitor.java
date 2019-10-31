package edu.cs3500.spreadsheets.model;

/**
 * To represent a LessthanVisitor that is used when the lessthan function object is used.
 * This gets the corresponding value from the Value objects.
 */
public class LessthanVisitor implements ValueVisitor<Double>{

  @Override
  public Double visitBool(boolean b) {
    throw new IllegalArgumentException("Arguments must be a number!");
  }

  @Override
  public Double visitStr(String s) {
    throw new IllegalArgumentException("Arguments must be a number!");
  }

  @Override
  public Double visitNum(double d) {
    return d;
  }

  @Override
  public Double visitBlank() {
    throw new IllegalArgumentException("Arguments must be a number!");
  }

}
