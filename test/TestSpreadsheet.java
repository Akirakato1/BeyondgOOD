import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.StringWriter;

import edu.cs3500.spreadsheets.model.Blank;
import edu.cs3500.spreadsheets.model.Bool;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Num;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.SpreadsheetModelViewOnly;
import edu.cs3500.spreadsheets.model.Str;
import edu.cs3500.spreadsheets.model.Value;
import edu.cs3500.spreadsheets.model.WorksheetBuilderImpl;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.WorksheetReader.WorksheetBuilder;
import edu.cs3500.spreadsheets.view.SpreadsheetView;
import edu.cs3500.spreadsheets.view.TextualView;
import edu.cs3500.spreadsheets.model.Error;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

import static org.junit.Assert.assertEquals;


/**
 * Class to test spreadsheet.
 */
public class TestSpreadsheet {

  // test to check that the textual view renders the correct spreadsheet given a file:
  // read in a file, render it through this view, and then read this
  // view’s output back in as a second model, and check that the two models are equivalent.
  @Test
  public void testTextualView() {
    try {
      String filename1 = "square_of_distance.txt";
      String filename2 = "testFile.txt";
      WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
      Readable file1 = new FileReader(filename1);
      WorksheetReader.read(builder1, file1);
      ISpreadsheetModel ss1 = builder1.createWorksheet();

      File outputFile = new File("C:\\Users\\BeiBei\\Desktop\\BeyondgOOD\\" + filename2);
      PrintWriter writeFile;
      writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
      SpreadsheetView tv = new TextualView(writeFile, new SpreadsheetModelViewOnly(ss1));
      tv.render();
      writeFile.flush();
      writeFile.close();

      WorksheetBuilder<ISpreadsheetModel> builder2 = new WorksheetBuilderImpl();
      Readable file2 = new FileReader(filename2);
      WorksheetReader.read(builder2, file2);
      ISpreadsheetModel ss2 = builder2.createWorksheet();

      assertEquals(true, ss1.equals(ss2));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  // test to check that the textual view renders the correct blank spreadsheet given a file.
  // read in a file, render it through this view, and then read this
  // view’s output back in as a second model, and check that the two models are equivalent.
  @Test
  public void testTextualView2Blank() {
    try {
      String filename1 = "blank.txt";
      String filename2 = "testBlank.txt";
      WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
      Readable file1 = new FileReader(filename1);
      WorksheetReader.read(builder1, file1);
      ISpreadsheetModel ss1 = builder1.createWorksheet();

      File outputFile = new File("C:\\Users\\BeiBei\\Desktop\\BeyondgOOD\\" + filename2);
      PrintWriter writeFile;
      writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
      SpreadsheetView tv = new TextualView(writeFile,  new SpreadsheetModelViewOnly(ss1));
      tv.render();
      writeFile.flush();
      writeFile.close();

      WorksheetBuilder<ISpreadsheetModel> builder2 = new WorksheetBuilderImpl();
      Readable file2 = new FileReader(filename2);
      WorksheetReader.read(builder2, file2);
      ISpreadsheetModel ss2 = builder2.createWorksheet();

      assertEquals(true, ss1.equals(ss2));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  // test to check that the textual view renders the correct spreadsheet (even if contains
  // bad cells).
  // read in a file, render it through this view, and then read this
  // view’s output back in as a second model, and check that the two models are equivalent.
  @Test
  public void testTextualView3ErrorsIncluded() {
    try {
      String filename1 = "cycleDetected.txt";
      String filename2 = "testCycle.txt";
      WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
      Readable file1 = new FileReader(filename1);
      WorksheetReader.read(builder1, file1);
      ISpreadsheetModel ss1 = builder1.createWorksheet();

      File outputFile = new File("C:\\Users\\BeiBei\\Desktop\\BeyondgOOD\\" + filename2);
      PrintWriter writeFile;
      writeFile = new PrintWriter(new FileOutputStream(outputFile, true));
      SpreadsheetView tv = new TextualView(writeFile,  new SpreadsheetModelViewOnly(ss1));
      tv.render();
      writeFile.flush();
      writeFile.close();

      WorksheetBuilder<ISpreadsheetModel> builder2 = new WorksheetBuilderImpl();
      Readable file2 = new FileReader(filename2);
      WorksheetReader.read(builder2, file2);
      ISpreadsheetModel ss2 = builder2.createWorksheet();

      assertEquals(true, ss1.equals(ss2));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }


  // checks if textual view output is equal to expected output (blank).
  @Test
  public void testRenderSpreadsheetBlank2() {

    WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
    ISpreadsheetModel ss1 = builder1.createWorksheet();

    StringWriter out = new StringWriter();
    PrintWriter writeFile;
    writeFile = new PrintWriter(out);
    SpreadsheetView tv = new TextualView(writeFile,  new SpreadsheetModelViewOnly(ss1));

    tv.render();
    writeFile.flush();
    writeFile.close();
    assertEquals("", out.toString());

  }


  // checks if textual view output is equal to expected output
  // (contains some cells that are updated)
  @Test
  public void testRenderSpreadsheet3() {

    WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
    ISpreadsheetModel ss1 = builder1.createWorksheet();
    ss1.updateCell(new Coord(1, 1), "=true");
    ss1.updateCell(new Coord(1, 1), "=false");
    ss1.updateCell(new Coord(1, 2), "1");
    ss1.updateCell(new Coord(1, 2), "2");
    ss1.updateCell(new Coord(1, 3), "=A1");
    ss1.updateCell(new Coord(1, 3), "=B2");

    StringWriter out = new StringWriter();
    PrintWriter writeFile;
    writeFile = new PrintWriter(out);
    SpreadsheetView tv = new TextualView(writeFile,  new SpreadsheetModelViewOnly(ss1));

    tv.render();
    writeFile.flush();
    writeFile.close();
    assertEquals("A2 2.000000\n" +
            "A1 false\n" +
            "A3 B2\n", out.toString());
  }


  // checks if textual view output is equal to expected output
  // (contains some cells that are updated)
  @Test
  public void testRenderSpreadsheet4() {

    WorksheetBuilder<ISpreadsheetModel> builder1 = new WorksheetBuilderImpl();
    ISpreadsheetModel ss1 = builder1.createWorksheet();
    ss1.updateCell(new Coord(1, 1), "=true");
    ss1.updateCell(new Coord(1, 2), "2");
    ss1.updateCell(new Coord(1, 3), "=A1");
    ss1.updateCell(new Coord(1, 4), "=A1:A3");

    StringWriter out = new StringWriter();
    PrintWriter writeFile;
    writeFile = new PrintWriter(out);
    SpreadsheetView tv = new TextualView(writeFile,  new SpreadsheetModelViewOnly(ss1));

    tv.render();
    writeFile.flush();
    writeFile.close();
    assertEquals("A2 2.000000\n" +
            "A1 true\n" +
            "A4 A1:A3\n" +
            "A3 A1\n", out.toString());
  }


  // test that boolean is inputted correctly into cell.
  @Test
  public void testBooleanCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=true");
    Value value = model.getFormulaAtCoord(new Coord(1, 1)).evaluate();
    assertEquals(new Bool(true), value);
  }

  // test that number is inputted correctly into cell.
  @Test
  public void testNumCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=5");
    Value value = model.getFormulaAtCoord(new Coord(1, 1)).evaluate();
    assertEquals(new Num(5), value);
  }

  // test that formula works correctly when inputted into a cell.
  @Test
  public void testFormatCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=5");
    model.updateCell(new Coord(1, 2), "=A1");
    Value value = model.getFormulaAtCoord(new Coord(1, 2)).evaluate();
    assertEquals(new Num(5), value);
  }

  // test that string is inputted correctly into cell.
  @Test
  public void testStringCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "\"HELLO\"");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HELLO"), value.evaluate());
  }

