package joglg2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import joglg2d.util.Painter;
import joglg2d.util.TestWindow;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author borkholder
 * @created Feb 6, 2010
 */
public class VisualTest {
  static TestWindow tester;

  @BeforeClass
  public static void initialize() {
    tester = new TestWindow();
  }

  @AfterClass
  public static void close() {
    tester.close();
  }

  @Test
  public void lineTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        g2d.drawLine(10, 10, 50, 50);
      }
    });

    tester.assertSame();
  }

  @Test
  public void fillRectTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(50, 123, 99, 7);
      }
    });

    tester.assertSame();
  }

  @Test
  public void lineWidthTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(4));
        g2d.drawLine(8, 99, 143, 400);
      }
    });

    tester.assertSame();
  }

  @Test
  public void drawRectTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        g2d.drawRect(50, 90, 70, 32);
      }
    });

    tester.assertSame();
  }

  @Test
  public void rectangleShapeTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        g2d.fill(new Rectangle2D.Float(48, 123, 49, 34));
      }
    });

    tester.assertSame();
  }

  @Test
  public void strokedShapeTest() throws Exception {
    tester.setPainter(new Painter() {
      @Override
      public void paint(Graphics2D g2d) {
        Stroke stroke = new BasicStroke(4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
        g2d.fill(stroke.createStrokedShape(new Rectangle2D.Float(48, 123, 49, 34)));
      }
    });

    tester.assertSame();
  }
}