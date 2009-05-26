/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.signal;

import com.ixcode.bugsim.model.agent.cabbage.signal.MultipleSurfaceSignalFactory;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.SignalSurfaceStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.SignalSurfaceStrategyFactory;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 12:12:44 PM by jim
 */
public class MultipleSurfaceSignalStrategy extends SignalStrategyBase {

    public static StrategyDefinitionParameter createDefaultSignalS() {
        List surfaces = new ArrayList();
        surfaces.add(FunctionalSignalSurfaceStrategy.createDefaultSurfaceS());
        return createSignalStrategyS(surfaces);

    }

    public static StrategyDefinitionParameter createSignalStrategyS(List surfaceStrategyParameters) {
        StrategyDefinitionParameter signalS = new StrategyDefinitionParameter(S_MULTIPLE_SURFACE, MultipleSurfaceSignalFactory.class.getName());

        signalS.addParameter(new Parameter(P_SIGNAL_SURFACES, surfaceStrategyParameters));

        return signalS;

    }


    public MultipleSurfaceSignalStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean isForwardEvents) {
        super(sparam, params, isForwardEvents);

        initialiseSignalSurfaces();
    }

    private void initialiseSignalSurfaces() {
        _signalSurfaces = new ArrayList();
        List surfaceStrategyParameters = (List)super.getParameter(P_SIGNAL_SURFACES).getValue();
        for (Iterator itr = surfaceStrategyParameters.iterator(); itr.hasNext();) {
            StrategyDefinitionParameter surfaceS = (StrategyDefinitionParameter)itr.next();
            SignalSurfaceStrategyBase signalSurface = SignalSurfaceStrategyFactory.createSignalSurface(surfaceS, super.getParameterMap());
            _signalSurfaces.add(signalSurface);
        }
    }


    public List getSignalSurfaces() {
        return _signalSurfaces;
    }


    public static final String S_MULTIPLE_SURFACE = "multipleSurface";
    public static final String P_SIGNAL_SURFACES = "signalSurfaces";

    private List _signalSurfaces = new ArrayList();
}
