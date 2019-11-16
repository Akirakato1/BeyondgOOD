package edu.cs3500.spreadsheets.model;

/**
 * To represent a bad function if the user inputs something that is other than the defined functions
 * (FUNC != SUM/PRODUCT/CONCAT/LESSTHAN). This was created so the spreadsheet could hold
 * bad functions (which the user can fix later).c
 */
public class BadFunc extends AbstractFunction {
  private String funcName;

  /**
   * Constructor for bad function.
   *
   * @param name the faulty function name
   * @param args array of formulas that are inputted
   */
  public BadFunc(String name, Formula... args) {
    super(args);
    funcName = name;
  }

  @Override
  public Value evaluate() {
    throw new IllegalArgumentException("Something went wrong with badfunc, should not get here");
  }

  @Override
  public String toString() {
    return "(" + funcName + super.toString() + ")";
  }

  @Override
  public void validateFormula() {
    throw new IllegalArgumentException(Error.NAME.toString());
  }

}
