/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.release;

import com.ixcode.bugsim.agent.boundary.CircularBoundaryAgent;
import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.Geometry;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ReleaseRandomInZoneStrategy extends ReleaseRandomAroundBorderStrategy {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);

        CartesianBounds outerBounds = ButterflyParameters.getReleaseZoneBounds(strategyP);
        BoundaryShape outerShape = ButterflyParameters.getReleaseZoneShape(strategyP);

        if (outerShape != BoundaryShape.CIRCULAR) {
            throw new IllegalStateException("Cannot have a boundary which is not circular");
        }


        CartesianBounds innerBounds = super.getZeroBoundaryBounds();

        _outerBoundaryAgent = new CircularBoundaryAgent("releaseOuterBoundary", new Location(outerBounds.getCentre()), outerBounds.getRadiusOfInnerCircle());
        _innerBoundaryAgent = new CircularBoundaryAgent("releaseInnerBOundary", new Location(innerBounds.getCentre()), innerBounds.getRadiusOfInnerCircle());

        _outerBoundary = _outerBoundaryAgent.getBoundary();
        _innerBoundary = _innerBoundaryAgent.getBoundary();

    }

    public RectangularCoordinate generateInitialLocation() {

        RectangularCoordinate startCoord = null;
        while (startCoord == null) {

            RectangularCoordinate randomCoord = Geometry.generateUniformRandomCoordinate(super.getSimulation().getRandom(), _outerBoundary.getBounds());
            if (!_innerBoundary.isInside(randomCoord) && _outerBoundary.isInside(randomCoord)) {
                startCoord = randomCoord;
            }
        }


        return startCoord;
    }



    public double generateReleaseI() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private CircularBoundaryAgent _outerBoundaryAgent;
    private CircularBoundaryAgent _innerBoundaryAgent;
    private static final Logger log = Logger.getLogger(ReleaseRandomInZoneStrategy.class);
    private IBoundary _outerBoundary;
    private IBoundary _innerBoundary;
}
