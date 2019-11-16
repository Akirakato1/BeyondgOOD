package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

/**
 * To represent a spreadsheet table to be rendered.
 * It has rows and columns and converts the model into a graphical view.
 * The default spreadsheet size is 100 x 100 cells, and row headers are gray and
 * uneditable.
 */
public class SpreadsheetTable extends JPanel {
  private JTable table;
  private final ISpreadsheetModel ss;
  private static final int MINIMUM_COLUMNS = 100;
  private static final int MINIMUM_ROWS = 100;
  private int col;
  private int row;
  private final int windowWidth;
  private final int windowHeight;

  /**
   * Constructor for SpreadsheetTable.
   *
   * @param ss spreadsheet model
   * @param ww window width
   * @param wh window height
   */
  public SpreadsheetTable(ISpreadsheetModel ss, int ww, int wh) {
    this.ss = ss;
    this.windowHeight = wh;
    this.windowWidth = ww;
    this.col = MINIMUM_COLUMNS;
    this.row = MINIMUM_ROWS;
    this.calculateRowCol();

    this.createTable();
  }

  /**
   * Creates the JTable for the GUI to use. (Used in SpreadsheetTable constructor).
   */
  private void createTable() {

    String[] header = this.generateHeader();
    String[][] data = this.generateContent();


    DefaultTableModel model = new DefaultTableModel(data, header);

    table = new JTable(model);

    table.setPreferredScrollableViewportSize(
            new Dimension(this.windowWidth - 100, this.windowHeight - 100));
    table.setFillsViewportHeight(true);

    JScrollPane js = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    js.setVisible(true);
    add(js);
    table.getColumnModel().getColumn(0).setCellRenderer(new GrayBackground());
    table.getColumnModel().getColumn(0).setPreferredWidth(50);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    FixedColumnTable fct = new FixedColumnTable(1, js);
    this.table = fct.getFixedTable();
  }

  /**
   * Calculates the maximum row column size.
   */
  private void calculateRowCol() {
    List<Coord> coords = ss.getOccupiedCoords();
    for (Coord c : coords) {
      if (c.col > this.col) {
        this.col = c.col;
      }
      if (c.row > this.row) {
        this.row = c.row;
      }
    }
    col++;
  }

  /**
   * Creates the table headers for the columns (eg A, B, C).
   *
   * @return array of string having the coordinate columns.
   */
  private String[] generateHeader() {
    String[] header = new String[col];
    header[0] = "";
    for (int i = 1; i < col; i++) {
      header[i] = Coord.colIndexToName(i);
    }
    return header;
  }

  /**
   * Gets the formula from the model and evaluates them. Then places it in the array of array of
   * string, which will represent our data in the view.
   */
  private String[][] generateContent() {
    String[][] content = new String[row][col];
    List<Coord> coords = ss.getOccupiedCoords();
    for (int i = 0; i < row; i++) {
      content[i][0] = i + 1 + "";
    }
    for (Coord c : coords) {
      if (ss.evaluateCell(c) == null) {
        content[c.row - 1][c.col] = "null" + c.toString();
      } else {
        content[c.row - 1][c.col] = ss.evaluateCell(c).toString();
      }
    }
    return content;
  }

  /**
   * Gets the table of the spreadsheet view. May be helpful in future assignments.
   *
   * @return spreadsheet Jtable
   */
  JTable getTable() {
    return table;

  }

}