package edu.cs3500.spreadsheets.model;

public interface Formula {

  Value evaluate();

  String getType();

  boolean cyclePresent(Coord currentCoord);
  
}
