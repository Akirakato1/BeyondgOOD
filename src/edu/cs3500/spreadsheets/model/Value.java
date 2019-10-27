package edu.cs3500.spreadsheets.model;

public interface Value extends Formula{
  
  String getString();
  
  Double getDouble();
  
  Boolean getBoolean();
  
}
