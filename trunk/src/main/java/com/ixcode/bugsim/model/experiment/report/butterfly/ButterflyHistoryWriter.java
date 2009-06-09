/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.butterfly;

import com.ixcode.bugsim.model.agent.butterfly.ButterflyAgentState;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyHistoryWriter extends SimulationWriterBase {

    public ButterflyHistoryWriter(int butterflyId, List butterflyHistory) {
        super(true);
        _butterflyHistory = butterflyHistory;
        _butterflyId = butterflyId;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(ButterflyHistoryRow.HEADER_ROW);
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        ButterflyHistoryRow historyRow = new ButterflyHistoryRow();

        historyRow.setExperimentId(experimentId);
        historyRow.setIteration(currentIteration);
        historyRow.setReplicate(currentReplicate);

        for (Iterator itr = _butterflyHistory.iterator(); itr.hasNext();) {
            ButterflyAgentState butterflyState = (ButterflyAgentState)itr.next();
            historyRow.setId(_butterflyId);
            historyRow.setAge(butterflyState.getAge());
            historyRow.setBehaviour(butterflyState.getBehaviour());
            historyRow.setEggCount(butterflyState.getEggCount());
            historyRow.setDirection(butterflyState.getHeading());
            historyRow.setLocation(butterflyState.getLocation());
            historyRow.setCabbage(butterflyState.getCurrentCabbage());
            historyRow.setLastMove(butterflyState.getLastMove());
            out.writeRow(historyRow);
        }

    }


    private List _butterflyHistory;
    private int _butterflyId;
}
