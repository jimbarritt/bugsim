/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.bugsim.agent.cabbage.EggCount;
import com.ixcode.bugsim.agent.cabbage.PlantCount;

import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class EggCountWriter extends SimulationWriterBase {

    public EggCountWriter(long countId, EggCount count) {
        super(false);
        _eggCount = count;
        _countId = countId;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(EggCountRow.HEADER_ROW);
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {

        for (Iterator itr = _eggCount.getCounts().iterator(); itr.hasNext();) {
            PlantCount plantCount = (PlantCount)itr.next();

            EggCountRow row = new EggCountRow();
            row.setCountId(_countId);
            row.setPlantCount(plantCount);
            out.writeRow(row);
        }


    }



    private EggCount _eggCount;
    private long _countId;
}
