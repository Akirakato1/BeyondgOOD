package edu.cs3500.spreadsheets.model;

public class Product extends AbstractFunction {
  Formula[] arguments;

  public Product(Formula... args) {
    super(args);
  }

  @Override
  public Value evaluate() {
    Value output;
    double result = 1;
    int ignored = 0;
    for (int i = 0; i < arguments.length; i++) {
      if (arguments[i].getType().equals("RectangleRef")) {
        output = new Product(((RectangleRef) arguments[i]).expand()).evaluate();
      } else {
        output = arguments[i].evaluate();
      }
      switch (output.getType()) {
        case "Num":
          result = result * output.getDouble();
          break;
        default:
          ignored++;
      }
    }
    if (ignored == arguments.length) {
      result = 0;
    }
    return new Num(result);
  }

  @Override
  public String getType() {
    return "PRODUCT";
  }



}
