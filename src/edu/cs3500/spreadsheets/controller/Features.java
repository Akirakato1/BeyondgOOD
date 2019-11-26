package edu.cs3500.spreadsheets.controller;

public interface Features {
  void submit(String newFormula);

  void cancel();
  
  void addRow();
  
  void addCol();

  void displayFormula(int row, int col);

}
