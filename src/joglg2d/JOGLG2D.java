package joglg2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;

import javax.media.opengl.GL;

public class JOGLG2D extends Graphics2D implements Cloneable {
  private final GL gl;

  private final int height;

  protected BasicStroke stroke;

  protected Color color;

  protected Color background;

  protected AffineTransform transform;

  protected Font font;

  protected JOGLPathIterator shapeDrawer;

  public JOGLG2D(GL gl, int height) {
    this.gl = gl;
    this.height = height;
    setStroke(new BasicStroke());
    setColor(Color.BLACK);
    setBackground(Color.BLACK);
    setFont(new Font(null, Font.PLAIN, 10));
    transform = new AffineTransform();
    shapeDrawer = new JOGLPathIterator(gl);
  }

  protected void paint(Component component) {
    setBackground(component.getBackground());
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    gl.glPushMatrix();
    gl.glTranslatef(0, height, 0);
    gl.glScalef(1, -1, 1);
    component.paint(this);
    gl.glPopMatrix();
    gl.glFlush();
  }

  @Override
  public void draw(Shape s) {
    shapeDrawer.draw(s, false);
  }

  @Override
  public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawString(String str, int x, int y) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawString(String str, float x, float y) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawString(AttributedCharacterIterator iterator, int x, int y) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawString(AttributedCharacterIterator iterator, float x, float y) {
    // TODO Auto-generated method stub
  }

  @Override
  public void drawGlyphVector(GlyphVector g, float x, float y) {
    // TODO Auto-generated method stub

  }

  @Override
  public void fill(Shape s) {
    shapeDrawer.draw(s, true);
  }

  @Override
  public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public GraphicsConfiguration getDeviceConfiguration() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setComposite(Composite comp) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setPaint(Paint paint) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setStroke(Stroke s) {
    assert s instanceof BasicStroke : "Only BasicStroke is supported currently.";

    stroke = (BasicStroke) s;
    gl.glLineWidth(stroke.getLineWidth());
  }

  @Override
  public void setRenderingHint(Key hintKey, Object hintValue) {
    // TODO Auto-generated method stub

  }

  @Override
  public Object getRenderingHint(Key hintKey) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setRenderingHints(Map<?, ?> hints) {
    // TODO Auto-generated method stub

  }

  @Override
  public void addRenderingHints(Map<?, ?> hints) {
    // TODO Auto-generated method stub

  }

  @Override
  public RenderingHints getRenderingHints() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void translate(int x, int y) {
    transform.translate(x, y);
    setTransform(transform);
  }

  @Override
  public void translate(double tx, double ty) {
    transform.translate(tx, ty);
    setTransform(transform);
  }

  @Override
  public void rotate(double theta) {
    transform.rotate(theta);
    setTransform(transform);
  }

  @Override
  public void rotate(double theta, double x, double y) {
    transform.rotate(theta, x, y);
    setTransform(transform);
  }

  @Override
  public void scale(double sx, double sy) {
    transform.scale(sx, sy);
    setTransform(transform);
  }

  @Override
  public void shear(double shx, double shy) {
    transform.shear(shx, shy);
    setTransform(transform);
  }

  @Override
  public void transform(AffineTransform Tx) {
    transform.concatenate(Tx);
    setTransform(Tx);
  }

  @Override
  public void setTransform(AffineTransform Tx) {
    if (transform != Tx) {
      transform = (AffineTransform) Tx.clone();
    }

    double[] matrix = new double[16];
    matrix[0] = transform.getScaleX();
    matrix[1] = transform.getShearY();
    matrix[4] = transform.getShearX();
    matrix[5] = transform.getScaleY();
    matrix[10] = 1;
    matrix[12] = transform.getTranslateX();
    matrix[13] = transform.getTranslateY();
    matrix[15] = 1;

    gl.glLoadMatrixd(matrix, 0);
  }

  @Override
  public AffineTransform getTransform() {
    return (AffineTransform) transform.clone();
  }

  @Override
  public Paint getPaint() {
    return color;
  }

  @Override
  public Composite getComposite() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setBackground(Color color) {
    background = color;
    int rgb = background.getRGB();
    gl.glClearColor((rgb >> 16 & 0xFF) / 255F, (rgb >> 8 & 0xFF) / 255F, (rgb & 0xFF) / 255F, (rgb >> 24 & 0xFF) / 255F);
  }

