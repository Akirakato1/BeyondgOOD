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
  public boolean cyclePresent(Coord currentCoord) {
    return false;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitBlank();
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }

}
