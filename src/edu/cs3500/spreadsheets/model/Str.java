package edu.cs3500.spreadsheets.model;

/**
 * To represent a string value of a single cell. Used when we translate
 * SExp into values.
 */
class Str implements Value{
  private final String value;

  /**
   * Constructor to create a string value.
   * @param val string to be stored
   */
  public Str(String val) {
    this.value=val;
  }
  
  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String getType() {
    return "Str";
  }

  @Override
  public String getString() {
    return value;
  }

  @Override
  public Double getDouble() {
    return null;
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
