package edu.cs3500.spreadsheets.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.cs3500.spreadsheets.controller.Features;

/**
 * To represent the editing textbox seen when a user needs to edit a cell. This panel also contains
 * several buttons that allows the user to add columns, submit the current edits, cancel the edits,
 * and add more rows to the spreadsheet.
 */
class EditBoxAndExpand extends JPanel {
  private JButton submit;
  private JButton cancel;
  private JButton addRow;
  private JButton addCol;
  private JTextField textbox;

  /**
   * Constructor for the text box and button panel.
   */
  EditBoxAndExpand() {
    this.addTextbox();
    this.addButtons();
  }

  /**
   * Initializes the textbox.
   */
  private void addTextbox() {
    textbox = new JTextField(30);
    add(textbox);
  }

  /**
   * Adds the submit/cancel/addRow/addCol buttons to the panel.
   */
  private void addButtons() {
    submit = new JButton("Submit");
    cancel = new JButton("Cancel");
    addRow = new JButton("Add a Row");
    addCol = new JButton("Add a Column");
    add(submit);
    add(cancel);
    add(addRow);
    add(addCol);
  }


  /**
   * Sets the textbox text.
   */
  void setTextbox(String s) {
    this.textbox.setText(s);
  }

  /**
   * Adds the functionality of each button given a Feature.
   *
   * @param f feature controller
   */
  void addFeatures(Features f) {
    submit.addActionListener(evt -> f.submit(this.textbox.getText()));
    cancel.addActionListener(evt -> f.cancel());
    addRow.addActionListener(evt -> f.addRow());
    addCol.addActionListener(evt -> f.addCol());
    submit.setActionCommand("submit");
    cancel.setActionCommand("cancel");
    addRow.setActionCommand("addRow");
    addCol.setActionCommand("addCol");
  }
}
