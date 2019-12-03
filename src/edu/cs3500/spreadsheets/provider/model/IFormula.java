package edu.cs3500.spreadsheets.provider.model;

import java.util.List;
import java.util.Set;
import java.util.Stack;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents content in a spreadsheet that can evaluate to a {@code IValue}.
 */
public interface IFormula {

  /**
   * Produces the value that this formula evaluates. This formula must not have a cycle.
   *
   * @return the value that this formula simplifies to.
   * @throws IllegalStateException if the evaluation cannot be made
   */
  IValue evaluate() throws IllegalStateException;

  /**
   * Determines whether or not this formula has a reference to a cell with the given coordinate.
   *
   * @param coord the coordinate to be checked
   * @return whether the formula has a reference to the cell with the given coordinate
   */
  boolean hasReference(Coord coord);

  /**
   * Gets all parts of the cell that are needed to be evaluated and alters any values that cannot be
   * evaluated.
   *
   * @return the list of all Iformulas that are necessary to be evaluated
   */
  List<IFormula> getParts();

  /**
   * Determines whether or not this formula has a cycle.
   *
   * @param alreadySeen the coordinates that have already been traversed
   * @param noCycs      the coordinates which have been proved to not have cycles
   * @return whether or not this formula has a cycle
   */
  boolean hasCycle(Stack<Coord> alreadySeen, Set<Coord> noCycs);

  /**
   * Retrieves all the cell references that are immediately available in this formula. References
   * inside other references are not retrieved, but references inside functions are.
   *
   * @return all the cells immediately referenced by this formula
   */
  Set<SingleRef> getInitialRefs();
}
