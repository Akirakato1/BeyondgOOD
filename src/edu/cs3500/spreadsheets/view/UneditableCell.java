package edu.cs3500.spreadsheets.view;

import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

/**
 * To represent an uneditable cell. Used in JTable on the row header column so users cannot edit the
 * headers.
 */
class UneditableCell extends DefaultCellEditor {

  /**
   * Constructor for an uneditable cell.
   * @param textField text field inside the cell
   */
  public UneditableCell(JTextField textField) {
    super(textField);
  }

  @Override
  public boolean isCellEditable(EventObject anEvent) {
    return false;
  }
}
