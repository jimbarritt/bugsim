/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class BoundaryBase implements IBoundary, IParameterisedStrategy {

    protected BoundaryBase() {
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getParameterSummary() {
        return "B=" + _bounds + ", Sh=" + _shape;
    }

    public BoundaryBase(CartesianBounds bounds, BoundaryShape shape) {
        _bounds = bounds;
        _shape = shape;
    }

    public CartesianBounds getBounds() {
        return _bounds;
    }

    public RectangularCoordinate getLocation() {
        return _bounds.getLocation();
    }

    public CartesianDimensions getDimension() {
        return _bounds.getDimensions();
    }

    public BoundaryShape getShape() {
        return _shape;
    }

    protected void setBounds(CartesianBounds bounds) {
        _bounds = bounds;
    }

    protected void setShape(BoundaryShape shape) {
        _shape = shape;
    }

    CartesianBounds _bounds;
    BoundaryShape _shape;

}
