/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.landscape.boundary;

import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;

import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class RectangularBoundary extends BoundaryBase {

    public RectangularBoundary() {
        super();
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);

        RectangularBoundaryStrategy boundaryS = new RectangularBoundaryStrategy(strategyP, params, StrategyDefinition.getForwardEvents(initialisationObjects));
        super.setBounds(boundaryS.getBounds());
        super.setShape(boundaryS.getBoundaryShape());
    }

    public RectangularBoundary(CartesianBounds bounds) {
        super(bounds, BoundaryShape.RECTANGULAR);
    }

    public boolean isInside(RectangularCoordinate coordinate) {
        return super.getBounds().isInside(coordinate);
    }

    public boolean isOnEdge(RectangularCoordinate coordinate) {
        return super.getBounds().isOnEdge(coordinate);
    }

    public boolean isOutside(RectangularCoordinate coordinate) {
        return super.getBounds().isOutside(coordinate);
    }
}
