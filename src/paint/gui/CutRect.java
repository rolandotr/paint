package paint.gui;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.awt.Image;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.image.WritableRaster;

public class CutRect extends RectangleDefine{

  public CutRect(BufferedImage image) {
    super(image);
  }

  protected boolean move = false;
  protected boolean first_move = true;

  protected int x_now;
  protected int y_now;

  public static final int STEP = 5;//representa la cantidad de pixels para la discontinuidad del rect

  protected BufferedImage piece;
  protected boolean couldDrag = false;

  protected void paintImage(){//es necesario redefinir esto aqui
    if (move && couldDrag){
      Graphics g = image.createGraphics();
      g.setColor(color);
      paint(g);
    }
  }

  public void paint(Graphics g) {
    if (move && couldDrag){
//      g.setColor(color.white);
//      g.fillRect(x,y,w,h);
//      g.drawImage(piece, x_now, y_now, null);
      /** comment 2007-Mar-06 Trujillo
       * @todo Preguntar por que esto
       * Es increible pero esto de aqui arriba no funciona como se espera pone dos rectangulos
       * en distintas posiciones, pero ambos blanco completamentes, sin embargo si intercambiamos
       * las sentencias 2 y 3, entonces si se dinuja la imagen correctamente, Por que?
       *
       */
      /** comment 2007-Mar-06 Trujillo
       * Hombre despues de 4 horas encuantras la respuesta, el probvlea es que la imagen
       * que te devuelve image de una region de ella es una referencia, de modo que cualquier
       * cambio que se en ella afecta tambien a est
       */
      g.setColor(color.white);
      g.fillRect(x,y,w,h);
       g.drawImage(piece, x_now, y_now, null);
    }
    else{
      g.setColor(color.black);
      for (int i = x; i < x+w; i+=2*STEP){
        g.drawLine(i, y, i+STEP, y);
      }
      for (int i = x; i < x+w; i+=2*STEP){
        g.drawLine(i, y+h, i+STEP, y+h);
      }
      for (int i = y; i < y+h; i+=2*STEP){
        g.drawLine(x, i, x, i+STEP);
      }
      for (int i = y; i < y+h; i+=2*STEP){
        g.drawLine(x+w, i, x+w, i+STEP);
      }
    }
  }

  public void mouseDragged(MouseEvent e) {
    if (move && couldDrag){//entonces hay que mover la imagen
      x_now = e.getX();
      y_now = e.getY();
      repaint();
    }
    else{//hay que seleccionar
      computeBounds(e.getX(), e.getY());
      repaint();
    }
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
    if (move){
      if (e.getX() >= x && e.getX() <= x+w && e.getY() >= y && e.getY() <= y+h)
        couldDrag = true;
    }
    else{
      setReferencePoint(e.getX(), e.getY());
    }
  }

  public void mouseReleased(MouseEvent e) {
    if (move){//entonces hay que mover la imagen
      tool_listener.imageChange();
      finish();
    }
    else{//hay que seleccionar
      piece = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
      BufferedImage tmp = image.getSubimage(x,y,w,h);
//      image.getSubimage(x,y,w,h).setData(piece.getRaster());
//    piece.setData(image.getSubimage(x,y,w,h).getRaster());
      WritableRaster w = tmp.copyData(piece.getRaster());
      piece = new BufferedImage(image.getColorModel(), w, image.isAlphaPremultiplied(), null);
//      for (int i = 0; i < tmp.getWidth(); i++){
//        for (int j = 0; j < tmp.getHeight(); j++) {
//          piece.setRGB(i,j,tmp.getRGB(i,j));
//        }
//      }
      move = true;
    }
  }

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
    move = false;
    couldDrag = false;
  }

}
