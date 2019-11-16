package edu.cs3500.spreadsheets.model;

/**
 * To represent the lessthan function "<". (Eg: 1 < 2 -> true).
 */
class Lessthan extends AbstractFunction {

  /**
   * Constructor for the lessthan formula.
   *
   * @param args arguments of the function
   */
  public Lessthan(Formula... args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    Double[] input = new Double[2];
    if (arguments.length == 1) {
      return arguments[0].accept(new EvaluateVisitor("<"));
    }
    if (arguments.length == 2) {
      for (int i = 0; i < 2; i++) {
        output = arguments[i].accept(new EvaluateVisitor("<"));
        input[i] = output.accept(new LessthanVisitor());
      }
      return new Bool(input[0] < input[1]);
    }

    throw new IllegalArgumentException(Error.VALUE.toString());
  }

  @Override
  public String toString() {
    return "(<" + super.toString() + ")";
  }


}
