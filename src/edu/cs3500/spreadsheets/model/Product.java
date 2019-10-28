package edu.cs3500.spreadsheets.model;

/**
 * To represent a product function. It multiples 2 cells or a range of cells.
 */
public class Product extends AbstractFunction {
  private Formula[] arguments;

  /**
   * Consturctor to create a formula function.
   * @param args array of formulas that are inputted
   */
  public Product(Formula... args) {
    super(args);
  }

  /**
   * If there are blank or non-number cells, they are ignored. If all inputs are non-number,
   * then outputs 0. Otherwise, outputs the product of the cells.
   * @return product of the cells
   */
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
