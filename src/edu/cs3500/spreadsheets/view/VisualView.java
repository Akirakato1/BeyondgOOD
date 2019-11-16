package edu.cs3500.spreadsheets.view;

import javax.swing.JFrame;

import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

/**
 * To represent a visual view. A user can use this to see the actual GUI/spreadsheet with all its
 * cells. It uses JTable to display the cells and we made it so that if we scroll horizontally, the
 * row column header also scrolls with the table (meaning that it is fixed). Current implementation
 * does not show formulas (shows only values).
 */
public class VisualView implements SpreadsheetView {
  private ISpreadsheetModel ss;
  private String name;
  private int windowWidth;
  private int windowHeight;

  /**
   * Constructor for the visual view.
   *
   * @param filename file name of the spreadsheet to be created
   * @param ss       spreadsheet model
   * @param ww       width of gui window
   * @param wh       height of gui window
   */
  public VisualView(String filename, ISpreadsheetModel ss, int ww, int wh) {
    this.ss = ss;
    this.windowHeight = wh;
    this.windowWidth = ww;
    name = filename;
  }

  @Override
  public void render() {
    JFrame jf = new JFrame();
    SpreadsheetTable tab = new SpreadsheetTable(ss, this.windowWidth, this.windowHeight);
    jf.setTitle(name);
    jf.setSize(this.windowWidth, this.windowHeight);
    jf.setVisible(true);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.add(tab);
  }

}
