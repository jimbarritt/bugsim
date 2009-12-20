/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.bugsim.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.agent.cabbage.EggDistribution;
import com.ixcode.bugsim.agent.cabbage.ForagerEgg;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.IAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.UniformAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.resource.IResourceAgent;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 2, 2007 @ 11:12:01 AM by jim
 */
public class DiscreteBirth {

    public DiscreteBirth(int generation, Simulation simulation, IForagerFactory foragerFactory, EggDistribution eggDistribution) {
        _simulation = simulation;

        _foragerFactory = foragerFactory;
        _eggDistribution = eggDistribution;

        _patchPopulation = new ForagerPopulation(createPopulationLabel(generation, foragerFactory),eggDistribution.getTotalEggs(),  foragerFactory.getAgentFilter(), eggDistribution.getTotalEggs(), eggDistribution.getTotalDeadEggs());
        _simulation.addSimulationListener(_patchPopulation);

        _eggIndex = 0;
        _randomAzimuthGenerator = new UniformAzimuthGenerator(simulation.getRandom());


    }



    private String createPopulationLabel(int generation, IForagerFactory foragerFactory) {
        return "BIRTH-" + DiscreteBirth.FORMAT.format(generation) + "-" + IntrospectionUtils.getShortClassName(foragerFactory.getAgentClass());
    }

    /**
     * Do not have an egg limit here - just run until all the foragers are either dead or escaped...
     * @return
     */
    public boolean isBirthComplete() {
        return  (_patchPopulation.getForagersAlive() <=0 && _patchPopulation.getForagersRemaining() <=0);
    }

    public void nextTimestep() {
        if (_patchPopulation.getForagersRemaining() > 0 && _patchPopulation.getForagersAlive() == 0) {
            double azimuth = _randomAzimuthGenerator.generateCourseChange(0).getNewAzimuth();
            ForagerEgg egg= _eggDistribution.getEgg(_eggIndex);
            RectangularCoordinate location = egg.getLocation();
            IMotileAgent forager = _foragerFactory.createForager(location, azimuth,  ForagingAgentBehaviour.RELEASE);
            IResourceAgent resource = _eggDistribution.findResource(egg, _simulation);
            ((ButterflyAgent)forager).setCurrentCabbage((CabbageAgent)resource);
            _simulation.addAgent(forager);
            _eggIndex++; // Should never go over totalEggs cos we should stop before then.
        }

    }

    public void tidyUp() {
        _simulation.removeSimulationListener(_patchPopulation);
    }

    public IPopulation getPopulation() {
        return _patchPopulation;
    }

    private static final Logger log = Logger.getLogger(DiscreteBirth.class);
    private static final DecimalFormat FORMAT = new DecimalFormat("00");
    private ForagerPopulation _patchPopulation;
    private Simulation _simulation;

    private IForagerFactory _foragerFactory;
    private EggDistribution _eggDistribution;
    private int _eggIndex;
    private IAzimuthGenerator _randomAzimuthGenerator;
}
