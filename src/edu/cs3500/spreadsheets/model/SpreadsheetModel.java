package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.plaf.ListUI;
import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * Implementation of the spreadsheet model interface. It contains a hashmap that has the coordinates
 * as well as the formulas, so users can perform basic "CRUD" operations on the spreadsheet. All
 * cells are blank unless defined. If a cell is initiated and references another cell that has not
 * been defined yet (eg. A1 = B1), A1 will be blank as B1 has not been defined yet. If the user
 * introduces a cycle in B1/refers to A1, the model will throw an error.
 */
public class SpreadsheetModel implements ISpreadsheetModel {
  private HashMap<Coord, Formula> cells = new HashMap<>();
  private HashMap<Coord, Value> values = new HashMap<>();
  private List<String> errorMessages = new ArrayList<>();

  @Override
  public void updateCell(Coord coord, String sexp) {
    String exp = sexp;
    if (exp.substring(0, 1).equals("=")) {
      exp = exp.substring(1, exp.length());
    }

    try {
      Formula formula = Parser.parse(exp).accept(new TranslateSexp(this));
      if (cyclePresent(coord, formula)) {
        this.errorMessages
            .add("Cycle detected at " + coord.toString() + " formula: =" + formula.toString());
      } else {
        cells.put(coord, formula);
      }
    } catch (IllegalArgumentException e) {
      this.errorMessages.add(e.getMessage());
    }

  }

  @Override
  public void deleteCell(Coord coord) {
    if (!cells.containsKey(coord)) {
      throw new IllegalArgumentException("Coord is not mapped");
    }
    cells.remove(coord);
  }

  @Override
  public Formula getFormulaAtCoord(Coord coord) {
    if (cells.containsKey(coord)) {
      return cells.get(coord);
    }
    return new Blank();
  }

  @Override
  public Value evaluateCell(Coord coord) {
    if (!cells.containsKey(coord)) {
      return new Blank();
    }
    if(values.containsKey(coord)) {
      return values.get(coord);
    }
    values.put(coord, cells.get(coord).evaluate());
    return values.get(coord);
  }

  /**
   * Checks if there is a cycle at the given coordinate and formula
   * 
   * @param currentCoord current coordinate
   * @param formula formula to be evaluated
   * @return
   */
  private boolean cyclePresent(Coord currentCoord, Formula formula) {
    return formula.cyclePresent(currentCoord);
  }

  @Override
  public List<String> errorMessages() {
    return new ArrayList<String>(errorMessages);
  }

}
