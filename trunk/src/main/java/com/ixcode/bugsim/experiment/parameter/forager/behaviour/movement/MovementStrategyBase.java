/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.AzimuthStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.movelength.MoveLengthStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.movelength.MoveLengthStrategyFactory;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.AzimuthGeneratorBase;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 4, 2007 @ 9:02:21 PM by jim
 */
public abstract class MovementStrategyBase extends StrategyDefinition {

    public MovementStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public MovementStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

        AzimuthStrategyBase azimuthStrategy = (AzimuthStrategyBase)createStrategyDefinition(P_AZIMUTH_GENERATOR, getAzimuthS());
        super.addStrategyDefinition(P_AZIMUTH_GENERATOR, azimuthStrategy);

        StrategyDefinitionParameter moveLengthS = super.getParameter(P_MOVE_LENGTH_GENERATOR).getStrategyDefinitionValue();
        MoveLengthStrategyBase moveLengthStrategy = MoveLengthStrategyFactory.createMoveLengthStrategy(moveLengthS, params, forwardEvents);
        super.addStrategyDefinition(P_MOVE_LENGTH_GENERATOR, moveLengthStrategy);
    }

    public static void addStrategyParams(StrategyDefinitionParameter base, StrategyDefinitionParameter azimuthS, StrategyDefinitionParameter moveLengthS) {
        base.addParameter(new Parameter(P_AZIMUTH_GENERATOR, azimuthS));
        base.addParameter(new Parameter(P_MOVE_LENGTH_GENERATOR, moveLengthS));
    }

    public AzimuthStrategyBase getAzimuthGenerator() {
        return (AzimuthStrategyBase)super.getStrategyDefinition(P_AZIMUTH_GENERATOR);
    }

    public void setAzimuthGenerator(StrategyDefinition azimuthGenerator) {
        super.replaceStrategyDefinition(P_AZIMUTH_GENERATOR, azimuthGenerator);
    }

    public void setAzimuthGeneratorS(StrategyDefinitionParameter azimuthS) {
        super.replaceStrategyDefinitionParameter(P_AZIMUTH_GENERATOR, azimuthS);
    }

    public StrategyDefinitionParameter getAzimuthS() {
        return super.getParameter(P_AZIMUTH_GENERATOR).getStrategyDefinitionValue();
    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        if (parameterName.equals(P_AZIMUTH_GENERATOR)) {
            return AzimuthStrategyFactory.createAzimuthStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            return super.createStrategyDefinition(parameterName, strategyS);
        }
    }

    public MoveLengthStrategyBase getMoveLengthGenerator() {
        return (MoveLengthStrategyBase)super.getStrategyDefinition(P_MOVE_LENGTH_GENERATOR);
    }

    public void setMoveLengthGenerator(MoveLengthStrategyBase strategy) {
        super.replaceStrategyDefinition(P_MOVE_LENGTH_GENERATOR, strategy);
    }




    public static final String P_AZIMUTH_GENERATOR = "azimuthGenerator";
    public static final String P_MOVE_LENGTH_GENERATOR = "moveLengthGenerator";
}
