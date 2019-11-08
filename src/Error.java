package edu.cs3500.spreadsheets.model;

import java.util.HashSet;

public enum Error implements Value{
  REF("#REF!"), NAME("#NAME?"), VALUE("#VALUE!");
  String message;

  Error(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public Value evaluate(){
    throw new IllegalArgumentException("");
  }

  @Override
  public boolean cyclePresent(Coord currentCoord, HashSet<Coord> noCycle, HashSet<Coord> hasCycle) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public <R> R accept(FormulaVisitor<R> visitor) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    // TODO Auto-generated method stub
    return null;
  }
  
}
