package edu.cs3500.spreadsheets.provider.model;

/**
 * A literal value, such as a number or string.
 */
public interface IValue extends IFormula {
  <R> R accept(IValueVisitor<R> visitor);
}
