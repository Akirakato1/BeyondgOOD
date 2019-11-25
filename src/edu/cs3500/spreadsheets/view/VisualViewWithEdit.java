package edu.cs3500.spreadsheets.view;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

/**
 * To represent a visual view that allows the user to see a textbox so
 * they can edit the contents of a cell. The view also contains buttons
 * that allow the user to add more columns/rows to the view.
 */
public class VisualViewWithEdit implements SpreadsheetView {
  private ISpreadsheetViewOnly ss;
  private String name;
  private int windowWidth;
  private int windowHeight;
  private SpreadsheetTable table;
  private EditBoxAndExpand editUI;
  JFrame jf = new JFrame();

  public VisualViewWithEdit(String name, ISpreadsheetViewOnly ss, int windowWidth,
      int windowHeight) {
    this.table = new SpreadsheetTable(ss, windowWidth, windowHeight);
    this.ss=ss;
    this.editUI = new EditBoxAndExpand();
    this.windowHeight = windowHeight;
    this.windowWidth = windowWidth;
    this.name = name;
  }

  @Override
  public void render() {
    jf = new JFrame();
    jf.setTitle(name);
    jf.setSize(this.windowWidth, this.windowHeight);
    jf.setVisible(true);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.add(editUI);
    container.add(table);
    jf.add(container, BorderLayout.CENTER);
    System.out.println(container + " " + table + " " + editUI);
  }

  @Override
  public void addFeatures(Features f) {
    this.table.addFeatures(f);
    this.editUI.addFeatures(f);
  }

  @Override
  public void refresh() {
    this.table.rebuildTable();
  }

  @Override
  public void setFormulaDisplay(String formula) {
    editUI.setTextbox(formula);
  }
  
  @Override
  public void updateCellValue(String value,int row,int col) {
    this.table.updateCellValue(value, row, col);
  }
  
  @Override
  public void increaseRow() {
   this.table.increaseRow();
  }
  
  @Override
  public void increaseCol() {
    this.table.increaseCol();
  }

}
