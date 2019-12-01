package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.util.Objects;
import javax.swing.JPanel;

import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;

/**
 * A panel that draws all the cells in a worksheet as a grid, along with column and row headers.
 */
class JCellPanel extends JPanel {

  private ReadOnlyWorksheetModel model;
  private int curRows;
  private int curCols;
  static final int cellWidth = 100;
  static final int cellHeight = 30;
  static final int clipPix = 4; // amount the text is offset from the cell border
  private Coord highlighted; // location that is highlighted; NULL if no cell highlighted

  /**
   * Creates a JCellPanel that displays the contents in the given worksheet model.
   *
   * @param model the model to base the view off of
   */
  JCellPanel(ReadOnlyWorksheetModel model) {
    this.model = model;
    curRows = Math.max(model.getContentRows(), 100);
    curCols = Math.max(model.getContentCols(), 56);
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    this.setVisible(true);
  }

  /**
   * Draws all the cells that are currently visible from the scrolling viewport, along with their
   * contents. At minimum, 50 rows and 26 columns are displayed - more may be visible if the user
   * has added rows and columns or the spreadsheet is larger.
   *
   * @param g the Graphics object to draw with
   */
  @Override
  protected void paintComponent(Graphics g) {
    //never forget to call super.paintComponent!
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // area currently shown in the scrolling viewport (in pixels)
    Rectangle drawHere = this.getVisibleRect();

    g2d.setColor(Color.WHITE);
    g2d.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

    Shape oldClip = g2d.getClip();
    g2d.setColor(Color.BLACK);

    // first and last row/col that is shown or partially shown
    int firstRow = drawHere.y / JCellPanel.cellHeight;
    int lastRow = (int) Math
        .ceil((double) (drawHere.y + drawHere.height) / (double) (JCellPanel.cellHeight));
    int firstCol = drawHere.x / JCellPanel.cellWidth;
    int lastCol = (int) Math
        .ceil((double) (drawHere.x + drawHere.width) / (double) (JCellPanel.cellWidth));

    int topLeftX = firstCol * JCellPanel.cellWidth;
    int topLeftY = firstRow * JCellPanel.cellHeight;

    for (int row = firstRow; row <= lastRow; row += 1) { //drawing the cells!!! yeehaw!!!
      for (int col = firstCol; col <= lastCol; col += 1) {
        Coord curLoc = new Coord(col + 1, row + 1);
        g2d.drawRect(topLeftX, topLeftY, cellWidth, cellHeight);
        g2d.clipRect(topLeftX + clipPix, topLeftY + clipPix, cellWidth - clipPix * 2,
            cellHeight - clipPix * 2);
        g2d.drawString(this.getValString(curLoc),
            topLeftX + clipPix, topLeftY + (cellHeight * 3 / 4));
        g2d.setClip(oldClip);
        topLeftX += cellWidth;
      }
      topLeftX = firstCol * JCellPanel.cellWidth;
      topLeftY += cellHeight;
    }

    // draw the highlighted border over whatever was originally drawn
    if (this.highlighted != null) {
      g2d.setStroke(new BasicStroke(2));
      g2d.setColor(new Color(36, 186, 255));
      topLeftX = (highlighted.col - 1) * cellWidth;
      topLeftY = (highlighted.row - 1) * cellHeight;
      g2d.drawRect(topLeftX, topLeftY, cellWidth, cellHeight);
    }
  }

  /**
   * Displays the cell at the given location as highlighted; unhighlights any previously highlighted
   * cell.
   *
   * @param loc the location to highlight at
   * @throws NullPointerException if the Coord is null
   */
  void highlightCellAt(Coord loc) {
    Objects.requireNonNull(loc);
    this.highlighted = loc;
  }

  /**
   * Turns off highlighting for any cell that was previously highlighted.
   */
  void removeHighlight() {
    this.highlighted = null;
  }

  /**
   * Returns the coordinate of the cell that is currently highlighted.
   *
   * @return location of the editing cell, or null if no cell is being highlighted
   */
  Coord getHighlightedLoc() {
    return this.highlighted;
  }


  /**
   * Returns a string representation of the evaluated value at the location, or an error message if
   * the location cannot be evaluated.
   *
   * @param loc cell coordinates to get the value from
   * @return string representation of the evaluated value, or an error message
   */
  private String getValString(Coord loc) {
    try {
      return model.getValueAt(loc).toString();
    } catch (IllegalStateException e) {
      return "#" + e.getMessage();
    }
  }

  /**
   * Gets the current number of visible rows.
   *
   * @return the number of rows
   */
  int getCurRows() {
    return this.curRows;
  }

  /**
   * Gets the current number of visible columns.
   *
   * @return the number of columns
   */
  int getCurCols() {
    return this.curCols;
  }

  @Override
  public int getWidth() {
    return this.curCols * cellWidth;
  }

  @Override
  public int getHeight() {
    return this.curRows * cellHeight;
  }

  /**
   * Converts the given x and y pixel coordinate into the corresponding cell location.
   *
   * @param x the x value pixel coordinate
   * @param y the y value pixel coordinate
   * @return the coordinate of the grid that x and y is located at
   */
  static Coord cellCoordAt(int x, int y) {
    return new Coord((x / cellWidth) + 1, (y / cellHeight) + 1);
  }

  /**
   * Adds the given number of rows to the display.
   *
   * @param rows number of rows to add
   */
  void addRows(int rows) {
    this.curRows += rows;
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    this.revalidate();
  }

  /**
   * Adds the given number of columns to the display.
   *
   * @param cols number of columns to add
   */
  void addCols(int cols) {
    this.curCols += cols;
    this.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
    this.revalidate();
  }

  /**
   * Changes the model being displayed by this cell panel.
   *
   * @param model the new model to be displayed
   */
  void changeModel(ReadOnlyWorksheetModel model) {
    this.model = model;
  }
}
