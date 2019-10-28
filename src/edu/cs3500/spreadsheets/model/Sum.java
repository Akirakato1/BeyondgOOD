package edu.cs3500.spreadsheets.model;

/**
 * To represent a sum function. 1
 */
public class Sum extends AbstractFunction {
  private Formula[] arguments;

  /**
   * Constructor for a sum function.
   *
   * @param args array of formula arguments
   */
  public Sum(Formula... args) {
    arguments = args;
  }

  @Override
  public Value evaluate() {
    Value output;
    double result = 0;
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].getType().equals("RectangleRef")) {
        output = new Sum(((RectangleRef) arguments[i]).expand()).evaluate();
      } else {
        output = arguments[i].evaluate();
      }
      switch (output.getType()) {
        case "Num":
          result = result + output.getDouble();
          break;
        default:
      }
    }
    return new Num(result);
  }

  @Override
  public String getType() {
    return "SUM";
  }


}