  // test for sum on a simple good input (same inputs).
  @Test
  public void testSumSame() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "5");
    model.updateCell(new Coord(1, 2), "=(SUM A1 A1)");
    Value value = model.evaluateCell(new Coord(1, 2));
    assertEquals(new Num(10), value.evaluate());
  }

  // test for product on a simple good input (same inputs).
  @Test
  public void testProductSame() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "5");
    model.updateCell(new Coord(1, 2), "=(PRODUCT A1 A1)");
    Value value = model.evaluateCell(new Coord(1, 2));
    assertEquals(new Num(25), value.evaluate());
  }

  // test for < on a simple good input (same inputs).
  @Test
  public void testLessThanSame() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "5");
    model.updateCell(new Coord(1, 2), "=(< A1 A1)");
    Value value = model.evaluateCell(new Coord(1, 2));
    assertEquals(new Bool(false), value.evaluate());
  }

  // test for sum on a bad input (mismatched inputs).
  @Test
  public void testSumMismatch() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "5");
    model.updateCell(new Coord(1, 2), "=(SUM A1 true)");
    assertEquals(0, model.errorMessages().size());
  }

  // test for product on a simple good input (same inputs).
  @Test
  public void testProductMismatch() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "\"HELLO\"");
    model.updateCell(new Coord(1, 2), "=(PRODUCT true A1)");
    assertEquals(0, model.errorMessages().size());
  }

  // test for concat on multiple good input (same inputs).
  @Test
  public void testConcatSame() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "\"HI\"");
    model.updateCell(new Coord(1, 2), "=(CONCAT A1 A1)");
    Value value = model.evaluateCell(new Coord(1, 2));
    assertEquals(new Str("HIHI"), value.evaluate());
  }

  // test to check that rendering values as string works.
  @Test
  public void renderString() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "\"Jack says \\\\ \"");
    model.updateCell(new Coord(1, 2), "=\"b says bye\"");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("Jack says \\ "), value);
    Value value2 = model.evaluateCell(new Coord(1, 2));
    assertEquals(new Str("b says bye"), value2.evaluate());
  }


  // test for creating an empty spreadsheet.
  @Test
  public void createEmptySpreadsheet() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.getFormulaAtCoord(new Coord(1, 1));
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Blank(), value);
  }


  // test that created cells are there.
  @Test
  public void getFormulaAtCoord() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(SUM A1 A2)");
    assertEquals(new Num(36), model.getFormulaAtCoord(new Coord(1, 1)).evaluate());
    assertEquals(new Num(36), model.getFormulaAtCoord(new Coord(1, 2)).evaluate());
    assertEquals(new Num(72), model.getFormulaAtCoord(new Coord(1, 3)).evaluate());
  }


  // test for sum on a simple good input.
  @Test
  public void testSum() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(10), value.evaluate());
  }

  // test for sum on multiple good input.
  @Test
  public void testSum2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(10), value.evaluate());
  }

  // test for sum on multiple good input and functions.
  @Test
  public void testSum3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(36), value.evaluate());
  }

  // test sum on references.
  @Test
  public void testSum4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(SUM A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(72), value.evaluate());
  }

  // test sum on rectangle of references.
  @Test
  public void testSum5() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(82), value.evaluate());
  }

  // test for no numeric values.
  @Test
  public void testSumNoNumericValues() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(0), value.evaluate());

  }

  // test sum for many cells without any doubles.
  @Test
  public void testSumMany() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(0), value.evaluate());
  }

  // test sum for many cells with a cycle.
  @Test
  public void testSumManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B10)");
    assertEquals(1, model.errorMessages().size());
  }

  // test for sum with an arbitrary amount of arguments.
  @Test
  public void testSumSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(6), value.evaluate());
  }

  // test sum when it contains blank cell.
  @Test
  public void testBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Blank(), value.evaluate());
  }

  // test sum when cell refers to itself.
  @Test
  public void testUpdateCellCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=A1");
    assertEquals(1, model.errorMessages().size());
  }

  // test when cell performs a function on itself (SUM).
  @Test
  public void testUpdateCellCycle2() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(SUM A1 1)");
    assertEquals(1, model.errorMessages().size());
  }


  // test when cell contains a cycle (refers to itself indirectly).
  @Test
  public void testUpdateCellCycle3() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=B1");
    model.updateCell(new Coord(2, 1), "=A1");
    assertEquals(2, model.errorMessages().size());
  }

  // test when cell performs a function on itself (<).
  @Test
  public void testUpdateCellCycle4() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< A1 1)");
    assertEquals(1, model.errorMessages().size());
  }

  // test when cell performs a function on itself (CONCAT).
  @Test
  public void testUpdateCellCycle5() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(CONCAT A1 1)");
    assertEquals(1, model.errorMessages().size());
  }

  // test when cell performs a function on itself (PRODUCT).
  @Test
  public void testUpdateCellCycle6() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(PRODUCT A1 1)");
    assertEquals(1, model.errorMessages().size());
  }


  // test model when cell is updated with a cycle.
  @Test
  public void testSumCycleAfterUpdate() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=A1");
    assertEquals(1, model.errorMessages().size());
  }


  // test for < on a simple good input.
  @Test
  public void testLessThan() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Bool(false), value.evaluate());
  }

  // test for < on multiple good input.
  @Test
  public void testLessThan2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Bool(true), value.evaluate());
  }

  // test for < on multiple good input and functions.
  @Test
  public void testLessThan3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) (SUM 1 3))");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Bool(true), value.evaluate());
  }


  // test < on references.
  @Test(expected = IllegalArgumentException.class)
  public void testLessThan4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 1)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(< A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Bool(false), value.evaluate());
  }

  // test for < with arbitrary inputs.
  @Test
  public void testBadLessThan2() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< 1 2 3)");
    assertEquals(1, model.errorMessages().size());
  }

  // test < when it contains blank cell.
  @Test
  public void testBadLessThan3() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "B1");
    model.updateCell(new Coord(1, 2), "=(< A1 B5)");

    assertEquals(Error.VALUE, model.evaluateCell(new Coord(1, 2)));
  }

  // test for concat on a simple good input.
  @Test
  public void testConcat() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"HELLO\" \"WORLD\")");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HELLOWORLD"), value.evaluate());
  }

  // test for concat on multiple good input.
  @Test
  public void testConcat2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"HI\" \"HI\" \"HI\")");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HIHIHI"), value.evaluate());
  }

  // test for concat on multiple good input and functions.
  @Test
  public void testConcat3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"HI\" (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HI3.03.04.0"), value.evaluate());
  }

  // test concat on references.
  @Test
  public void testConcat4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=\"HELLO\"");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Str("HELLOHELLO"), value.evaluate());
  }

  // test concat on rectangle of references.
  @Test
  public void testConcat5() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=\"HELLO\"");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Str("HELLO10.0HELLO"), value.evaluate());
  }

  // test concat for no numeric values.
  @Test
  public void testConcat6() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 6), "=(SUM A1:B5)");
    Value value = model.evaluateCell(new Coord(1, 6));
    assertEquals("0.000000", value.evaluate().toString());

  }

  // test concat for many cells with a cycle.
  @Test
  public void testConcatManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(CONCAT (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1:B10)");
    assertEquals(1, model.errorMessages().size());

  }

  // test for concat with arbitrary inputs.
  @Test
  public void testConcatSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"hello\" \"product\" (PRODUCT 1 2 3))");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("helloproduct6.0"), value.evaluate());
  }

  // test concat that contains blank cell.
  @Test
  public void testConcatBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "B1");
    model.updateCell(new Coord(1, 2), "=(CONCAT B1 B1)");
    Value value = model.evaluateCell(new Coord(1, 2)).evaluate();
    assertEquals(new Str(""), value);
  }

  // test for concat on a given string.
  @Test
  public void testConcat7() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"Hello\" \"World\")");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HelloWorld"), value);
  }


  // test for product on a simple good input.
  @Test
  public void testProduct() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(25), value.evaluate());
  }

  // test for product on multiple good input.
  @Test
  public void testProduct2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (PRODUCT 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(24), value.evaluate());
  }

  // test for product on multiple good input and functions.
  @Test
  public void testProduct3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(36), value.evaluate());
  }

  // test product on references.
  @Test
  public void testProduct4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(PRODUCT A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(1296), value.evaluate());
  }

  // test product on rectangle of references.
  @Test
  public void testProduct5() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(1, 3), "=(PRODUCT A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(new Num(12960), value.evaluate());
  }

  // test for no numeric values.
  @Test
  public void testProductNoNumericValues() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 6), "=(SUM A1:B5)");
    Value value = model.evaluateCell(new Coord(1, 6));
    assertEquals(new Num(0), value.evaluate());

  }

  // test product for many cells with a cycle.
  @Test
  public void testProductManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(PRODUCT A1:B10)");
    assertEquals(1, model.errorMessages().size());
  }

  // test for product with arbitrary inputs.
  @Test
  public void testProductSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Num(6), value.evaluate());
  }

  // test that contains blank cell.
  @Test
  public void testProductBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "B1");
    model.updateCell(new Coord(1, 2), "=(PRODUCT B1 B1)");
    Value value = model.evaluateCell(new Coord(1, 2)).evaluate();
    assertEquals(new Num(0), value);
  }


}