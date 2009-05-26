/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.function;

import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionFunction;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 1:33:43 PM by jim
 */
public class GaussianSignalFunctionStrategy extends SignalFunctionStrategyBase {

    public static StrategyDefinitionParameter createDefaultFunctionS() {        
        return createSignalFunctionS(1e7, 1e5);
    }

    public static StrategyDefinitionParameter createSignalFunctionS(double standardDeviation, double magnification) {
        StrategyDefinitionParameter signalFunctionS = new StrategyDefinitionParameter(S_GAUSSIAN_SIGNAL_FUNCTION, GaussianDistributionFunction.class.getName());
        signalFunctionS.addParameter(new Parameter(P_STANDARD_DEVIATION, standardDeviation));
        signalFunctionS.addParameter(new Parameter(P_MAGNIFICATION, magnification));
        return signalFunctionS;
    }

    public GaussianSignalFunctionStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public double getStandardDeviation() {
        return super.getParameter(P_STANDARD_DEVIATION).getDoubleValue();
    }

    public void setStandardDeviation(double signalSD) {
        super.getParameter(P_STANDARD_DEVIATION).setValue(signalSD);
    }

    public double getMagnification() {
        return super.getParameter(P_MAGNIFICATION).getDoubleValue();
    }

    public void setMagnification(double magnification) {
        super.getParameter(P_MAGNIFICATION).setValue(magnification);
    }

    public Parameter getStandardDeviationP() {
        return super.getParameter(P_STANDARD_DEVIATION);
    }


    public static final String P_STANDARD_DEVIATION = "standardDeviation";
    public  static final String P_MAGNIFICATION = "magnification";
    public static final String S_GAUSSIAN_SIGNAL_FUNCTION = "gaussianSignalFunction";
}
