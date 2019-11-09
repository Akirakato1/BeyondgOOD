package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

public class TextualView implements SpreadsheetView {

  Appendable outputFile;
  ISpreadsheetModel ss;

  public TextualView(Appendable app, ISpreadsheetModel ss) {
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
          output ="=" + output;
        }
        outputFile.append(c.toString() + " " + output + "\n");
        System.out.println(c.toString() + " " + output + "\n");
      } catch (IOException e) {
        throw new IllegalStateException("Something went wrong with append in textualview render");
      }
    }
  }


}
