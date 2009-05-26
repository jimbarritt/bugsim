/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.landscape.map;

import com.ixcode.framework.math.geometry.RectangularCoordinate;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MapOutline {

    /**
     * @param g
     * @param sx scale x
     * @param sy scale y
     */
    public void render(Graphics2D g, double sx, double sy) {
        GeneralPath path = new GeneralPath();
        boolean first = true;
        for (Iterator itr = _points.iterator(); itr.hasNext();) {
            Point2D.Double imagePoint = (Point2D.Double)itr.next();
            Point2D.Double screenPoint = getScreenPoint(imagePoint, sx, sy);
            renderPoint(g, screenPoint, sx, sy);

            if (first) {
                path.moveTo((float)screenPoint.getX(), (float)screenPoint.getY());
                first = false;
            } else {
                path.lineTo((float)screenPoint.getX(), (float)screenPoint.getY());
            }

        }
        if (_points.size() > 3) {
            Point2D.Double screenPoint = getScreenPoint((Point2D.Double)_points.get(0), sx, sy);
            path.lineTo((float)screenPoint.getX(), (float)screenPoint.getY());
        }

        g.setStroke(new BasicStroke(.5f));
        g.setColor(Color.RED);
        g.draw(path);

    }

    private Point2D.Double getScreenPoint(Point2D.Double imagePoint, double sx, double sy) {
        double cx = imagePoint.getX() * sx;
        double cy = imagePoint.getY() * sy;
        return new Point2D.Double(cx, cy);
    }

    private void renderPoint(Graphics2D g, Point.Double screenPoint, double sx, double sy) {
        double cx = screenPoint.getX();
        double cy = screenPoint.getY();

//        System.out.println("rendering point " + cx + ", "+ cy + ", sx=" + sx + ", sy="+sy);
        final int SIZE = 40;
        Rectangle2D renderPoint = new Rectangle2D.Double(cx - (SIZE / 2), cy - (SIZE / 2), SIZE, SIZE);
        g.setColor(Color.DARK_GRAY);
        g.draw(renderPoint);

        Line2D.Double crosshairV = new Line2D.Double(cx, cy - (SIZE / 2), cx, cy + (SIZE / 2));
        Line2D.Double crosshairH = new Line2D.Double(cx - (SIZE / 2), cy, cx + (SIZE / 2), cy);
        g.draw(crosshairV);
        g.draw(crosshairH);
//        g.setColor(new Color(51, 153, 102));
//        g.fill(renderPoint);
    }

    public void addPoint(Point2D.Double point) {
        _points.add(point);
        if (_points.size() == 1) {
            _path.moveTo((float)point.getX(), (float)point.getY());
        } else {
            _path.lineTo((float)point.getX(), (float)point.getY());
        }
    }

    public void rotate(double radians, double cx, double cy) {

        AffineTransform rotate = AffineTransform.getRotateInstance(radians, cx, cy);


        double[] coords = new double[2];
        _points = new ArrayList();
        for (PathIterator itr = _path.getPathIterator(rotate); !itr.isDone();) {

            itr.currentSegment(coords);
//            System.out.println("New coordinate : " + coords[0] + ", " + coords[1]);
            _points.add(new Point2D.Double(coords[0], coords[1]));
            itr.next();
        }
    }


    /**
     * Tells you how much this outli ne is "tilted"
     * the current implementation simply asusmes that you have drawn a square and that
     * the last two points make a line wich should be at 90 degrees (i.e. angle of 0.
     * It then calculates the angle between the two points to work out how much it is out by
     *
     * @return
     */
    public double getAngleOfRotationRadians() {
        if (_points.size() != 4) {
            throw new IllegalStateException("The current implementation requires that you draw a square, startign top left and finishing bottom left");
        }
        Point2D.Double bottomLeft = (Point2D.Double)_points.get(3);
        Point2D.Double bottomRight = (Point2D.Double)_points.get(2);

        RectangularCoordinate br = new RectangularCoordinate(bottomRight.getX(), bottomRight.getY());
        RectangularCoordinate bl = new RectangularCoordinate(bottomLeft.getX(), bottomLeft.getY());

        double angle = br.calculatePolarCoordinateTo(bl).getAngle();

//        System.out.println("bottomRight : " + bottomRight + ", bottomLeft = " + bottomLeft + ", xincr=" + xincr + ", yincr=" + yincr);
//        System.out.println("Calculated angle : " + angle + " -90=" + (angle - 90) + ", as radians =" + Math.toRadians(angle - 90));
        return Math.toRadians(angle);
    }

    /**
     * @return a rectangle which containsCoord all the points.
     */
    public Rectangle2D getBounds2D() {
        return _path.getBounds2D();
    }


    private GeneralPath createGeneralPath() {
           GeneralPath path = new GeneralPath();
           boolean first = true;
           for (Iterator itr = _points.iterator(); itr.hasNext();) {
               Point2D.Double point = (Point2D.Double)itr.next();
//               System.out.println("Old coord : " + point);
               if (first) {
                   path.moveTo((float)point.getX(), (float)point.getY());
                   first = false;
               } else {
                   path.lineTo((float)point.getX(), (float)point.getY());
               }
           }
           return path;
       }

    private List _points = new ArrayList();
    private GeneralPath _path = new GeneralPath();
}
