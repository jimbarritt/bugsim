/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.DerivedParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CentredLocationDerivedCalculation;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.FixedLocationReleaseImmigrationPattern;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.RandomPointReleaseImmigrationPattern;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class RandomPointReleaseImmigrationStrategyDefinition extends ImmigrationPatternStrategyBase {

    public RandomPointReleaseImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

  public RandomPointReleaseImmigrationStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return RandomPointReleaseImmigrationStrategyDefinition.createStrategyS(true, true, 5, 10000, 100, false, 0, true);
    }
    public static StrategyDefinitionParameter createStrategyS(boolean limitToResourcePatch, boolean avoidAgents, double avoidingRadius, long eggLimit, long numberToRelease, boolean applyFixedAzimuth, double fixedAzimuth, boolean optimiseRelease) {
        StrategyDefinitionParameter randomPointS = new StrategyDefinitionParameter(STRATEGY_NAME, RandomPointReleaseImmigrationPattern.class.getName());

        ImmigrationPatternStrategyBase.addBaseParameters(randomPointS, numberToRelease,  eggLimit, optimiseRelease);
        randomPointS.addParameter(new Parameter(P_LIMIT_TO_RESOURCE_PATCH, limitToResourcePatch));

        randomPointS.addParameter(new Parameter(P_AVOID_AGENTS, avoidAgents));
        randomPointS.addParameter(new Parameter(P_AVOIDING_RADIUS, avoidingRadius));


        randomPointS.addParameter(new Parameter(P_APPLY_FIXED_AZIMUTH, applyFixedAzimuth));
        randomPointS.addParameter(new Parameter(P_FIXED_AZIMUTH, fixedAzimuth));

        return randomPointS;
    }

    public boolean isLimitToResourcePatch() {
        return super.getParameter(P_LIMIT_TO_RESOURCE_PATCH).getBooleanValue();
    }

    public void setLimitToResourcePatch(boolean limit) {
        super.getParameter(P_LIMIT_TO_RESOURCE_PATCH).setValue(limit);
    }

    public void setReleaseLocationP(Parameter locationP) {
        super.setParameter(locationP);
    }

    public double getFixedAzimuth() {
        return super.getParameter(RandomPointReleaseImmigrationStrategyDefinition.P_FIXED_AZIMUTH).getDoubleValue();
    }

    public void setFixedAzimuth(double heading) {
        super.getParameter(RandomPointReleaseImmigrationStrategyDefinition.P_FIXED_AZIMUTH).setValue(heading);
    }

    public boolean isApplyFixedAzimuth() {
        return super.getParameter(RandomPointReleaseImmigrationStrategyDefinition.P_APPLY_FIXED_AZIMUTH).getBooleanValue();
    }
    public void setApplyFixedAzimuth(boolean applyFixed) {
        super.getParameter(RandomPointReleaseImmigrationStrategyDefinition.P_APPLY_FIXED_AZIMUTH).setValue(applyFixed);
    }


    public static void centreOnLandscape(RandomPointReleaseImmigrationStrategyDefinition releaseStrategy, LandscapeCategory landscapeCategory) {

        Parameter outerBoundaryP = landscapeCategory.getExtent().getOuterBoundaryP();

        List sp = CentredLocationDerivedCalculation.createSourceParameters(outerBoundaryP);
        DerivedParameter locationDP = new DerivedParameter(RandomPointReleaseImmigrationStrategyDefinition.P_LIMIT_TO_RESOURCE_PATCH, sp, new CentredLocationDerivedCalculation());

        releaseStrategy.setReleaseLocationP(locationDP);

    }

    public Parameter getReleaseLocationP() {
        return super.getParameter(RandomPointReleaseImmigrationStrategyDefinition.P_LIMIT_TO_RESOURCE_PATCH);
    }

    public boolean isAvoidAgents() {
        return super.getParameter(P_AVOID_AGENTS).getBooleanValue();
    }

    public double getAvoidingRadius() {
        return super.getParameter(P_AVOIDING_RADIUS).getDoubleValue();
    }

    public void setAvoidingRadius(double radius) {
        super.getParameter(P_AVOIDING_RADIUS).setValue(radius);
    }

    public void setAvoidAgents(boolean avoid) {
        super.getParameter(P_AVOID_AGENTS).setValue(avoid);
    }

    public static final String STRATEGY_NAME = "randomPointRelease";

    public static final String P_LIMIT_TO_RESOURCE_PATCH = "limitToResourcePatch";
    public static final String P_AVOID_AGENTS = "avoidAgents";
    public static final String P_AVOIDING_RADIUS = "avoidingRadius";
    public static final String P_FIXED_AZIMUTH = "fixedAzimuth";
    public static final String P_APPLY_FIXED_AZIMUTH = "applyFixedAzimuth";








}
