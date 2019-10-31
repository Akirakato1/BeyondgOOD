package edu.cs3500.spreadsheets.model;

/**
 * To represent a sum visitor. It visits the various values and gets a double representing whether
 * the value should be summed or ignored.
 */
public class SumVisitor implements ValueVisitor<Double> {

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
