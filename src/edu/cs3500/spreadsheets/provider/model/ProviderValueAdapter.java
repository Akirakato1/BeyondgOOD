package edu.cs3500.spreadsheets.provider.model;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Error;
import edu.cs3500.spreadsheets.model.Value;

public class ProviderValueAdapter extends ProviderFormulaAdapter implements IValue{
  
  public ProviderValueAdapter(Value v) {
    super(v);
  }
  
  @Override
  public <R> R accept(IValueVisitor<R> visitor) {
    throw new UnsupportedOperationException("provider value needs accept implementation apparantly");
  }

}
