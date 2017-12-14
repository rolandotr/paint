package paint.gui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class NewDialog extends JDialog {

  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jt1, jt2;
  public NewDialog(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    try {
      jbInit();
      pack();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
    JPanel p1 = new JPanel();
    p1.setLayout(new FlowLayout(FlowLayout.LEFT));
    p1.add(new JLabel("width"));
    jt1 = new JTextField();
    jt1.setPreferredSize(new Dimension(80, 20));
    p1.add(jt1);
    JPanel p2 = new JPanel();
    p2.setLayout(new FlowLayout(FlowLayout.LEFT));
    p2.add(new JLabel("height"));
    jt2 = new JTextField();
    jt2.setPreferredSize(new Dimension(80, 20));
    p2.add(jt2);
    JPanel p3 = new JPanel();
    p3.setLayout(new FlowLayout(FlowLayout.LEFT));
    JButton jb_accept = new JButton("Accept");
    JButton jb_cancel = new JButton("Cancel");
    jb_accept.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jb_accept_actionPerformed();
      }
    });
    jb_cancel.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        jb_cancel_actionPerformed();
      }
    });
    p3.add(jb_accept);
    p3.add(jb_cancel);
    panel1.add(p1);
    panel1.add(p2);
    panel1.add(p3);
    getContentPane().add(panel1);
  }
  private int width;
  private int height;

  public int getWidth(){
    return width;
  }

  private void jb_cancel_actionPerformed(){
    dispose();
  }
  private void jb_accept_actionPerformed(){
    try {
      width = Integer.parseInt(jt1.getText());
      height = Integer.parseInt(jt2.getText());
    } catch (NumberFormatException ex) {
      JOptionPane.showMessageDialog(this, "Bad frame dimension", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    accept = true;
    this.dispose();
  }

  public int getHeight(){
    return height;
  }

  private boolean accept = false;

  public boolean accept(){
    return accept;
  }
}

