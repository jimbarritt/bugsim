/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.agent.butterfly.population;

import com.ixcode.framework.model.ModelBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.SensoryRandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.physical.IAdultMortalityStrategy;
import com.ixcode.framework.simulation.model.agent.physical.ILarvalMortalityStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.parameter.model.ParameterisedStrategyFactory;
import com.ixcode.bugsim.model.agent.butterfly.*;
import com.ixcode.bugsim.model.agent.butterfly.immigration.IImmigrationStrategy;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.PopulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.ImmigrationStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 *
 */
class PopulationFactory extends ModelBase implements IPopulationFactory {


    public void initialise(BugsimParameterMap bugsimParams, Simulation simulation) {


        ForagerCategory foragerCategory = bugsimParams.getForagerCategory();

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(simulation);

        _immigrationStrategy = createImmigrationStrategy(foragerCategory, initObjects);

        _foragerFactory = createForagerFactory(foragerCategory, initObjects, bugsimParams.getParameterMap());

        if (_eggCounter != null) {
            _eggCounter.unregister();
        }
        ResourceCategory resource = bugsimParams.getResourceCategory();
        _eggCounter = new EggCounter(_foragerFactory.getAgentClass(), resource.getEggCountInterval());

        _eggCounter.register(simulation.getLiveAgents(CabbageAgentFilter.INSTANCE));


    }



    public String getParameterSummary() {
        return "Population Factory";
    }

    public IImmigrationStrategy getImmigrationStrategy() {
        return _immigrationStrategy;
    }


    public IForagerFactory getForagerFactory() {
        return _foragerFactory;
    }



    private ForagerFactory createForagerFactory(ForagerCategory foragerCategory, Map initObjects, ParameterMap params) {
        BehaviourCategory behaviourC = foragerCategory.getBehaviourCategory();

        SensorCategory sensorCategory = foragerCategory.getSensorCategory();
        PopulationCategory populationCategory = foragerCategory.getPopulationCategory();

        IMovementStrategy movement= (IMovementStrategy)behaviourC.getMovementStrategy().instantiateImplementedStrategy(initObjects);
        IForagingStrategy foraging= (IForagingStrategy)behaviourC.getForagingStrategy().instantiateImplementedStrategy(initObjects);
        IVisionStrategy vision= (IVisionStrategy)sensorCategory.getVisualSensorStrategy().instantiateImplementedStrategy(initObjects);
        IOlfactionStrategy olfaction= (IOlfactionStrategy)sensorCategory.getOlfactorySensorStrategy().instantiateImplementedStrategy(initObjects);

        updateEnabledStateOfSensoryStrategies(movement, vision, olfaction);

        IAdultMortalityStrategy adultMortality = (IAdultMortalityStrategy)populationCategory.getAdultMortality().instantiateImplementedStrategy(initObjects);
        ILarvalMortalityStrategy larvalMortality = (ILarvalMortalityStrategy)populationCategory.getLarvalMortality().instantiateImplementedStrategy(initObjects);

        List motivations= createMotivationStrategies(params, initObjects);

        ForagerAgentStrategies strategies = new ForagerAgentStrategies(movement, adultMortality, larvalMortality, foraging, vision, olfaction,  motivations);

        PopulationCategory populationC = foragerCategory.getPopulationCategory();

        String agentClassName = populationC.getAgentClass();

        return new ForagerFactory(agentClassName, strategies, behaviourC.isRecordLifeHistory());
    }

    /**
     * this is a temporary method until we move the vision and olfaction enabled flags to the various sensor categories....
     * @todo move them !!
     * @param movement
     * @param vision
     * @param olfaction
     */
    private void updateEnabledStateOfSensoryStrategies(IMovementStrategy movement, IVisionStrategy vision, IOlfactionStrategy olfaction) {
        boolean visionEnabled = false;
        boolean olfactionEnabled=false;
        if (movement instanceof SensoryRandomWalkStrategy) {
            SensoryRandomWalkStrategy srws = (SensoryRandomWalkStrategy)movement;
            visionEnabled = srws.isVisionEnabled();
            olfactionEnabled = srws.isOlfactionEnabled();
        }

        vision.setVisionEnabled(visionEnabled);
        olfaction.setOlfactionEnabled(olfactionEnabled);
    }

    private List createMotivationStrategies(ParameterMap params, Map initObjects) {
        List motivationStrategyPs = new ArrayList();         // Not using these at the moment.
        List motivationStrategies = new ArrayList();
        for (Iterator itr = motivationStrategyPs.iterator(); itr.hasNext();) {
            StrategyDefinitionParameter strategyP = (StrategyDefinitionParameter)itr.next();
            IMotivationStrategy motivationS = (IMotivationStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP, params, initObjects);
            motivationStrategies.add(motivationS);
        }
        return motivationStrategies;
    }

    private IImmigrationStrategy createImmigrationStrategy(ForagerCategory foragerCategory, Map initObjects) {
        PopulationCategory populationC = foragerCategory.getPopulationCategory();

        ImmigrationStrategyBase immigrationStrategy = populationC.getImmigrationStrategy();
        return (IImmigrationStrategy)immigrationStrategy.instantiateImplementedStrategy(initObjects);
    }



    public EggCounter getEggCounter() {
        return _eggCounter;
    }



    private static final Logger log = Logger.getLogger(PopulationFactory.class);



    private ForagerFactory _foragerFactory;
    private IImmigrationStrategy _immigrationStrategy;

    private EggCounter _eggCounter;

}
