package edu.cs3500.spreadsheets.controller;

public interface Features {
  void submit(String newFormula);

  void cancel();
  
  void addRow();
  
  void addCol();

  void displayFormula(int row, int col);

  void save(String filename);

  void open(String filename);

  void move(String dir);

}
