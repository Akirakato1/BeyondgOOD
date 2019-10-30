package edu.cs3500.spreadsheets.model;

/**
 * To represent a boolean value (true/false)
 */
class Bool implements Value{
  private final boolean value;
  
  public Bool(boolean bool) {
    this.value=bool;
  }
  
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
    return visitor.visitBool(value);
  }
  
  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }


}
