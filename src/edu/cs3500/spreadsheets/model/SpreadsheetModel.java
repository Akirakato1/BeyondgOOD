package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import edu.cs3500.spreadsheets.sexp.Parser;

/**
 * Implementation of the spreadsheet model interface. It contains a hashmap that has the coordinates
 * as well as the formulas, so users can perform basic "CRUD" operations on the spreadsheet. All
 * cells are blank unless defined. If a cell is initiated and references another cell that has not
 * been defined yet (eg. A1 = B1), A1 will be blank as B1 has not been defined yet. If the user
 * introduces a cycle in B1/refers to A1, the model will throw an error.
 */
public class SpreadsheetModel implements ISpreadsheetModel {
  private int row;
  private int col;
  private HashMap<Coord, Formula> cells = new HashMap<>();
  private HashMap<Coord, Value> values = new HashMap<>();
  private HashMap<Coord, HashSet<Coord>> dependents = new HashMap<>();
  private HashMap<Coord, HashSet<Coord>> dependee = new HashMap<>();
  private int DEFAULT_ROW = 20;
  private int DEFAULT_COL = 20;


  /**
   * Default constructor for spreadsheet model.
   */
  public SpreadsheetModel() {
    this.row = this.DEFAULT_ROW;
    this.col = this.DEFAULT_COL;
  }

  /**
   * Construct a spreadsheet model given row and column numbers
   *
   * @param r row size
   * @param c column size
   */
  public SpreadsheetModel(int r, int c) {
    this.row = r;
    this.col = c;
  }

  /**
   * Sets the max row and column given a coordinate.
   *
   * @param coord cell coordinate
   */
  private void calculateRowCol(Coord coord) {
    if (coord.col > col) {
      col = coord.col;
    }
    if (coord.row > row) {
      row = coord.row;
    }
  }

  @Override
  public void updateCell(Coord coord, String sexp) {
    String exp = sexp;
    if (exp.substring(0, 1).equals("=")) {
      exp = exp.substring(1, exp.length());
    }
    this.calculateRowCol(coord);
    try {
      Formula formula = Parser.parse(exp).accept(new TranslateSexp(this));
      cells.put(coord, formula);
      this.initDependee(coord);
      this.initDependent(coord);
      this.updateDependentDependee(coord);
      formula.validateFormula();
      if (cyclePresent(coord, formula)) {
        values.put(coord, Error.REF);
      } else {
        if (values.containsKey(coord)) {
          values.remove(coord);
        }
        this.evaluateCell(coord);
      }
      this.reevaluateValueMap(coord);

    } catch (IllegalArgumentException e) {
      boolean errorHandled = this.handleErrorValue(e.getMessage(), coord);
      this.reevaluateValueMap(coord);
      if (errorHandled) {
        return;
      }
    }
  }

  /**
   * Checks the current coordinate for any errors.
   *
   * @param c coordinate
   * @return boolean representing if current coordinate is an error
   */
  private boolean checkIfErrorValue(Coord c) {
    List<Error> errorList = Arrays.asList(Error.values());
    for (Error err : errorList) {
      if (values.get(c).toString().equals(err.toString())) {
        return true;
      }
    }
    return false;
  }

  /**
   * Re-evalutes the hashmap and puts in the REF Error values.
   */
  private void reevaluateValueMap(Coord c) {
    if (this.cells.get(c) == null) {
      for (Coord dependeeCell : this.dependee.get(c)) {
        this.reevaluateValueMap(dependeeCell);
      }
      return;
    }
    boolean cIsError = this.checkIfErrorValue(c);
    HashSet<Coord> nonErrorValueCoords = new HashSet<>();
    for (Coord dependeeCell : this.dependee.get(c)) {
      if (!this.checkIfErrorValue(dependeeCell)) {
        nonErrorValueCoords.add(dependeeCell);
        values.remove(dependeeCell);
      }
    }

    for (Coord nonErrorDependee : nonErrorValueCoords) {
      if (cIsError) {
        values.put(nonErrorDependee, values.get(c));
      } else {
        this.evaluateCell(nonErrorDependee);
      }
      this.reevaluateValueMap(nonErrorDependee);
    }

  }

  @Override
  public void deleteCell(Coord coord) {
    if (!cells.containsKey(coord)) {
      throw new IllegalArgumentException("Coord is not mapped");
    }
    cells.remove(coord);
    values.remove(coord);
    this.updateDependentDependee(coord);
    this.reevaluateValueMap(coord);
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

  /**
   * Gets the coordinates the current coord is dependent on (EG if A1= A2+1, set should return A2).
   * @param coord cell coordinate
   * @return set of coordinates that current cell is dependent on
   */
  private HashSet<Coord> getDependent(Coord coord) {
    return this.cells.get(coord).getDependent();
  }

  /**
   * If a coord is updated, use this to update the dependees of a cell.
   * @param coord given cell coordinate
   */
  private void updateDependentDependee(Coord coord) {
    if (this.cells.get(coord) == null) {
      this.dependents.put(coord, new HashSet<Coord>());
      return;
    }
    HashSet<Coord> newDependent = this.getDependent(coord);
    HashSet<Coord> newDependentCopy = (HashSet) newDependent.clone();
    HashSet<Coord> oldDependent = (HashSet) this.dependents.get(coord).clone();
    oldDependent.removeAll(newDependent);
    newDependent.removeAll(this.dependents.get(coord));

    for (Coord c : newDependent) {
      this.initDependee(c);
      this.dependee.get(c).add(coord);
    }

    for (Coord c : oldDependent) {
      this.initDependee(c);
      this.dependee.get(c).remove(coord);
    }
    this.dependents.put(coord, newDependentCopy);
  }

  /**
   * Puts a new empty hashset into dependee if current coordinate has no dependees.
   * @param c cell coordinate
   */
  private void initDependee(Coord c) {
    if (!this.dependee.containsKey(c)) {
      this.dependee.put(c, new HashSet<Coord>());
    }
  }

  /**
   * Puts a new empty hashset into dependents if current coordinate has no cell it depends on.
   * @param c cell coordinate
   */
  private void initDependent(Coord c) {
    if (!this.dependents.containsKey(c)) {
      this.dependents.put(c, new HashSet<Coord>());
    }
  }

  /**
   * Returns true if it was successful in putting the corresponding error message into the hashmap.
   *
   * @param exceptionMsg given error message
   * @param coord        given coordinate
   * @return representing whether function successfully put in the error
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
   * @param formula      formula to be evaluated
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
    System.out.println(this.col + " " + this.row);
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

  @Override
  public int hashCode() {
    return Objects.hash(cells, values);
  }

  @Override
  public int getCol() {
    return this.col;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public void addCol() {
    this.col++;
  }

  @Override
  public void addRow() {
    this.row++;
  }

}
