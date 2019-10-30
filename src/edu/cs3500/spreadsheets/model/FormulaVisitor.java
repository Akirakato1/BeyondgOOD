package edu.cs3500.spreadsheets.model;

public interface FormulaVisitor<R> {
  
  R visitRectangleRef(RectangleRef rf);
  
  R visitFormula(Formula f);
}
