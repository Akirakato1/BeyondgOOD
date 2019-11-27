package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import edu.cs3500.spreadsheets.model.ISpreadsheetViewOnly;

public abstract class AbstractVisualView extends DefaultView{

  protected String name;
  protected int windowWidth;
  protected int windowHeight;
  protected JFrame jf;
  protected SpreadsheetTable table;
  
  public AbstractVisualView(String filename, ISpreadsheetViewOnly ss, int ww, int wh) {
    super(ss);
    this.windowHeight = wh;
    this.windowWidth = ww;
    this.name = filename;
    this.table = new SpreadsheetTable(ss, windowWidth, windowHeight);
  }
  
  protected void renderSetup() {
    jf = new JFrame();
    
    jf.setTitle(name);
    jf.setSize(this.windowWidth, this.windowHeight);
    jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jf.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent componentEvent) {
        Rectangle r = jf.getBounds();
        table.getTable()
            .setPreferredScrollableViewportSize(new Dimension(r.width - 150, r.height - 150));
        table.getTable().setFillsViewportHeight(true);

        jf.setPreferredSize(jf.getSize());

        System.out.println("w:" + r.width + "h:" + r.height);
      }
    });
  }
  
  
}
