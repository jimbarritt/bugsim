/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.view.experiment.experimentX;


import com.ixcode.bugsim.model.agent.butterfly.population.*;
import com.ixcode.bugsim.model.agent.cabbage.EggCounter;
import com.ixcode.bugsim.model.experiment.IBugsimExperiment;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.framework.javabean.JavaBeanException;
import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.swing.property.PropertyBinding;
import com.ixcode.framework.swing.property.PropertyGroupPanel;
import com.ixcode.framework.swing.property.ReadOnlyPropertyEditor;
import com.ixcode.framework.simulation.model.ITimestepListener;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.timescale.DiscreteGenerationsTimescale;
import com.ixcode.framework.experiment.model.IExperimentListener;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.parameter.model.ParameterMap;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentXProgressPanel extends PropertyGroupPanel implements ITimestepListener , IExperimentListener {

    public ExperimentXProgressPanel(IBugsimExperiment experiment) throws JavaBeanException {
        super("Population Status");
        IModelAdapter modelAdapter = new JavaBeanModelAdapter();

        _experiment = (ExperimentX)experiment;

        _generation = new ReadOnlyPropertyEditor("generation", "Current Generation", 150);
        super.addPropertyEditor(_generation);

        _population = new ReadOnlyPropertyEditor(ForagerPopulation.P_POPULATION_LABEL, "Current Population", 150);
        super.addPropertyEditor(_population);

        _remaining = new ReadOnlyPropertyEditor(ForagerPopulation.P_FORAGERS_REMAINING_FORMATTED, "Foragers Remaining", 150);
        super.addPropertyEditor(_remaining);


        _alive = new ReadOnlyPropertyEditor(ForagerPopulation.P_FORAGERS_ALIVE_FORMATTED, "Foragers Alive", 150);
        super.addPropertyEditor(_alive);


        if (experiment.getPopulationWeb()!= null) {
            EggCounter eggCounter = experiment.getPopulationWeb().getForagerPopulationFactory().getEggCounter();
            _eggCount = new ReadOnlyPropertyEditor(EggCounter.PROPERTY_EGG_COUNT_FORMATTED, eggCounter.getCounterId() + " Eggs", 150);
        } else {
            _eggCount = new ReadOnlyPropertyEditor(EggCounter.PROPERTY_EGG_COUNT_FORMATTED, "Eggs", 150);
        }
//        PropertyBinding.bindEditor(_eggCount, modelAdapter, eggCounter);
        super.addPropertyEditor(_eggCount);

        experiment.getSimulation().addTimestepListener(this);
        updateFields();
        _experiment.addExperimentListener(this);

    }

    public void experimentInitialised(IExperiment source, ExperimentPlan plan) {
        updateFields();
    }

    public void iterationInitialised(IExperiment source, ParameterMap currentParameters, ExperimentProgress experimentProgress) {
        updateFields();
    }

    public void beforeTimestepExecuted(Simulation simulation) {

    }

    public void timestepCompleted(Simulation simulation) {
        updateFields();

    }

    private void updateFields() {
        IPopulationWeb populationWeb = _experiment.getPopulationWeb();
        if (!(populationWeb instanceof DiscreteGenerationPopulationWeb)) {
            throw new IllegalStateException("Can only report progress for discrete generations!");
        }
        DiscreteGenerationPopulationWeb discreteWeb = (DiscreteGenerationPopulationWeb)populationWeb;

        DiscreteGenerationsTimescale timescale = discreteWeb.getTimescale();
        DiscreteGeneration gen = discreteWeb.getCurrentGeneration();
        IPopulation population = gen.getCurrentPopulation();

        String generationStatus;

        if (discreteWeb.getCurrentGeneration().isEndOfGeneration() && !discreteWeb.hasNextGeneration()) {
            generationStatus = "Complete";
        } else {
            generationStatus = gen.getGenerationTime() + " of " + timescale.getGenerationLimit() + " ("  + gen.getGenerationTimestep() +  " step of " + timescale.getTimestepLimit() + ")";
        }
        _generation.setValue(generationStatus);
        if (population != null) {
            _population.setValue(population.getPopulationLabel());
            _alive.setValue(population.getForagersAliveFormatted());
            _remaining.setValue(population.getForagersRemainingFormatted());

        } else {
            _population.setValue(NA);
            _alive.setValue(NA);
            _remaining.setValue(NA);
            _eggCount.setValue(NA);
        }

            EggCounter eggCounter = _experiment.getPopulationWeb().getForagerPopulationFactory().getEggCounter();
            _eggCount.setValue(eggCounter.getTotalEggsFormatted());
    }


    private static final String NA = "N/A";
    private ReadOnlyPropertyEditor _remaining;
    private ReadOnlyPropertyEditor _alive;
    private ReadOnlyPropertyEditor _eggCount;
    private ExperimentX _experiment;
    private ReadOnlyPropertyEditor _population;
    private ReadOnlyPropertyEditor _generation;
}
