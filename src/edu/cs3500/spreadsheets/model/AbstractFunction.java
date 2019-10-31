package edu.cs3500.spreadsheets.model;

import java.util.Objects;

/**
 * To represent a function that users can use (SUM/PRODUCT/LESSTHAN/CONCAT). Abstract because there
 * is some shared code between the different functions, especially how they check cycles.
 */
abstract class AbstractFunction implements Function {
  protected Formula[] arguments;

  /**
   * Constructor to be shared among the functions.
   *
   * @param args array list of arguments/formulas
   */
  public AbstractFunction(Formula... args) {
    arguments = args;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    boolean output = false;
    for (Formula arg : arguments) {
      output = output || arg.cyclePresent(currentCoord);
    }

    return output;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof AbstractFunction)) {
      return false;
    }
    int isEqualCount = 0;
    for (int i = 0; i < arguments.length; i++) {
      if (this.arguments[i].equals(((AbstractFunction) o).arguments[i])) {
        isEqualCount++;
      }
    }

    return isEqualCount == arguments.length - 1;
  }


  @Override
  public int hashCode() {
    return Objects.hash(arguments);
  }

  @Override
  public String toString() {
    String ans = "";
    for (int i = 0; i < arguments.length; i++) {
      ans += this.arguments.toString();
    }
    return ans;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

}
