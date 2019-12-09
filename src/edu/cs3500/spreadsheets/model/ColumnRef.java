package edu.cs3500.spreadsheets.model;

class ColumnRef extends RectangleRef {

  /**
   * Constructor for a column of references.
   *
   * @param first  coordinate of first cell
   * @param second coordinate of last cell
   * @param ss     spreadsheet model
   */
  public ColumnRef(Coord first, Coord second, ISpreadsheetModel ss) {
    super(first, second, ss);
  }

  @Override
  public String toString() {
    return super.first.toString().substring(0,1) + ":" + super.second.toString().substring(0,1);
  }
}
