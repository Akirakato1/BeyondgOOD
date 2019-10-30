package edu.cs3500.spreadsheets.model;

/**
 * To represent a sum function. 1
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
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }


}
