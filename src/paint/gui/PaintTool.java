package paint.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;

/** comment 2007-Feb-12 Trujillo
 * Esta sera la clase padre de todas las herramientas que influyen en la pintura
 */

/** @todo Definir aqui el metodo mouse move, para avisarle al frame y que lo ponga en
* el status bar */
public abstract class PaintTool extends JPanel implements MouseListener, MouseMotionListener{

  public static final Color DEFAULT_COLOR = Color.black;
  protected Color color = DEFAULT_COLOR;
  /** comment 2007-Feb-12 Trujillo
   * Esta escucha es para que el Painting se enter de cuando se termina de pintar y
   * por tanto se puede proceder a consumar lo pintrado en el cuadro. De modo que
   * es de real importancia que todos los tengan en cuentan y avicen con tiempo.
   */
  protected PaintToolListener tool_listener;

  /** comment 2007-Mar-06 Trujillo
   * Esta es la imagen sobre la cual trabajaran las herramientas
   */
  protected BufferedImage image;

  public PaintTool(BufferedImage image) {
    if (image == null) throw new NullPointerException("image == null");
    this.image = image;
    addMouseListener(this);
    addMouseMotionListener(this);
    //setBorder(BorderFactory.createLoweredBevelBorder());
    setOpaque(false);
  }


  public void setColor(Color c){
    color = c;
  }

  public void addFigureListener(PaintToolListener listener){
    if (listener == null) throw new NullPointerException("listener == null");
    tool_listener = listener;
  }

  /** comment 2007-Feb-12 Trujillo
   * Es para resetear al instrumento
   */
  public abstract void reset();

  /** comment 2007-Feb-12 Trujillo
   * Mediante este metodo se obliga a terminar a la figura
   */
  public abstract void finish();

  public BufferedImage getImage() {
    return image;
  }

  public void setImage(BufferedImage image) {
    this.image = image;
  }

  protected int zoom = 1;

  public void zoom(int zoom){
    /** comment 2006-Sep-17 Trujillo
     * Aqui no voy a validar nada porque en definitiva este es cliente del Scheme y alli esta
     * validado
     */
    this.zoom = zoom;
  }

}
