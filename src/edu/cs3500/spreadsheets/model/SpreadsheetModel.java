package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.Sexp;

public class SpreadsheetModel implements ISpreadsheetModel {
  private HashMap<Coord, String> cells = new HashMap<>();
  //value hashmap only for numeric coordinates
  //null for error (cyclic)
  private HashMap<Coord, IValue> values = new HashMap<>();

  private void cellDontExistException(Coord coord) {
    if (!cells.containsKey(coord)) {
      throw new IllegalArgumentException("Cell doesn't exist at given coordinate");
    }
  }

  @Override
  public double computeValue(Coord coord) {
    cellDontExistException(coord);
    Sexp sexp=Parser.parse(cells.get(coord));
    
    
    return 0;
  }

  private double computeValueHelper(Sexp expression) {
    return 1;
  }

  @Override
  public void updateCellSexp(Coord coord, String sexp) {
    cellDontExistException(coord);
    cells.put(coord, sexp);
    updateValuesMap(coord,sexp);
  }

  @Override
  public void createCell(int row, int col, String sexp) {
    Coord coordinate = new Coord(col, row);
    cells.put(coordinate, sexp);
    updateValuesMap(coordinate,sexp);
  }
  
  private void updateValuesMap(Coord coordinate, String sexp) {
    Sexp parsed=Parser.parse(sexp);
    if(parsed instanceof SNumber) {
      values.put(coordinate, parsed.accept(new GetNumber()));
    }else if(values.containsKey(coordinate)){
      values.remove(coordinate);
    }
  }

  @Override
  public void deleteCell(Coord coord) {
    cellDontExistException(coord);
    cells.remove(coord);
    values.remove(coord);
  }

  private Coord nameToCoord(String name) {
    int index = validateCoordName(name);
    return new Coord(Coord.colNameToIndex(name.substring(0, index)),
        Integer.parseInt(name.substring(index, name.length())));
  }

  private int validateCoordName(String name) {
    int indexAlphabet = 0;
    
    for (; indexAlphabet < name.length(); indexAlphabet++) {
      if (!(name.charAt(indexAlphabet) <= 'Z' && name.charAt(indexAlphabet) >= 'A')) {
        if(indexAlphabet==0) {
          throw new IllegalArgumentException("First letter not alphabet");
        }
        break;
      }
    }

    int indexInteger = indexAlphabet;
    for (; indexInteger < name.length(); indexInteger++) {
      if (!(name.charAt(indexInteger) <= '9' && name.charAt(indexInteger) >= '0')) {
        if(indexInteger==name.length()-1) {
          throw new IllegalArgumentException("Last letter not integer");
        }
        break;
      }
    }
    
    if(indexInteger<name.length()-1) {
      throw new IllegalArgumentException("Doesn't follow alphabet+number pattern or have other symbols");
    }

    return indexAlphabet;
  }

}
