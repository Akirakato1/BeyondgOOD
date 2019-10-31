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
    argException(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    Double[] input = new Double[2];
    for (int i = 0; i < 2; i++) {
      output = arguments[i].accept(new EvaluateVisitor("<"));
      input[i] = output.accept(new LessthanVisitor());
    }

    return new Bool(input[0] < input[1]);
  }

  /**
   * Throws exception if length of arguments is not equal to 2.
   *
   * @param args given arguments/formula
   */
  private void argException(Formula... args) {
    if (args.length == 2) {
      for (int i = 0; i < args.length; i++) {
        if (arguments[i].accept(new EvaluateVisitor("<")) == null) {
          throw new IllegalArgumentException("Arguments must be numbers!");
        }
      }
    }
    if (args.length != 2) {
      throw new IllegalArgumentException("not exactly 2 arguments to lessthan" + args.length);
    }

  }

  @Override
  public String toString() {
    return "(<" + super.toString() + ")";
  }


}
