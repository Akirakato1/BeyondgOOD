package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetTable;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.VisualView;

/**
 * The main class for our program where users can access the spreadsheet.
 */
public class BeyondGood {
  /**
   * The main entry point for our program, where users can load up a file they made and access the
   * values inside the spreadsheet.
   *
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl();
    File outputFile = new File("C:\\EclipseWorkspace\\Beyond gOOD\\outputFile.txt");
    try {
      PrintWriter writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
      Readable file = new FileReader(args[1]);
      WorksheetReader.read(builder, file);
      ISpreadsheetModel ss = builder.createWorksheet();
      
      SpreadsheetView vv=new VisualView(args[1],ss,1000,500);
      SpreadsheetView tv = new TextualView(writeFile, ss);
      vv.render();
      tv.render();
      writeFile.flush();
      writeFile.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    /*
     * WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl(); try { if
     * (!(args[0].equals("-in") && args[2].equals("-eval"))) {
     * System.out.println("first arg not -in or 3rd arg not -eval"); } else { Readable file = new
     * FileReader(args[1]); WorksheetReader.read(builder, file); String cell = args[3];
     * ISpreadsheetModel ss = builder.createWorksheet(); List<String> errs = ss.errorMessages(); if
     * (errs.size() == 0) { try { Value result = ss.evaluateCell(nameToCoord(cell));
     * System.out.print(result.toString()); } catch (IllegalArgumentException e3) {
     * System.out.println("Cell: " + nameToCoord(cell) + e3.getMessage()); } } else { for (String s
     * : errs) { System.out.println(s); } } } } catch (FileNotFoundException e) {
     * System.out.println("File not found"); } catch (ArrayIndexOutOfBoundsException e2) {
     * System.out.println("malformatted input args"); }
     * 
     */
  }

  /**
   * Given a string coordinate name representing its position, turns it into an actual coordinate.
   *
   * @param name string name of coordinate
   * @return actual coordinate representing that position
   */
  private static Coord nameToCoord(String name) {
    final Pattern cellRef = Pattern.compile("([A-Za-z]+)([1-9][0-9]*)");
    Matcher m = cellRef.matcher(name);
    int col;
    int row;
    if (m.matches()) {
      col = Coord.colNameToIndex(m.group(1));
      row = Integer.parseInt(m.group(2));
    } else {
      throw new IllegalStateException("Expected cell ref");
    }
    return new Coord(col, row);
  }
}
