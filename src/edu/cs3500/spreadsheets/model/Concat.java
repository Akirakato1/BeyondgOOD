package edu.cs3500.spreadsheets.model;

public class Concat extends AbstractFunction {
  public Concat(Formula... args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    String result = "";
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].getType().equals("RectangleRef")) {
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
