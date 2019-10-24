package edu.cs3500.spreadsheets.model;

import java.util.List;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class GetValues implements SexpVisitor{

  @Override
  public Boolean visitBoolean(boolean b) {
    return b;
  }

  @Override
  public Double visitNumber(double d) {
    return d;
  }

  @Override
  public List<Sexp> visitSList(List l) {
    return l;
  }

  @Override
  public String visitSymbol(String s) {
    return s;
  }

  @Override
  public String visitString(String s) {
    return s;
  }

  

}
