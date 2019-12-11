import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.controller.SpreadsheetController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModelViewOnly;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.VisualViewWithEdit;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

import static org.junit.Assert.assertEquals;

/**
 * Test for controller class.
 */
public class TestControllerView {
  ISpreadsheetModel ss = new SpreadsheetModel();
  SpreadsheetView vv = new VisualViewWithEdit("test", new SpreadsheetModelViewOnly(ss), 1000, 500);
  Features controller = new SpreadsheetController(ss, vv);
  WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl();

  /**
   * Tests if submit works.
   */
  @Test
  public void testSubmit() {

    // test update
    controller.displayFormula(1, 1);
    controller.submit("1.5");
    assertEquals("1.500000", ss.getFormulaAtCoord(new Coord(1, 1)).toString());
    // test delete
    controller.submit("");
    assertEquals("", ss.getFormulaAtCoord(new Coord(1, 1)).toString());
  }

  /**
   * Tests if addRow actually adds a row in the model.
   */
  @Test
  public void testAddRow() {
    assertEquals(20, ss.getRow());
    controller.addRow();
    assertEquals(21, ss.getRow());
  }

  /**
   * Tests if addRow actually adds a column in the model.
   */
  @Test
  public void testAddCol() {
    assertEquals(20, ss.getCol());
    controller.addCol();
    assertEquals(21, ss.getCol());
  }

  /**
   * Tests if move actually moves the selected cell and can modify the correct new position in the
   * model.
   */
  @Test
  public void testMove() {
    controller.displayFormula(1, 1);
    controller.submit("1.1");
    assertEquals("1.100000", ss.getFormulaAtCoord(new Coord(1, 1)).toString());
    controller.move("right");
    controller.submit("1.2");
    assertEquals("1.200000", ss.getFormulaAtCoord(new Coord(2, 1)).toString());
    controller.move("down");
    controller.submit("2.2");
    assertEquals("2.200000", ss.getFormulaAtCoord(new Coord(2, 2)).toString());
    controller.move("left");
    controller.submit("2.1");
    assertEquals("2.100000", ss.getFormulaAtCoord(new Coord(1, 2)).toString());
    controller.move("up");
    assertEquals("1.100000", ss.getFormulaAtCoord(new Coord(1, 1)).toString());
  }

  /**
   * Tests if save makes a new file with correct output.
   */
  @Test
  public void testSave() {
    ISpreadsheetModel ss = new SpreadsheetModel();
    WorksheetBuilder<ISpreadsheetModel> builder = new WorksheetBuilderImpl();
    Readable file;
    try {
      controller.displayFormula(3, 2);
      controller.submit("31415");
      controller.save("testSave");

      file = new FileReader("testSave.txt");
      WorksheetReader.read(builder, file);
      ss = builder.createWorksheet();

      assertEquals("31415.000000", ss.getFormulaAtCoord(new Coord(2, 3)).toString());
    } catch (FileNotFoundException e) {
      System.out.println("test file not found");
    }
  }

  // test that column widths persist after save and that the output of the .dat file is
  // what we expected.
  @Test
  public void testColWidthDat1() {
    HashMap<String, Integer> map = new HashMap<>();
    map.put("A", 75);
    map.put("B", 122);

    this.ss.setColHeaderWidths(map);
    SpreadsheetView vv2 =
            new VisualViewWithEdit("test2", new SpreadsheetModelViewOnly(ss)
                    , 1000, 500);
    Features controller2 = new SpreadsheetController(ss, vv2);
    controller2.save("testColWidthDatOne");

    try {
      Readable datFile = new FileReader("testColWidthDatOne" + ".dat");

      WorksheetReader.readDat(builder, datFile);
      ISpreadsheetModel otherss = builder.createWorksheet();

      assertEquals((int) map.get("A"), otherss.getColWidth("A"));
      assertEquals((int) map.get("B"), otherss.getColWidth("B"));
      assertEquals((int) SpreadsheetModel.DEFAULT_COL_WIDTH, otherss.getColWidth("C"));

      Scanner scanDat = new Scanner(new File("testColWidthDatOne.dat"));
      String text = scanDat.useDelimiter("\\A").next();
      scanDat.close();

      String ssColWidthText = "";
      for (int i = 1; i < ss.getCol(); i++) {
        if (ss.getColWidth(Coord.colIndexToName(i)) != SpreadsheetModel.DEFAULT_COL_WIDTH) {
          ssColWidthText = ssColWidthText + Coord.colIndexToName(i) + " "
                  + this.ss.getColWidth(Coord.colIndexToName(i)) + "\n";
        }
      }

      assertEquals(text, ssColWidthText);

    } catch (FileNotFoundException e1) {
      System.out.println("could not find dat file");
    }
  }

}
