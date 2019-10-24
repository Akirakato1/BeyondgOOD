package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import edu.cs3500.spreadsheets.sexp.Sexp;

public class Functions {
  
  
  class SUM{
    private List<Double> nums;
    
    public SUM(Double... contents) {
      this(Arrays.asList(contents));
    }

    public SUM(List<Double> contents) {
      this.nums = new ArrayList<>(Objects.requireNonNull(contents));
    }
    
    public double sum() {
      double output = 0;
      for(int i=0;i<nums.size();i++) {
        output+=nums.get(i);
      }
      return output;
    }
  }
}
