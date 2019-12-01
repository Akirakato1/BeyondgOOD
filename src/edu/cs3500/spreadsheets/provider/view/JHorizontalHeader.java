package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.model.Coord;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Shape;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * The horizontal header on a spreadsheet that labels the columns of each cell using letters to
 * index their coordinate.
 */
class JHorizontalHeader extends JComponent {

  /**
   * Sets the preferred width, which should correspond to the width of the spreadsheet grid.
   *
   * @param pixels the number of pixels for the width of the grid
   */
  void setPreferredWidth(int pixels) {
    setPreferredSize(new Dimension(pixels, JCellPanel.cellHeight));
  }

  /**
   * Draws this component as a column header, a series of horizontally aligned  cells with the
   * column numbers inside. Only the portion that is currently visible from the scrolling viewport
   * is drawn.
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

    // first and last col that is shown or partially shown
    int firstIdx = drawHere.x / JCellPanel.cellWidth;
    int lastIdx = (int) Math
        .ceil((double) (drawHere.x + drawHere.width) / (double) (JCellPanel.cellWidth));

    int topLeftX = firstIdx * JCellPanel.cellWidth;
    for (int col = firstIdx; col <= lastIdx; col += 1) {
      g2d.drawRect(topLeftX, 0, JCellPanel.cellWidth, JCellPanel.cellHeight);
      g2d.clipRect(topLeftX + JCellPanel.clipPix, JCellPanel.clipPix,
          JCellPanel.cellWidth - JCellPanel.clipPix * 2,
          JCellPanel.cellHeight - JCellPanel.clipPix * 2);
      g2d.drawString(Coord.colIndexToName(col + 1), topLeftX + JCellPanel.clipPix,
          JCellPanel.cellHeight * 3 / 4);
      g2d.setClip(oldClip);
      topLeftX += JCellPanel.cellWidth;
    }
  }

}
