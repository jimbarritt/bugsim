/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.bugsim.agent.butterfly.immigration.pattern.FixedLocationReleaseImmigrationPattern;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.DerivedParameter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CentredLocationDerivedCalculation;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class FixedLocationImmigrationStrategyDefinition extends ImmigrationPatternStrategyBase {

    public FixedLocationImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

  public FixedLocationImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }                                                                  

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(new RectangularCoordinate(100, 100), 10000, 100, false, 0, true);
    }
    public static StrategyDefinitionParameter createStrategyS(RectangularCoordinate location, long eggLimit, long numberToRelease, boolean applyFixedAzimuth, double fixedAzimuth, boolean optimiseRelease) {
        StrategyDefinitionParameter fixedLocationS = new StrategyDefinitionParameter(STRATEGY_NAME, FixedLocationReleaseImmigrationPattern.class.getName());

        ImmigrationPatternStrategyBase.addBaseParameters(fixedLocationS, numberToRelease,  eggLimit, optimiseRelease);
        fixedLocationS.addParameter(new Parameter(P_RELEASE_LOCATION, location));

        fixedLocationS.addParameter(new Parameter(P_APPLY_FIXED_AZIMUTH, applyFixedAzimuth));
        fixedLocationS.addParameter(new Parameter(P_FIXED_AZIMUTH, fixedAzimuth));

        return fixedLocationS;
    }

    public RectangularCoordinate getReleaseLocation() {
        return (RectangularCoordinate)super.getParameter(P_RELEASE_LOCATION).getValue();
    }

    public void setReleaseLocation(RectangularCoordinate location) {
        super.getParameter(P_RELEASE_LOCATION).setValue(location);
    }

    public void setReleaseLocationP(Parameter locationP) {
        super.setParameter(locationP);
    }

    public double getFixedAzimuth() {
        return super.getParameter(P_FIXED_AZIMUTH).getDoubleValue();
    }

    public void setFixedAzimuth(double heading) {
        super.getParameter(P_FIXED_AZIMUTH).setValue(heading);
    }

    public boolean isApplyFixedAzimuth() {
        return super.getParameter(P_APPLY_FIXED_AZIMUTH).getBooleanValue();
    }
    public void setApplyFixedAzimuth(boolean applyFixed) {
        super.getParameter(P_APPLY_FIXED_AZIMUTH).setValue(applyFixed);
    }


    public static void centreOnLandscape(FixedLocationImmigrationStrategyDefinition releaseStrategy, LandscapeCategory landscapeCategory) {

        Parameter outerBoundaryP = landscapeCategory.getExtent().getOuterBoundaryP();

        List sp = CentredLocationDerivedCalculation.createSourceParameters(outerBoundaryP);
        DerivedParameter locationDP = new DerivedParameter(P_RELEASE_LOCATION, sp, new CentredLocationDerivedCalculation());

        releaseStrategy.setReleaseLocationP(locationDP);

    }

    public Parameter getReleaseLocationP() {
        return super.getParameter(P_RELEASE_LOCATION);
    }


    public static final String STRATEGY_NAME = "fixedLocationRelease";

    public static final String P_FIXED_AZIMUTH = "fixedAzimuth";
    public static final String P_APPLY_FIXED_AZIMUTH = "applyFixedAzimuth";
    public static final String P_RELEASE_LOCATION = "releaseLocation";







}
