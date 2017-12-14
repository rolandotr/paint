package paint.gui;

import java.io.*;
import java.util.*;
import javax.imageio.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class Frame extends JFrame {
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileOpen = new JMenuItem();
  JMenuItem jMenuFileNew = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  StatusBar statusBar = new StatusBar();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel central = new JPanel();
  JPanel tools = new JPanel();
  SelecterColorPane colors = new SelecterColorPane();
  JPanel marco = new JPanel();
  JScrollPane sc_marco = new JScrollPane();
  JButton jb_brocha = new JButton(new ImageIcon(".//images/brocha.PNG"));
  JButton jb_pen = new JButton(new ImageIcon(".//images/pen01.PNG"));
  JButton jb_circle = new JButton(new ImageIcon(".//images/circle01.PNG"));
  JButton jb_rectangle = new JButton(new ImageIcon(".//images/rectangle.PNG"));
  JButton jb_zoom = new JButton(new ImageIcon(".//images/zoom.PNG"));
  JButton jb_poligono = new JButton(new ImageIcon(".//images/Poligono01.JPG"));
  JButton jb_line = new JButton(new ImageIcon(".//images/Line01.JPG"));
  JButton jb_cutRect = new JButton(new ImageIcon(".//images/cut.PNG"));
  JButton jb_spray = new JButton(new ImageIcon(".//images/spray.PNG"));
  JPanel jpToolOptions = new JPanel();

  JPanelEsp panel_esp;
  boolean image_change = false;
  //Construct the frame

  public Frame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(700, 600));
    this.setTitle("Paint");
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileOpen.setMnemonic('O');
    jMenuFileOpen.setText("Open");
      jMenuFileNew.setMnemonic('N');
    jMenuFileNew.setText("New");
    jMenuFileOpen.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileOpen_actionPerformed(e);
      }
    });
    jMenuFileNew.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileNew_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Edit");
    jMenuHelpAbout.setMnemonic('U');
    jMenuHelpAbout.setText("Undo");
    jMenuHelpAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke('Z', java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jMenuItem1.setMinimumSize(new Dimension(0, 0));
    jMenuItem1.setMnemonic('S');
    jMenuItem1.setText("Save");
    jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuFileSave_actionPerformed(e);
      }
    });
    jMenuItem2.setMnemonic('A');
    jMenuItem2.setText("Save As");
    jMenuItem3.setMnemonic('R');
    jMenuItem3.setText("Redo");
    jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke('X', java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem3_actionPerformed(e);
      }
    });
    jMenu1.setText("Image");
    jMenuItem4.setText("Expand or Contract");
    jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem4_actionPerformed(e);
      }
    });
    jMenuItem5.setText("Attributes");
    jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem5_actionPerformed(e);
      }
    });
    jMenuItem6.setText("Erase Image");
    jMenu2.setText("Help");
    jMenuItem7.setText("About Us");
    jMenuItem8.setMnemonic('E');
    jMenuItem8.setText("Exit");


    jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jMenuItem8_actionPerformed(e);
      }
    });
    jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        MenuItem6_actionPerformed(e);
      }
    });
    jMenuFile.add(jMenuFileNew);
    jMenuFile.add(jMenuFileOpen);
    jMenuFile.add(jMenuItem1);
    jMenuFile.add(jMenuItem2);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuItem8);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuHelp.add(jMenuItem3);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuHelp);
    jMenuBar1.add(jMenu1);
    jMenuBar1.add(jMenu2);
    this.setJMenuBar(jMenuBar1);
    //central.setBackground(Color.black);
    //marco.setBackground(Color.DARK_GRAY);
    marco.setLayout(new GridLayout(1,1));
