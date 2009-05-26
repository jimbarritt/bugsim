/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth;

import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinition;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.Parameter;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Feb 5, 2007 @ 2:48:16 PM by jim
 */
public abstract class AzimuthStrategyBase extends StrategyDefinition {

    public static void addParameters(StrategyDefinitionParameter strategyS, double visualNoiseThreshold) {
        strategyS.addParameter(new Parameter(P_VISUAL_NOISE_THRESHOLD, visualNoiseThreshold, "Any signal greater than this will have zero noise."));


    }

    public AzimuthStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public AzimuthStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public double getVisualNoiseThreshold() {
        return super.getParameter(P_VISUAL_NOISE_THRESHOLD).getDoubleValue();
    }

    public void setVisualNoiseThreshold(double threshold) {
        super.getParameter(P_VISUAL_NOISE_THRESHOLD).setValue(threshold);
    }

    public abstract Parameter getAngleOfTurnP();



    public static final String P_VISUAL_NOISE_THRESHOLD = "visualNoiseThreshold";
    
}
