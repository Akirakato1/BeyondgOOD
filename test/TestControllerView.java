import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.swing.JButton;

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
  JButton submit = new JButton("submit");

  /**
   * Tests if submit button works.
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
      controller.save("testSave.txt");

      file = new FileReader("testSave.txt");
      WorksheetReader.read(builder, file);
      ss = builder.createWorksheet();

      assertEquals("31415.000000", ss.getFormulaAtCoord(new Coord(2, 3)).toString());
    } catch (FileNotFoundException e) {
      System.out.println("test file not found");
    }
  }
}
