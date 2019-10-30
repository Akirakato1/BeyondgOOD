package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * To represent a type of value that a user can input into a cell. Because a formula can result into
 * a value, it extends formula. A value can be one of the following: 1) string 2) double 3) boolean
 */
public interface Value extends Formula {
  
  <R> R accept(ValueVisitor<R> visitor);

}
