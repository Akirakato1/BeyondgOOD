package edu.cs3500.spreadsheets.model;

/**
 * To represent a sum function.
 */
class Sum extends AbstractFunction {

  /**
   * Constructor for a sum function.
   *
   * @param args array of formula arguments
   */
  public Sum(Formula... args) {
    super(args);
  }

  /**
   * Adds up all the values and ignores any non-numeric values.
   *
   * @return Value representing the sum
   */
  @Override
  public Value evaluate() {
    Value output;
    double result = 0;
    for (int i = 0; i < arguments.length; i++) {
      output = arguments[i].accept(new EvaluateVisitor("SUM"));
      result += output.accept(new SumVisitor());
    }
    return new Num(result);
  }

  @Override
  public String toString() {
    return "(SUM" + super.toString() + ")";
  }

}
