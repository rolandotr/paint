package paint.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.Stack;

/** comment 2007-Feb-12 Trujillo
 * Esta clase es como el marco, es decir ella va crear un lienzo para que se pinten
* las figuras y este lienzo avisara cuando es que esta figura debe ser puesta en
* el cuadro como tal. De modo que es bastante pasiva solo tiene que encargarce del
* lienzo y de cambiar la imagen que es donde se va a guardar la pintura.
 */
public class Scheme extends JPanel implements PaintToolListener{

  protected PaintTool tool;
  protected BufferedImage image;
  public static final Color DEFAULT_COLOR = Color.black;
  protected Color color = DEFAULT_COLOR;

  public Scheme(BufferedImage image) {
    if (image == null) throw new NullPointerException("image == null");
    this.image = image;
    images_undo.push(copyImage(image));
//    this.image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
//    BufferedImage tmp = image.getSubimage(0, 0, image.getWidth(), image.getHeight());
////      image.getSubimage(x,y,w,h).setData(piece.getRaster());
////    piece.setData(image.getSubimage(x,y,w,h).getRaster());
//    for (int i = 0; i < tmp.getWidth(); i++) {
//      for (int j = 0; j < tmp.getHeight(); j++) {
//        image.setRGB(i, j, tmp.getRGB(i, j));
//      }
//    }
    setSize(image.getWidth(), image.getHeight());
    setPreferredSize(new Dimension(image.getWidth(),image.getHeight()));
    setLayout(new GridLayout(1,1));//para poder poner el canvas en una posicion absolutas
    this.addMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent e) {
        if (listener != null) listener.mouseDragged(e);
      }

      public void mouseMoved(MouseEvent e) {
        if (listener != null) listener.mouseMoved(e);
      }
    });
  }
  public Scheme(int w, int h) {
    if (w < 0) throw new IllegalArgumentException("w == "+w+" < 0");
    if (h < 0) throw new IllegalArgumentException("h == "+h+" < 0");
    setSize(w,h);
    setPreferredSize(new Dimension(w, h));
    setLayout(new GridLayout(1,1));//para poder poner el canvas en una posicion absolutas
    image = new BufferedImage(w,h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.createGraphics();
    Color last = g.getColor();
    g.setColor(Color.white);
    g.fillRect(0,0,w,h);
//    g.drawImage((new ImageIcon(".//images/ready7.JPG")).getImage(), 20, 20, 100, 100, null);
    g.setColor(last);
    images_undo.push(copyImage(image));
    this.addMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent e) {
        if (listener != null) listener.mouseDragged(e);
      }

      public void mouseMoved(MouseEvent e) {
        if (listener != null) listener.mouseMoved(e);
      }
    });
//    setBorder(BorderFactory.createRaisedBevelBorder());
//    setBackground(Color.blue);
  }

//  public void consummateFigure() {
//    removePainting();
//    tool.paint(image.createGraphics(), color);
//    tool.reset();
//    repaint();
//    addPainting();
//  }
//
//  private void removePainting() {
//    if (canvas ==  null){
//      assert(getComponents().length == 0) : "assert(getComponents().length == 0)";
//      return;
//    }
//    remove(canvas);
//    canvas = null;
//  }

//  public void paint(Graphics g){
//    super.paint(g);
//    g.drawImage(image, 0, 0, null);
//  }
  public void paintComponent(Graphics g){
//System.out.println(getWidth()+","+ getHeight());
    if (zoom == ZOOM_1X){
      System.out.println("si");
      ((Graphics2D)g).drawImage(image, p, 0, 0);
      return;
    }
    g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
  }

//  public void paint(Graphics g){
//    super.paint(g);
//    g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
//  }

  public PaintTool getPaintTool(){
    return tool;
  }

  public void setPaintTool(PaintTool tool){
    if (tool == null) throw new NullPointerException("tool == null");
    tool.reset();
    tool.setImage(image);
    this.tool = tool;
    this.tool.addFigureListener(this);
    addTool();
  }
  public void setPaintTool(Tool tool){
    if (tool == null) throw new NullPointerException("tool == null");
    if (tool == Tool.BRUSH){
      this.tool = new Brush(image);
    }
    else if (tool == Tool.CIRCLE){
      this.tool = new Circle(image);
    }
    else if (tool == Tool.LINE){
      this.tool = new Line(image);
    }
    else if (tool == Tool.LOOP){
//      tool = new Loop(image);
    }
    else if (tool == Tool.PEN){
      this.tool = new Pen(image);
    }
    else if (tool == Tool.POLYGON){
      this.tool = new Polygon(image);
    }
    else if (tool == Tool.RECTANGLE){
      this.tool = new Rectangle(image);
    }
    else if (tool == Tool.CUT_RECT){
      this.tool = new CutRect(image);
    }
    else if (tool == Tool.SPRAY){
      this.tool = new Spray(image);
    }
    else if (tool == Tool.EMPTY_TOOL){
      this.tool = new PaintTool(image){
        public void finish() {
        }

        public void reset() {
        }

        public void mouseDragged(MouseEvent e) {
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
        }

        public void mouseReleased(MouseEvent e) {
        }
      };
    }
    this.tool.addFigureListener(this);
    addTool();
  }

  private void addTool() {
    tool.setColor(color);
    tool.setSize(new Dimension(getWidth(), getHeight()));
    tool.setPreferredSize(new Dimension(getWidth(), getHeight()));
    removeAll();
    add(tool);
    revalidate();
  }

  protected MouseMotionListener listener;

  public void setMouseMotionListener(MouseMotionListener listener) {
//    canvas.setMouseMotionListener(listener);
    this.listener = listener;
  }

  public void setColor(Color color) {
    if (color == null) throw new NullPointerException("color == null");
    this.color = color;
    if (tool != null)
      tool.setColor(color);
  }

