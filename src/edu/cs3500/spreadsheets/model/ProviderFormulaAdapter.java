package edu.cs3500.spreadsheets.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import edu.cs3500.spreadsheets.provider.model.IFormula;
import edu.cs3500.spreadsheets.provider.model.IValue;

/**
 * Adapts our Formula into their IFormula. We use it so the calculations performed onto the model
 * via the controller works with our code. In this case, the Formula is either a value or a
 * function.
 */
public class ProviderFormulaAdapter implements IFormula {
  Formula valueOrFunc;

  /**
   * Constructor to make a provider formula adapter.
   *
   * @param f given Formula
   */
  public ProviderFormulaAdapter(Formula f) {
    valueOrFunc = f;
  }

  @Override
  public IValue evaluate() throws IllegalStateException {
    Value eval = valueOrFunc.evaluate();
    if (this.checkIfErrorValue(eval)) {
      throw new IllegalStateException("ourValue was an error value");
    }
    return new ProviderValueAdapter(eval);
  }

  @Override
  public boolean hasReference(Coord coord) {
    throw new UnsupportedOperationException(
            "Provider Formula needs hasReference implementation");
  }

  @Override
  public List<IFormula> getParts() {
    throw new UnsupportedOperationException(
            "Provider Formula needs getParts implementation");
  }

  @Override
  public boolean hasCycle(Stack<Coord> alreadySeen, Set<Coord> noCycs) {
    throw new UnsupportedOperationException(
            "Provider Formula needs hasCycle implementation");
  }

  @Override
  public Set<edu.cs3500.spreadsheets.provider.model.SingleRef> getInitialRefs() {
    throw new UnsupportedOperationException(
            "Provider Formula needs getInitialrefs implementation");
  }

  /**
   * Checks if the value contains an error.
   *
   * @param v given value
   * @return true if value is an error
   */
  private boolean checkIfErrorValue(Value v) {
    List<Error> errorList = Arrays.asList(Error.values());
    for (Error err : errorList) {
      if (v.toString().equals(err.toString())) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    String output = this.valueOrFunc.toString();
    if (output.length() > 0 && output.charAt(0) == '(') {
      output = "=" + output;
    }
    return output;
  }
}
