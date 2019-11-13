package edu.cs3500.spreadsheets.view;

import java.util.EventObject;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class UneditableCell extends DefaultCellEditor {

  public UneditableCell(JCheckBox checkBox) {
    super(checkBox);
  }

  public UneditableCell(JComboBox comboBox) {
    super(comboBox);
  }

  public UneditableCell(JTextField textField) {
    super(textField);
  }

  @Override
  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }
}
