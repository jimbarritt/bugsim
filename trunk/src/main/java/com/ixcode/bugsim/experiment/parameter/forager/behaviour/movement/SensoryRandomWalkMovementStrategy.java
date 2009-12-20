/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.SensoryRandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.VonMisesAzimuthGenerator;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.movelength.FixedMoveLengthStrategy;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 9, 2007 @ 2:57:43 PM by jim
 */
public class SensoryRandomWalkMovementStrategy extends RandomWalkMovementStrategy {

    public SensoryRandomWalkMovementStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public SensoryRandomWalkMovementStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }


    public static StrategyDefinitionParameter createDefaultStrategyS() {
        StrategyDefinitionParameter azimuthS = VonMisesAzimuthStrategy.createDefaultStrategyS();
        StrategyDefinitionParameter moveLengthS = FixedMoveLengthStrategy.createStrategyS(10);
        return createStrategyS(0, azimuthS, moveLengthS, false, false);
    }

    public static StrategyDefinitionParameter createStrategyS(int refactoryPeriod, StrategyDefinitionParameter azimuthS, StrategyDefinitionParameter moveLengthS, boolean visionEnabled, boolean olfactionEnabled) {
        StrategyDefinitionParameter srw = new StrategyDefinitionParameter(S_SENSORY_RANDOM_WALK, SensoryRandomWalkStrategy.class.getName());
        RandomWalkMovementStrategy.addStrategyParams(srw, azimuthS, moveLengthS);
        srw.addParameter(new Parameter(P_REFACTORY_PERIOD, refactoryPeriod));
        srw.addParameter(new Parameter(P_VISION_ENABLED, visionEnabled));
        srw.addParameter(new Parameter(P_OLFACTION_ENABLED, olfactionEnabled));

        return srw;
    }

    public int getRefactoryPeriod() {
        return super.getParameter(P_REFACTORY_PERIOD).getIntValue();
    }

    public void setRefactoryPeriod(int refactoryPeriod) {
        super.getParameter(P_REFACTORY_PERIOD).setValue(refactoryPeriod);
    }

    public boolean isVisionEnabled() {
        return super.getParameter(P_VISION_ENABLED).getBooleanValue();
    }

    public void setVisionEnabled(boolean visionEnabled) {
        super.getParameter(P_VISION_ENABLED).setValue(visionEnabled);
    }

    public boolean isOlfactionEnabled() {
        return super.getParameter(P_OLFACTION_ENABLED).getBooleanValue();
    }

    public void setOlfactionEnabled(boolean olfactionEnabled) {
        super.getParameter(P_OLFACTION_ENABLED).setValue(olfactionEnabled);
    }

    public static final String S_SENSORY_RANDOM_WALK = "sensoryRandomWalk";

    public static final String P_REFACTORY_PERIOD = "refactoryPeriod";
    public static final String P_VISION_ENABLED = "visionEnabled";
    public static final String P_OLFACTION_ENABLED = "olfactionEnabled";

}
