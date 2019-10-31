package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a product visitor that is used when for the product object. It visits the various
 * Values and outputs an array of double representing if the value should be ignored or not, and the
 * actual value (if it is a number).
 */
public class ProductVisitor implements ValueVisitor<List<Double>> {

  @Override
  public List<Double> visitBool(boolean b) {
    return new ArrayList<>(Arrays.asList(1.0, 1.0));
  }

  @Override
  public List<Double> visitStr(String s) {
    return new ArrayList<>(Arrays.asList(1.0, 1.0));
  }

  @Override
  public List<Double> visitNum(double d) {
    return new ArrayList<>(Arrays.asList(d, 0.0));
  }

  @Override
  public List<Double> visitBlank() {
    return new ArrayList<>(Arrays.asList(1.0, 1.0));
  }

}
