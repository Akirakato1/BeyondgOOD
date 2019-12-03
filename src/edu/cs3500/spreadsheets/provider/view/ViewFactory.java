package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;

/**
 * A factory class for making different kinds of WorksheetViews.
 */
public class ViewFactory {

  /**
   * Creates a text based view of the given type.
   *
   * @param model the model to base the view off of
   * @param app   the Appendable that the view should write to
   * @param type  type of the view
   * @return the created view
   */
  public static WorksheetView makeTextual(ReadOnlyWorksheetModel model, Appendable app,
      TxtViewType type) {
    if (type == TxtViewType.FILE) {
      return new TextualWorksheetView(model, app);
    }
    throw new IllegalStateException("Unrecognized view type :((");
  }

  /**
   * Creates a graphical view of the given type.
   *
   * @param model the model to base the view off of
   * @param type  the type of the view
   * @return the created view
   */
  public static WorksheetView makeGraphical(ReadOnlyWorksheetModel model, GUIViewType type) {
    switch (type) {
      case STATIC:
        return new GUIWorksheetView(model);
      case EDIT:
        return new GUIEditorWorksheetView(model);
      default:
        throw new IllegalStateException("Unrecognized view type :((");
    }
  }

  /**
   * Represents the type of graphical view; read only or editable.
   */
  public enum GUIViewType {
    STATIC, EDIT
  }

  /**
   * Represents the type of textual view.
   */
  public enum TxtViewType {
    FILE
  }

}
