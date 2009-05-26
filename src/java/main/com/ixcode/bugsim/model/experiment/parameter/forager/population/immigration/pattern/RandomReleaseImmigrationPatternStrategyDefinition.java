/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.RandomReleaseImmigrationPattern;
import com.ixcode.framework.parameter.model.*;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class RandomReleaseImmigrationPatternStrategyDefinition extends ImmigrationPatternStrategyBase {

    public RandomReleaseImmigrationPatternStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);


    }
    public RandomReleaseImmigrationPatternStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);


    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100000, 100, false, true);
    }
    public static StrategyDefinitionParameter createStrategyS(long eggLimit, long numberToRelease, boolean releaseInZone , boolean optimiseRelease) {
        StrategyDefinitionParameter randomS = new StrategyDefinitionParameter(STRATEGY_NAME, RandomReleaseImmigrationPattern.class.getName());

        ImmigrationPatternStrategyBase.addBaseParameters(randomS, numberToRelease, eggLimit, optimiseRelease);
        randomS.addParameter(new Parameter(P_RELEASE_IN_ZONE, releaseInZone));


        return randomS;
    }

//    public static StrategyDefinitionParameter createStrategyS(BoundaryShape shape, Parameter innerBoundaryP, double boundaryDistance, ShapeLocationType locationType) {
//        StrategyDefinitionParameter releaseS = new StrategyDefinitionParameter(S_RANDOM_RELEASE, RandomReleaseImmigrationPattern.class.getName());
//        Parameter distanceP = new Parameter(P_DISTANCE, boundaryDistance);
//
//        releaseS.addParameter(distanceP);
//
//        StrategyDefinitionParameter boundaryS = null;
//        if (shape.isCircular()) {
//            boundaryS = CircularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP, locationType);
//        } else if (shape.isRectangular()) {
//            boundaryS = RectangularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP);
//        } else {
//            throw new IllegalArgumentException("Cannot create a release boundary of shape " + shape + " yet.");
//        }
//
//        releaseS.addParameter(new Parameter(P_RELEASE_BOUNDARY, boundaryS));
//        return releaseS;
//
//    }







    
    public boolean isReleaseInZone() {
        return super.getParameter(P_RELEASE_IN_ZONE).getBooleanValue();
    }

    public void setReleaseInZone(boolean releaseInZone) {
        super.getParameter(P_RELEASE_IN_ZONE).setValue(releaseInZone);
    }

    public Parameter getReleaseInZoneP() {
        return super.getParameter(P_RELEASE_IN_ZONE);
    }


    private static final Logger log = Logger.getLogger(RandomReleaseImmigrationPatternStrategyDefinition.class);

    public static final String STRATEGY_NAME = "randomRelease";

    public static final String P_RELEASE_IN_ZONE = "releaseInZone";
    







}
