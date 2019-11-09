package edu.cs3500.spreadsheets.view;

import javax.swing.JFrame;
import edu.cs3500.spreadsheets.model.ISpreadsheetModel;

public class VisualView implements SpreadsheetView {
  private ISpreadsheetModel ss;
  private String name;
  private int windowWidth;
  private int windowHeight;

  public VisualView(String filename, ISpreadsheetModel ss, int ww, int wh) {
    this.ss = ss;
    this.windowHeight = wh;
    this.windowWidth = ww;
    name = filename;
  }

  @Override
  public void render() {
    JFrame jf = new JFrame();
    SpreadsheetTable tab = new SpreadsheetTable(ss,this.windowWidth,this.windowHeight);
    jf.setTitle(name);
    jf.setSize(this.windowWidth, this.windowHeight);
    jf.setVisible(true);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.add(tab);
  }

}
