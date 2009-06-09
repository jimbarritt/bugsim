/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CircularBoundaryStrategy;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CircularBoundary extends BoundaryBase {

    public CircularBoundary() {                                                
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);
        CircularBoundaryStrategy cbs = new CircularBoundaryStrategy(strategyP, params, false);

        //@todo move these into superclass..
        super.setBounds(cbs.getBounds());
        super.setShape(cbs.getBoundaryShape());
        _radius = cbs.getRadius();
        _diameter = _radius * 2;
    }

    public CircularBoundary(RectangularCoordinate location, double radius) {
        super(createCircularOuterBounds(location, radius), BoundaryShape.CIRCULAR);
        _radius = radius;
        _diameter = radius * 2;
    }

    public static CartesianBounds createCircularOuterBounds(RectangularCoordinate location, double radius) {
        return new CartesianBounds(location.getDoubleX() - radius, location.getDoubleY() - radius, radius * 2, radius * 2);
    }

    public boolean isInside(RectangularCoordinate coord) {
        RectangularCoordinate centre = _bounds.getCentre();


        return Geometry.isPointInCircleDouble(coord.getDoubleX(), coord.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), _radius);
    }

    public boolean isOnEdge(RectangularCoordinate coordinate) {
        RectangularCoordinate centre = _bounds.getCentre();
        return Geometry.isPointOnEdgeOfCircle(coordinate.getDoubleX(), coordinate.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), _radius);
    }

    public boolean isOutside(RectangularCoordinate coordinate) {
        RectangularCoordinate centre = _bounds.getCentre();
        return !Geometry.isPointInCircleDouble(coordinate.getDoubleX(), coordinate.getDoubleY(), centre.getDoubleX(), centre.getDoubleY(), _radius);
    }

    public BoundaryShape getShape() {
        return BoundaryShape.CIRCULAR;
    }

    /**
     * @return
     * @todo replace this with CartesianDimension
     */
    public double getRadius() {
        return _radius;
    }


    public double getDiameter() {
        return _diameter;
    }


    private double _radius;
    private double _diameter;


}
