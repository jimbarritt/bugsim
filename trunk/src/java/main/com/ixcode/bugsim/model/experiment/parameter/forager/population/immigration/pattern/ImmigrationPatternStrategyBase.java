/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.pattern;

import com.ixcode.framework.parameter.model.*;
import com.ixcode.bugsim.model.agent.cabbage.layout.ResourceLayoutBase;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import sun.security.util.DerValue;

import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:24:27 PM by jim
 */
public abstract class ImmigrationPatternStrategyBase extends StrategyDefinition {

    public ImmigrationPatternStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public ImmigrationPatternStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    protected static void addBaseParameters(StrategyDefinitionParameter sparam, long numberToRelease, long eggLimit, boolean optimiseRelease) {
        sparam.addParameter(new Parameter(P_EGG_LIMIT, eggLimit));
        sparam.addParameter(new Parameter(P_NUMBER_TO_RELEASE, numberToRelease));
        sparam.addParameter(new Parameter(P_OPTIMISE_RELEASE_AZIMUTH, optimiseRelease));

    }

    public final long getNumberToRelease() {
        return super.getParameter(P_NUMBER_TO_RELEASE).getLongValue();
    }

    public final void setNumberToRelease(long numberToRelease) {
        super.getParameter(P_NUMBER_TO_RELEASE).setValue(numberToRelease);
    }

    public final long getEggLimit() {
        return super.getParameter(P_EGG_LIMIT).getLongValue();
    }

    public final void setEggLimit(long eggLimit) {
        super.getParameter(P_EGG_LIMIT).setValue(eggLimit);
    }

    public void deriveEggLimitFromLayout() {
        Parameter expectedEggCountP = ResourceLayoutStrategyBase.findExpectedEggCountP(super.getParameterMap());
        List sourceParams = DirectValueDerivationCalculation.createSourceParameters(expectedEggCountP);
        DerivedParameter derivedEggLimitP = new DerivedParameter(P_EGG_LIMIT, new DirectValueDerivationCalculation());
        derivedEggLimitP.setSourceParameters(sourceParams);
        super.setParameter(derivedEggLimitP);
        super.fireParameterChangeEvent(P_EGG_LIMIT, null, derivedEggLimitP.getValue());
    }

    public void detatchEggLimitFromLayout() {
        Parameter p = new Parameter(P_EGG_LIMIT, getEggLimit());
        super.setParameter(p);
    }

    public Parameter getEggLimitP() {
        return super.getParameter(P_EGG_LIMIT);
    }

    public boolean isOptimiseReleaseAzimuth() {
        if (!super.hasParameter(P_OPTIMISE_RELEASE_AZIMUTH)) {
            super.getStrategyS().addParameter(new Parameter(P_OPTIMISE_RELEASE_AZIMUTH, true));
        }
        return super.getParameter(P_OPTIMISE_RELEASE_AZIMUTH).getBooleanValue();
    }

    public void setOptimiseReleaseAzimuth(boolean optimise) {
        if (!super.hasParameter(P_OPTIMISE_RELEASE_AZIMUTH)) {
            super.getStrategyS().addParameter(new Parameter(P_OPTIMISE_RELEASE_AZIMUTH, true));
        }
        super.getParameter(P_OPTIMISE_RELEASE_AZIMUTH).setValue(optimise);
    }

    public static final String P_EGG_LIMIT = "eggLimit";
    public static final String P_NUMBER_TO_RELEASE = "numberToRelease";
    public static final String P_OPTIMISE_RELEASE_AZIMUTH = "optimiseReleaseAzimuth";

}
