package edu.cs3500.spreadsheets.provider.view;

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * A header for the rows in a spreadsheet, that displays the number for each row in a vertical
 * column.
 */
class JVerticalHeader extends JComponent {

  /**
   * Sets the preferred height, which should correspond to the height of the grid.
   *
   * @param pixels the number of pixels for the height of the grid
   */
  public void setPreferredHeight(int pixels) {
    setPreferredSize(new Dimension(JCellPanel.cellWidth / 2, pixels));
  }

  /**
   * Draws this component as a row header, a series of vertically stacked cells with the row numbers
   * inside. Only the portion that is currently visible from the scrolling viewport is drawn.
   *
   * @param g the graphics object to draw with.
   */
  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    // area currently shown in the scrolling viewport (in pixels)
    Rectangle drawHere = g2d.getClipBounds();

    // fill in the background
    g2d.setColor(Color.pink);
    g2d.fillRect(drawHere.x, drawHere.y, drawHere.width, drawHere.height);

    g2d.setColor(Color.black);
    Shape oldClip = g2d.getClip();

    // first and last row that is shown or partially shown
    int firstIdx = drawHere.y / JCellPanel.cellHeight;
    int lastIdx = (int) Math
        .ceil((double) (drawHere.y + drawHere.height) / (double) (JCellPanel.cellHeight));

    // draw headers for only the rows that are shown
    int topLeftY = firstIdx * JCellPanel.cellHeight;
    for (int row = firstIdx; row <= lastIdx; row += 1) {
      g2d.drawRect(0, topLeftY, JCellPanel.cellWidth / 2, JCellPanel.cellHeight);
      g2d.clipRect(JCellPanel.clipPix, topLeftY + JCellPanel.clipPix,
          (JCellPanel.cellWidth / 2) - JCellPanel.clipPix * 2,
          JCellPanel.cellHeight - JCellPanel.clipPix * 2);
      g2d.drawString(Integer.toString(row + 1), JCellPanel.clipPix,
          topLeftY + JCellPanel.cellHeight * 3 / 4);
      g2d.setClip(oldClip);
      topLeftY += JCellPanel.cellHeight;
    }
  }

}
