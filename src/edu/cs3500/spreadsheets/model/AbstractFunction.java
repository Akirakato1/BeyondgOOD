package edu.cs3500.spreadsheets.model;

/**
 * To represent a function that users can use (SUM/PRODUCT/LESSTHAN/CONCAT). Abstract because there
 * is some shared code between the different functions, especially how they check cycles.
 */
abstract class AbstractFunction implements Function {
  protected Formula[] arguments;

  /**
   * Constructor to be shared among the functions
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
  
}
