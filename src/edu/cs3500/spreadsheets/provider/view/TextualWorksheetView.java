package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import java.io.IOException;


import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;

/**
 * Represents the textual representation of the model which displays the typed values of all cells
 * in the model that are non-empty.
 */
public class TextualWorksheetView implements WorksheetView {

  private final ReadOnlyWorksheetModel model;
  private final Appendable appendable;

  /**
   * Standard constructor for the textual view.
   *
   * @param model      the model that the textual view is representing
   * @param appendable the appendable used to display the view
   */
  public TextualWorksheetView(ReadOnlyWorksheetModel model, Appendable appendable) {
    this.model = model;
    this.appendable = appendable;
  }

  /**
   * Renders the worksheet to this view's Appendable. The worksheet is rendered as text, in a format
   * that can be reloaded to create a new worksheet.
   */
  @Override
  public void refresh() {
    try {
      for (int col = 1; col < model.getContentCols() + 1; col += 1) {
        for (int row = 1; row < model.getContentRows() + 1; row += 1) {
          Coord loc = new Coord(col, row);
          String contents = model.getTypedContentsAt(loc);
          if (!contents.isEmpty()) {
            appendable.append(String.format("%s %s\n", loc.toString(), contents));
          }
        }
      }
    } catch (IOException e) {
      throw new IllegalStateException("This is a bad appendable");
    }
  }

  /**
   * This method does nothing in the textual view, since there are no events to listen to.
   *
   * @param sf the SpreadsheetFeatures to add to the view
   */
  @Override
  public void addSpreadsheetFeatures(SpreadsheetFeatures sf) {
    // Does not have any features because its a text view
  }

  /**
   * This method does nothing in the textual view, since cells cannot be highlighted or edited.
   *
   * @param loc cell location to edit at
   */
  @Override
  public void highlightAt(Coord loc) {
    // Does nothing
  }

  /**
   * This method changes nothing, since the textual view is always in its default state.
   */
  @Override
  public void turnOffHighlight() {
    // Does nothing, highlight is already off
  }

  /**
   * Writes a message as a comment (a string starting with #) in the appendable. (Does not require
   * another call to refresh() for the message to be written.)
   *
   * @param message the message to display
   */
  @Override
  public void displayMsg(String message) {
    try {
      this.appendable.append("# " + message);
    } catch (IOException e) {
      throw new IllegalStateException("This is a bad appendable");
    }

  }

  /**
   * This method should not be called on textual views, because this view can only ever write for
   * one model.
   *
   * @param model the new model that will be shown on the view
   * @throws UnsupportedOperationException for all calls to this method
   */
  @Override
  public void changeModel(ReadOnlyWorksheetModel model) {
    throw new UnsupportedOperationException("This view can only ever be coupled with a "
        + "single model");
  }

}
