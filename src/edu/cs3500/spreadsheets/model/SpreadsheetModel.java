package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

  @Override
  public void updateCell(Coord coord, String sexp) {
    String exp = sexp;
    if (exp.substring(0, 1).equals("=")) {
      exp = exp.substring(1, exp.length());
    }

    try {
      Formula formula = Parser.parse(exp).accept(new TranslateSexp(this));
      cells.put(coord, formula);
      formula.validateFormula();
      if (cyclePresent(coord, formula)) {
        values.put(coord, Error.REF);
        this.reevaluateValueMap();
      } else {
        this.evaluateCell(coord);
      }

    } catch (IllegalArgumentException e) {
      boolean errorHandled = this.handleErrorValue(e.getMessage(), coord);
      this.reevaluateValueMap();
      if (errorHandled) {
        return;
      }
    }
  }

  /**
   * Re-evalutes the hashmap and puts in the REF Error values.
   */
  private void reevaluateValueMap() {
    boolean isError = false;
    // try to get deep copy of the keys of values hashmap
    Set<Coord> coords = values.keySet();
    Object[] cs = coords.toArray();

    for (int i = 0; i < cs.length; i++) {
      List<Error> errorList = Arrays.asList(Error.values());
      for (Error err : errorList) {
        if (values.get(cs[i]).toString().equals(err.toString())) {
          isError = true;
        }
      }
      if (isError) {
        isError = false;
      } else { // remove non-error coord
        values.remove(cs[i]);
      }
    }

    for (Coord c : cells.keySet()) {
      if (!values.containsKey(c)) {
        if (cyclePresent(c, cells.get(c))) {
          values.put(c, Error.REF);
        } else {
          this.evaluateCell(c);
        }
      }
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
    try {
      if (!cells.containsKey(coord)) {
        return new Blank();
      }
      if (!values.containsKey(coord)) {
        values.put(coord, cells.get(coord).evaluate());
      }
      return values.get(coord);
    } catch (IllegalArgumentException e) {
      this.handleErrorValue(e.getMessage(), coord);
      return values.get(coord);
    }
  }

  //

  /**
   * Returns true if it was successful in putting the corresponding
   * error message into the hashmap.
   * @param exceptionMsg given error message
   * @param coord given coordinate
   * @return boolean representing whether function successfully put in the error (return true),
   * else returns false
   */
  private boolean handleErrorValue(String exceptionMsg, Coord coord) {
    List<Error> errorList = Arrays.asList(Error.values());
    for (Error err : errorList) {
      if (exceptionMsg.equals(err.toString())) {
        values.put(coord, err);
        return true;
      }
    }
    return false;
  }

  /**
   * Checks if there is a cycle at the given coordinate and formula.
   *
   * @param currentCoord current coordinate
   * @param formula formula to be evaluated
   */
  private boolean cyclePresent(Coord currentCoord, Formula formula) {
    return formula.cyclePresent(currentCoord, new HashSet<Coord>());
  }

  @Override
  public List<String> errorMessages() {
    List<String> errorMessages = new ArrayList<>();
    List<Error> errorList = Arrays.asList(Error.values());
    for (Coord c : values.keySet()) {
      for (Error err : errorList) {
        if (values.get(c).toString().equals(err.toString())) {
          errorMessages.add("ERROR: " + err.toString() + " at " + c.toString());
        }
      }
    }
    return errorMessages;
  }

  @Override
  public List<Coord> getOccupiedCoords() {
    List<Coord> output = new ArrayList<Coord>();
    for (Coord c : cells.keySet()) {
      output.add(new Coord(c.col, c.row));
    }
    return output;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ISpreadsheetModel)) {
      return false;
    }
    List<Coord> coordsOther = ((ISpreadsheetModel) o).getOccupiedCoords();
    HashMap<Coord, String> otherCells = new HashMap<>();
    for (Coord c : coordsOther) {
      otherCells.put(c, ((ISpreadsheetModel) o).getFormulaAtCoord(c).toString());
    }

    List<Coord> coordsThis = this.getOccupiedCoords();
    HashMap<Coord, String> thisCells = new HashMap<>();
    for (Coord c : coordsThis) {
      thisCells.put(c, this.getFormulaAtCoord(c).toString());
    }
    return thisCells.equals(otherCells);
  }

}
