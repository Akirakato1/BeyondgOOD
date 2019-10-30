package edu.cs3500.spreadsheets.model;

/**
 * To represent the lessthan function "<".
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
    double[] input = new double[2];
    for (int i = 0; i < 2; i++) {
      output = arguments[i].accept(new EvaluateVisitor("<"));
      input[i] = output.accept(new LessthanVisitor());
    }
    return new Bool(input[0] < input[1]);
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

  private void argException(Formula... args) {
    if (args.length != 2) {
      throw new IllegalArgumentException("not exactly 2 arguments to lessthan" + args.length);
    }
  }



}
