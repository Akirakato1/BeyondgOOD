package edu.cs3500.spreadsheets.model;

public abstract class AbstractFunction implements Function {
  Formula[] arguments;

  public AbstractFunction(Formula... args) {
    arguments = args;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    boolean output=false;
    for(Formula arg:arguments) {
      output=output||arg.cyclePresent(currentCoord);
    }
    return output;
  }

}
