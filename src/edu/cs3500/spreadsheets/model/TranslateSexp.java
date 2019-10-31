package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Visitor that translates given s-expressions into a formula. In this way, we avoid the pitfalls of
 * storing a hashmap of coordinate/SExp pairs to represent cells. This class is used when we create
 * our hashmap of coordinates/Formulas.
 */
class TranslateSexp implements SexpVisitor<Formula> {
  private ISpreadsheetModel ss;

  /**
   * Constructor for a TranslateSexp that translates SExps into Formulas.
   */
  public TranslateSexp(ISpreadsheetModel ss) {
    this.ss = ss;
  }


  @Override
  public Formula visitBoolean(boolean b) {
    return new Bool(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new Num(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    List<Sexp> temp = new ArrayList<>(l);
    Formula[] args = new Formula[l.size() - 1];
    String command = temp.get(0).toString();
    temp.remove(0);

    for (int i = 0; i < temp.size(); i++) {
      args[i] = temp.get(i).accept(this);
    }
    switch (command) {
      case "SUM":
        return new Sum(args);
      case "PRODUCT":
        return new Product(args);
      case "<":
        return new Lessthan(args);
      case "CONCAT":
        return new Concat(args);
      default:
        throw new IllegalArgumentException("Not a valid command/function "+command);
    }
  }

  @Override
  public Formula visitSymbol(String s) {
    try {
      Coord coord = nameToCoord(s);
      return new SingleRef(coord, ss);
    } catch (IllegalArgumentException e) {
      if (isValidRectangle(s)) {
        List<Coord> coords = validRectangleHelper(s);
        return new RectangleRef(coords.get(0), coords.get(1), ss);
      } else {
        throw new IllegalArgumentException(s+" is Not a valid singleref or rectangleref symbol");
      }
    }
  }

  @Override
  public Formula visitString(String s) {
    return new Str(s);
  }

  /**
   * Returns a list of coordinates representing the two coordinates given from a rectangle. ("A1:B1"
   * -> creates A1 and B1 and inserts into a list).
   *
   * @param name A string representing the range of cells
   * @return a list containing the first and last coordinates of the rectangle
   * Otherwise, returns null if there is an error/invalid rectangle
   */
  private static List<Coord> validRectangleHelper(String name) {
    int colonIndex = name.indexOf(':');
    if (colonIndex < 0) {
      return null;
    }
    String cell1 = name.substring(0, colonIndex);
    String cell2 = name.substring(colonIndex + 1, name.length());
    try {
      Coord cell1Coord = nameToCoord(cell1);
      Coord cell2Coord = nameToCoord(cell2);
      if (cell1Coord.col <= cell2Coord.col && cell1Coord.row <= cell2Coord.row) {
        return new ArrayList<>(Arrays.asList(cell1Coord, cell2Coord));
      }
      return null;

    } catch (IllegalArgumentException e) {
      return null;
    }

  }

  /**
   * Returns whether given input is a valid rectangle.
   * @param name given range of cells (eg. "A1:B3"
   * @return boolean of whether given string is a valid rectangle representation
   */
  private static boolean isValidRectangle(String name) {
    return validRectangleHelper(name) != null;
  }

  /**
   * Returns index of where the letter ends and the numbers start. Throws error
   * if cell name is malformed.
   * @param name given cell name
   * @return index of the last letter in the cell name
   */
  private static int isValidCell(String name) {
    int indexAlphabet = 0;

    for (; indexAlphabet < name.length(); indexAlphabet++) {
      if (!(name.charAt(indexAlphabet) <= 'Z' && name.charAt(indexAlphabet) >= 'A')) {
        if (indexAlphabet == 0) {
          throw new IllegalArgumentException("First letter not alphabet");
        }
        break;
      }
    }

    int indexInteger = indexAlphabet;
    for (; indexInteger < name.length(); indexInteger++) {
      if (!(name.charAt(indexInteger) <= '9' && name.charAt(indexInteger) >= '0')) {
        if (indexInteger == name.length() - 1) {
          throw new IllegalArgumentException("Last letter not integer");
        }
        break;
      }
    }

    if (indexInteger < name.length() - 1) {
      throw new IllegalArgumentException(
              "Doesn't follow alphabet+number pattern or have other symbols");
    }

    return indexAlphabet;
  }

  /**
   * Turns a named cell into a coordinate.
   * @param name name of cell (eg. "A1")
   * @return returns coordinate of that cell
   */
  private static Coord nameToCoord(String name) {
    int index = isValidCell(name);
    return new Coord(Coord.colNameToIndex(name.substring(0, index)),
            Integer.parseInt(name.substring(index, name.length())));
  }

}
