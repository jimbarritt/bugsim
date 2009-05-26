/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.bugsim.model.experiment.parameter.ButterflyParameters;

import java.util.Map;
import java.text.DecimalFormat;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class FixedMoveLengthGenerator implements IMoveLengthGenerator, IParameterisedStrategy {
    public FixedMoveLengthGenerator() {
    }

    public FixedMoveLengthGenerator(double moveLength) {
        _moveLength = moveLength;
        _L = FORMAT2D.format(_moveLength);
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        _moveLength = ButterflyParameters.getMoveLength(strategyP);
        _L = FORMAT2D.format(_moveLength);
    }


    /**
     * @todo impliment initial move length into a separate strategy where it is explicitly set.
     * if (agent.getAge() == 0 && agent.getInitialMoveLength() > 0) {
            mvlen = agent.getInitialMoveLength();
        }
     * @param agent
     * @return
     */
    public double calculateMoveLength(IMotileAgent agent) {
        return _moveLength;
    }

    public String getParameterSummary() {
        return "L= " + _L;
    }

    public double getMoveLength() {
        return _moveLength;
    }

    public double getInitialMoveLength() {
        return _moveLength;
    }

    private static final DecimalFormat FORMAT2D = new DecimalFormat("0.00");
    private String _L;
    private double _moveLength;
}
