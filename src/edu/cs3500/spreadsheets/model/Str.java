package edu.cs3500.spreadsheets.model;

/**
 * To represent a string value of a single cell. Used when we translate
 * SExp into values.
 */
public class Str implements Value{
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
  public boolean cyclePresent(Coord currentCoord) {
    return false;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitStr(value);
  }
  
  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    return visitor.visitFormula(this);
  }
  
  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Str)) {
      return false;
    }
    return this.value.equals(((Str)o).value);
  }
  
  @Override
  public String toString() {
    return this.value;
  }

}