  @Override
  public Color getBackground() {
    return background;
  }

  @Override
  public Stroke getStroke() {
    return stroke;
  }

  @Override
  public void clip(Shape s) {
    // TODO Auto-generated method stub

  }

  @Override
  public FontRenderContext getFontRenderContext() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Graphics create() {
    return clone();
  }

  @Override
  public Color getColor() {
    return color;
  }

  @Override
  public void setColor(Color c) {
    color = c;
    int rgb = color.getRGB();
    gl.glColor4f((rgb >> 16 & 0xFF) / 255F, (rgb >> 8 & 0xFF) / 255F, (rgb & 0xFF) / 255F, (rgb >> 24 & 0xFF) / 255F);
  }

  @Override
  public void setPaintMode() {
    // TODO Auto-generated method stub

  }

  @Override
  public void setXORMode(Color c1) {
    // TODO Auto-generated method stub

  }

  @Override
  public Font getFont() {
    return font;
  }

  @Override
  public void setFont(Font font) {
    this.font = font;
  }

  @Override
  public FontMetrics getFontMetrics(Font f) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Rectangle getClipBounds() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void clipRect(int x, int y, int width, int height) {
    // TODO Auto-generated method stub

  }

  @Override
  public void setClip(int x, int y, int width, int height) {
    // TODO Auto-generated method stub

  }

  @Override
  public Shape getClip() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setClip(Shape clip) {
    // TODO Auto-generated method stub

  }

  @Override
  public void copyArea(int x, int y, int width, int height, int dx, int dy) {
    // TODO Auto-generated method stub

  }

  @Override
  public void drawLine(int x1, int y1, int x2, int y2) {
    gl.glBegin(GL.GL_LINES);
    gl.glVertex2i(x1, y1);
    gl.glVertex2i(x2, y2);
    gl.glEnd();
  }

  @Override
  public void fillRect(int x, int y, int width, int height) {
    gl.glBegin(GL.GL_QUADS);
    gl.glVertex2i(x, y);
    gl.glVertex2i(x + width, y);
    gl.glVertex2i(x + width, y + height);
    gl.glVertex2i(x, y + height);
    gl.glEnd();
  }

  @Override
  public void clearRect(int x, int y, int width, int height) {
    Color origColor = color;
    setColor(background);
    drawRect(x, y, width, height);
    setColor(origColor);
  }

  @Override
  public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    shapeDrawer.drawRoundRect(x, y, width, height, arcWidth, arcHeight, false);
  }

  @Override
  public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
    shapeDrawer.drawRoundRect(x, y, width, height, arcWidth, arcHeight, true);
  }

  @Override
  public void drawOval(int x, int y, int width, int height) {
    shapeDrawer.drawOval(x, y, width, height, false);
  }

  @Override
  public void fillOval(int x, int y, int width, int height) {
    shapeDrawer.drawOval(x, y, width, height, true);
  }

  @Override
  public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    shapeDrawer.drawArc(x, y, width, height, startAngle, arcAngle, false);
  }

  @Override
  public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
    shapeDrawer.drawArc(x, y, width, height, startAngle, arcAngle, true);
  }

  @Override
  public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
    gl.glBegin(GL.GL_LINE_STRIP);
    for (int i = 0; i < nPoints; i++) {
      gl.glVertex2i(xPoints[i], yPoints[i]);
    }

    gl.glEnd();
  }

  @Override
  public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
    gl.glBegin(GL.GL_LINE_LOOP);
    for (int i = 0; i < nPoints; i++) {
      gl.glVertex2i(xPoints[i], yPoints[i]);
    }

    gl.glEnd();
  }

  @Override
  public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
    gl.glBegin(GL.GL_POLYGON);
    for (int i = 0; i < nPoints; i++) {
      gl.glVertex2i(xPoints[i], yPoints[i]);
    }

    gl.glEnd();
  }

  @Override
  public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor,
      ImageObserver observer) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public void dispose() {
    // TODO Auto-generated method stub
  }

  @Override
  protected JOGLG2D clone() {
    try {
      JOGLG2D copy = (JOGLG2D) super.clone();
      // clone mutable members
      copy.transform = (AffineTransform) transform.clone();
      return copy;
    } catch (CloneNotSupportedException exception) {
      throw new RuntimeException(exception);
    }
  }
}