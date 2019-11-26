package edu.cs3500.spreadsheets.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

/**
 * To represent a spreadsheet table to be rendered. It has rows and columns and converts the model
 * into a graphical view. The default spreadsheet size is 100 x 100 cells, and row headers are gray
 * and uneditable.
 */
public class SpreadsheetTable extends JPanel {
  private JTable table;
  private final ISpreadsheetViewOnly ss;
  private final int windowWidth;
  private final int windowHeight;

  /**
   * Constructor for SpreadsheetTable.
   *
   * @param ss spreadsheet model
   * @param ww window width
   * @param wh window height
   */
  public SpreadsheetTable(ISpreadsheetViewOnly ss, int ww, int wh) {
    this.ss = ss;
    this.windowHeight = wh;
    this.windowWidth = ww;
    this.createTable();
  }

  /**
   * Creates the JTable for the GUI to use. (Used in SpreadsheetTable constructor).
   */
  private void createTable() {
    String[] header = this.generateHeader();
    String[][] data = this.generateContent();


    CellUneditable model = new CellUneditable(data, header);
    table = new JTable(model);
    table.setPreferredScrollableViewportSize(
        new Dimension(this.windowWidth - 150, this.windowHeight - 150));
    table.setFillsViewportHeight(true);

    JScrollPane js = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    js.setVisible(true);
    add(js);
    table.getColumnModel().getColumn(0).setCellRenderer(new GrayBackground());
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

  }

  /**
   * Creates the table headers for the columns (eg A, B, C).
   *
   * @return array of string having the coordinate columns.
   */
  private String[] generateHeader() {
    String[] header = new String[ss.getCol()];
    header[0] = "";
    for (int i = 1; i < ss.getCol(); i++) {
      header[i] = Coord.colIndexToName(i);
    }
    return header;
  }

  /**
   * Gets the formula from the model and evaluates them. Then places it in the array of array of
   * string, which will represent our data in the view.
   */
  private String[][] generateContent() {
    String[][] content = new String[ss.getRow()][ss.getCol() + 1];
    List<Coord> coords = ss.getOccupiedCoords();
    for (int i = 0; i < ss.getRow(); i++) {
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

  public void addFeatures(Features f) {
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint()) + 1;
        int col = table.columnAtPoint(evt.getPoint());
        f.displayFormula(row, col);

      }
    });
  }

  /**
   * Gets the table of the spreadsheet view. May be helpful in future assignments.
   *
   * @return spreadsheet Jtable
   */
  JTable getTable() {
    return table;
  }

  void rebuildTable() {
    // this.createTable();
  }

  void updateCellValue(String value, int row, int col) {
    this.table.setValueAt(value, row - 1, col);
  }

  void increaseRow() {
    CellUneditable model = (CellUneditable) table.getModel();
    String[] rh = new String[1];
    rh[0] = ss.getRow() + "";
    model.addRow(rh);
  }

  void increaseCol() {
    CellUneditable model = (CellUneditable) table.getModel();
    model.addColumn(Coord.colIndexToName(ss.getCol() - 1), new String[0]);
    table.setModel(model);
    table.getColumnModel().getColumn(0).setCellRenderer(new GrayBackground());
    table.getColumnModel().getColumn(0).setPreferredWidth(50);
  }

  class CellUneditable extends DefaultTableModel {

    CellUneditable(String[][] data, String[] header) {
      super(data, header);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }

  }
}
