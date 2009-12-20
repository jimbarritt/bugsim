/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.simulation.timescale;

import com.ixcode.bugsim.agent.butterfly.immigration.pattern.FixedLocationReleaseImmigrationPattern;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.timescale.ContinuousGenerationsTimescale;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 9, 2007 @ 12:25:26 PM by jim
 */
public class ContinuousTimescaleStrategyDefinition extends TimescaleStrategyBase {

    public ContinuousTimescaleStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

  public ContinuousTimescaleStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(100000);
    }
    public static StrategyDefinitionParameter createStrategyS(long timestepLimit) {
        StrategyDefinitionParameter continuousS = new StrategyDefinitionParameter(ContinuousTimescaleStrategyDefinition.STRATEGY_NAME, ContinuousGenerationsTimescale.class.getName());

        addParameters(continuousS, timestepLimit);

        return continuousS;
    }

    private static void addParameters(StrategyDefinitionParameter strategyS, long timestepLimit) {
        strategyS.addParameter(new Parameter(P_TIMESTEP_LIMIT, timestepLimit));
    }

    public long getTimestepLimit() {
        return super.getParameter(P_TIMESTEP_LIMIT).getLongValue();
    }

    public void setTimestepLimit(long timescaleLimit) {
        super.getParameter(P_TIMESTEP_LIMIT).setValue(timescaleLimit);
    }


    public static final String STRATEGY_NAME = "continuousGenerations";

    public static final String P_TIMESTEP_LIMIT= "timestepLimit";









}
