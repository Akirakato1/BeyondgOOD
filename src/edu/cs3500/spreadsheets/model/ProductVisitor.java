package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductVisitor implements ValueVisitor<List<Double>>{

  @Override
  public List<Double> visitBool(boolean b) {
    return new ArrayList<Double>(Arrays.asList(1.0,1.0));
  }

  @Override
  public List<Double> visitStr(String s) {
    return new ArrayList<Double>(Arrays.asList(1.0,1.0));
  }

  @Override
  public List<Double> visitNum(double d) {
    return new ArrayList<Double>(Arrays.asList(d,0.0));
  }

  @Override
  public List<Double> visitBlank() {
    return new ArrayList<Double>(Arrays.asList(1.0,1.0));
  }

}