//    marco.setLayout(new FlowLayout(FlowLayout.LEFT));
    marco.setBorder(BorderFactory.createLoweredBevelBorder());
    //sc_marco = new JScrollPane(marco);
    //sc_marco.setPreferredSize(new Dimension(getWidth(), getHeight()));
    central.setLayout(new BorderLayout());
    initTools();
    central.add(tools, BorderLayout.WEST);
    central.add(colors, BorderLayout.SOUTH);
    central.add(marco, BorderLayout.CENTER);
    contentPane.add(central, BorderLayout.CENTER);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    jMenu1.add(jMenuItem4);
    jMenu1.add(jMenuItem5);
    jMenu1.add(jMenuItem6);
    jMenu2.add(jMenuItem7);
    oleo = new Scheme(300, 300);
    oleo.addImageChangeListener(new ImageChangeListener(){
      public void imageChangeEvent() {
        image_change = true;
      }
    });
    oleo.setColor(colors.getColor());
    oleo.setPaintTool(Scheme.Tool.PEN);
    oleo.setMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent e) {
        statusBar.setPosition(e.getX(), e.getY());
      }

      public void mouseMoved(MouseEvent e) {
        statusBar.setPosition(e.getX(), e.getY());
      }
    });
    this.addComponentListener(new ComponentAdapter(){
      public void componentResized(ComponentEvent e) {
        System.out.println("marco "+marco.getWidth()+", "+marco.getHeight());
        System.out.println("sc_marco "+sc_marco.getWidth()+", "+sc_marco.getHeight());
        System.out.println("oleo "+oleo.getWidth()+", "+oleo.getHeight());
//        sc_marco.setPreferredSize(new Dimension(marco.getWidth(), marco.getHeight()));
//        sc_marco.revalidate();
      }
    });
    marco.removeAll();
    //sc_marco.setPreferredSize(new Dimension(getWidth(), getHeight()));
    //sc_marco.setPreferredSize(new Dimension(200,200));
    //sc_marco.setBorder(BorderFactory.createEmptyBorder());
    //sc_marco.setLayout(new FlowLayout(FlowLayout.LEFT));
    panel_esp = new JPanelEsp(oleo);
    sc_marco.getViewport().add(panel_esp, null);
    marco.add(sc_marco);
    marco.revalidate();
  }

  public class JPanelEsp extends JPanel{

    protected Scheme oleo;

    public JPanelEsp(Scheme oleo){
      this.oleo = oleo;
      setLayout(new FlowLayout(FlowLayout.LEFT));
      add(oleo);
    }

    public Dimension getPreferredSize(){
      return oleo.getPreferredSize();
    }

    public void zoom(int zoom){
      setSize(getWidth()*zoom, getHeight()*zoom);
      setPreferredSize(new Dimension(getWidth(), getHeight()));
    }

    public void setOleo(Scheme oleo){
      removeAll();
      this.oleo = oleo;
      add(oleo);
      revalidate();
    }
  }

  private int height = 300;
  private int weight = 200;
  private Scheme oleo;
  JMenuItem jMenuItem1 = new JMenuItem();
  JMenuItem jMenuItem2 = new JMenuItem();
  JMenuItem jMenuItem3 = new JMenuItem();
  JMenu jMenu1 = new JMenu();
  JMenuItem jMenuItem4 = new JMenuItem();
  JMenuItem jMenuItem5 = new JMenuItem();
  JMenuItem jMenuItem6 = new JMenuItem();
  JMenu jMenu2 = new JMenu();
  JMenuItem jMenuItem7 = new JMenuItem();
  JMenuItem jMenuItem8 = new JMenuItem();

  private void newOleo(BufferedImage image){
    PaintTool last_tool = oleo.getPaintTool();
    oleo = new Scheme(image);
    oleo.addImageChangeListener(new ImageChangeListener(){
      public void imageChangeEvent() {
        image_change = true;
      }
    });
    oleo.setColor(colors.getColor());
    oleo.setPaintTool(last_tool);
    oleo.setMouseMotionListener(new MouseMotionListener(){
      public void mouseDragged(MouseEvent e) {
        statusBar.setPosition(e.getX(), e.getY());
      }

      public void mouseMoved(MouseEvent e) {
        statusBar.setPosition(e.getX(), e.getY());
      }
    });
    panel_esp.setOleo(oleo);
//    sc_marco.getViewport().add(oleo, null);
    //sc_marco.setBounds(4,4, 400, 400);
//    sc_marco.revalidate();
//    marco.add(oleo);
//    oleo.setBounds(4,4,oleo.getWidth(), oleo.getHeight());
//    marco.repaint();
//    marco.revalidate();
}

  void jMenuFileNew_actionPerformed(ActionEvent e){
    if (image_change) {
      JOptionPane p = new JOptionPane();
      int answer = p.showOptionDialog(this, "Image has been change, would you like to save it", "Image Change", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
      System.out.println(JOptionPane.OK_OPTION);
      System.out.println(JOptionPane.CANCEL_OPTION);
      System.out.println(JOptionPane.OK_CANCEL_OPTION);
      System.out.println(p.getOptions());
      if ((answer == JOptionPane.OK_OPTION))
        jMenuFileSave_actionPerformed(e);
      else if ((answer == JOptionPane.CANCEL_OPTION))
        return;
      image_change = false;
    }
    NewDialog dialog = new NewDialog(this, "New Image", true);
    dialog.setLocation(getLocation().x + getWidth()/3, getLocation().y + getHeight()/3);
    dialog.setVisible(true);
    if (dialog.accept()){
      BufferedImage image = new BufferedImage(dialog.getWidth(), dialog.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = image.createGraphics();
      Color last = g.getColor();
      g.setColor(Color.white);
      g.fillRect(0, 0, dialog.getWidth(), dialog.getHeight());
      g.setColor(last);
      newOleo(image);
    }
  }
  private void initTools() {
    tools.setLayout(new GridBagLayout());
    tools.setBorder(BorderFactory.createEtchedBorder());
    jb_brocha.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.BRUSH);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_circle.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.CIRCLE);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_line.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.LINE);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_pen.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.PEN);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_poligono.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.POLYGON);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_rectangle.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.RECTANGLE);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_cutRect.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.CUT_RECT);
        oleo.zoom(oleo.ZOOM_1X);
      }
    });
    jb_spray.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        oleo.closePaintTool();
        oleo.setPaintTool(Scheme.Tool.SPRAY);
        oleo.zoom(oleo.ZOOM_1X);
        jpToolOptions.removeAll();
        jpToolOptions.add(new SprayWidth((Spray)oleo.getPaintTool()));
        jpToolOptions.revalidate();
        validate();
      }
    });
    jb_zoom.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        /** comment 2007-Feb-12 Trujillo
         * Tienes que pensar bien, porque hay algunas cosas que no son figuras
         * como es el caso de la brocha, esto seria otra cosa, porque ni siquiera
         * tiene que trabajar sobre la imagen. Por tanto este tipo de herramienta
         * debe caer en otra calificacion
         */
        oleo.closePaintTool();
        jpToolOptions.removeAll();
        jpToolOptions.add(new ZoomWidth(oleo));
        jpToolOptions.revalidate();
        validate();
      }
    });
    JPanel jpButtons = new JPanel();
    jpButtons.setLayout(new GridLayout(9, 1));
    jpButtons.add(jb_brocha);
    jpButtons.add(jb_pen);
    jpButtons.add(jb_spray);
    jpButtons.add(jb_circle);
    jpButtons.add(jb_rectangle);
    jpButtons.add(jb_zoom);
    jpButtons.add(jb_poligono);
    jpButtons.add(jb_line);
    jpButtons.add(jb_cutRect);
    jpToolOptions.setBorder(BorderFactory.createLoweredBevelBorder());
    jpToolOptions.setPreferredSize(new Dimension(60, 100));
    jpToolOptions.setMinimumSize(new Dimension(60, 100));
    tools.add(jpButtons, new GridBagConstraints(0,0,1,1,0.5,0.5, GridBagConstraints.NORTH, GridBagConstraints.BOTH, new Insets(0,0,2,0), 0, 0));
    tools.add(jpToolOptions, new GridBagConstraints(0,1, 1, 1, 0.1, 0.1, GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(2, 0, 4, 0), 0, 0));
    //tools.add(Box.createVerticalGlue());
  }

  //File | Exit action performed
  public void jMenuFileOpen_actionPerformed(ActionEvent e) {
    if (image_change) {
      JOptionPane p = new JOptionPane();
      int answer = p.showOptionDialog(this, "Image has been change, would you like to save it", "Image Change", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
      System.out.println(JOptionPane.OK_OPTION);
      System.out.println(JOptionPane.CANCEL_OPTION);
      System.out.println(JOptionPane.OK_CANCEL_OPTION);
      System.out.println(p.getOptions());
      if ((answer == JOptionPane.OK_OPTION))
        jMenuFileSave_actionPerformed(e);
      else if ((answer == JOptionPane.CANCEL_OPTION))
        return;
      image_change = false;
    }
    JFileChooser chooser = new JFileChooser();
    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
    // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
    ImageFileFilter filter = new ImageFileFilter();
    chooser.setFileFilter(filter);
    int returnVal = chooser.showOpenDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      last_directory = chooser.getCurrentDirectory();
      File selected = chooser.getSelectedFile();
      BufferedImage image = null;
      try {
        image = ImageIO.read(selected);
      } catch (IOException ex) {
        JOptionPane p = new JOptionPane();
        p.showMessageDialog(this, "Loading image fail..", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
      newOleo(image);
    }
  }

  public void jMenuFileSave_actionPerformed(ActionEvent e) {
    JFileChooser chooser = new JFileChooser();
    // Note: source for ExampleFileFilter can be found in FileChooserDemo,
    // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
    chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
    chooser.setFileFilter(new FileFilter(){
      public boolean accept(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase().equals("jpg");
            };
        }
        return false;
      }

      public String getDescription() {
        return "jpg";
      }
    });
    chooser.setFileFilter(new FileFilter(){
      public boolean accept(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase().equals("png");
            };
        }
        return false;
      }

      public String getDescription() {
        return "png";
      }
    });
    int returnVal = chooser.showSaveDialog(this);
    if(returnVal == JFileChooser.APPROVE_OPTION) {
      last_directory = chooser.getCurrentDirectory();
      File selected = chooser.getSelectedFile();
      String ext = chooser.getFileFilter().getDescription();
      File f = new File(selected.getAbsolutePath()+"."+ext);
      BufferedImage img = oleo.getImage();
      try {
        ImageIO.write(img, ext, f);
      } catch (IOException ex) {
        JOptionPane p = new JOptionPane();
        p.showMessageDialog(this, "Writing image fail..", "Error", JOptionPane.ERROR_MESSAGE);
        return;
      }
    }
  }

  protected File last_directory;

  public class ImageFileFilter extends FileFilter{

    Hashtable filters = new Hashtable();

    public ImageFileFilter(){
      filters.put("jpg", this);
      filters.put("gif", this);
      filters.put("png", this);
    }

    public boolean accept(File f) {
        if(f != null) {
            if(f.isDirectory()) {
                return true;
            }
            String extension = getExtension(f);
            if(extension != null && filters.get(getExtension(f)) != null) {
                return true;
            };
        }
        return false;
    }

     public String getExtension(File f) {
        if(f != null) {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if(i>0 && i<filename.length()-1) {
                return filename.substring(i+1).toLowerCase();
            };
        }
        return null;
    }

    public String getDescription() {
      return "JPG & GIF & PNG Files";
    }
  }

  //Help | About action performed
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    oleo.undo();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
//    if (e.getID() == WindowEvent.WINDOW_STATE_CHANGED) {
//      System.out.println(marco.getWidth()+","+ marco.getHeight());
//      sc_marco.setPreferredSize(new Dimension(marco.getWidth(), marco.getHeight()));
//    }
  }

  class StatusBar extends JPanel{

    JLabel text = new JLabel();
    JLabel position = new JLabel();

    public StatusBar(){
      //setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      setLayout(new GridBagLayout());
      setBorder(BorderFactory.createLoweredBevelBorder());
//      text.setBorder(BorderFactory.createRaisedBevelBorder());
//      position.setBorder(BorderFactory.createRaisedBevelBorder());
      position.setPreferredSize(new Dimension(30, 15));
      position.setMinimumSize(new Dimension(20, 10));
      position.setMaximumSize(new Dimension(50, 20));
//      add(position);
//      add(text);
      JPanel tmp = new JPanel();
      tmp.setBorder(BorderFactory.createRaisedBevelBorder());
//      text.setBorder(BorderFactory.createLineBorder(Color.BLUE));
//      position.setBorder(BorderFactory.createLineBorder(Color.red));
      tmp.setPreferredSize(new Dimension(2,20));
      position.setHorizontalAlignment(JLabel.CENTER);
      add(text, new GridBagConstraints(0,0,1,1, 0.5,0.5,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,0,0,0),10,10));
      add(tmp, new GridBagConstraints(1,0,1,1, 0.1,0.1,GridBagConstraints.EAST, GridBagConstraints.VERTICAL, new Insets(0,0,0,0),0,0));
      add(position, new GridBagConstraints(2,0,1,1, 0.1,0.1,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,0,0,0),0,0));

    }

    public void setText(String text){
      this.text.setText(text);
    }

    public void setPosition(int x, int y){
      position.setText(x+", "+y);
    }

  }

  public class SelecterColorPane extends JPanel{

    public final Color DEFAULT_COLOR = Color.black;
    protected JPanel selected_color = new JPanel();
    protected JPanel colors = new ColorsPane();
    protected Color color = DEFAULT_COLOR;

    public SelecterColorPane() {
      setLayout(new GridBagLayout());
      selected_color.setBorder(BorderFactory.createLoweredBevelBorder());
      selected_color.setPreferredSize(new Dimension(40,30));
      add(selected_color, new GridBagConstraints(0,0,1,1, 0.1,0.1,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4,4,4,4),0,0));
      add(colors, new GridBagConstraints(1,0,1,1, 1,1,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(4,4,4,4),0,0));
      add(new JPanel(), new GridBagConstraints(2,0,1,1, 1.5,1.5,GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,0,0,0),0,0));

    }


    public class ColorsPane extends JPanel{

      public ColorsPane(){
        setLayout(new GridLayout(2, 33, 1, 1));
        int inc = 70;
//      int cont = 0;
        int ini = 30;
        for (int i = 20; i <= 255; i+=inc) {
          for (int j = 20; j <= 255; j+=inc) {
            for (int k = 20; k <= 255; k+=inc) {
              add(new ColorPane(new Color(i, j, k)));
//            cont++;
            }
          }
        }
//      System.out.println(cont);
        add(new ColorPane(new Color(255,255,255)));
        add(new ColorPane(new Color(0,0,0)));
      }

    }

    private void setColor(Color c){
      selected_color.setBackground(c);
      color = c;
      oleo.closePaintTool();
      oleo.setColor(color);
    }

    public Color getColor(){
      return color;
    }

    public class ColorPane extends JPanel{

      Color color;

      public ColorPane(Color c){
        setBorder(BorderFactory.createLoweredBevelBorder());
        setPreferredSize(new Dimension(15,15));
        color = c;
        setBackground(c);
        addMouseListener(new MouseAdapter(){
          public void mouseClicked(MouseEvent e) {
            mouseClickAction(e);
          }
        });
        //setPreferredSize(new Dimension(20,20));
      }
      void mouseClickAction(MouseEvent e){
        if (e.getButton() != e.BUTTON1) return;
        if (e.getClickCount() == 1)
          setColor(color);
        else {
          JColorChooser chooser = new JColorChooser(color);
          Color new_color = chooser.showDialog(this, "Choose Color", color);
          if (new_color == null) return;
          color = new_color;
          changeColor();
          setColor(color);
        }
      }
      void changeColor(){
        setBackground(color);
      }

    }
  }

  public class SprayWidth extends JPanel{


    public SprayWidth(Spray tool2){
      assert(tool2 != null) : "assert(tool != null)";
      final Spray tool = tool2;
      setLayout(new GridLayout(3, 1));
      JLabel tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.setWidth(Spray.SMALL);
        }
      });
      tmp.setIcon(new ImageIcon(".//images/spray/small.JPG"));
      add(tmp);
      tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.setWidth(Spray.MEDIUN);
        }
      });
      tmp.setIcon(new ImageIcon(".//images/spray/mediun.JPG"));
      add(tmp);
      tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.setWidth(Spray.BIG);
        }
      });
      tmp.setIcon(new ImageIcon(".//images/spray/big.JPG"));
      add(tmp);
    }
  }

  private PaintTool last_tool;

  public class ZoomWidth extends JPanel{

    protected Scheme tool;

    public ZoomWidth(Scheme oleo){
      assert(oleo != null) : "assert(tool != null)";
      tool = oleo;
      last_tool = oleo.getPaintTool();
      tool.setPaintTool(Scheme.Tool.EMPTY_TOOL);
      setLayout(new GridLayout(4, 1));
      JLabel tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.zoom(Scheme.ZOOM_1X);
        }
      });
      tmp.setText("1x");
      add(tmp);
      tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.zoom(Scheme.ZOOM_2X);
          panel_esp.removeAll();
          panel_esp.setOleo(tool);
          panel_esp.revalidate();
        }
      });
      tmp.setText("2x");
      add(tmp);
      tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.zoom(Scheme.ZOOM_4X);
        }
      });
      tmp.setText("4x");
      add(tmp);
      tmp = new JLabel();
      tmp.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent e) {
          tool.zoom(Scheme.ZOOM_8X);
        }
      });
      tmp.setText("8x");
      add(tmp);
    }
  }

  void jMenuItem8_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  void jMenuItem3_actionPerformed(ActionEvent e) {
    oleo.redo();
  }

  void jMenuItem5_actionPerformed(ActionEvent e) {
    NewDialog dialog = new NewDialog(this, "Change Image Atributtes", true);
    dialog.setLocation(getLocation().x + getWidth()/3, getLocation().y + getHeight()/3);
    dialog.setVisible(true);
    if (dialog.accept()){
      BufferedImage image = new BufferedImage(dialog.getWidth(), dialog.getHeight(), BufferedImage.TYPE_INT_ARGB);
      Graphics g = image.createGraphics();
      Color last = g.getColor();
      g.setColor(Color.white);
      g.fillRect(0, 0, dialog.getWidth(), dialog.getHeight());
      g.drawImage(oleo.getImage(), 0, 0, null);
      g.setColor(last);
      newOleo(image);
      repaint();
    }
  }

  private void MenuItem6_actionPerformed(ActionEvent e){
    oleo.eraseImage();
  }

  void jMenuItem4_actionPerformed(ActionEvent e) {
    TransparencyDialog dialog = new TransparencyDialog(this, "Transparency", false, oleo);
    Point p = getLocation();
    dialog.setLocation(p.x + (getWidth()/3), p.y + (getHeight()/3));
    dialog.setVisible(true);
  }
}
