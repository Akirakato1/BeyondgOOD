package edu.cs3500.spreadsheets.model;

/**
 * To represent a type of value that a user can input into a cell. Because a formula can result into
 * a value, it extends formula. A value can be one of the following: 1) string 2) double 3) boolean
 */
public interface Value extends Formula {

  /**
   * Gets the string value of the formula/cell.
   * @return string of cell input
   */
  String getString();

  /**
   * Gets the double value of the formula/cell.
   * @return double/number from cell input
   */
  Double getDouble();

  /**
   * Gets the boolean value of the formula/cell.
   * @return boolean of cell input
   */
  Boolean getBoolean();

}
