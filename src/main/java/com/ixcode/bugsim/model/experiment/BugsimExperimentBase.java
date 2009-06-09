/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.report.IExperimentReporter;
import com.ixcode.framework.simulation.experiment.SimulationExperimentBase;
import com.ixcode.framework.simulation.model.Simulation;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class BugsimExperimentBase extends SimulationExperimentBase implements IBugsimExperiment {

    public BugsimExperimentBase(Simulation simulation, ExperimentPlan plan, IExperimentReporter reporter) {
        super(simulation, plan, reporter);
    }

    public BugsimExperimentBase(Simulation simulation, ExperimentPlan plan, IExperimentReporter reporter, IExperimentReporter summaryReporter) {
        super(simulation, plan, reporter, summaryReporter);
    }


}
