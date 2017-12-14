package paint.gui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Vector;
import java.awt.Graphics;
import java.util.Enumeration;
import java.awt.Point;
import java.util.Random;
import java.util.Hashtable;

public class Spray extends Figure {

  protected Hashtable points;
  protected int radio;

  public static final int SMALL = 1;
  public static final int MEDIUN = 2;
  public static final int BIG = 3;

  protected PaintWithoutMove paint;
  /** comment 2006-Sep-17 Trujillo
   * Este es el crecimiento geometrico del radio segun el tamaño del punto
   */
  protected static final int RADIO_INC = 5;

  /** comment 2006-Sep-17 Trujillo
   * Esta es mas o menos una medida de cuantos puntos habran en el menor de los
   * radios, de modo que para cualquier radio la cantidad de punto puede ser.
   * cant_points*radio_inc o talves cant_points*radio_inc^2 que concuerda mas con
   * el area del circulo segun el crecimiento del radio
   */
  protected static final int CANT_POINTS = 6;

  public Spray(BufferedImage image) {
    super(image);
    setWidth(MEDIUN);
  }

  public void paint(Graphics g) {
    g.setColor(color);
    if (points == null) return;
    Enumeration e = points.keys();
    Point next;
    while(e.hasMoreElements()){
      next = (Point)e.nextElement();
      g.drawLine(next.x, next.y, next.x, next.y);
    }
  }

  public void addPoint(Point p){
    if (points == null){
      points = new Hashtable();
    }
    Random r = new Random();
    int cont = 0;
    int x;
    int y;
    int rc = (int)Math.pow(radio, 2);
    while (cont < CANT_POINTS*radio){
      x = (r.nextInt(2*radio)-radio);
      y = (r.nextInt(2*radio)-radio);
      if ((int)(Math.pow(x,2)) + (int)(Math.pow(y,2)) < rc) cont++;
      else continue;
      points.put(new Point(p.x+x,p.y+y), new Point(p.x+x,p.y+y));
    }
  }

  public void finish() {
    if (paint != null)
      paint.stopExec();
    paintImage();
    if (tool_listener != null)
      tool_listener.closed();
  }

  public void reset() {
    points = null;
  }

  public void mouseDragged(MouseEvent e) {
    if (paint != null)
      paint.setPoint(e.getX(), e.getY());
    repaint();
  }

  public void mouseMoved(MouseEvent e) {
    if (paint != null)
      paint.pause();
  }

  public void mouseClicked(MouseEvent e) {
    addPoint(new Point(e.getX(), e.getY()));
    repaint();
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
    paint = new PaintWithoutMove(e.getX(), e.getY());
    paint.setPriority(Thread.MAX_PRIORITY);
    paint.start();
    repaint();
  }

  public void mouseReleased(MouseEvent e) {
      tool_listener.imageChange();
    finish();
  }

  public void setWidth(int width) {
    if (width != SMALL && width != MEDIUN && width != BIG )
      throw new IllegalArgumentException("El ancho debe ser uno de los especificados ESTATICOS EN LA CLASE");
    radio = width*RADIO_INC;
  }

  public class PaintWithoutMove extends Thread{

    protected int x, y;
    boolean stop = false;
    boolean pause = false;

    public PaintWithoutMove(int x, int y){
      this.x = x;
      this.y = y;
    }

    public synchronized void setPoint(int x, int y){
      this.x = x;
      this.y = y;
      resumeExec();
    }

    public synchronized void stopExec(){
      stop = true;
    }
    public synchronized void resumeExec(){
      pause = false;
    }

    public synchronized void pause(){
      pause = true;
    }

    public void run(){
      while (!stop){
        if (!pause){
          addPoint(new Point(x, y));
          repaint();
        }
        try {
          Thread.sleep(100);
        } catch (InterruptedException ex) {
        }
      }
    }
  }
}
