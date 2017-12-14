package paint.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Rectangle extends RectangleDefine{

  public Rectangle(BufferedImage image){
    super(image);
  }

  private boolean can_paint = false;

  public void paint(Graphics g) {
    g.setColor(color);
    if (can_paint)
      g.drawRect(x,y,w,h);
  }


  public void reset() {
    super.reset();
    can_paint = false;
  }

  public void mouseDragged(MouseEvent e) {
//    if (e.getX() < 0 || e.getY() < 0) return;
    computeBounds(e.getX(), e.getY());
    can_paint = true;
    repaint();
//    tool_listener.changeState();
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    setReferencePoint(e.getX(), e.getY());
  }

  public void mouseReleased(MouseEvent e) {
      tool_listener.imageChange();
    finish();
  }

}
