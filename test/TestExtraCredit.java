import org.junit.Test;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Num;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.Str;
import edu.cs3500.spreadsheets.model.Value;

import static org.junit.Assert.assertEquals;


/**
 * Class to test extra credit features (column references).
 */
public class TestExtraCredit {

  // test sum on a column of references.
  @Test
  public void testColSum() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(82), value.evaluate());
  }

  // test for no numeric values.
  @Test
  public void testSumNoNumericValuesCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 3), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(0), value.evaluate());

  }

  // test sum for many cells without any doubles.
  @Test
  public void testSumCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 3), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(0), value.evaluate());
  }

  // test sum for many cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testSumColWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(2, 3), "=(SUM A:B)");
    model.evaluateCell(new Coord(2, 3)).evaluate();
  }

  // test concat on column of references.
  @Test
  public void testConcatCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=\"HELLO\"");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(CONCAT A1:B2)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Str("HELLO10.0HELLO"), value.evaluate());
  }

  // test concat for no numeric values.
  @Test
  public void testColConcat() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 6), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 6));
    assertEquals("0.000000", value.evaluate().toString());

  }

  // test concat for column of cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testColConcatWithCycle() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(CONCAT (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 3), "=(CONCAT A:C)");
    model.evaluateCell(new Coord(3, 3)).evaluate();

  }


  // test product on column of references.
  @Test
  public void testProductCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(PRODUCT A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(12960), value.evaluate());
  }

  // test for no numeric values.
  @Test
  public void testProductNoNumericValuesCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(< (SUM 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 6), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 6));
    assertEquals(new Num(0), value.evaluate());

  }

  // test product for many cells with a cycle.
  @Test(expected = IllegalArgumentException.class)
  public void testProductManyWithCycleCol() {
    SpreadsheetModel model = new SpreadsheetModel();
    assertEquals(0, model.errorMessages().size());
    model.updateCell(new Coord(1, 1), "=(< (PRODUCT 1 2) 3)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "=A2");
    model.updateCell(new Coord(3, 3), "=(PRODUCT A:C)");
    Value value = model.evaluateCell(new Coord(3, 3));
    value.evaluate();
  }

  // adding items in new row updates column sum.
  @Test
  public void testColSumUpdate() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(82), value.evaluate());
    model.addRow();
    model.updateCell(new Coord(1, 21), "=5");
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(87), value2.evaluate());
  }

  // adding items in new row updates column product.
  @Test
  public void testColProductUpdate() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(PRODUCT A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(12960), value.evaluate());
    model.addRow();
    model.updateCell(new Coord(1, 21), "=5");
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(64800), value2.evaluate());
  }

  // adding items in new row updates column concat.
  @Test
  public void testConcatCol2() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=\"HELLO\"");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(CONCAT A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Str("HELLO10.0HELLO"), value.evaluate());
    model.addRow();
    model.updateCell(new Coord(1, 21), "=5");
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Str("HELLO10.0HELLO5.0"), value2.evaluate());
  }

  // deleting items in new row updates column sum.
  @Test
  public void testColSumDelete() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(SUM A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(82), value.evaluate());
    model.deleteCell(new Coord(1, 1));
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(10), value2.evaluate());
  }

  // deleting items in new row updates column product.
  @Test
  public void testColProductDelete() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=(PRODUCT (SUM 1 2) 3 4)");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(PRODUCT A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(12960), value.evaluate());
    model.deleteCell(new Coord(1, 1));
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Num(10), value2.evaluate());
  }

  // deleting items in new row updates column concat.
  @Test
  public void testConcatCol3() {
    SpreadsheetModel model = new SpreadsheetModel();
    model.updateCell(new Coord(1, 1), "=\"HELLO\"");
    model.updateCell(new Coord(1, 2), "=A1");
    model.updateCell(new Coord(2, 1), "10");
    model.updateCell(new Coord(3, 3), "=(CONCAT A:B)");
    Value value = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Str("HELLO10.0HELLO"), value.evaluate());
    model.deleteCell(new Coord(2, 1));
    Value value2 = model.evaluateCell(new Coord(3, 3));
    assertEquals(new Str("HELLOHELLO"), value2.evaluate());
  }
}