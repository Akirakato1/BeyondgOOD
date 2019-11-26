package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

/**
 * To represent a textual view. This renders the spreadsheet where it goes through the spreadsheet
 * and outputs the formulas (not values) of each cell. The output is not ordered, but the formulas
 * are rendered correctly. Implementation takes a model and an Appendable and renders the model into
 * the Appendable in the same format as we read files in HW5.
 */
public class TextualView implements SpreadsheetView {

  private Appendable outputFile;
  private ISpreadsheetViewOnly ss;

  /**
   * Constructor to create a textual view.
   */
  public TextualView(Appendable app, ISpreadsheetViewOnly ss) {
    outputFile = app;
    this.ss = ss;
  }

  @Override
  public void render() {
    List<Coord> coords = ss.getOccupiedCoords();
    for (Coord c : coords) {
      try {
        String output = ss.getFormulaAtCoord(c).toString();
        if (output.charAt(0) == '(') {
          output = "=" + output;
        }
        outputFile.append(c.toString() + " " + output + "\n");
        System.out.println(c.toString() + " " + output + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Something went wrong with append in Textual View render");
      }
    }
  }

  @Override
  public void addFeatures(Features f) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setFormulaDisplay(String formula) {
    // TODO Auto-generated method stub

  }

  @Override
  public void updateCellValue(String value, int row, int col) {
    // TODO Auto-generated method stub

  }

  @Override
  public void increaseRow() {
    // TODO Auto-generated method stub

  }

  @Override
  public void increaseCol() {
    // TODO Auto-generated method stub

  }

  @Override
  public void close() {
    // TODO Auto-generated method stub

  }


}
