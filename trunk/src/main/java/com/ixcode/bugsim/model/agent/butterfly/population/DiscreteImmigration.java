/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.javabean.IntrospectionUtils;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 2, 2007 @ 11:12:01 AM by jim
 */
public class DiscreteImmigration {

    public DiscreteImmigration(int generation, IImmigrationPattern pattern, Simulation simulation, IForagerFactory foragerFactory, EggCounter eggCounter) {
        _simulation = simulation;
        _pattern = pattern;
        _foragerFactory = foragerFactory;
        _eggCounter = eggCounter;

        _immigrantPopulation = new ForagerPopulation(createPopulationLabel(generation, foragerFactory), _pattern.getNumberToRelease(), foragerFactory.getAgentFilter());
        _simulation.addSimulationListener(_immigrantPopulation);
        _eggCounter.resetCounter(_pattern.getEggLimit());

    }

    private String createPopulationLabel(int generation, IForagerFactory foragerFactory) {
        return "IMMIG-" + FORMAT.format(generation) + "-" + IntrospectionUtils.getShortClassName(foragerFactory.getAgentClass());
    }

    /**
     * With the egg limit westill can't complete until all the foragers are dead. once the immigration is complete... we
     * need to kill off all the foragers but they might not die until next timestep...
     * @return
     */
    public boolean isImmigrationComplete() {
        return  (_immigrantPopulation.getForagersAlive() ==0 && _immigrantPopulation.getForagersRemaining() ==0) ||
                (_eggCounter.getTotalEggs()>=_pattern.getEggLimit() && _immigrantPopulation.getForagersAlive() == 0);
    }

    public void nextTimestep() {
        if (_immigrantPopulation.getForagersRemaining() > 0 && _immigrantPopulation.getForagersAlive() == 0) {
            RectangularCoordinate location = _pattern.nextReleaseLocation();
            double initialMoveLength = _pattern.nextReleaseMoveLength(_foragerFactory.getForagerStrategies().getMovementStrategy());
            double azimuth = _pattern.nextReleaseAzimuth(location, initialMoveLength);
            IMotileAgent forager = _foragerFactory.createForager(location,azimuth,  ForagingAgentBehaviour.RELEASE);
            _simulation.addAgent(forager);
        }

    }

    public void tidyUp() {
        _immigrantPopulation.killAllAgents(_simulation);
        _simulation.removeSimulationListener(_immigrantPopulation);
    }

    public IPopulation getPopulation() {
        return _immigrantPopulation;
    }

    private static final Logger log = Logger.getLogger(DiscreteImmigration.class);
    private static final DecimalFormat FORMAT = new DecimalFormat("00");
    private ForagerPopulation _immigrantPopulation;
    private Simulation _simulation;
    private IImmigrationPattern _pattern;
    private IForagerFactory _foragerFactory;
    private EggCounter _eggCounter;
}
