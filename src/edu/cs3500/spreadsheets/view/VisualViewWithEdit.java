package edu.cs3500.spreadsheets.view;

import java.awt.GridBagLayout;
import java.util.HashMap;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

/**
 * To represent a visual view that allows the user to see a textbox so they can edit the contents of
 * a cell. The view also contains buttons that allow the user to add more columns/rows to the view.
 */
public class VisualViewWithEdit extends AbstractVisualView {
  private EditBoxAndExpand editUI;
  private SaveOpen saveopen;

  /**
   * Constructor for visual view edit.
   * 
   * @param name name of file
   * @param ss spreadsheet model
   * @param windowWidth window width
   * @param windowHeight window height
   */
  public VisualViewWithEdit(String name, ISpreadsheetViewOnly ss, int windowWidth,
      int windowHeight) {
    super(name, ss, windowWidth, windowHeight);
    this.editUI = new EditBoxAndExpand();
    this.saveopen = new SaveOpen();
  }

  @Override
  public void render() {
    super.renderSetup();
    JPanel container = new JPanel(new GridBagLayout());
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.add(saveopen);
    container.add(editUI);
    container.add(table);
    jf.add(container, BorderLayout.CENTER);
    jf.setVisible(true);
  }

  @Override
  public void addFeatures(Features f) {
    this.saveopen.addFeatures(f);
    this.table.addFeatures(f);
    this.editUI.addFeatures(f);
  }

  @Override
  public void setFormulaDisplay(String formula) {
    editUI.setTextbox(formula);
  }

  @Override
  public void updateCellValue(String value, int row, int col) {
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

  @Override
  public void close() {
    this.jf.setVisible(false);
  }

  @Override
  public HashMap<String, Integer> getColumnWidths() {
    return this.table.getCurrentColWidths();
  }

}
