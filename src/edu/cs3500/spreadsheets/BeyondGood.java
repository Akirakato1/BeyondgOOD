package edu.cs3500.spreadsheets;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * 
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    /*
     * TODO: For now, look in the args array to obtain a filename and a cell name, - read the file
     * and build a model from it, - evaluate all the cells, and - report any errors, or print the
     * evaluated value of the requested cell.
     */
    WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl();
    try {
      Readable file = new FileReader(args[1]);
      WorksheetReader.read(builder, file);
      String cell = args[3];
      ISpreadsheetModel ss = builder.createWorksheet();
      Value result = ss.evaluateCell(nameToCoord(cell));
      System.out.println(result.toString());

    } catch (FileNotFoundException e) {
      System.out.println("File not found");
    } catch (IllegalArgumentException e1) {
      // prints the error message when any exception is thrown. Detail of which are documented at
      // each method.
      System.out.println(e1.getMessage());
    } catch (ArrayIndexOutOfBoundsException e2) {
      System.out.println("malformatted input args");
    }
  }

  private static Coord nameToCoord(String name) {
    final Pattern cellRef = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");
    Matcher m = cellRef.matcher(name);
    int col, row;
    if (m.matches()) {
      col = Coord.colNameToIndex(m.group(1));
      row = Integer.parseInt(m.group(2));
    } else {
      throw new IllegalStateException("Expected cell ref");
    }
    return new Coord(col, row);
  }
}
