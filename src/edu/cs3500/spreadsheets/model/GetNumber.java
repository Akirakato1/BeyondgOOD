package edu.cs3500.spreadsheets.model;

import java.util.List;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class GetNumber implements SexpVisitor<Double>{

  @Override
  public Double visitBoolean(boolean b) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double visitNumber(double d) {
    return d;
  }

  @Override
  public Double visitSList(List<Sexp> l) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double visitSymbol(String s) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Double visitString(String s) {
    // TODO Auto-generated method stub
    return null;
  }

}
