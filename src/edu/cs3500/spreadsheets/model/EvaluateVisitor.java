package edu.cs3500.spreadsheets.model;

/**
 * To represent a visitor that evaluates the given formula. All methods output Values. This visitor
 * visits either rectangle references or single references that contain formulas.
 */
public class EvaluateVisitor implements FormulaVisitor<Value> {
  String func;

  /**
   * Constructor to create an EvaluateVisitor.
   *
   * @param f function in string form (form before it is parsed)
   */
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
        throw new IllegalArgumentException(Error.NAME.toString());
    }
  }

  @Override
  public Value visitFormula(Formula f) {
    return f.evaluate();
  }

}
