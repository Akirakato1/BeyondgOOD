package edu.cs3500.spreadsheets.model;

public class EvaluateVisitor implements FormulaVisitor<Value> {
  Function func;

  public EvaluateVisitor(Function f) {
    func = f;
  }

  @Override
  public Value visitRectangleRef(RectangleRef rf) {
    func.setArgs(rf.expand());
    return func.evaluate();
  }

  @Override
  public Value visitFormula(Formula f) {
    return f.evaluate();
  }

}
