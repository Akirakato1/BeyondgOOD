package edu.cs3500.spreadsheets.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;


/**
 * NOTE: THIS WAS NOT OUR CODE! We got it from this tutorial website:
 * https://tips4java.wordpress.com/2008/11/05/fixed-column-table/
 * Also, we asked professor Freifeld
 * for permission, just in case. This class makes certain columns fixed so if we scroll
 * horizontally, it scrolls with it. We used it to make the row headers because JTable doesn't come
 * with that by default. Basically it makes sure to move with the scroll pane.
 */
class FixedColumnTable implements ChangeListener, PropertyChangeListener {
  private JTable main;
  private JTable fixed;
  private JScrollPane scrollPane;


  /**
   * Specify the number of columns to be fixed and the scroll pane containing the table.
   *
   * @param fixedColumns number of columns to fix
   * @param scrollPane   the scroll pane
   */
  public FixedColumnTable(int fixedColumns, JScrollPane scrollPane) {
    this.scrollPane = scrollPane;

    main = ((JTable) scrollPane.getViewport().getView());
    main.setAutoCreateColumnsFromModel(false);
    main.addPropertyChangeListener(this);

    // Use the existing table to create a new table sharing
    // the DataModel and ListSelectionModel

    fixed = new JTable();
    fixed.setAutoCreateColumnsFromModel(false);
    fixed.setModel(main.getModel());
    fixed.setSelectionModel(main.getSelectionModel());
    fixed.setFocusable(false);

    // Remove the fixed columns from the main table
    // and add them to the fixed table

    for (int i = 0; i < fixedColumns; i++) {
      TableColumnModel columnModel = main.getColumnModel();
      TableColumn column = columnModel.getColumn(0);
      columnModel.removeColumn(column);
      fixed.getColumnModel().addColumn(column);

      // We added this line and created the UneditableCell class
      column.setCellEditor(new UneditableCell(new JTextField()));
    }

    // Add the fixed table to the scroll pane

    fixed.setPreferredScrollableViewportSize(fixed.getPreferredSize());
    scrollPane.setRowHeaderView(fixed);
    scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixed.getTableHeader());

    // Synchronize scrolling of the row header with the main table

    scrollPane.getRowHeader().addChangeListener(this);
  }

  /**
   * Return the table being used in the row header.
   *
   * @return table being used in row header
   */
  JTable getFixedTable() {
    return fixed;
  }

  @Override
  public void stateChanged(ChangeEvent e) {
    // Sync the scroll pane scrollbar with the row header
    JViewport viewport = (JViewport) e.getSource();
    scrollPane.getVerticalScrollBar().setValue(viewport.getViewPosition().y);
  }

  @Override
  public void propertyChange(PropertyChangeEvent e) {
    if ("selectionModel".equals(e.getPropertyName())) {
      fixed.setSelectionModel(main.getSelectionModel());
    }

    if ("model".equals(e.getPropertyName())) {
      fixed.setModel(main.getModel());
    }
  }
}
