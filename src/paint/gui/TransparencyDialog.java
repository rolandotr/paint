package paint.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TransparencyDialog extends JDialog {
  JPanel panel1 = new JPanel();
  BorderLayout borderLayout1 = new BorderLayout();
  Scheme oleo;
  public TransparencyDialog(Frame frame, String title, boolean modal, Scheme oleo) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
    this.oleo = oleo;
  }

  private void jbInit() throws Exception {
    panel1.setLayout(borderLayout1);
    JSlider slide = new JSlider(0, 100);
    slide.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent e) {
        JSlider b = (JSlider)e.getSource();
        oleo.setOpacity(b.getValue() / 100f);
      };
    });
    setSize(new Dimension(50, 150));
    panel1.add(slide, BorderLayout.CENTER);
    getContentPane().add(panel1);
  }
}
