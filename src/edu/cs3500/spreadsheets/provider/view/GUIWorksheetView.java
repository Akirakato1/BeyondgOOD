package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;
import edu.cs3500.spreadsheets.model.Coord;

import javax.swing.JFrame;
import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;
import javax.swing.JOptionPane;

/**
 * A graphical user interface for a spreadsheet that displays cells as a grid.
 */
public class GUIWorksheetView extends JFrame implements WorksheetView {

  private final ScrollingCellPanel cellPanel;


  /**
   * Makes a GUIWorksheetView that corresponds to the given model, sets up all the components and
   * makes the view visible.
   *
   * @param model the worksheet holding the data for this view
   */
  public GUIWorksheetView(ReadOnlyWorksheetModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.cellPanel = new ScrollingCellPanel(model);
    // set up the JFrame
    this.add(cellPanel);
    this.pack();
    this.setVisible(true);
  }


  /**
   * Draws all the cells, with their evaluated values, in a scrollable grid. If any cells are
   * editing, they will display a highlighted border and the formula text (or whatever the user is
   * typing). At minimum, 50 rows and 26 columns are displayed - more may be visible if the user has
   * added rows and columns or the spreadsheet is larger.
   */
  @Override
  public void refresh() {
    this.repaint();
  }

  /**
   * This method does nothing, because this class does not support interactivity on its
   * own.
   *
   * @param sf the SpreadsheetFeatures to add to the view
   */
  @Override
  public void addSpreadsheetFeatures(SpreadsheetFeatures sf) {
    // this view has no events to raise, so it does not need to track features
  }

  @Override
  public void highlightAt(Coord loc) {
    this.cellPanel.highlightAt(loc);
  }

  @Override
  public void turnOffHighlight() {
    this.cellPanel.turnOffHighlight();
  }

  /**
   * Displays a message as a pop up window.
   *
   * @param message the message to display
   */
  @Override
  public void displayMsg(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void changeModel(ReadOnlyWorksheetModel model) {
    this.cellPanel.changeModel(model);
  }

}
