package edu.cs3500.spreadsheets.model;

/**
 * To represent the concat function that users can use. An example concat would be ("hello " +
 * "world" -> "hello world").
 */
class Concat extends AbstractFunction {
  /**
   * Constructor to create a concat function.
   *
   * @param args array of formula arguments
   */
  public Concat(Formula... args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    String result = "";

    for (int i = 0; i < arguments.length; i++) {
      output = arguments[i].accept(new EvaluateVisitor("CONCAT"));
      result += output.accept(new ConcatVisitor());
    }
    return new Str(result);
  }

  @Override
  public String toString() {
    return "(CONCAT" + super.toString() + ")";
  }

}
