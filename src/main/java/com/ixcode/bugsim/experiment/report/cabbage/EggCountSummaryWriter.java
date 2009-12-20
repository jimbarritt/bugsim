/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.bugsim.agent.cabbage.EggCount;

import java.util.Iterator;
import java.util.List;
import java.text.Format;
import java.text.DecimalFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class EggCountSummaryWriter extends SimulationWriterBase {

    public EggCountSummaryWriter(List eggCounts) {
        super(false);
        _eggCounts = eggCounts;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(EggCountSummaryRow.HEADER_ROW);
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        int eggCountId = 1;
        for (Iterator itr = _eggCounts.iterator(); itr.hasNext();) {
            EggCount eggCount = (EggCount)itr.next();
            EggCountSummaryRow row = new EggCountSummaryRow();


            row.setExperimentId(experimentId);
            row.setIteration(currentIteration);
            row.setReplicate(currentReplicate);

            row.setEggCountId(eggCountId);
            row.setEggCount(eggCount);

            out.writeRow(row);
            eggCountId++;
        }

    }



    private Format FORMAT = new DecimalFormat("000");
    private CabbageStatistics _stats;
    private String _analysisCategoryName;
    private List _eggCounts;
}
