package paint.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class Circle extends RectangleDefine{

  public Circle(BufferedImage image){
    super(image);
  }
  /** comment 2007-Feb-12 Trujillo
   * Aqui lo que vienen son las coordenadas del rectangulo que define la elipse
   */


  public void paint(Graphics g) {
    g.setColor(color);
    g.drawOval(x,y,w,h);
  }


  public void mouseDragged(MouseEvent e) {
//    if (e.getX() < 0 || e.getY() < 0) return;
    computeBounds(e.getX(), e.getY());
    repaint();
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
