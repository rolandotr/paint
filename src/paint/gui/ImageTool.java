package paint.gui;

import java.awt.image.BufferedImage;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.Graphics;

/** comment 2007-Feb-12 Trujillo
 * Esta clase es para toda aquella funcionalidad que trabaje sobre una imagen,
* digase un tanquecito de pintura, un recorte o lo que sea. Por lo general este tipo
* de transpformaciones no requiere de un canvas que haga sus cosas, pero por ejemplo un recorte
* si lo necesita para que de el efectico de recorte.
 */
public abstract class ImageTool extends PaintTool{


  public ImageTool(BufferedImage image) {
    super(image);
  }

  protected void paintImage(){
    Graphics g = image.createGraphics();
    g.setColor(color);
    paint(g);
  }
}
