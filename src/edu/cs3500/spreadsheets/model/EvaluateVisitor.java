package edu.cs3500.spreadsheets.model;

public class EvaluateVisitor implements FormulaVisitor<Value> {
  String func;

  public EvaluateVisitor(String f) {
    func = f;
  }

  @Override
  public Value visitRectangleRef(RectangleRef rf) {
    switch (func) {
      case "SUM":
        return new Sum(rf.expand()).evaluate();
      case "PRODUCT":
        return new Product(rf.expand()).evaluate();
      case "<":
        return new Lessthan(rf.expand()).evaluate();
      case "CONCAT":
        return new Concat(rf.expand()).evaluate();
      default:
        throw new IllegalArgumentException(
            "function type not exist, should never throw this exception since visitor");
    }
  }

  @Override
  public Value visitFormula(Formula f) {
    return f.evaluate();
  }

}
