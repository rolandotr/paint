package paint.gui;

import java.awt.Graphics;
import java.util.Vector;
import java.awt.Point;
import java.util.Enumeration;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** comment 2007-Feb-12 Trujillo
 * Esta clase por su forma de construccion, no tiene forma de saber cuando esta cerrada, de modo
 * alguien les tiene que decir a la figura que se cierre. Pero la mejor variante y es la que voy a
* adoptar, sera cuando se de click en el punto de inicio o en una vencindad
 */
public class Polygon extends Figure{

  protected Vector points;
  protected int x,y;
  public static final int NEAREST = 5;//5 PIXELS DE DISTANCIA NOS PARECE BASTANTE CERCANO
  /** comment 2007-Feb-12 Trujillo
   * Bueno, el constructor de poligono no va a ser con un conjunto de puntos por defecto
   * porque a medida que se vaya creando se le añaden los ptos, por supuesto tambien puede
   * haber uno por defecto
   */
  public Polygon(BufferedImage image){
    super(image);
  }

  public void setPolygon(int x, int y) {
    if (x < 0) throw new IllegalArgumentException("x == "+x+" < 0");
    if (y < 0) throw new IllegalArgumentException("y == "+y+" < 0");
    this.x = x;
    this.y = y;
    points = new Vector();
    points.add(new Point(x,y));
  }

  public void setPolygon(Point[] ptos){
    if (ptos == null) throw new NullPointerException("ptos == null");
    points = new Vector();
    for (int i = 0; i < ptos.length; i++) {
      if (ptos[i] == null) throw new IllegalArgumentException("ptos["+i+"]=null");
      if (ptos[i].getX() < 0) throw new IllegalArgumentException("ptos[i].x == "+ptos[i].x+" < 0");
      if (ptos[i].getY() < 0) throw new IllegalArgumentException("ptos[i].getY() == "+ptos[i].getY()+" < 0");
      points.add(ptos[i]);
    }
  }

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
    if (p == null) throw new NullPointerException("p == null");
    if (p.x < 0) throw new IllegalArgumentException("p.x == "+p.x+" < 0");
    if (p.y < 0) throw new IllegalArgumentException("p.y == "+p.y+" < 0");
    if (points == null) setPolygon(p.x, p.y);
    points.add(p);
  }

  public void paintLastLine(Graphics g){
    Point p1 = (Point)points.get(points.size()-2);
    Point p2 = (Point)points.get(points.size()-1);
    g.drawLine(p1.x, p1.y, p2.x, p2.y);
  }

  public void mouseDragged(MouseEvent e) {
    //nada que hacer
  }

  public void mouseMoved(MouseEvent e) {
    //nada que hacer
  }

  public void mouseClicked(MouseEvent e) {
    if (points != null && points.size() > 2){
      Point first = (Point)points.firstElement();
      if (Math.abs(first.x-e.getX()) < NEAREST && Math.abs(first.y-e.getY()) < NEAREST ){
        /** comment 2007-Feb-12 Trujillo
         * En este caso ya se cerro el poligono por tanto ya terminamos
         */
        addPoint(first);
        finish();
        return;
      }
    }
    addPoint(new Point(e.getX(), e.getY()));
    repaint();
//    tool_listener.changeState();
  }

  public void mouseEntered(MouseEvent e) {
    //nada que hacer
  }

  public void mouseExited(MouseEvent e) {
    //nada que hacer
  }

  public void mousePressed(MouseEvent e) {
    //nada que hacer
  }

  public void mouseReleased(MouseEvent e) {
    //nada que hacer
  }

  public void reset() {
    points = null;
    x = 0;
    y = 0;
  }

  public void finish() {
    if (points != null) {
      if (points.size() == 1) {
        points = null;
      }
      else{
        Point first = (Point)points.firstElement();
        addPoint(first);
        tool_listener.imageChange();
      }
    }
    paintImage();
    tool_listener.closed();
  }

}
