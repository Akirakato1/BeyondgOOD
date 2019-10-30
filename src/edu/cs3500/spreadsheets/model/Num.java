package edu.cs3500.spreadsheets.model;

/**
 * To represent a number value inside a spreadsheet cell.
 */
public class Num implements Value{
  private final double value;

  /**
   * Constructor to make a number.
   * @param val value of cell
   */
  public Num(double val) {
    this.value=val;
  }
  
  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String getType() {
    return "Num";
  }

  @Override
  public String getString() {
    return null;
  }

  @Override
  public Double getDouble() {
    return value;
  }

  @Override
  public Boolean getBoolean() {
    return null;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    return false;
  }

}