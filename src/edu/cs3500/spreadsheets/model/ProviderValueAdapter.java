package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.provider.model.IValue;
import edu.cs3500.spreadsheets.provider.model.IValueVisitor;

/**
 * Turns our Value into provider's IValue. Since it is very similar to the ProviderFormulaAdapter,
 * we abstracted it and added the accept method. This class is used to make sure their
 * view and model interfaces work with our code.
 */
public class ProviderValueAdapter extends ProviderFormulaAdapter implements IValue {
  /**
   * Constructor for provider value adapter.
   * @param v given value object (our interface)
   */
  public ProviderValueAdapter(Value v) {
    super(v);
  }
  
  @Override
  public <R> R accept(IValueVisitor<R> visitor) {
    throw new UnsupportedOperationException("provider value needs accept implementation");
  }

}
