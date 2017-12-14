package paint.gui;

import java.awt.Point;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Line extends Polygon{

  public Line(BufferedImage image){
    super(image);
  }

  public void setLine(int x1, int y1, int x2, int y2) {
    setPolygon(new Point[]{new Point(x1,y1), new Point(x2,y2)});
  }

  public void mouseDragged(MouseEvent e) {
    if (e.getX() < 0 || e.getY() < 0) return;//incrible pero puede suceder
    if (points.size() != 1) {
      assert(points.size() == 2) : "assert(points.size() == 2)";
      points.removeElementAt(1);
    }
    addPoint(new Point(e.getX(), e.getY()));
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
    setPolygon(e.getX(), e.getY());
  }

  public void mouseReleased(MouseEvent e) {
    tool_listener.imageChange();
    finish();
  }

  public void finish(){
    paintImage();
    if (tool_listener != null)
      tool_listener.closed();
  }

}
