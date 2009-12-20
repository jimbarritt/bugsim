/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.matchstick;

import com.ixcode.bugsim.agent.boundary.BoundaryAgentIntersection;
import com.ixcode.bugsim.agent.boundary.IBoundaryAgent;
import com.ixcode.bugsim.agent.matchstick.MatchstickAgent;
import com.ixcode.bugsim.agent.matchstick.MatchstickAgentFilter;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.boundary.IBoundary;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickIntersectionsWriter extends SimulationWriterBase {

    public MatchstickIntersectionsWriter() {
        super(true);

    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(MatchstickIntersectionsRow.HEADER_ROW);
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        List matchsticks = simulation.getLiveAgents(MatchstickAgentFilter.INSTANCE);
        if (log.isInfoEnabled()) {
            log.info("Reporting " + matchsticks.size() + " matchsticks.");
        }
        MatchstickIntersectionsRow row = new MatchstickIntersectionsRow();
        row.setExperimentId(experimentId);
        row.setIterationId(FORMAT.format(new Long(currentIteration)));
        row.setReplicateId(FORMAT.format(new Long(currentReplicate)));


        for (Iterator itr = matchsticks.iterator(); itr.hasNext();) {
            MatchstickAgent matchstick = (MatchstickAgent)itr.next();

            row.setMatchStick(matchstick);
            row.setIntersection(null);

            if (matchstick.getIntersections().size() > 0) {
                BoundaryAgentIntersection ix = matchstick.getClosestIntersection();
                IBoundaryAgent boundaryAgent = ix.getBoundaryAgent();
                IBoundary  boundary = boundaryAgent.getBoundary();
                if (boundary.getShape().isLinear() || boundary.isInside(matchstick.getEndCoord())) { //@todo need to sort this out !! one trial wont work otherwise
                    row.setIntersection(ix.getCoord());
                    row.setIntersectionD(matchstick.calculateIntersectionD(ix.getCoord()));
                    out.writeRow(row);
                }
            }
        }


    }

    private static final Logger log = Logger.getLogger(MatchstickIntersectionsWriter.class);
    private Format FORMAT = new DecimalFormat("000");
    private List _butterflyHistory;
}
