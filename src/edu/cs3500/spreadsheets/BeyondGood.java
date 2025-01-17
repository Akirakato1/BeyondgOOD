package edu.cs3500.spreadsheets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModelViewOnly;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.controller.ProviderFeaturesAdapter;
import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;
import edu.cs3500.spreadsheets.model.ProviderModelAdapter;
import edu.cs3500.spreadsheets.provider.view.OurViewAdapter;
import edu.cs3500.spreadsheets.provider.view.ViewFactory;
import edu.cs3500.spreadsheets.provider.view.ViewFactory.GUIViewType;
import edu.cs3500.spreadsheets.provider.view.WorksheetView;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.VisualView;
import edu.cs3500.spreadsheets.view.VisualViewWithEdit;

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
    Features controller;
    File outputFile;
    ISpreadsheetModel ss;
    SpreadsheetView vv;
    SpreadsheetView tv;
    Readable file;
    Readable datFile;
    PrintWriter writeFile;

    try {
      int counter = 0;
      boolean loopCondition = args.length > 0;
      while (loopCondition) {
        switch (counter) {
          case 0:
            if (args[counter].equals("-gui") && args.length == 1) {
              ss = builder.createWorksheet();
              vv = new VisualView("New Blank", new SpreadsheetModelViewOnly(ss), 1000, 500);
              controller = new SpreadsheetController(ss, vv);
              vv.render();
              loopCondition = false;
              break;
            } else if (args[counter].equals("-provider") && args.length == 1) {
              ss = builder.createWorksheet();
              WorksheetView wv =
                      ViewFactory.makeGraphical(new ProviderModelAdapter(ss), GUIViewType.STATIC);
              loopCondition = false;
              break;
            } else if (args[counter].equals("-edit") && args.length == 1) {
              ss = builder.createWorksheet();
              vv = new VisualViewWithEdit("New Blank", new SpreadsheetModelViewOnly(ss), 1000, 500);
              controller = new SpreadsheetController(ss, vv);
              vv.render();
              loopCondition = false;
              break;
            } else if (!args[counter].equals("-in")) {
              System.out.println("Please make sure inputs are not malformed! in");
              loopCondition = false;
              break;
            }
            break;
          case 1:
            file = new FileReader(args[counter] + ".txt");
            try {
              datFile = new FileReader(args[counter] + ".dat");
            } catch (FileNotFoundException e1) {
              File createNewDat = new File(".\\" + args[counter] + ".dat");
              try {
                createNewDat.createNewFile();
              } catch (IOException e) {
                System.out.println("failed to create new dat file");
              }
              datFile = new FileReader(args[counter] + ".dat");
            }
            WorksheetReader.readDat(builder, datFile);
            WorksheetReader.read(builder, file);
            ss = builder.createWorksheet();
            for (String s : ss.errorMessages()) {
              System.out.println(s);
            }
            break;
          case 2:
            ss = builder.createWorksheet();
            if (args[counter].equals("-gui") && args.length == 3) {
              vv = new VisualView(args[1], new SpreadsheetModelViewOnly(ss), 1000, 500);
              controller = new SpreadsheetController(ss, vv);
              vv.render();
              loopCondition = false;
              break;
            } else if (args[counter].equals("-edit") && args.length == 3) {
              vv = new VisualViewWithEdit(args[1], new SpreadsheetModelViewOnly(ss), 1000, 500);
              controller = new SpreadsheetController(ss, vv);
              vv.render();
              loopCondition = false;
              break;
            } else if (args[counter].equals("-provider") && args.length == 3) {
              vv = new OurViewAdapter(
                      ViewFactory.makeGraphical(new ProviderModelAdapter(ss), GUIViewType.EDIT));
              controller = new SpreadsheetController(ss, vv);
              SpreadsheetFeatures pc = new ProviderFeaturesAdapter(controller);

              loopCondition = false;
              break;
            } else if (args[counter].equals("-eval") && args.length == 4) {
              Value result = ss.evaluateCell(nameToCoord(args[3]));
              System.out.println("Evaluated " + args[3] + ": " + result.toString());
              break;
            } else if (args[counter].equals("-save") && args.length == 4) {
              outputFile = new File(".\\" + args[3]);
              outputFile.delete();
              outputFile = new File(".\\" + args[3]);
              writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
              tv = new TextualView(writeFile, new SpreadsheetModelViewOnly(ss));
              tv.render();
              writeFile.flush();
              writeFile.close();
              break;
            } else {
              System.out.println("Please make sure inputs are not malformed! f");
              break;
            }
          default:
            System.out.println("Please make sure inputs are not malformed! d");
            break;
        }
        counter++;
        if (counter == 3 || counter > args.length - 1) {
          loopCondition = false;
        }
      }
    } catch (FileNotFoundException e) {
      System.out.println("input file not found");
    }
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
