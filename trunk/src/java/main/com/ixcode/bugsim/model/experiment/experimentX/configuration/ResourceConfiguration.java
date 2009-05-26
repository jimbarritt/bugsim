/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.experimentX.configuration;

import com.ixcode.bugsim.model.experiment.experimentX.IExperimentXConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.SignalStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.MultipleSurfaceSignalStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.SignalSurfaceStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.agent.cabbage.layout.IResourceLayout;
import com.ixcode.bugsim.model.agent.cabbage.signal.IResourceSignalFactory;
import com.ixcode.framework.simulation.model.agent.surveyor.SignalSurveyingAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 11:29:59 AM by jim
 */
public class ResourceConfiguration implements IExperimentXConfiguration {
    public void initialise(ExperimentX experimentX, BugsimParameterMap bugsimParameters) {

        ParameterMap params = bugsimParameters.getParameterMap();
        Landscape landscape = experimentX.getSimulation().getLandscape();

        Parameter layoutP = CabbageParameters.getCabbageLayoutP(params);


        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(landscape.getSimulation());

        StrategyDefinitionParameter layoutStrategy = (StrategyDefinitionParameter)layoutP.getValue();

        IResourceLayout resourceLayout = (IResourceLayout)ParameterisedStrategyFactory.createParameterisedStrategy(layoutStrategy, params, initObjects);
        resourceLayout.createCabbages(landscape.getSimulation());
        experimentX.setResourceLayout(resourceLayout);

        ResourceCategory resourceC = bugsimParameters.getResourceCategory();

        IResourceSignalFactory signalFactory = resourceC.getResourceSignal().createSignalFactory(initObjects);
        signalFactory.createResourceSignal(landscape);

        initialiseInformationSurveyor(experimentX, bugsimParameters);



    }

    private void initialiseInformationSurveyor(ExperimentX experimentX, BugsimParameterMap params) {
        SignalStrategyBase ssb = params.getResourceCategory().getResourceSignal();

        if (!(ssb instanceof MultipleSurfaceSignalStrategy)) {
            throw new IllegalStateException("Cannot deal with anything other than a MultipleSurfaceSignalStrategy");
        }
        MultipleSurfaceSignalStrategy mss = (MultipleSurfaceSignalStrategy)ssb;

        List signalSurfaces = mss.getSignalSurfaces();
        if (signalSurfaces.size() > 1) {
            throw new IllegalStateException("Must only have 1 signal surface at the moment");
        }
        SignalSurfaceStrategyBase sssb = (SignalSurfaceStrategyBase)signalSurfaces.get(0);
        if (!(sssb instanceof FunctionalSignalSurfaceStrategy)) {
            throw new IllegalStateException("Must have a functional signal surface strategy");
        }
        FunctionalSignalSurfaceStrategy fss = (FunctionalSignalSurfaceStrategy)sssb;



        if (fss.getIncludeSurvey()) {
            SignalSurveyingAgent surveyor = new SignalSurveyingAgent(experimentX, fss.getSurfaceName(), fss.getSurveyResolution());
            experimentX.getSimulation().addAgent(surveyor);
        }
    }

    private static final Logger log = Logger.getLogger(ResourceConfiguration.class);

}
