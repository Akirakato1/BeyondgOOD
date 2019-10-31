package edu.cs3500.spreadsheets.model;

/**
 * To represent a LessthanVisitor that is used when the lessthan function object is used.
 * This gets the corresponding value from the Value objects.
 */
public class LessthanVisitor implements ValueVisitor<Double>{

  @Override
  public Double visitBool(boolean b) {
    return 0.0;
  }

  @Override
  public Double visitStr(String s) {
    return 0.0;
  }

  @Override
  public Double visitNum(double d) {
    return d;
  }

  @Override
  public Double visitBlank() {
    return 0.0;
  }

}
