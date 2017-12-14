package paint.gui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** comment 2007-Feb-12 Trujillo
 * Esta clase es para representar toda figura que pueda ser definida a traves de un
* rectangulo que a su vez es definido a traves de dos ptos.
 */
public abstract class RectangleDefine extends Figure{

  public RectangleDefine(BufferedImage image){
    super(image);
  }

  protected int x,y, w, h;


  /** comment 2007-Feb-12 Trujillo
   * Este es un metodo solo para el uso de esta clase que calcula segun la entrada
   * de un nuevo punto que define el triangul sus coordenadas
   */
  protected void computeBounds(int a, int b){
//    System.out.println("(x1,y1,x2,y2)"+x1 + " " + y1 + " " + x2 + " " + y2);
    x = (ref_x < a)?ref_x:a;
    w = Math.abs(ref_x-a);
    y = (ref_y < b)?ref_y:b;
    h = Math.abs(ref_y-b);
//    System.out.println("(x,y,w,h)"+x + " " + y + " " + w + " " + h);
  }

  public void finish() {
    paintImage();
    tool_listener.closed();
  }
  protected int ref_x, ref_y;
  protected void setReferencePoint(int x, int y){
    this.x = x;
    this.y = y;
    ref_x = x;
    ref_y = y;
  }

  public void reset() {
    x = 0;
    y = 0;
    w = 0;
    h = 0;
  }
}
