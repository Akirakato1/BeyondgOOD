package edu.cs3500.spreadsheets.view;

import java.awt.Component;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

public class SpreadsheetTable extends JPanel {
  private JTable table;
  private final ISpreadsheetModel ss;
  private static final int MINIMUM_COLUMNS = 100;
  private static final int MINIMUM_ROWS = 100;
  private int col;
  private int row;
  private final int windowWidth;
  private final int windowHeight;

  public SpreadsheetTable(ISpreadsheetModel ss, int ww, int wh) {
    this.ss = ss;
    this.windowHeight = wh;
    this.windowWidth = ww;
    this.col = MINIMUM_COLUMNS;
    this.row = MINIMUM_ROWS;
    this.calculateRowCol();

    String[] header = this.generateHeader();
    String[][] data = this.generateContent();


    DefaultTableModel model = new DefaultTableModel(data, header);

    table = new JTable(model);

    table.setPreferredScrollableViewportSize(
        new Dimension(this.windowWidth - 50, this.windowHeight - 100));
    table.setFillsViewportHeight(true);

    JScrollPane js = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    js.setVisible(true);
    add(js);
    table.getColumnModel().getColumn(0).setCellRenderer(new RowHeaderRenderer());
    FrozenTablePane ftp=new FrozenTablePane(table,4);
  }

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

  private String[] generateHeader() {
    String[] header = new String[col];
    header[0] = "";
    for (int i = 1; i < col; i++) {
      header[i] = Coord.colIndexToName(i);
    }
    return header;
  }

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

  static class RowHeaderRenderer extends DefaultTableCellRenderer {
    public RowHeaderRenderer() {
      setHorizontalAlignment(JLabel.CENTER);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
        boolean hasFocus, int row, int column) {
      if (table != null) {
        JTableHeader header = table.getTableHeader();

        if (header != null) {
          setForeground(header.getForeground());
          setBackground(header.getBackground());
          setFont(header.getFont());
        }
      }
      setValue(value);
      return this;
    }
  }

}