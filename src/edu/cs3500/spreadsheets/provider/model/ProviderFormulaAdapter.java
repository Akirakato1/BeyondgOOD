package edu.cs3500.spreadsheets.provider.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Error;
import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.Value;

public class ProviderFormulaAdapter implements IFormula{
  Formula valueOrFunc;

  public ProviderFormulaAdapter(Formula f) {
    valueOrFunc = f;
  }

  @Override
  public IValue evaluate() throws IllegalStateException {
    Value eval = valueOrFunc.evaluate();
    if (this.checkIfErrorValue(eval)) {
      throw new IllegalStateException("ourValue was error value");
    }
    return new ProviderValueAdapter(eval);
  }

  @Override
  public boolean hasReference(Coord coord) {
    throw new UnsupportedOperationException(
        "provider Formula needs hasReference implementation apparantly");
  }

  @Override
  public List<IFormula> getParts() {
    throw new UnsupportedOperationException(
        "provider Formula needs getParts implementation apparantly");
  }

  @Override
  public boolean hasCycle(Stack<Coord> alreadySeen, Set<Coord> noCycs) {
    throw new UnsupportedOperationException(
        "provider Formula needs hasCycle implementation apparantly");
  }

  @Override
  public Set<SingleRef> getInitialRefs() {
    throw new UnsupportedOperationException(
        "provider Formula needs getinitialrefs implementation apparantly");
  }

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
    String output=this.valueOrFunc.toString();
    /*if(output.charAt(0)=='(') {
      output="="+output;
    }*/
    return output;
  }
}
