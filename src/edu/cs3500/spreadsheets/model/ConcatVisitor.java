package edu.cs3500.spreadsheets.model;

public class ConcatVisitor implements ValueVisitor<String> {

  @Override
  public String visitBool(boolean b) {
    return b+"";
  }

  @Override
  public String visitStr(String s) {
    return s;
  }

  @Override
  public String visitNum(double d) {
    return d+"";
  }

  @Override
  public String visitBlank() {
    return "";
  }

}
