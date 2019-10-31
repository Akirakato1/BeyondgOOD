package edu.cs3500.spreadsheets.model;

/**
 * Visitor that visits formulas. It is used in RectangleRef to evaluate
 * @param <R>
 */
public interface FormulaVisitor<R> {

  /**
   * Given a rectangle of references, visit each one and output the answer.
   * @param rf rectangle formula
   * @return
   */
  R visitRectangleRef(RectangleRef rf);
  
  R visitFormula(Formula f);
}
