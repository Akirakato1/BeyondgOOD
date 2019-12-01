package edu.cs3500.spreadsheets.provider.view;

import edu.cs3500.spreadsheets.provider.controller.SpreadsheetFeatures;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.provider.model.ReadOnlyWorksheetModel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A graphical user interface that displays the worksheet and allows the user to interact with the
 * worksheet.
 */
public class GUIEditorWorksheetView extends JFrame implements WorksheetView {

  private final ScrollingCellPanel cellPanel;
  private final JTextField editBar;
  private final List<SpreadsheetFeatures> features;
  private ReadOnlyWorksheetModel model;

  /**
   * Makes a GUIEditorWorksheetView that corresponds to the given model, sets up all the components
   * that displays the cells and allows for editing and makes the view visible.
   *
   * @param model the worksheet holding the data for this view
   */
  public GUIEditorWorksheetView(ReadOnlyWorksheetModel model) {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("gOOD!");
    this.features = new ArrayList<SpreadsheetFeatures>();
    this.model = model;

    JPanel editInfo = new JPanel();
    editInfo.setLayout(new FlowLayout());
    JButton confirmButton = new JButton("âœ”");
    confirmButton.addActionListener(new CellDataEnteredListener());
    JButton rejectButton = new JButton("â�Œ");
    rejectButton.addActionListener(new CellDataRejectedListener());
    this.editBar = new JTextField(60);
    this.editBar.addActionListener(new CellDataEnteredListener());
    editInfo.add(confirmButton);
    editInfo.add(rejectButton);
    editInfo.add(this.editBar);

    this.cellPanel = new ScrollingCellPanel(model);
    // the spreadsheet always highlights at the first coordinate to start
    this.highlightAt(new Coord(1, 1));
    this.cellPanel.addGridMouseListener(new CellsMouseListener());
    this.cellPanel.addKeyListener(new CellKeyboardListener());

    // make the menubar and options to save/load files
    JMenuBar menubar = new JMenuBar();
    JMenu fileMenu = new JMenu("File");
    JMenuItem save = new JMenuItem("Save to");
    JMenuItem load = new JMenuItem("Load from");
    menubar.add(fileMenu);
    fileMenu.add(save);
    fileMenu.add(load);
    save.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String fileName = (String) JOptionPane.showInputDialog(
            GUIEditorWorksheetView.this,
            "What file do you want to save to?",
            "Save To",
            JOptionPane.PLAIN_MESSAGE, null, null, "");
        GUIEditorWorksheetView.this.features.stream().forEach(f -> f.saveTo(fileName));
      }
    });
    load.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent actionEvent) {
        String fileName = (String) JOptionPane.showInputDialog(
            GUIEditorWorksheetView.this,
            "What file do you want to load from?",
            "Load From",
            JOptionPane.PLAIN_MESSAGE, null, null, "");
        GUIEditorWorksheetView.this.features.stream().forEach(f -> f.loadFile(fileName));
      }
    });

    // set up the JFrame
    this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
    this.add(editInfo);
    this.add(cellPanel);
    this.setJMenuBar(menubar);
    this.pack();
    this.setVisible(true);
  }

  @Override
  public void refresh() {
    this.repaint();
    this.cellPanel.requestFocus();
  }

  @Override
  public void addSpreadsheetFeatures(SpreadsheetFeatures sf) {
    this.features.add(sf);
  }

  @Override
  public void highlightAt(Coord loc) {
    this.cellPanel.highlightAt(loc);
    this.editBar.setText(this.model.getTypedContentsAt(loc));
  }

  @Override
  public void turnOffHighlight() {
    this.cellPanel.turnOffHighlight();
    this.editBar.setText("");
  }

  /**
   * Displays a message as a pop up window.
   *
   * @param message the message to display
   */
  @Override
  public void displayMsg(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public void changeModel(ReadOnlyWorksheetModel model) {
    this.model = model;
    this.cellPanel.changeModel(model);
  }

  /**
   * Responds to mouse events in the cellPanel by calling the appropriate method on all of the
   * view's SpreadsheetFeatures.
   */
  private class CellsMouseListener implements MouseListener {
    //implement clicking and dragging to highlight a region?

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
      Coord coord = JCellPanel.cellCoordAt(mouseEvent.getX(), mouseEvent.getY());
      GUIEditorWorksheetView.this.features.stream().forEach((sf) -> sf.cellSelected(coord));
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
      // don't need to do anything
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
      // don't need to do anything
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
      // don't need to do anything
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
      // don't need to do anything
    }
  }

  /**
   * Responds to the user entering text in a spreadsheet cell by calling the appropriate method on
   * all of the view's SpreadsheetFeatures.
   */
  private class CellDataEnteredListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      String editText = GUIEditorWorksheetView.this.editBar.getText();
      Coord coord = GUIEditorWorksheetView.this.cellPanel.getHighlightLoc();
      GUIEditorWorksheetView.this.features.stream()
          .forEach((sf) -> sf.dataEntered(editText, coord));
    }
  }

  /**
   * Responds to the user rejecting text in a spreadsheet cell by removing edits and redisplaying
   * the original formula.
   */
  private class CellDataRejectedListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
      // does not notify the features bc this is purely changing the display in the edit bar -
      // model is never updated
      Coord loc = GUIEditorWorksheetView.this.cellPanel.getHighlightLoc();
      String editText = GUIEditorWorksheetView.this.model.getTypedContentsAt(loc);
      GUIEditorWorksheetView.this.editBar.setText(editText);
      GUIEditorWorksheetView.this.highlightAt(loc);
      GUIEditorWorksheetView.this.refresh();
    }
  }

  /**
   * Responds to key events that occur on the cells (deleting from a cell and moving between cells
   * using arrow keys), by calling the appropriate method on SpreadsheetFeatures.
   */
  private class CellKeyboardListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent keyEvent) {
      // We are not concerned with what happens when it is typed
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
      final Coord loc = GUIEditorWorksheetView.this.cellPanel.getHighlightLoc();
      if (loc == null) {
        return;  // don't need to do anything if no cell selected
      }
      int shift;
      Coord newLoc;
      switch (keyEvent.getKeyCode()) {
        case KeyEvent.VK_BACK_SPACE:
          GUIEditorWorksheetView.this.features.stream().forEach((f) -> f.deleteData(loc));
          break;
        case KeyEvent.VK_RIGHT:
          shift = Math.min(GUIEditorWorksheetView.this.cellPanel.getCurCols(), loc.col + 1);
          newLoc = new Coord(shift, loc.row);
          GUIEditorWorksheetView.this.features.stream().forEach((f) -> f.cellSelected(newLoc));
          break;
        case KeyEvent.VK_UP:
          shift = Math.max(loc.row - 1, 1);
          newLoc = new Coord(loc.col, shift);
          GUIEditorWorksheetView.this.features.stream().forEach((f) -> f.cellSelected(newLoc));
          break;
        case KeyEvent.VK_DOWN:
          shift = Math.min(loc.row + 1, GUIEditorWorksheetView.this.cellPanel.getCurRows());
          newLoc = new Coord(loc.col, shift);
          GUIEditorWorksheetView.this.features.stream().forEach((f) -> f.cellSelected(newLoc));
          break;
        case KeyEvent.VK_LEFT:
          shift = Math.max(loc.col - 1, 1);
          newLoc = new Coord(shift, loc.row);
          GUIEditorWorksheetView.this.features.stream().forEach((f) -> f.cellSelected(newLoc));
          break;
        default:
          // Do nothing
      }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
      // We are not concerned with what happens when it is just released
    }
  }
}
