package edu.cs3500.spreadsheets.model;

/**
 * To represent a Formula, which is used to represent the values stored within the cells. A formula
 * is characterized when a user inputs an equal sign (eg. "= SUM 1 2").
 */
public interface Formula {

  /**
   * Evaluates the formula and returns a value representing its type (Str/Bool/Num)
   *
   * @return returns output/result of the formula after it is evaluated.
   */
  Value evaluate();

  /**
   * Gets the type of function/operation to be performed.
   *
   * @return a string to represent which function it is  (PRODUCT/SUM/LESSTHAN/CONCAT)
   */
  String getType();

  /**
   * Since cycles may be detected differently depending on the type of value/formula it is (eg.
   * single blank cells cannot have cycles, this method allows more flexibility to detect where
   * cycles are.
   * @param currentCoord current coordinate
   * @return boolean to represent if there is a cycle at that coordinate
   */
  boolean cyclePresent(Coord currentCoord);

}
