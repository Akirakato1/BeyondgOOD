package edu.cs3500.spreadsheets.view;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import edu.cs3500.spreadsheets.controller.Features;

public class EditBoxAndExpand extends JPanel{
  private JButton submit, cancel, addRow, addCol;
  private JTextField textbox;
  public EditBoxAndExpand() {
    this.addTextbox();
    this.addButtons();
  }
  
  private void addTextbox() {
    textbox=new JTextField(30);
    add(textbox);
  }
  
  private void addButtons() {
    submit=new JButton("submit");
    cancel=new JButton("cancel");
    addRow=new JButton("addRow");
    addCol=new JButton("addCol");
    add(submit);
    add(cancel);
    add(addRow);
    add(addCol);
  }
  
  public String getTextbox() {
    return this.textbox.getText();
  }
  
  public void setTextbox(String s) {
    this.textbox.setText(s);
  }

  public void addFeatures(Features f) {
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
