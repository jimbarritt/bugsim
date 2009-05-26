/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.cabbage;

import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.Iterator;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageStatsWriter extends SimulationWriterBase {

    public CabbageStatsWriter(CabbageStatistics stats, String analysisCategoryName) {
        super(false);
        _stats = stats;
        _analysisCategoryName = analysisCategoryName;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(CabbageStatisticRow.HEADER_ROW);
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        if (_stats.hasAnalysisCategory(_analysisCategoryName)) {
            writeStatsByAnalysisCategory(experimentId, currentIteration, currentReplicate, out);
        }


    }

    private void writeStatsByAnalysisCategory(String experimentId, long currentIteration, long currentReplicant, CSVWriter out) {
        AnalysisCategory cat = (AnalysisCategory)_stats.getAnalysisCategory(_analysisCategoryName);

        for (Iterator itrValue = cat.getValues().iterator(); itrValue.hasNext();) {
            AnalysisValue value = (AnalysisValue)itrValue.next();
            CabbageStatisticEntry entry = _stats.getStatisticEntry(value);
            CabbageStatisticRow row = new CabbageStatisticRow();
            row.setExperimentId(experimentId);
            row.setIterationId(FORMAT.format(new Long(currentIteration)));
            row.setReplicateId(FORMAT.format(new Long(currentReplicant)));
            row.setAnalysisCode(value.getCode());
            row.setAnalysisDesc(value.getDescription());
            row.setEggsPerPlant(entry.getEggsPerPlant());
            row.setEggTotal(entry.getTotalEggs());
            row.setNumberOfPlants(entry.getNumberOfPlants());
            row.setEggsPerPlantRatio(entry.getEggsPerPlantRatio());
            out.writeRow(row);

        }

        CabbageStatisticRow row = new CabbageStatisticRow();
        row.setEggsPerPlant(_stats.getTotalEggsPerPlant());
        row.setEggTotal(_stats.getTotalEggs());
        row.setNumberOfPlants(_stats.getTotalPlants());
        row.setEggsPerPlantRatio(_stats.getTotalEggsPerPlantRatio());
        out.writeRow(row);
    }


    private Format FORMAT = new DecimalFormat("000");
    private CabbageStatistics _stats;
    private String _analysisCategoryName;
}
