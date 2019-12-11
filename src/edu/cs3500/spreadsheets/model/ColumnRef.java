package edu.cs3500.spreadsheets.model;

/**
 * Represents a whole column reference in the spreadsheet. It is similar to the rectangle ref, hence
 * extending it.
 */
class ColumnRef extends RectangleRef {

  /**
   * Constructor for a column of references. A column reference contains the whole column. For
   * instance, (SUM A:A) would sum the column of A and (SUM A:C) would sum columns A to C.
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
    return super.first.toString().substring(0, 1) + ":" + super.second.toString().substring(0, 1);
  }

  @Override
  public boolean hasColumnRef() {
    return true;
  }

  @Override
  protected Formula[] expand() {
    this.second = new Coord(this.second.col, ss.getRow());
    return super.expand();
  }

}
