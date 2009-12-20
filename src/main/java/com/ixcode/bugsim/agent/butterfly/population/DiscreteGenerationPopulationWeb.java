/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.agent.butterfly.population;

import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 2:18:34 PM by jim
 */
public class DiscreteGenerationPopulationWeb implements IPopulationWeb {

    public DiscreteGenerationPopulationWeb(DiscreteGenerationsTimescale timescale) {
        _timescale = timescale;
    }

    public void initialise(BugsimParameterMap bugsimParameterMap, Simulation simulation) {

        _foragerPopulationFactory.initialise(bugsimParameterMap, simulation);

        _populationFactories = new ArrayList();
        _populationFactories.add(_foragerPopulationFactory);

        _generationArchive = new ArrayList();
        _currentGenerationTime = 1;
        _currentGeneration = new DiscreteGeneration(_timescale, _currentGenerationTime, _populationFactories, simulation);

        _haltGenerations = false;

    }


    public void beforeTimestepExecuted(Simulation simulation) {

    }

    /**
     * Do all our work in here ... work out if we need to create some foragers, if so which population ...
     *
     * @param simulation
     */
    public void timestepCompleted(Simulation simulation) {
        nextGenerationTimestep(simulation);
    }

    private void nextGenerationTimestep(Simulation simulation) {
        if (!_currentGeneration.isEndOfGeneration()) {
            _currentGeneration.nextTimestep();
        }

        if (_currentGeneration.isEndOfGeneration()) {
            _currentGeneration.tidyUp();
            _generationArchive.add(_currentGeneration);
            _haltGenerations = _currentGeneration.haltGenerations();
            if (hasNextGeneration()) {
                _currentGenerationTime += 1;
                if (!_haltGenerations) {
                    _currentGeneration = new DiscreteGeneration(_timescale, _currentGenerationTime, _populationFactories, simulation);
                    if (log.isInfoEnabled()) {
                        log.info("Next Generation: " + _currentGenerationTime);
                    }
                }
            }
        }
    }

    public boolean hasNextGeneration() {
        return (((_currentGenerationTime + 1) <= _timescale.getGenerationLimit()) && !_haltGenerations);
    }


    public boolean haltSimulation(Simulation simulation) {
        return (_currentGeneration.isEndOfGeneration() && !hasNextGeneration());
    }

    public String getParameterSummary() {
        return "Discreet Generations: ";
    }

    public IPopulationFactory getForagerPopulationFactory() {
        return _foragerPopulationFactory;
    }

    public IPopulationFactory getCompetitorPopulationFactory() {
        return _competitorPopulationFactory;
    }

    public IPopulationFactory getParasitoidPopulationFactory() {
        return _parasitoidPopulationFactory;
    }

    public List getGenerationArchive() {
        return _generationArchive;
    }

    public DiscreteGeneration getCurrentGeneration() {
        return _currentGeneration;
    }

    public DiscreteGenerationsTimescale getTimescale() {
        return _timescale;
    }

    public List getPopulationFactories() {
        return _populationFactories;
    }

    private static final Logger log = Logger.getLogger(DiscreteGenerationPopulationWeb.class);

    private PopulationFactory _foragerPopulationFactory = new PopulationFactory();
    private PopulationFactory _competitorPopulationFactory = new PopulationFactory();
    private PopulationFactory _parasitoidPopulationFactory = new PopulationFactory();


    private DiscreteGenerationsTimescale _timescale;
    private int _currentGenerationTime;
    private DiscreteGeneration _currentGeneration;
    private List _populationFactories;
    private List _generationArchive = new ArrayList();
    private boolean _haltGenerations;
}
