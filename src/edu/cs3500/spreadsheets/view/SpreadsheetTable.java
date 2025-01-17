package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;

/**
 * To represent a spreadsheet table to be rendered. It has rows and columns and converts the model
 * into a graphical view. The default spreadsheet size is 100 x 100 cells, and row headers are gray
 * and uneditable.
 */
class SpreadsheetTable extends JPanel {
  private JTable table;
  private final ISpreadsheetViewOnly ss;
  private final int windowWidth;
  private final int windowHeight;
  private HashMap<String, Integer> localColWidthMap = new HashMap<>();

  /**
   * Constructor for SpreadsheetTable.
   *
   * @param ss spreadsheet model
   * @param ww window width
   * @param wh window height
   */
  SpreadsheetTable(ISpreadsheetViewOnly ss, int ww, int wh) {
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
    this.setColWidths();
    table.getColumnModel().getColumn(0).setPreferredWidth(50);
  }

  /**
   * Set the column width to that stored in the model.
   */
  protected void setColWidths() {
    for (int i = 1; i < ss.getCol(); i++) {
      this.table.getColumnModel().getColumn(i)
          .setPreferredWidth(ss.getColWidth(Coord.colIndexToName(i)));
      this.table.getColumnModel().getColumn(i).setWidth(ss.getColWidth(Coord.colIndexToName(i)));
    }
  }

  /**
   * Creates and returns a hashmap between columnheader to its width. Mapping is created if the
   * width is not equal to the default width.
   * 
   * @return Hashmap of header to width.
   */
  protected HashMap<String, Integer> getCurrentColWidths() {
    HashMap<String, Integer> output = new HashMap<>();
    int colWidth;
    for (int i = 1; i < this.table.getColumnCount(); i++) {
      colWidth = this.table.getColumnModel().getColumn(i).getWidth();
      // if (colWidth != SpreadsheetModel.DEFAULT_COL_WIDTH) {
      output.put(Coord.colIndexToName(i), colWidth);
      // }
    }
    return output;
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

  /**
   * Adds the mouse and keyboard listening features to the table. If a user clicks a specific cell,
   * it will highlight the cell and then display the formula. If a key is pressed, it will move the
   * user's selected cell and display the corresponding formula.
   *
   * @param f controller feature
   */
  void addFeatures(Features f) {
    table.addMouseListener(new java.awt.event.MouseAdapter() {
      @Override
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        int row = table.rowAtPoint(evt.getPoint()) + 1;
        int col = table.columnAtPoint(evt.getPoint());
        f.displayFormula(row, col);

      }
    });
    table.addKeyListener(new KeyListener() {

      @Override
      public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
          case KeyEvent.VK_BACK_SPACE:
            System.out.println("reach delete in table");
            f.submit("");
            break;
          case KeyEvent.VK_LEFT:
            System.out.println("rech left");
            f.move("left");
            break;
          case KeyEvent.VK_RIGHT:
            f.move("right");
            break;
          case KeyEvent.VK_UP:
            f.move("up");
            break;
          case KeyEvent.VK_DOWN:
            f.move("down");
            break;
          default:
            break;
        }
      }

      @Override
      public void keyReleased(KeyEvent e) {
        // Do nothing.

      }

      @Override
      public void keyTyped(KeyEvent e) {
        // Do nothing.

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


  /**
   * Updates the current cell value in the table.
   *
   * @param value new formula
   * @param row row of cell
   * @param col column of cell
   */
  void updateCellValue(String value, int row, int col) {
    this.table.setValueAt(value, row - 1, col);
  }

  /**
   * Increases number of visible rows.
   */
  void increaseRow() {
    CellUneditable model = (CellUneditable) table.getModel();
    String[] rh = new String[1];
    rh[0] = ss.getRow() + "";
    model.addRow(rh);
  }

  /**
   * Increases number of visible columns.
   */
  void increaseCol() {
    CellUneditable model = (CellUneditable) table.getModel();
    localColWidthMap = this.getCurrentColWidths();
    model.addColumn(Coord.colIndexToName(ss.getCol() - 1), new String[0]);
    table.setModel(model);
    table.getColumnModel().getColumn(0).setCellRenderer(new GrayBackground());
    table.getColumnModel().getColumn(0).setPreferredWidth(50);
    this.setColWidthsTable();
  }

  /**
   * Sets the table's width with the cached table width data.
   */
  private void setColWidthsTable() {
    for (String col : this.localColWidthMap.keySet()) {
      this.table.getColumnModel().getColumn(Coord.colNameToIndex(col))
          .setPreferredWidth(this.localColWidthMap.get(col));
      this.table.getColumnModel().getColumn(Coord.colNameToIndex(col))
          .setWidth(this.localColWidthMap.get(col));
    }
  }

  /**
   * To represent an uneditable cells. Cells that use this class cannot be editted by the user
   * (disables double-clicking feature that is a default for jtable).
   */
  class CellUneditable extends DefaultTableModel {

    /**
     * Constructor for uneditable cell.
     *
     * @param data data in array form
     * @param header header array to display
     */
    CellUneditable(String[][] data, String[] header) {
      super(data, header);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
      return false;
    }

  }
}
