/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.cabbage;

import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.physical.PhysicalAgentBase;
import com.ixcode.framework.simulation.model.landscape.Location;

import java.awt.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class AttractionFieldAgent extends PhysicalAgentBase {
    public void setColor(Color color) {
        _color = color;
    }

    public Color getColor() {
        return _color;
    }


    public static final String AGENT_CLASS_ID = "AttractionField";

    public AttractionFieldAgent(Location location, double radius) {
        super(AGENT_CLASS_ID, location);
        _radius = radius;


    }

    public void executeTimeStep(Simulation simulation) {

    }

    public double getRadius() {
        return _radius;
    }


    public boolean containsPoint(RectangularCoordinate coord) {
        return Geometry.isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), super.getLocation().getDoubleX(), super.getLocation().getDoubleY(), getRadius());
    }

    public boolean intersectsLine(RectangularCoordinate p1, RectangularCoordinate p2) {
        RectangularCoordinate centre = getLocation().getCoordinate();
        return Geometry.lineInstersectsCircle(p1.getDoubleX(), p1.getDoubleY(), p2.getDoubleX(), p2.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), getRadius());
    }


    public float getTransparency() {
        return _transparency;
    }

    public void setTransparency(float transparency) {
        _transparency = transparency;
    }

    private double _radius = 10.0;


    private Color _color = Color.blue;
    private float _transparency = .2f;
}
