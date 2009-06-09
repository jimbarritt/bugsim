/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.physical.ILarvalMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.agent.cabbage.EggDistribution;
import com.ixcode.bugsim.model.agent.cabbage.ForagerEgg;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 2, 2007 @ 10:28:53 AM by jim
 */
public class DiscreteGeneration {
    public DiscreteGeneration(DiscreteGenerationsTimescale timescale, int generationTime, List populationFactories, Simulation simulation) {
        _timescale = timescale;
        _time = generationTime;

        _factories = populationFactories;
        _simulation = simulation;

        if (_factories.size() == 0) {
            throw new IllegalArgumentException("Must pass in at least one forager factory!!");
        }

        resetImmigrations();
        resetEggDistributions();
        for (Iterator itr = populationFactories.iterator(); itr.hasNext();) {
            IPopulationFactory populationFactory = (IPopulationFactory)itr.next();
            EggCounter eggCounter = populationFactory.getEggCounter();

            _eggDistributions.add(eggCounter.createEggDistribution());
            eggCounter.removeAllEggs(simulation);

            if (populationFactory.getImmigrationStrategy().hasImmigration(_time)) {
                IImmigrationPattern pattern = populationFactory.getImmigrationStrategy().getImmigration(_time);
                _immigrations.add(new DiscreteImmigration(_time, pattern, _simulation, populationFactory.getForagerFactory(), eggCounter));
            }
        }

        if (hasNextImmigration()) {
            nextImmigration();
        } else if (hasNextEggDistribution()) {
            nextEggDistribution();
        }

    }

    private void resetImmigrations() {
        _immigrations = new ArrayList();
        _immigrationIndex = -1;
    }

    private void resetEggDistributions() {
        _eggDistributions = new ArrayList();
        _eggDistributionIndex = -1;
    }


    /**

     */
    public void nextTimestep() {
        if (_currentImmigration != null) {
            if (!_currentImmigration.isImmigrationComplete()) {
                _currentImmigration.nextTimestep();
            } else if (hasNextImmigration()) {
                nextImmigration();
            } else {
                tidyUpImmigration();
                _currentImmigration = null;

                if (hasNextEggDistribution()) {
                    nextEggDistribution();
                }
            }
        } else if (_currentBirth != null) {
            if (!_currentBirth.isBirthComplete()) {
                _currentBirth.nextTimestep();
            } else if (hasNextEggDistribution()) {
                nextEggDistribution();
            } else {
                tidyUpBirth();
                _currentBirth = null;
            }
        }
        _timestepsThisGeneration += 1;
    }


    private void nextEggDistribution() {
        tidyUpBirth();
        _eggDistributionIndex += 1;
        EggDistribution eggDistribution = (EggDistribution)_eggDistributions.get(_eggDistributionIndex);
        IPopulationFactory populationFactory = (IPopulationFactory)_factories.get(_eggDistributionIndex);
        populationFactory.getEggCounter().setEggLimit(0);

        processLarvalMortality(populationFactory.getForagerFactory().getForagerStrategies().getLarvalMortalityStrategy(), eggDistribution);
        _currentBirth = new DiscreteBirth(_time, _simulation, populationFactory.getForagerFactory(), eggDistribution);

        _currentPopulation = _currentBirth.getPopulation();

    }

    /**
     * Go through and for each egg calculate its mortality and remove those that die.
     *
     * @param eggDistribution
     * @todo somehow need to store this value somewhere...
     * @todo at some point we are going to need real egg agents ...
     */
    private void processLarvalMortality(ILarvalMortalityStrategy mortalityStrategy, EggDistribution eggDistribution) {
        List eggs = new ArrayList(eggDistribution.getEggs());

        for (Iterator itr = eggs.iterator(); itr.hasNext();) {
            ForagerEgg egg = (ForagerEgg)itr.next();
            if (mortalityStrategy.isLarvaeDead(egg)) {
                eggDistribution.removeEgg(egg);
            }
        }



    }

    private boolean hasNextEggDistribution() {
        return _eggDistributionIndex + 1 < _eggDistributions.size();
    }

    private boolean hasNextImmigration() {
        return _immigrationIndex + 1 < _immigrations.size();
    }

    private void nextImmigration() {
        tidyUpImmigration();
        _immigrationIndex += 1;
        _currentImmigration = (DiscreteImmigration)_immigrations.get(_immigrationIndex);
        _currentPopulation = _currentImmigration.getPopulation();
    }

    private void tidyUpBirth() {
        if (_currentBirth != null) {
            _currentBirth.tidyUp();
            _birthArchive.add(_currentBirth);
            _currentBirth = null;
            _currentPopulation = null;
        }

    }


    private void tidyUpImmigration() {
        if (_currentImmigration != null) {
            _currentImmigration.tidyUp();
            _immigrationArchive.add(_currentImmigration);
            _currentImmigration = null;
            _currentPopulation = null;
        }
    }

    public boolean isEndOfGeneration() {
        boolean end = (_timestepsThisGeneration > _timescale.getTimestepLimit()) ||
                ((_currentImmigration == null && !hasNextImmigration()) &&
                (_currentBirth == null && !hasNextEggDistribution()));

        if (end) {
            if (log.isDebugEnabled()) {
                log.debug("endGeneration : timesteps=" + _timestepsThisGeneration + " : limit=" + _timescale.getTimestepLimit() + " : hasCurrentImmigration=" + (_currentImmigration != null) + " : hasNextImmigration=" + hasNextImmigration());
            }
        }
        return end;
    }

    public void tidyUp() {
        recordEggCounts();

        tidyUpImmigration();
        tidyUpBirth();
        List liveForagers = new ArrayList(_simulation.getLiveAgents(ForagingAgentFilter.INSTANCE));
        for (Iterator itr = liveForagers.iterator(); itr.hasNext();) {
            IMotileAgent agent = (IMotileAgent)itr.next();
            agent.kill(_simulation);
        }

    }

    private void recordEggCounts() {

        for (Iterator itr = _factories.iterator(); itr.hasNext();) {
            IPopulationFactory factory = (IPopulationFactory)itr.next();
            factory.getEggCounter().generationCompleted(_time,  _simulation);    
        }
    }

    public List getImmigrationArchive() {
        return _immigrationArchive;
    }


    public IPopulation getCurrentPopulation() {
        return _currentPopulation;
    }

    public int getGenerationTime() {
        return _time;
    }

    /**
     * This generation can decide to be the last - for instance if there are no more eggs or immigrations planned...
     *
     * @return
     */
    public boolean haltGenerations() {
        return false;
    }

    public long getGenerationTimestep() {
        return _timestepsThisGeneration;
    }

    public List getBirthArchive() {
        return _birthArchive;
    }

    private static final Logger log = Logger.getLogger(DiscreteGeneration.class);


    private DiscreteGenerationsTimescale _timescale;
    private int _time;
    private List _factories;

    private long _timestepsThisGeneration;
    private int _currentFactoryIndex;
    private IPopulation _currentPopulation;

    private Simulation _simulation;

    private DiscreteImmigration _currentImmigration;
    private List _immigrationArchive = new ArrayList();
    private List _immigrations;
    private int _immigrationIndex;

    private int _eggDistributionIndex;
    private DiscreteBirth _currentBirth;
    private List _eggDistributions;
    private List _birthArchive = new ArrayList();
}
