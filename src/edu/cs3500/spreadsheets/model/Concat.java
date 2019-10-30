package edu.cs3500.spreadsheets.model;

/**
 * To represent the concat function that users can use.
 * An example concat would be ("hello " + "world" -> "hello world").
 */
class Concat extends AbstractFunction {
  /**
   * Constructor to create a concat function.
   * @param args array list of formula arguments
   */
  public Concat(Formula... args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    String result = "";

    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].getType().equals("RectangleRef")) { // if rectangle (A1:B3)
        output = new Concat(((RectangleRef) arguments[i]).expand()).evaluate();
      } else {
        output = arguments[i].evaluate();
      }
      switch (output.getType()) {
        case "Str":
          result = result + output.getString();
          break;
        case "Num":
          result = result + output.getDouble();
        default:
      }
    }
    return new Str(result);
  }

  @Override
  public String getType() {
    return "CONCAT";
  }


}
