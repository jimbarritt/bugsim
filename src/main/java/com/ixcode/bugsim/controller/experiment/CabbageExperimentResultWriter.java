/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgentFilter;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageRow;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatisticEntry;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatisticRow;
import com.ixcode.bugsim.model.experiment.report.cabbage.CabbageStatistics;
import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;

import java.util.Iterator;
import java.util.List;

/**
 *  Description : Writes out the results of a Cabbage Experiment!
 */
public class CabbageExperimentResultWriter extends SimulationResultWriter {

    public CabbageExperimentResultWriter(String experimentalGridName) {
        _experimentalGridName = experimentalGridName;
    }

    protected void writeResults(CSVWriter out, Simulation simulation) {
        List cabbages = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);
        out.writeRow(CabbageRow.HEADER_ROW);
        CabbageStatistics stats = new CabbageStatistics();
        for (Iterator itr = cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbage = (CabbageAgent)itr.next();
            Grid grid = simulation.getLandscape().getGrid(_experimentalGridName);
            List gridSqaures = grid.getContainingGridSquares(cabbage.getLocation().getCoordinate());
            GridSquare gridSquare = (GridSquare)gridSqaures.get(0); // We happend to know theres only 1 in this case = @todo make this work for multiple ones.
            List analysisValues = gridSquare.getAnalysisValues();

            RectangularCoordinate relativeLocation = gridSquare.getCoordRelativeToParent(cabbage.getLocation().getCoordinate());

            writeCabbage(out, cabbage, analysisValues, relativeLocation);

            stats.addCabbage(cabbage, analysisValues);

        }

        writeStatistics(out, stats);
    }

    public void writeStatistics(CSVWriter out, CabbageStatistics stats) {
        out.println();
        out.println("Summary Statistics:");
        out.println();

        for (Iterator itr = stats.getAnalysisCategories().iterator(); itr.hasNext();) {
            AnalysisCategory cat = (AnalysisCategory)itr.next();
            out.writeProperty("Category", cat.getName());
            out.println();
            out.writeRow(CabbageStatisticRow.HEADER_ROW);
            for (Iterator itrValue = cat.getValues().iterator(); itrValue.hasNext();) {
                AnalysisValue value = (AnalysisValue)itrValue.next();
                CabbageStatisticEntry entry = stats.getStatisticEntry(value);
                CabbageStatisticRow row = new CabbageStatisticRow();
                row.setAnalysisCode(value.getCode());
                row.setEggsPerPlant(entry.getEggsPerPlant());
                row.setEggTotal(entry.getTotalEggs());
                row.setNumberOfPlants(entry.getNumberOfPlants());
                out.writeRow(row);

            }
        }

    }

    private void writeCabbage(CSVWriter out, CabbageAgent cabbage, List analysisValues, RectangularCoordinate relativeLocation) {
        CabbageRow row = new CabbageRow();
        row.setCabbageId(cabbage.getResourceId());
        row.setLocation(cabbage.getLocation().getCoordinate());
        row.setRelativeLocation(relativeLocation);
        row.setEggCount(cabbage.getEggCount());

        AnalysisValue value = (AnalysisValue)analysisValues.get(0);
        row.setIsolationGroupCode(value.getCode());
        row.setIsolationGroupDesc(value.getDescription());

        out.writeRow(row);

    }

    private String _experimentalGridName;
}
