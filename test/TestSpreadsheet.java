import org.junit.Test;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.SpreadsheetModel;
import edu.cs3500.spreadsheets.model.Value;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import java.io.Reader;

import java.io.StringReader;

public class TestSpreadsheet {

  @Test
  public void testSum() {
    SpreadsheetModel model=new SpreadsheetModel();
    model.updateCell(new Coord(1,1), "=(SUM (SUM 1 2) 3 4)");
    Value value=model.evaluateCell(new Coord(1,1));
    System.out.println(value.getDouble());
    //assertEquals(10.0,value.getDouble());
  }
}
