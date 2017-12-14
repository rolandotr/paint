package paint.gui;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Color;
import java.awt.image.BufferedImage;

/** comment 2007-Feb-12 Trujillo
 * Esta clase es para todos los objetos que se van a pintar en el Oleo.
* Tdoas las figura se construiran dinamicamente en el Oleo. Por tanto el unico constructor
* posible sera el vacio y lo necesario para su construccion sera dado a traves de metodos.
 */
public abstract class Figure extends ImageTool {


  public Figure(BufferedImage image) {
    super(image);
  }

  protected void paint(Graphics g, Color color){
    Color last = g.getColor();
    g.setColor(color);
    repaint();
  }

}
