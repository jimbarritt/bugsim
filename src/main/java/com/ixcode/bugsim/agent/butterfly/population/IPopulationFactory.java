/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.framework.simulation.model.ISimulationDisruptor;
import com.ixcode.framework.simulation.model.ISimulationListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.agent.cabbage.EggCounter;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.agent.butterfly.immigration.IImmigrationStrategy;
import com.ixcode.bugsim.agent.butterfly.ForagerAgentStrategies;

import java.beans.PropertyChangeListener;

/**
 * Description : Public interface of the population factory.
 * Created     : Mar 1, 2007 @ 2:04:44 PM by jim
 */
public interface IPopulationFactory {

    void initialise(BugsimParameterMap bugsimParams, Simulation simulation);

    EggCounter getEggCounter();


    IForagerFactory getForagerFactory();


    IImmigrationStrategy getImmigrationStrategy();


}
