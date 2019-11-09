package edu.cs3500.spreadsheets.model;

public class BadFunc extends AbstractFunction {
  private String funcName;

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
