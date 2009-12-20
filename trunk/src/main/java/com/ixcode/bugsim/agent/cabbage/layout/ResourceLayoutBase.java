/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.agent.cabbage.layout;

import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;

import java.util.Map;

/**
 * Description : Knows how to layout cabbages in a certain way
 *
 * @todo - could refactor all this to be a generic "Resource" layout strategy - and then simply provide a factory for the *kind* of resource to be laid out. this would be a real cabbage factory.
 */
public abstract class ResourceLayoutBase implements IResourceLayout, IParameterisedStrategy {


    public void initialise(StrategyDefinitionParameter strategyParam, ParameterMap params, Map initialisationObjects) {


//        _signalSurfaceStrategies = CabbageParameters.getSignalSurfaceStrategies(strategyParam);
//        _signalSurfaceFactories = new ArrayList();
//
//        for (Iterator itr = _signalSurfaceStrategies.iterator(); itr.hasNext();) {
//            StrategyDefinitionParameter strategyP = (StrategyDefinitionParameter)itr.next();
//            ISignalSurfaceFactory signalSurfaceFactory = (ISignalSurfaceFactory)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP, params, initialisationObjects);
//            _signalSurfaceFactories.add(signalSurfaceFactory);
//        }


    }

    /**
     * @param landscape
     */
    protected void createCabbageInformationSurfaces(Landscape landscape) {

//        for (Iterator iterator = _signalSurfaceFactories.iterator(); iterator.hasNext();) {
//            ISignalSurfaceFactory signalSurfaceFactory = (ISignalSurfaceFactory)iterator.next();
//            ISignalSurface surface = signalSurfaceFactory.createSignalSurface(landscape);
//            landscape.addSignalSurface(surface);
//        }

    }


    public String getResourceLayoutGridName() {
        return GRID_NAME;
    }

    public static Grid getResourceLayoutGrid(Landscape landscape) {
        return landscape.getGrid(GRID_NAME);
    }

    public String getParameterSummary() {
        return "";
    }


    private static final String GRID_NAME = "Experimental Area";
//    private ISignalFunction _signalDecayFunction;
//    private double _standardDeviation;
//    private double _magnification;


//    private List _signalSurfaceStrategies;
//    private List _signalSurfaceFactories;
}
