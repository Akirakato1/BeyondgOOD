import org.junit.Test;
import edu.cs3500.spreadsheets.model.Bool;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.Str;
import edu.cs3500.spreadsheets.model.Value;

import static org.junit.Assert.assertEquals;

public class TestSpreadsheet {

  // TODO: REFACTOR ALL FUNCTIONS!!!!
  // test for all the different cell contents (q 19)............
  // rendering values as strings (24)
  // non matching (sum true 5)
  // product of empty cell should be 0 not 1
  // test for all public methods in ISpreadsheetModel

  // test for creating an empty spreadsheet.
  /*
  @Test
  public void createEmptySpreadsheet() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.getFormulaAtCoord(new Coord(1, 1));
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(null, value.getDouble());
  }


  // test that created cells are there.
  @Test
  public void getFormulaAtCoord() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(SUM A1 A2)");
    assertEquals(36, model.getFormulaAtCoord(new Coord(1,1)).evaluate().getDouble(), 0.0);
    assertEquals(36, model.getFormulaAtCoord(new Coord(1,2)).evaluate().getDouble(), 0.0);
    assertEquals(72, model.getFormulaAtCoord(new Coord(1,3)).evaluate().getDouble(), 0.0);
  }

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

  // test for no numeric values.
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

  // test sum for many cells without any doubles.
  @Test
  public void testSumMany() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(0, value.getDouble(), 0.0);
  }

  // test sum for many cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testSumManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(SUM A1:B10)");
  }

  // test for sum with an arbitrary amount of arguments.
  @Test
  public void testSumSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(SUM 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(6.0, value.getDouble(), 0.0);
  }

  // test sum when it contains blank cell.
  @Test
  public void testSumBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(null, value.getDouble());
  }

  // test sum when cell refers to itself.
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=A1");
  }

  // test when cell performs a function on itself (SUM).
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
    model.updateCell(new Coord(2, 1), "=A1");
  }

  // test when cell performs a function on itself (<).
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< A1 1)");
  }

  // test when cell performs a function on itself (CONCAT).
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle5() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT A1 1)");
  }

  // test when cell performs a function on itself (PRODUCT).
  @Test(expected = IllegalArgumentException.class)
  public void testUpdateCellCycle6() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT A1 1)");
  }

  // test model when cell is updated with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testSumCycleAfterUpdate() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 1), "=A1");
  }



  // test for < on a simple good input.
  @Test
  public void testLessThan() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(false, value.getBoolean());
  }

  // test for < on multiple good input.
  @Test
  public void testLessThan2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(true, value.getBoolean());
  }
*/
  // test for < on multiple good input and functions.
  @Test
  public void testLessThan3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT \"Hello\" \"World\")");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(new Str("HelloWorld"), value);
  }
/*
  // test < on references.
  @Test
  public void testLessThan4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 1)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(< A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(false, value.getBoolean());
  }

  // test < for no numeric values.
  @Test (expected = IllegalArgumentException.class)
  public void testBadLessThan() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< hello world)");
  }

  // test for < with arbitrary inputs.
  @Test (expected = IllegalArgumentException.class)
  public void testBadLessThan2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(6.0, value.getDouble(), 0.0);
  }

  // test < when it contains blank cell.
  @Test (expected = IllegalArgumentException.class)
  public void testBadLessThan3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1,1),"B1");
    model.updateCell(new Coord(1,2),"=(< B1)");
    double value = model.evaluateCell(new Coord(1,2)).getDouble();
    assertEquals(0, value,0.0);
  }


  // test for concat on a simple good input.
  @Test
  public void testConcat() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT hello world)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals("helloworld", value.getString());
  }

  // test for concat on multiple good input.
  @Test
  public void testConcat2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT HI HI HI)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(24.0, value.getString());
  }

  // test for concat on multiple good input and functions.
  @Test
  public void testConcat3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT HELLO (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(36.0, value.getString());
  }

  // test concat on references.
  @Test
  public void testConcat4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=Hello");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(1296.0, value.getString());
  }

  // test concat on rectangle of references.
  @Test
  public void testConcat5() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=HELLO");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1:B2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(12960.0, value.getString());
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
    assertEquals(0.0, value.getDouble(), 0.0);

  }

  // test concat for many cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testConcatManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(CONCAT A1:B10)");
  }

  // test for concat product with arbitrary inputs.
  @Test
  public void testConcatSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(CONCAT hello product (PRODUCT 1 2 3))");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(6.0, value.getString());
  }

  // test concat that contains blank cell.
  @Test
  public void testConcatBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1,1),"B1");
    model.updateCell(new Coord(1,2),"=(CONCAT B1 B1)");
    String value = model.evaluateCell(new Coord(1,2)).getString();
    assertEquals("", value);
  }




  // test for product on a simple good input.
  @Test
  public void testProduct() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT 5 5)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(25.0, value.getDouble(), 0.0);
  }

  // test for product on multiple good input.
  @Test
  public void testProduct2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (PRODUCT 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(24.0, value.getDouble(), 0.0);
  }

  // test for product on multiple good input and functions.
  @Test
  public void testProduct3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(36.0, value.getDouble(), 0.0);
  }

  // test product on references.
  @Test
  public void testProduct4() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "A1");
    model.updateCell(new Coord(1, 3), "=(PRODUCT A1 A2)");
    Value value = model.evaluateCell(new Coord(1, 3));
    assertEquals(1296.0, value.getDouble(), 0.0);
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
    assertEquals(12960.0, value.getDouble(), 0.0);
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
    assertEquals(0.0, value.getDouble(), 0.0);

  }

  // test product for many cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testProductManyWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(1, 3), "=(PRODUCT A1:B10)");
  }

  // test for product with arbitrary inputs.
  @Test
  public void testProductSingleCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT 1 2 3)");
    Value value = model.evaluateCell(new Coord(1, 1));
    assertEquals(6.0, value.getDouble(), 0.0);
  }

  // test that contains blank cell.
  @Test
  public void testProductBlankCell() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1,1),"B1");
    model.updateCell(new Coord(1,2),"=(PRODUCT B1 B1)");
    double value = model.evaluateCell(new Coord(1,2)).getDouble();
    assertEquals(0, value,0.0);
  }

*/


}