//  public PaintTool getPaintTool() {
//    return tool;
//  }

  public void closePaintTool(){
    tool.finish();
    tool.reset();
  }

  public void closed() {
    image = tool.getImage();
    comiteImage();
    tool.reset();
    repaint();
  }

  private ImageChangeListener img_list;

  public void addImageChangeListener(ImageChangeListener img_list){
    if (img_list == null) throw new NullPointerException("img_list == null");
    this.img_list = img_list;
  }

  public BufferedImage getImage(){
    return image;
  }

  public static abstract class Tool{

    public static final Tool LINE = new Tool(){};
    public static final Tool PEN = new Tool(){};
    public static final Tool BRUSH = new Tool(){};
    public static final Tool CIRCLE = new Tool(){};
    public static final Tool POLYGON = new Tool(){};
    public static final Tool RECTANGLE = new Tool(){};
    public static final Tool LOOP = new Tool(){};
    public static final Tool CUT_RECT = new Tool(){};
    public static final Tool SPRAY = new Tool(){};
    public static final Tool EMPTY_TOOL = new Tool(){};;

    private Tool(){
    }
  }

  Stack images_undo = new Stack();
  Stack images_redo = new Stack();

  boolean redo = true;

  public void undo(){
    assert(!images_undo.isEmpty()):"assert(!images_undo.isEmpty())";
    BufferedImage img;
    if (images_undo.size() > 1) {//esto es porque el del fondo es el blanco y nunca se quita
      images_redo.push(images_undo.pop());
    }
    img = (BufferedImage)images_undo.peek();
//    JOptionPane p = new JOptionPane();
//    p.showConfirmDialog(this, "sadf", "sadf", 0, 0, new ImageIcon(img));
    image = copyImage(img);
//    image = img;
    tool.setImage(image);
    repaint();
  }

  public void redo(){
    if (images_redo.isEmpty()) return;
    BufferedImage img = (BufferedImage)images_redo.pop();
    images_undo.push(img);
    image = img;
    tool.setImage(img);
    repaint();
  }

  private BufferedImage copyImage(BufferedImage img){
    BufferedImage piece = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
    WritableRaster w = img.copyData(piece.getRaster());
    piece = new BufferedImage(image.getColorModel(), w, image.isAlphaPremultiplied(), null);
//      for (int i = 0; i < image.getWidth(); i++){
//        for (int j = 0; j < image.getHeight(); j++) {
//          piece.setRGB(i,j,image.getRGB(i,j));
//        }
//      }
    return piece;
  }

  boolean image_change = false;
  public void imageChange() {
    image_change = true;
    img_list.imageChangeEvent();
  }

  private void comiteImage(){
    if (image_change){
      images_undo.push(copyImage(image));
      images_redo = new Stack();
      image_change = false;
    }
  }

  public static final int ZOOM_1X = 1;
  public static final int ZOOM_2X = 2;
  public static final int ZOOM_4X = 4;
  public static final int ZOOM_8X = 8;

  protected int zoom = 1;
  /** comment 2006-Sep-17 Trujillo
   * Al zoom solo le basta con ampliar el area de pintura
   */
  public void zoom(int zoom){
    if (!(zoom == ZOOM_1X || zoom == ZOOM_2X || zoom == ZOOM_4X || zoom == ZOOM_8X))
      throw new IllegalArgumentException("zoom debe ser una de las constantes validas");
    this.zoom = zoom;
    setPreferredSize(new Dimension(image.getWidth()*zoom, image.getHeight()*zoom));
    revalidate();
  }

  public Dimension getPreferredSize(){
    return new Dimension(image.getWidth()*zoom, image.getHeight()*zoom);
  }



  RescaleOp p = new RescaleOp(new float[] {1f, 1f, 1f, 1.0f}
                              , new float[4], null);

  /** comment 2006-Sep-17 Trujillo
   * Bueno esto es para aplicar un filtro sea cual sea. El de la transparencia
   * para ser mas preciso
   */
  public void setOpacity(float f) {
    p = new RescaleOp(new float[] {1f, 1f, 1f, f}
                              , new float[4], null);
  repaint();
  }

  public void eraseImage(){
    if (image_change){
      images_undo.push(copyImage(image));
      images_redo = new Stack();
      image_change = false;
    }
    Graphics g = image.createGraphics();
    Color last = g.getColor();
    g.setColor(Color.white);
    g.fillRect(0,0,image.getWidth(),image.getHeight());
//    g.drawImage((new ImageIcon(".//images/ready7.JPG")).getImage(), 20, 20, 100, 100, null);
    g.setColor(last);
  }
}
