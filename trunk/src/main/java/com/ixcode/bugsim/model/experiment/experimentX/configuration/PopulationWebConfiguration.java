/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.experimentX.configuration;

import com.ixcode.bugsim.model.experiment.experimentX.IExperimentXConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.agent.butterfly.population.IPopulationWeb;
import com.ixcode.bugsim.model.agent.butterfly.population.DiscreteGenerationPopulationWeb;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.framework.simulation.model.timescale.ITimescale;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;
import com.ixcode.framework.simulation.model.EscapingAgentCatcher;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.math.geometry.CartesianBounds;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Mar 1, 2007 @ 11:29:48 AM by jim
 */
public class PopulationWebConfiguration implements IExperimentXConfiguration  {

    public void initialise(ExperimentX experimentX, BugsimParameterMap bugsimParameters) {

        ITimescale timescale = experimentX.getTimescale();

        IPopulationWeb populationWeb = experimentX.getPopulationWeb();
        if (populationWeb == null) {
            populationWeb = createPopulationWeb(timescale, experimentX);
        }

        populationWeb.initialise(bugsimParameters, experimentX.getSimulation());


        EscapingAgentCatcher catcher = experimentX.getEscapingAgentCatcher();
        if (catcher != null) {
            catcher.tidyUp();            
        }
        catcher = createEscapingAgentCatcher(experimentX.getSimulation());
            experimentX.setEscapingAgentCatcher(catcher);

    }

    private IPopulationWeb createPopulationWeb(ITimescale timescale, ExperimentX experimentX) {

        IPopulationWeb populationWeb = null;
        if (timescale instanceof DiscreteGenerationsTimescale) {
            populationWeb = new DiscreteGenerationPopulationWeb((DiscreteGenerationsTimescale)timescale);
        } else {
            throw new IllegalStateException("We do not yet support continuous Time!");
        }

        experimentX.setPopulationWeb(populationWeb); // do this first so that it gets to listen to the agents being created.
        return populationWeb;
    }


    private EscapingAgentCatcher createEscapingAgentCatcher(Simulation simulation) {
        Landscape landscape = simulation.getLandscape();
        CartesianBounds escapeBounds = landscape.getLogicalBounds();
        boolean isCircular = landscape.isCircular();
//        if (){                                    // This is to make the zero boundary the escape boundary - can simply add this as a parameter of landscape now.
//            Grid zeroGrid = simulation.getLandscape().getGrid(Landscape.ZERO_BOUNDARY_GRID);
//            escapeBounds = zeroGrid.getBounds();
//            isCircular = zeroGrid.isCircular();
//        }
        return new EscapingAgentCatcher(simulation, escapeBounds, isCircular, ForagingAgentFilter.INSTANCE);

    }
}
