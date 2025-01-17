package edu.cs3500.spreadsheets.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModelViewOnly;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.view.VisualViewWithEdit;

/**
 * To represent a controller for the spreadsheet model. This controller communicates with the view
 * and the model so if the view has any events happening, the model will get updated as well.
 */
public class SpreadsheetController implements Features {
  private ISpreadsheetModel ss;
  private SpreadsheetView view;
  private int currentCol;
  private int currentRow;
  private boolean cellSelected;

  /**
   * Constructor to create a spreadsheet controller.
   *
   * @param ss spreadsheet model
   * @param view model view
   */
  public SpreadsheetController(ISpreadsheetModel ss, SpreadsheetView view) {
    this.ss = ss;
    this.view = view;
    this.cellSelected = false;
    view.addFeatures(this);
  }

  @Override
  public void submit(String newFormula) {
    if (currentCol > 0 && currentRow > 0) {
      if (newFormula.equals("")) {
        System.out.println("The new formula: " + newFormula);
        ss.deleteCell(new Coord(currentCol, currentRow));
        view.updateCellValue("", currentRow, currentCol);
        this.displayFormula(currentRow, currentCol);
      } else {
        ss.updateCell(new Coord(currentCol, currentRow), newFormula);
      }
    }
    for (Coord c : ss.getOccupiedCoords()) {
      view.updateCellValue(ss.evaluateCell(c).toString(), c.row, c.col);
    }
  }

  @Override
  public void cancel() {
    if (currentCol > 0 && currentRow > 0) {
      view.setFormulaDisplay(ss.getFormulaAtCoord(new Coord(currentCol, currentRow)).toString());
    }
  }

  @Override
  public void addRow() {
    ss.addRow();
    view.increaseRow();
  }

  @Override
  public void addCol() {
    ss.addCol();
    view.increaseCol();
    HashMap<String, Integer> colWidths = this.view.getColumnWidths();
    ss.setColHeaderWidths(colWidths);

  }

  @Override
  public void displayFormula(int row, int col) {
    this.currentCol = col;
    this.currentRow = row;
    view.setHighlight(row, col);
    if (!(currentCol > 0 && currentRow > 0)) {
      this.cellSelected = false;
      view.setFormulaDisplay("");
      return;
    }
    this.cellSelected = true;
    String formula = ss.getFormulaAtCoord(new Coord(col, row)).toString();
    if (formula.length() > 0 && formula.charAt(0) == '(') {
      formula = "=" + formula;
    }
    view.setFormulaDisplay(formula);
  }

  @Override
  public void save(String filename) {
    try {
      this.saveDatFile(filename);
      File outputFile = new File(".\\" + filename + ".txt");
      outputFile.delete();
      outputFile = new File(".\\" + filename + ".txt");
      PrintWriter writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
      SpreadsheetView tv = new TextualView(writeFile, new SpreadsheetModelViewOnly(ss));
      tv.render();
      writeFile.flush();
      writeFile.close();
    } catch (FileNotFoundException e) {
      System.out.println("input file not found");
    }
  }

  private void saveDatFile(String filename) {

    try {
      HashMap<String, Integer> colWidths = this.view.getColumnWidths();
      File outputFile = new File(".\\" + filename + ".dat");
      outputFile.delete();
      outputFile = new File(".\\" + filename + ".dat");
      PrintWriter writeFile;

      writeFile = new PrintWriter(new FileOutputStream(outputFile, true));


      for (String h : colWidths.keySet()) {
        writeFile.append(h + " " + colWidths.get(h) + "\n");
      }

      writeFile.flush();
      writeFile.close();
    } catch (FileNotFoundException e) {
      System.out.println("save dat file failed");
    }
  }

  @Override
  public void open(String filename) {
    try {
      WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl();
      Readable file = new FileReader(filename + ".txt");
      Readable datFile;
      try {
        datFile = new FileReader(filename + ".dat");
      } catch (FileNotFoundException e1) {
        File createNewDat = new File(".\\" + filename + ".dat");
        try {
          createNewDat.createNewFile();
        } catch (IOException e) {
          System.out.println("failed to create new dat file");
        }
        datFile = new FileReader(filename + ".dat");
      }
      WorksheetReader.readDat(builder, datFile);
      WorksheetReader.read(builder, file);
      this.view.close();
      this.ss = builder.createWorksheet();
      this.view = new VisualViewWithEdit(filename, new SpreadsheetModelViewOnly(ss), 1000, 500);
      view.addFeatures(this);
      this.view.render();
    } catch (FileNotFoundException e) {
      System.out.println("input file not found " + filename);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  @Override
  public void move(String dir) {
    switch (dir) {
      case "left":
        System.out.println("currentC: " + this.currentCol + " ssC: " + ss.getCol());
        if (this.currentCol > 0) {
          this.currentCol--;
        }
        break;
      case "right":
        System.out.println("currentC: " + this.currentCol + " ssC: " + ss.getCol());
        if (this.currentCol < ss.getCol()) {
          this.currentCol++;
        }
        break;
      case "up":
        System.out.println("currentR: " + this.currentRow + " ssR: " + ss.getRow());
        if (this.currentRow > 1) {
          this.currentRow--;
        }
        break;
      case "down":
        System.out.println("currentR: " + this.currentRow + " ssR: " + ss.getRow());
        if (this.currentRow < ss.getRow()) {
          this.currentRow++;
        }
        break;
      default:
        break;
    }
    this.displayFormula(currentRow, currentCol);
  }

}
