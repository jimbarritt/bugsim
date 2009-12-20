/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.butterfly;

import com.ixcode.bugsim.agent.butterfly.ButterflyAgent;
import com.ixcode.bugsim.agent.butterfly.ForagingAgentFilter;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyWriter extends SimulationWriterBase {

    

    public ButterflyWriter() {
         this(null);
    }
    public ButterflyWriter(String cabbageAnalysisGridName) {
        super(false);
        _cabbageAnalysisGridName = cabbageAnalysisGridName;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {

        out.writeRow(ButterflyRow.HEADER_ROW);

    }

    /**
     * hmmm not very tidfy!! @todo sort somethign out!
     * @return
     */
    public ButterflyStatistics calculateStatsWithoutOutput(Simulation simulation) {
        List butterflies = simulation.getHistoricalAgents(ForagingAgentFilter.INSTANCE);

        if (log.isInfoEnabled()) {
            log.info("Calculating stats for " + butterflies.size() + " Butterflies.");
        }

        _stats = new ButterflyStatistics(butterflies.size());

        for (Iterator itr = butterflies.iterator(); itr.hasNext();) {
            ButterflyAgent butterfly = (ButterflyAgent)itr.next();
            _stats.addButterfly(butterfly);

        }
        _stats.calculate();
        return _stats;
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        List butterflies = simulation.getHistoricalAgents(ForagingAgentFilter.INSTANCE);

        if (log.isInfoEnabled()) {
            log.info("Reporting " + butterflies.size() + " Butterflies.");
        }


        _stats = new ButterflyStatistics(butterflies.size());
        int butterflyId = 1;
        for (Iterator itr = butterflies.iterator(); itr.hasNext();) {
            ButterflyAgent butterfly = (ButterflyAgent)itr.next();            
            writeButterfly(out, butterfly, butterflyId, experimentId, currentIteration, currentReplicate, simulation);
            butterflyId++;
            _stats.addButterfly(butterfly);
        }
        _stats.calculate();
    }



    private void writeButterfly(CSVWriter out, ButterflyAgent butterfly, int butterflyId,  String experimentId, long currentIteration, long currentReplicate, Simulation simulation) {
        ButterflyRow row = new ButterflyRow();

        row.setExperimentId(experimentId);
        row.setIteration(currentIteration);
        row.setReplicate(currentReplicate);
        row.setButterflyId(butterflyId);
        row.setLocation(butterfly.getLocation().getCoordinate());
        row.setInitialLocation(butterfly.getInitialLocation().getCoordinate());
        row.setInitialAzimuth(butterfly.getInitialAzimuth());
        row.setEggCount(butterfly.getEggCount());
        row.setDisplacementDistance(butterfly.calculateDisplacementDistance());
        row.setAge(butterfly.getAge());
        row.setLastBehaviour(butterfly.getBehaviour().getName());
        row.setLastCabbage(butterfly.getCurrentCabbage(), simulation, _cabbageAnalysisGridName);
        row.setReleaseI(butterfly.getReleaseI()); // @todo calculate this from initial location rather than storing it.

        out.writeRow(row);

    }

    public ButterflyStatistics getStats() {
        return _stats;
    }



    private Format FORMAT= new DecimalFormat("000");

    private ButterflyStatistics _stats;
    private static final Logger log = Logger.getLogger(ButterflyWriter.class);
    private String _cabbageAnalysisGridName;
}
