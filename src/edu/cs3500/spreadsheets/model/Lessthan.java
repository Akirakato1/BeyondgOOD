package edu.cs3500.spreadsheets.model;

public class Lessthan extends AbstractFunction {
  
  public Lessthan(Formula...args) {
    super(args);
    if(args.length!=2) {
      throw new IllegalArgumentException("not exactly 2 arguments to lessthan");
    }
  }
  
  @Override
  public Value evaluate() {
    Value output;
    double[] input=new double[2];
    for(int i=0;i<2;i++) {
      output=arguments[i].evaluate();
      switch(output.getType()) {
        case "Num":
          input[i]=output.getDouble();
          break;
        default:
      }
    }
    return new Bool(input[0]<input[1]);
  }

  @Override
  public String getType() {
    return "<";
  }
 


}
