package edu.cs3500.spreadsheets.model;

import java.util.HashMap;

import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * Implementation of the spreadsheet model interface. It contains a hashmap that has the coordinates
 * as well as the formulas, so users can perform basic "CRUD" operations on the spreadsheet.
 */
public class SpreadsheetModel implements ISpreadsheetModel {
  private HashMap<Coord, Formula> cells = new HashMap<>();

  @Override
  public void updateCell(Coord coord, String sexp) {
    String exp = sexp;
    if (exp.substring(0, 1).equals("=")) {
      exp = exp.substring(1, exp.length());
    }
    Formula formula = Parser.parse(exp).accept(new TranslateSexp(this));
    if (cyclePresent(coord, formula)) {
      throw new IllegalArgumentException("Cycle detected in formula");
    }
    cells.put(coord, formula);
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
    return cells.get(coord).evaluate();
  }

  private boolean cyclePresent(Coord currentCoord, Formula formula) {
    return formula.cyclePresent(currentCoord);
  }

}
