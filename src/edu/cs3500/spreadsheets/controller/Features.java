package edu.cs3500.spreadsheets.controller;

import java.awt.event.ActionListener;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;

public interface Features {
  void submit(String newFormula);

  void cancel();
  
  void addRow();
  
  void addCol();

  void displayFormula(int row, int col);

}
