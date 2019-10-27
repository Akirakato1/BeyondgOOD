package edu.cs3500.spreadsheets.model;

public class RectangleRef implements Ref {
  Coord first;
  Coord second;
  ISpreadsheetModel ss;
  

  public RectangleRef(Coord first, Coord second,ISpreadsheetModel ss) {
    if(!(first.col<=second.col&&first.row<=second.row)) {
      throw new IllegalArgumentException("First coord not less than or equal to second coord");
    }
    
    this.first=first;
    this.second=second;
    this.ss=ss;
  }

  @Override
  public Value evaluate() {
    return new Blank();
  }

  @Override
  public String getType() {
    return "RectangleRef";
  }

  public Formula[] expand() {
    Formula[] expanded=new Formula[(second.col-first.col+1)*(second.row-first.row+1)];
    int k=0;
    for(int i=first.row;i<=second.row;i++) {
      for(int j=first.col;j<=second.col;j++) {
        expanded[k]=new SingleRef(new Coord(j,i),ss);
        k++;
      }
    }
    return expanded;
  }

  @Override
  public boolean cyclePresent(Coord currentCoord) {
    Formula[] refs=this.expand();
    boolean output=false;
    for(Formula r:refs) {
      output=output||r.cyclePresent(currentCoord);
    }
    return output;
  }

}
