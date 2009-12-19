/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.grid;

import com.ixcode.bugsim.view.landscape.LandscapeView;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class GridRenderer {

    public void render(Graphics2D g, Grid grid, GridRenderOptions options, LandscapeView landscapeView) {
        Composite before = g.getComposite();
        recurseChildren(g, grid, options, 1, landscapeView);
        renderTopLevelGrid(grid, g, options, landscapeView);
        g.setComposite(before);
    }

    private void recurseChildren(Graphics2D g, Grid grid, GridRenderOptions options, int recursionLevel, LandscapeView landscapeView) {
        for (Iterator itr = grid.getGridSquares().iterator(); itr.hasNext();) {
            GridSquare square = (GridSquare)itr.next();

            if (square.hasChildGrid()) {
                recurseChildren(g, square.getChildGrid(), options, recursionLevel + 1, landscapeView);
                renderChildGrid(g, square.getChildGrid(), options, recursionLevel, landscapeView);

            }

        }
    }

    private void renderChildGrid(Graphics2D g, Grid grid, GridRenderOptions options, int gridLevel, LandscapeView landscapeView) {
        Rectangle2D.Double outerRectangle = getRectangleForGrid(grid, landscapeView);

        drawGridlines(g, grid, outerRectangle, options, gridLevel, landscapeView);
    }

    private void renderTopLevelGrid(Grid grid, Graphics2D g, GridRenderOptions options, LandscapeView landscapeView) {
        Rectangle2D.Double rc = getRectangleForGrid(grid, landscapeView);
        Ellipse2D.Double outerCircle = new Ellipse2D.Double(rc.getX(), rc.getY(), rc.getWidth(), rc.getHeight());

        Shape outerBounds = rc;
        if (grid.isCircular()) {
            outerBounds = outerCircle;
        }

        int type = AlphaComposite.SRC_OVER;
        float opacity = options.getOpacity(grid);
        AlphaComposite composite = AlphaComposite.getInstance(type, opacity);
        g.setComposite(composite);

        g.setColor(options.getDarkColor(0, grid));
        g.setStroke(new BasicStroke(1));

        drawGridlines(g, grid, rc, options, 0, landscapeView);
        AlphaComposite composite2 = AlphaComposite.getInstance(type, 1f);
        g.setComposite(composite2);

        g.draw(outerBounds);
        g.setComposite(composite);
        g.setColor(options.getLightColor(grid));
        g.fill(outerBounds);

//        System.out.println("Rendering top Level Grid : " + grid);
//        g.setStroke(new BasicStroke(2));
//        g.drawString(grid.getName(), 40, 40);
    }

    private Rectangle2D.Double getRectangleForGrid(Grid grid, LandscapeView view) {
        double x = grid.getLandscapeOrigin().getDoubleX();
        double y = view.getLandscape().getExtentY() - grid.getLandscapeOrigin().getDoubleY();
        double width = grid.getLandscapeWidth();
        double height = grid.getLandscapeHeight();
        Rectangle2D.Double outerRectangle = new Rectangle2D.Double(x, y - height, width, height);
//        System.out.println("Creating rectangle for Grid at: " + grid.getLandscapeOrigin() + " " + outerRectangle);
        return outerRectangle;
    }

    private void drawGridlines(Graphics2D g, Grid grid, Rectangle2D bounds, GridRenderOptions options, int gridLevel, LandscapeView landscapeView) {

        double scaleX = landscapeView.getLandscapeClipSizeX();
        float magnification = 0.5f;

        if (scaleX >= 400) {
            magnification = 1f;
        } else if (scaleX >= 2000) {
            magnification= 1f;
        } else if (scaleX >= 10000) {
            magnification= 10f;
        }

        g.setColor(options.getDarkColor(gridLevel, grid));
        float thickness = options.getStrokeThickness(gridLevel);
        thickness = thickness * magnification;
        Stroke s = new BasicStroke(thickness);
        g.setStroke(s);


        if (grid.isNonUniformGrid()) {
            drawRealGridSquares(grid, bounds, g, options.getStrokeThickness(gridLevel));
        } else {
            drawEqualGridSquares(grid, bounds, g);
        }


    }

    private void drawRealGridSquares(Grid grid, Rectangle2D bounds, Graphics2D g, float lineWidth) {


        for (Iterator itr = grid.getGridSquares().iterator(); itr.hasNext();) {
            GridSquare square = (GridSquare)itr.next();

//            System.out.println("Drawing Grid Square (" + grid.getGridSquares().indexOf(square) + ") : "  + square);
            RectangularCoordinate origin = square.getLandscapeOrigin();
            double width = square.getLandscapeWidth();
            double height = square.getLandscapeHeight();
            double x = bounds.getX() + origin.getDoubleX();
            double y = bounds.getY() + origin.getDoubleY();
//            Rectangle2D.Double rect = new Rectangle2D.Double(bounds.getDoubleX() + origin.getDoubleX(), bounds.getDoubleY() + origin.getDoubleY(), width, height);


            Line2D lineBottom = new Line2D.Double(x, y, x + width - lineWidth, y);
            g.draw(lineBottom);
            Line2D lineRight = new Line2D.Double(x + width, y, x + width, y + height);
            g.draw(lineRight);


        }

    }

    private void drawEqualGridSquares(Grid grid, Rectangle2D bounds, Graphics2D g) {
        int countx = grid.getGridSize().getWidth();
        double gridSquareWidth = bounds.getWidth() / countx;
        for (int i = 1; i <= countx - 1; ++i) {
            Line2D line = new Line2D.Double(bounds.getX() + (gridSquareWidth * i), bounds.getY(), bounds.getX() + (gridSquareWidth * i), bounds.getY() + bounds.getHeight());
            g.draw(line);

        }

        int county = grid.getGridSize().getWidth();
        double gridSquareHeight = bounds.getWidth() / county;
        for (int i = 1; i <= countx - 1; ++i) {
            Line2D line = new Line2D.Double(bounds.getX(), bounds.getY() + (gridSquareHeight * i), bounds.getX() + bounds.getWidth(), bounds.getY() + (gridSquareHeight * i));
            g.draw(line);

        }
    }
}
