/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.framework.simulation.model.ISimulationListener;

/**
 * Description : Public interface to the population
 * Created     : Mar 1, 2007 @ 2:12:28 PM by jim
 */
public interface IPopulation extends ISimulationListener  {

    String getPopulationLabel();

    String getForagersRemainingFormatted();

    String getForagersAliveFormatted();

    long getForagersAlive();

    long getForagersRemaining();

    long getForagersDead();

    long getForagersEscaped();

    long getLaraveDead();

    
    long getEggCount();

    long getPopulationSize();

    Class getAgentClass();
}
