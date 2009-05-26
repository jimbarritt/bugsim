/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.release;

import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.*;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseRandomAroundBorderStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 9, 2007 @ 12:36:53 PM by jim
 * @deprecated
 * 
 */
public class RandomBoundaryReleaseStrategy extends ReleaseStrategyBase{

    public RandomBoundaryReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public RandomBoundaryReleaseStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

        BoundaryStrategyBase boundary = (BoundaryStrategyBase)createStrategyDefinition(P_BOUNDARY, getBoundaryS());
        super.addStrategyDefinition(P_BOUNDARY, boundary);
    }

    public static StrategyDefinitionParameter createStrategyS(BoundaryShape shape, Parameter innerBoundaryP, double boundaryDistance, ShapeLocationType locationType) {
        StrategyDefinitionParameter releaseS = new StrategyDefinitionParameter(S_RANDOM_BOUNDARY, ReleaseRandomAroundBorderStrategy.class.getName());
        Parameter distanceP = new Parameter(P_DISTANCE, boundaryDistance);

        releaseS.addParameter(distanceP);

        StrategyDefinitionParameter boundaryS = null;
        if (shape.isCircular()) {
            boundaryS = CircularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP, locationType);
        } else if (shape.isRectangular()) {
            boundaryS = RectangularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP);
        } else {
            throw new IllegalArgumentException("Cannot create a release boundary of shape " + shape + " yet.");
        }

        releaseS.addParameter(new Parameter(P_BOUNDARY, boundaryS));
        return releaseS;

    }

    /**
     * @todo move this into the boundary stuff or on landscape category... can do it now that we have generic parameter accessors.
     * @param releaseStrategy
     * @param boundaryStrategy
     * @param landscapeCategory
     */
    public static void centreOnLandscape(RandomBoundaryReleaseStrategy releaseStrategy, BoundaryStrategyBase boundaryStrategy, LandscapeCategory landscapeCategory) {

        Parameter outerBoundaryP = landscapeCategory.getExtent().getOuterBoundaryP();

        List sp = CentredBoundaryLocationDerivedCalculation.createSourceParameters(outerBoundaryP, releaseStrategy.getBoundaryP());
        DerivedParameter locationDP = new DerivedParameter(BoundaryStrategyBase.P_LOCATION, sp, new CentredBoundaryLocationDerivedCalculation());

        if (log.isInfoEnabled()) {
            log.info("[setLocationCentred] : newLocationP=" + locationDP);
        }

        boundaryStrategy.setLocationP(locationDP);

    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        if (parameterName.equals(P_BOUNDARY)) {
            return BoundaryStrategyFactory.createBoundaryStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            return super.createStrategyDefinition(parameterName, strategyS);
        }
    }

    public Parameter getBoundaryP() {
        return super.getParameter(P_BOUNDARY);
    }
    public BoundaryStrategyBase getBoundary() {
        return (BoundaryStrategyBase)super.getStrategyDefinition(P_BOUNDARY);
    }

    public void setBoundary(BoundaryStrategyBase boundary) {
        super.replaceStrategyDefinition(P_BOUNDARY, boundary);
    }

    public StrategyDefinitionParameter getBoundaryS() {
        return super.getParameter(P_BOUNDARY).getStrategyDefinitionValue();
    }

    public double getDistance() {
        return super.getParameter(P_DISTANCE).getDoubleValue();
    }

    public void setDistance(double distance) {
        super.getParameter(P_DISTANCE).setValue(distance);
    }
    private static final Logger log = Logger.getLogger(RandomBoundaryReleaseStrategy.class);
    public static final String S_RANDOM_BOUNDARY = "randomBoundary";
    public static final String P_DISTANCE = "distance";
    public static final String P_BOUNDARY = "boundary";
}
