/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.experimentX.configuration;

import com.ixcode.bugsim.model.experiment.experimentX.IExperimentXConfiguration;
import com.ixcode.bugsim.model.experiment.experimentX.ExperimentX;
import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.agent.dispersal.DispersalDistanceRecorder;
import com.ixcode.framework.simulation.model.agent.motile.release.ReleaseRandomAroundBorderStrategy;
import com.ixcode.framework.simulation.model.agent.boundary.BoundaryCrossingRecorderAgent;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.timescale.ITimescale;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;

import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Mar 1, 2007 @ 11:28:39 AM by jim
 */
public class SimulationConfiguration implements IExperimentXConfiguration {

    public void initialise(ExperimentX experimentX, BugsimParameterMap bugsimParameters) {
        SimulationCategory simulationC = bugsimParameters.getSimulationCategory();

        experimentX.getSimulation().setArchiveRemovedAgents(simulationC.getArchiveRemovedAgents());

        setArchiveEscapedAgents(simulationC, experimentX.getSimulation());

        initialiseBoundaryRecorder(experimentX, simulationC);

        initialiseDispersalRecorder(experimentX, simulationC);

        Map initObjects = SimulationCategory.createSimulationStrategyInitialisation(experimentX.getSimulation());
        ITimescale timescale = (ITimescale)simulationC.getTimescale().instantiateImplementedStrategy(initObjects);
        experimentX.setTimescale(timescale);
    }

    /**
     * @param simulationC
     * @todo going to have to tidy this up to get multiple agents running...
     */
    private void setArchiveEscapedAgents(SimulationCategory simulationC, Simulation simulation) {
        if (!simulationC.getArchiveEscapedAgents()) {
            simulation.setAgentArchiveFilter(new IAgentFilter() {
                public boolean acceptAgent(IAgent agent) {
                    boolean accept = true;
                    if (agent instanceof ButterflyAgent) {
                        ButterflyAgent bf = (ButterflyAgent)agent;
                        if (bf.getBehaviour() == ForagingAgentBehaviour.ESCAPED) {
                            accept = false;
                        }
                    }

                    return accept;
                }
            });
        }
    }

    private void initialiseBoundaryRecorder(ExperimentX experimentX, SimulationCategory simulationC) {
        Simulation simulation = experimentX.getSimulation();

        if (simulationC.getRecordBoundaryCrossings()) {
            if (simulation.getLandscape().hasGrid(ReleaseRandomAroundBorderStrategy.ZERO_BOUNDARY_GRID)) {
                Grid zeroBoundaryGrid = simulation.getLandscape().getGrid(ReleaseRandomAroundBorderStrategy.ZERO_BOUNDARY_GRID);
                BoundaryCrossingRecorderAgent recorder = new BoundaryCrossingRecorderAgent(zeroBoundaryGrid, simulation, ForagingAgentFilter.INSTANCE);
                simulation.addAgent(recorder);
            }
        }
    }

    private void initialiseDispersalRecorder(ExperimentX experimentX, SimulationCategory simulationC) {
        Simulation simulation = experimentX.getSimulation();
        if (simulationC.isRecordDispersalStatistics()) {
            DispersalDistanceRecorder recorder = new DispersalDistanceRecorder(simulation, ForagingAgentFilter.INSTANCE);
            simulation.addAgent(recorder);
        }

    }
}

