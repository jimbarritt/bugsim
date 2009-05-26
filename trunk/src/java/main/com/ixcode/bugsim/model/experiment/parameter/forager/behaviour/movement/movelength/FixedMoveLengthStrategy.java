/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.movelength;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.agent.motile.movement.FixedMoveLengthGenerator;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 3:32:10 PM by jim
 */
public class FixedMoveLengthStrategy extends MoveLengthStrategyBase {

    public FixedMoveLengthStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public FixedMoveLengthStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public static StrategyDefinitionParameter createStrategyS(double moveLength) {
        StrategyDefinitionParameter sdp = new StrategyDefinitionParameter(S_FIXED_MOVELENGTH, FixedMoveLengthGenerator.class.getName());
        sdp.addParameter(new Parameter(P_MOVE_LENGTH, moveLength));
        return sdp;
    }

    public double getMoveLength() {
        return super.getParameter(P_MOVE_LENGTH).getDoubleValue();
    }

    public void setMoveLength(double moveLength) {
        super.getParameter(P_MOVE_LENGTH).setValue(moveLength);
    }

    public Parameter getMoveLengthP() {
        return super.getParameter(P_MOVE_LENGTH);
    }

    public static final String S_FIXED_MOVELENGTH = "fixedMoveLength";

    public static final String P_MOVE_LENGTH = "moveLength";

}
