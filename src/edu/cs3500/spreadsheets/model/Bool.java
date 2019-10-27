package edu.cs3500.spreadsheets.model;

public class Bool implements Value{
  private final boolean value;
  
  public Bool(boolean bool) {
    this.value=bool;
  }
  
  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public String getType() {
    return "Bool";
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
    return value;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    return false;
  }


}
