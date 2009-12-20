/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.cabbage.signal;

import com.ixcode.bugsim.experiment.parameter.resource.CabbageParameters;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.ISignalSurface;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *  Description : Knows how to initialise a Signal Surface based signal
 *  Created     : Jan 25, 2007 @ 12:14:17 PM by jim
 */
public class MultipleSurfaceSignalFactory implements IResourceSignalFactory, IParameterisedStrategy  {

    public void initialise(StrategyDefinitionParameter signalS, ParameterMap params, Map initialisationObjects) {
        _signalSurfaceStrategies = CabbageParameters.getSignalSurfaceStrategies(signalS);
        _signalSurfaceFactories = new ArrayList();

        for (Iterator itr = _signalSurfaceStrategies.iterator(); itr.hasNext();) {
            StrategyDefinitionParameter surfaceS = (StrategyDefinitionParameter)itr.next();
            ISignalSurfaceFactory signalSurfaceFactory = (ISignalSurfaceFactory)ParameterisedStrategyFactory.createParameterisedStrategy(surfaceS, params, initialisationObjects);
            _signalSurfaceFactories.add(signalSurfaceFactory);
        }
    }

    public void createResourceSignal(Landscape landscape) {

           for (Iterator iterator = _signalSurfaceFactories.iterator(); iterator.hasNext();) {
            ISignalSurfaceFactory signalSurfaceFactory = (ISignalSurfaceFactory)iterator.next();
            ISignalSurface surface = signalSurfaceFactory.createSignalSurface(landscape);
            landscape.addSignalSurface(surface);
        }
    }


    public String getParameterSummary() {
          return null;
      }







    private List _signalSurfaceStrategies;
    private List _signalSurfaceFactories;
}
