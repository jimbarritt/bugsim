package com.ixcode.bugsim.view.grid;

import com.ixcode.framework.simulation.model.landscape.*;
import com.ixcode.framework.math.scale.*;

import java.awt.*;
import java.awt.geom.*;

public class GridLineRenderer {
    private int logicalGridResolution = 100;
    private boolean isVisible = true;
    private ScaledDistance gridResolution;
    private int logicalGridThickness;
    private ScaledDistance gridThickness;


    public void drawGridLines(Graphics2D g, double extentX, double extentY) {
        double cx = extentX / 2;
        double cy = extentY / 2;


        int countx = (int) (extentX / logicalGridResolution) + 1;
        int countXSide = countx / 2; // number of gridlines from centre to edge.
        int minX = (int) (cx - ((logicalGridResolution * 0.5) + (logicalGridResolution * (countXSide - 1))));

        int county = (int) (extentY / logicalGridResolution) + 1;
        int countYSide = county / 2; // number of gridlines from centre to edge.
        int minY = (int) (cy - ((logicalGridResolution * 0.5) + (logicalGridResolution * (countYSide - 1))));

        g.setColor(Color.lightGray);
        g.setStroke(new BasicStroke(0.025f));//logicalGridThickness

        int offsetx = minX;
        int offsety = minY;

        for (int i = 0; i < countx; ++i) {
            Line2D line = new Line2D.Double((logicalGridResolution * i) + offsetx, 0, (logicalGridResolution * i) + offsetx, extentY);
            g.draw(line);
        }


        for (int i = 0; i < county; ++i) {
            Line2D line = new Line2D.Double(0, (logicalGridResolution * i) + offsety, extentX, (logicalGridResolution * i) + offsety);
            g.draw(line);
        }

    }


    public void render(Landscape landscape, Graphics2D graphics2D) {
        if (isVisible) {
            drawGridLines(graphics2D, landscape.getExtentX(), landscape.getExtentY());
        }
    }

    public void setGridResolution(ScaledDistance resolution, Landscape landscape) {
        double logicalResolution = landscape.getScale().convertScaledToLogicalDistance(new Distance(resolution.getDistance(), resolution.getUnits()));
        setLogicalGridResolution((int) Math.round(logicalResolution));
        gridResolution = resolution;
    }

    public void setLogicalGridThickness(int gridWidth) {
        logicalGridThickness = gridWidth;
    }

    public int getLogicalGridThickness() {
        return logicalGridThickness;
    }

    public void setGridThickness(ScaledDistance gridThickness, Landscape landscape) {
        double logicalThickness = landscape.getScale().convertScaledToLogicalDistance(new Distance(gridThickness.getDistance(), gridThickness.getUnits()));
        setLogicalGridThickness((int) Math.round(logicalThickness));
        this.gridThickness = gridThickness;
    }

    public ScaledDistance getGridThickness() {
        return gridThickness;
    }


    public ScaledDistance getGridResolution() {
        return gridResolution;
    }

    public int getLogicalGridResolution() {
        return logicalGridResolution;
    }

    public void setLogicalGridResolution(int gridResolution) {
        logicalGridResolution = gridResolution;
    }




}
