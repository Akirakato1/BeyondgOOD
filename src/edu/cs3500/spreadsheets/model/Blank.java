package edu.cs3500.spreadsheets.model;

/**
 * To represent a blank cell, which is also a type of value.
 */
class Blank implements Value{
  
  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String getType() {
    return "Blank";
  }

  @Override
  public String getString() {
    return null;
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
