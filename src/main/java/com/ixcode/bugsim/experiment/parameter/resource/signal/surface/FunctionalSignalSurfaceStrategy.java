/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.signal.surface;

import com.ixcode.bugsim.agent.cabbage.signal.FunctionalSignalSurfaceFactory;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.GaussianSignalFunctionStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.SignalFunctionStrategyBase;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.SignalFunctionStrategyFactory;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;


/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 1:44:30 PM by jim
 */
public class FunctionalSignalSurfaceStrategy extends SignalSurfaceStrategyBase {

    public static StrategyDefinitionParameter createDefaultSurfaceS() {
        StrategyDefinitionParameter functionS = GaussianSignalFunctionStrategy.createDefaultFunctionS();
        return createSignalSurfaceS(functionS, true, 50d, "defaultSignalSurface");
    }

    public static StrategyDefinitionParameter createSignalSurfaceS(StrategyDefinitionParameter functionS, boolean includeSurvey, double surveyResolution, String surfaceName) {
        StrategyDefinitionParameter surfaceS = SignalSurfaceStrategyBase.createSignalSurfaceS(S_FUNCTIONAL_SIGNAL_SURFACE, FunctionalSignalSurfaceFactory.class.getName(), includeSurvey, surveyResolution,  surfaceName);
        surfaceS.addParameter(new Parameter(P_SIGNAL_FUNCTION, functionS));
        return surfaceS;
    }

    public FunctionalSignalSurfaceStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
        StrategyDefinitionParameter functionS = super.getParameter(P_SIGNAL_FUNCTION).getStrategyDefinitionValue();
        _signalFunction = SignalFunctionStrategyFactory.createSignalSurface(functionS, super.getParameterMap());
    }

    public SignalFunctionStrategyBase getSignalFunction() {
        return _signalFunction;
    }

    public void setSignalFunction(SignalFunctionStrategyBase signalFunction){
        _signalFunction = signalFunction;
        super.getParameter(P_SIGNAL_FUNCTION).setValue(signalFunction.getStrategyS());
    }



    public static final String S_FUNCTIONAL_SIGNAL_SURFACE = "functionalSignalSurface";

    public static final String P_SIGNAL_FUNCTION = "signalFunction";

    private SignalFunctionStrategyBase _signalFunction;


}
