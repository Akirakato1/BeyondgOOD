package edu.cs3500.spreadsheets.model;

import java.util.List;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class ProcessSum implements SexpVisitor<Double> {

  ISpreadsheetModel ss;
  String curCell;
  
  public ProcessSum(ISpreadsheetModel ss, String curCell) {
    this.ss = ss;
    this.curCell=curCell;
  }

  @Override
  public Double visitBoolean(boolean b) {
    return 0.0;
  }

  @Override
  public Double visitNumber(double d) {
    // TODO Auto-generated method stub
    return d;
  }

  @Override
  public Double visitSList(List<Sexp> l) {
    double output = 0;
    Sexp arg;
    for (int i = 1; i < l.size(); i++) {
      arg = l.get(i);
      if (arg instanceof SList) {
        output += arg.accept(new GeneralProcess()).accept(this);
      } else if (SymbolCheck.isValidRectangle(arg.toString())) {
        List<Sexp> temp = SymbolCheck.expand(arg.toString());
        temp.add(0, l.get(0));
        output += new SList(temp).accept(this);
      } else {
        output += arg.accept(this);
      }
    }
    return output;
  }

  @Override
  public Double visitSymbol(String s) {
    if(s.equals(this.curCell)) {
      throw new IllegalArgumentException("Cycle detected");
    }
    if (SymbolCheck.isValidCell(s)) {
      return this.ss.evalCell(s).accept(this);
    } else {
      return 0.0;
    }
  }

  @Override
  public Double visitString(String s) {
    return 0.0;
  }

}
