package edu.cs3500.spreadsheets.model;

public interface ValueVisitor<R> {
  
  R visitBool(boolean b);
  
  R visitStr(String s);
  
  R visitNum(double d);
  
  R visitBlank();
  
  
}
