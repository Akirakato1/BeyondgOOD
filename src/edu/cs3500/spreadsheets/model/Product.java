package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

/**
 * To represent a product function. It multiples 2 cells or a range of cells.
 */
class Product extends AbstractFunction {

  /**
   * Constructor to create a formula function.
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
    List<Double> visitorOutput=new ArrayList<>();
    int ignored = 0;
    for (int i = 0; i < arguments.length; i++) {
      output = arguments[i].accept(new EvaluateVisitor("PRODUCT"));
      visitorOutput = output.accept(new ProductVisitor());
      result*=visitorOutput.get(0);
      if(visitorOutput.get(1)==1) {
        ignored++;
      }
    }
    if (ignored == arguments.length) {
      result = 0;
    }
    return new Num(result);
  }

  @Override
  public String toString() {
    return "(PRODUCT"+super.toString()+")";
  }

}
