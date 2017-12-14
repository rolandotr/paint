package paint.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;

/** comment 2007-Feb-12 Trujillo
 * Por el momento se va aconportar como un Poligono, despues podriamos valorar
* si llevar a cabo o no un spline o cualquier otra intepolacion
 */
public class Pen extends Figure{


  protected Vector points;
  protected int x,y;
  /** comment 2007-Feb-12 Trujillo
   * Bueno, el constructor de poligono no va a ser con un conjunto de puntos por defecto
   * porque a medida que se vaya creando se le añaden los ptos, por supuesto tambien puede
   * haber uno por defecto
   */
  public Pen(BufferedImage image){
    super(image);
  }

//  public void setPen(int x, int y) {
//    if (x < 0) throw new IllegalArgumentException("x == "+x+" < 0");
//    if (y < 0) throw new IllegalArgumentException("y == "+y+" < 0");
//    this.x = x;
//    this.y = y;
//    points = new Vector();
//    points.add(new Point(x,y));
//  }

  public void paint(Graphics g) {
    g.setColor(color);
    if (points == null) return;
    Enumeration e = points.elements();
    Point last = null;
    if (e.hasMoreElements())
      last = (Point)e.nextElement();
    else return;
    Point next;
    while(e.hasMoreElements()){
      next = (Point)e.nextElement();
      g.drawLine(last.x, last.y, next.x, next.y);
      last = next;
    }
  }

  public void addPoint(Point p){
    if (points == null){
      this.x = p.x;
      this.y = p.y;
      points = new Vector();
      points.add(p);
    }
    else{
      points.add(p);
    }
  }
  public void addPoint(Point p, Graphics g){
    Point p1;
    if (points.size() > 0)
      p1 = (Point)points.lastElement();
    else{
      addPoint(p);
      return;
    }
    g.drawLine(p1.x, p1.y, p.x, p.y);
    points.add(p);
//    System.out.println(p1.x+","+ p1.y+" to "+ p.x +","+ p.y);
  }

  public void mouseDragged(MouseEvent e) {
    addPoint(new Point(e.getX(), e.getY()));
    repaint();
//    if (tool_listener != null)
//      tool_listener.changeState();
  }

  public void mouseMoved(MouseEvent e) {
    //nada que hacer
  }

  public void mouseClicked(MouseEvent e) {
    //nada que hacer
  }

  public void mousePressed(MouseEvent e) {
    addPoint(new Point(e.getX(), e.getY()));
  }

  public void mouseReleased(MouseEvent e) {
    tool_listener.imageChange();
    finish();
  }

  public void mouseEntered(MouseEvent e) {
    //nada que hacer
  }

  public void mouseExited(MouseEvent e) {
    //nada que hacer
  }

  public void reset() {
    points = null;
    x = 0;
    y = 0;
  }

  public void finish() {
    paintImage();
    if (tool_listener != null)
      tool_listener.closed();
  }

}
