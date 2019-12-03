package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

/**
 * A JPanel that displays the cells in a worksheet, in a scrollable grid with column and row
 * headers.
 */
class ScrollingCellPanel extends JPanel implements WorksheetView {

  private final JCellPanel cellPanel; // all the cells in the worksheet
  private final JVerticalHeader rowHeader; // numbers for the rows
  private final JHorizontalHeader columnHeader; // names for the columns

  /**
   * Makes a ScrollingCellPanel that corresponds to the given model, sets up all the components and
   * makes the view visible.
   *
   * @param model the worksheet holding the data for this view
   */
  ScrollingCellPanel(ReadOnlyWorksheetModel model) {
    // set up things inside the scrollpane
    this.cellPanel = new JCellPanel(model);
    this.rowHeader = new JVerticalHeader();
    this.columnHeader = new JHorizontalHeader();
    rowHeader.setPreferredHeight(cellPanel.getHeight());
    columnHeader.setPreferredWidth(cellPanel.getWidth());

    // set up the scrollPane
    // contains cellPanel
    JScrollPane scrollPane = new JScrollPane(this.cellPanel);
    scrollPane.setPreferredSize(new Dimension(800, 500));
    scrollPane.setOpaque(true);
    scrollPane.setRowHeaderView(this.rowHeader);
    scrollPane.setColumnHeaderView(this.columnHeader);
    JScrollBar horz = scrollPane.getHorizontalScrollBar();
    horz.addAdjustmentListener(adjustmentEvent -> {
      if ((adjustmentEvent.getValue() + horz.getVisibleAmount()) == horz.getMaximum()) {
        ScrollingCellPanel.this.extendColumns(15);
      }
    });

    JScrollBar vert = scrollPane.getVerticalScrollBar();
    vert.addAdjustmentListener(adjustmentEvent -> {
      if ((adjustmentEvent.getValue() + vert.getVisibleAmount()) == vert.getMaximum()) {
        ScrollingCellPanel.this.extendRows(15);
      }
    });

    this.setLayout(new BorderLayout());
    this.add(scrollPane);
    this.setVisible(true);
  }

  /**
   * Extends the number of rows to be displayed on our view.
   *
   * @param rows number of rows to add
   */
  private void extendRows(int rows) {
    cellPanel.addRows(rows);
    rowHeader.setPreferredHeight(this.cellPanel.getHeight());
  }

  /**
   * Extends the number of columns to be displayed on our view.
   *
   * @param cols number of columns to add
   */
  private void extendColumns(int cols) {
    cellPanel.addCols(cols);
    columnHeader.setPreferredWidth(this.cellPanel.getWidth());
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * This method does nothing, because the ScrollingCellPanel does not support interactivity on its
   * own.
   *
   * @param sf the SpreadsheetFeatures to add to the view
   */
  @Override
  public void addSpreadsheetFeatures(SpreadsheetFeatures sf) {
    // do nothing, because the ScrollingCellPanel does not support interactivity on its own
  }

  @Override
  public void highlightAt(Coord loc) {
    this.cellPanel.highlightCellAt(loc);
  }

  @Override
  public void turnOffHighlight() {
    this.cellPanel.removeHighlight();
  }

  @Override
  public void displayMsg(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  /**
   * Gets the location of the highlighted coordinate on the cell panel.
   *
   * @return the coordinate of the highlighted cell or null if no cell is being highlighted
   */
  Coord getHighlightLoc() {
    return this.cellPanel.getHighlightedLoc();
  }

  /**
   * Adds a mouse listener to respond to clicks on the grid of cells. This mouse listener will not
   * respond to events not on the grid, eg the column/row headers or scrollbars.
   *
   * @param cellsMouseListener the mouse listener being added
   */
  void addGridMouseListener(MouseListener cellsMouseListener) {
    this.cellPanel.addMouseListener(cellsMouseListener);
  }

  /**
   * Gets the current number of visible rows.
   *
   * @return the number of rows
   */
  int getCurRows() {
    return this.cellPanel.getCurRows();
  }

  /**
   * Gets the current number of visible columns.
   *
   * @return the number of columns
   */
  int getCurCols() {
    return this.cellPanel.getCurCols();
  }


  @Override
  public void changeModel(ReadOnlyWorksheetModel model) {
    this.cellPanel.changeModel(model);
  }
}
