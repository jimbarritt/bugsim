/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.bugsim.experiment.IBugsimExperiment;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.IImmigrationPattern;
import com.ixcode.bugsim.agent.butterfly.immigration.pattern.RandomReleaseImmigrationPattern;
import com.ixcode.bugsim.agent.butterfly.immigration.IImmigrationStrategy;
import com.ixcode.bugsim.agent.butterfly.immigration.InitialImmigrationStrategy;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentClassIdFilter;
import com.ixcode.framework.simulation.model.agent.boundary.BoundaryCrossingRecorderAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.FixedMoveLengthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.IMovementStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryCrossing;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class BoundaryCrossingWriter extends SimulationWriterBase {


    /**
     * @todo should really sort out simulation so that it knows about the experiment which created it - at the moment it has the old IExperiment interface there
     * @param experiment
     */
    public BoundaryCrossingWriter(IBugsimExperiment experiment) {
        super(true);
        _experiment = experiment;

    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(BoundaryCrossingRow.HEADER_ROW);
    }


    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        List agents = simulation.getLiveAgents(new AgentClassIdFilter(BoundaryCrossingRecorderAgent.AGENT_CLASS_ID));
        if (agents.size() > 0) {
            BoundaryCrossingRecorderAgent recorder = (BoundaryCrossingRecorderAgent)agents.get(0);
            List crossings = recorder.getCrossings();
            if (BoundaryCrossingWriter.log.isInfoEnabled()) {
                BoundaryCrossingWriter.log.info("Reporting " + crossings.size() + " Crossings.");
            }

            for (Iterator itr = crossings.iterator(); itr.hasNext();) {
                BoundaryCrossing crossing = (BoundaryCrossing)itr.next();
                writeCrossing(crossing,  out, experimentId, currentIteration,  currentReplicate, simulation);
            }
        }

    }


    private void writeCrossing(BoundaryCrossing crossing,CSVWriter out,  String experimentId, long currentIteration, long currentReplicant, Simulation simulation) {
        BoundaryCrossingRow row = new BoundaryCrossingRow();

        row.setExperimentId(experimentId);
        row.setIterationId(FORMAT.format(new Long(currentIteration)));
        row.setReplicateId(FORMAT.format(new Long(currentReplicant)));

        row.setCrossing(crossing);

        IImmigrationStrategy immigrationStrategy = _experiment.getPopulationWeb().getForagerPopulationFactory().getImmigrationStrategy();
        if (immigrationStrategy instanceof InitialImmigrationStrategy) {
            InitialImmigrationStrategy iis = (InitialImmigrationStrategy)immigrationStrategy;
            IImmigrationPattern immigrationPattern= iis.getImmigrationPattern();
            if (immigrationPattern instanceof RandomReleaseImmigrationPattern) {
                row.setB(((RandomReleaseImmigrationPattern)immigrationPattern).getB());
            }
        }

        // @todo could really get this directly from the parameter map...
        IMovementStrategy movementStrategy = _experiment.getPopulationWeb().getForagerPopulationFactory().getForagerFactory().getForagerStrategies().getMovementStrategy();
        if (movementStrategy instanceof RandomWalkStrategy) {
            RandomWalkStrategy cr = (RandomWalkStrategy)movementStrategy;

            if (cr.getAzimuthGenerator() instanceof GaussianAzimuthGenerator) {
                GaussianAzimuthGenerator g = (GaussianAzimuthGenerator)cr.getAzimuthGenerator();
                row.setA(g.getStdDeviation());
            }

            if (cr.getMoveLengthGenerator() instanceof FixedMoveLengthGenerator) {
                FixedMoveLengthGenerator fml = (FixedMoveLengthGenerator)cr.getMoveLengthGenerator();
                row.setL(fml.getMoveLength());
            }

        }
        out.writeRow(row);

    }


    private Format FORMAT = new DecimalFormat("000");
    private static final Logger log = Logger.getLogger(BoundaryCrossingWriter.class);
    private IBugsimExperiment _experiment; //@todo create a butterfly base class which has the butterfly factory
}
