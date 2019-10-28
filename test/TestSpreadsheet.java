import org.junit.Test;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.Value;

import static org.junit.Assert.assertEquals;

public class TestSpreadsheet {

  // test for sum on a simple good input.
  @Test
  public void testSum() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(10.0, value.getDouble(), 0.0);
  }

  // test for sum on multiple good input.
  @Test
  public void testSum2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(10.0, value.getDouble(), 0.0);
  }

  // test for sum on multiple good input and functions.
  @Test
  public void testSum3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(36.0, value.getDouble(), 0.0);
  }

  // test sum on references.
  @Test
  public void testSum4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(SUM A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(72.0, value.getDouble(), 0.0);
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
    assertEquals(82.0, value.getDouble(), 0.0);
  }

  // test for no numeric values
  @Test
  public void testSumNoNumericValues() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(0.0, value.getDouble(), 0.0);

  }

  /*
      SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    ^ INFINITE LOOP

    also

     SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B10)"); <- creates infinite loop



    also

    input strings into sexp fails rectangle test

    need tests for: less than, concat, product
   */

  // test for only single cell
  @Test
  public void testSumSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(6.0, value.getDouble(), 0.0);

  }

  // test that contains blank cell ///////////////////////////////////////////////////////////////////////
  @Test
  public void testSumBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(0.0, value.getDouble(), 0.0);
  }

  // test when cell refers to itself.
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=A1");
  }

  // test when cell performs a function on itself.
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM A1 1)");
  }

  // test when cell contains a cycle (refers to itself indirectly).
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=B1");
    model.updateCell(new Coord(1, 2), "=A1");
    System.out.println(model.evaluateCell(new Coord(1, 2)));
  }


}
