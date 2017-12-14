package paint.gui;

import java.awt.event.*;
import java.awt.image.*;
import java.util.Vector;
import java.awt.Point;

public class Brush extends ImageTool{


  public Brush(BufferedImage image) {
    super(image);
  }

  public void mouseDragged(MouseEvent e) {
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    Vector v = new Vector();
    v.addElement(new Point(e.getX(), e.getY()));
    paint(v, image.getRGB(e.getX(), e.getY()));
    assert(tool_listener != null) : "assert(tool_listener != null)";
    tool_listener.closed();
    tool_listener.imageChange();
  }

  private void paint(Vector points, int rgb_ini) {
    Point tmp = null;
    int x = -1;
    int y = -1;
    int rgb = 0;
    while (!points.isEmpty()){
      tmp = (Point)points.firstElement();
      points.remove(0);
      x = tmp.x;
      y = tmp.y;
      if (x < 0 || x >= image.getWidth() || y < 0 || y >= image.getHeight()) continue;
      rgb = image.getRGB(x, y);
      if (rgb != rgb_ini) continue;
      //condicion de parada
      //seteamos el color
      image.setRGB(x, y, color.getRGB());
      //ahora nos tiramos por todos los puntos cardinales despues de pintar
      points.addElement(new Point(x + 1, y));
      points.addElement(new Point(x, y + 1));
      points.addElement(new Point(x, y - 1));
      points.addElement(new Point(x - 1, y));
    }
  }


  private void paintQuadrant(int x, int y){
    if (x < 0 || x > image.getWidth() || y < 0 || y > image.getHeight()) return;
    int rgb = image.getRGB(x, y);
    do{
      image.setRGB(x, y, color.getRGB());
      x += 1;
      rgb = image.getRGB(x, y);
    }while (rgb != color.getRGB());
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void finish() {
    //aqui no hay nada que hacer ya todo esta sobre la imagen
  }

  public void reset() {
  }

}
