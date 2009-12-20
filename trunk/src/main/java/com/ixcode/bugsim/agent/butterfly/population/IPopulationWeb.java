/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.agent.cabbage.EggCounter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.ISimulationDisruptor;
import com.ixcode.framework.simulation.model.ITimestepListener;
import com.ixcode.framework.simulation.model.timescale.ITimescale;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : Represents the populations of all the foragers, competitors and parasitoids...
 * Created     : Mar 1, 2007 @ 3:20:55 PM by jim
 */
public interface IPopulationWeb extends ITimestepListener, ISimulationDisruptor {

    void initialise(BugsimParameterMap bugsimParameters, Simulation simulation);

    String getParameterSummary();


    IPopulationFactory getForagerPopulationFactory();

    IPopulationFactory getCompetitorPopulationFactory();

    IPopulationFactory getParasitoidPopulationFactory();

}
