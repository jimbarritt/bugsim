/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.bugsim.agent.cabbage.CabbageAgentFilter;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.io.csv.CSVWriter;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.ScaledCartesianCoordinate;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.experiment.report.SimulationWriterBase;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;

import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageWriter extends SimulationWriterBase {

    public CabbageWriter(String experimentalGridName, ScaledDistance outputScale) {
        super(false);

        _experimentalGridName = experimentalGridName;
        _outputScale =      outputScale;
    }

    protected void writeHeaders(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {
        out.writeRow(CabbageRow.HEADER_ROW);
    }

    /**
     * hmmm not very tidfy!! @todo sort somethign out!
     * @return
     */
    public CabbageStatistics calculateStatsWithoutOutput(Simulation simulation) {
        List cabbages = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);

        _stats = new CabbageStatistics();
        for (Iterator itr = cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbage = (CabbageAgent)itr.next();
            Grid grid = simulation.getLandscape().getGrid(_experimentalGridName);
            List analysisValues = getAnalysisValues(grid, cabbage);

            _stats.addCabbage(cabbage, analysisValues);

        }
        _stats.calculate();
        return _stats;
    }

    protected void writeResults(CSVWriter out, Simulation simulation, String experimentId, long currentIteration, long currentReplicate) {


        List cabbages = simulation.getLiveAgents(CabbageAgentFilter.INSTANCE);

        Landscape landscape = simulation.getLandscape();
        ScaledDistance landscapeScale = landscape.getScale();


        _stats = new CabbageStatistics();
        for (Iterator itr = cabbages.iterator(); itr.hasNext();) {
            CabbageAgent cabbage = (CabbageAgent)itr.next();
            Grid grid = simulation.getLandscape().getGrid(_experimentalGridName);
            List analysisValues = getAnalysisValues(grid, cabbage);
            RectangularCoordinate relativeLocation = grid.getBounds().convertGlobalToLocalCoord(cabbage.getLocation().getCoordinate());

            ScaledCartesianCoordinate scaledCoord = new ScaledCartesianCoordinate(relativeLocation, landscapeScale);
            RectangularCoordinate outputCoord = scaledCoord.scaleCoordinate(_outputScale);

            writeCabbage(out, cabbage, analysisValues, relativeLocation, outputCoord, _outputScale, experimentId, currentIteration, currentReplicate);

            _stats.addCabbage(cabbage, analysisValues);

        }
        _stats.calculate();

    }

    private List getAnalysisValues(Grid grid, CabbageAgent cabbage) {
        List gridSqaures = grid.getContainingGridSquares(cabbage.getLocation().getCoordinate());
        List analysisValues;
        if (gridSqaures.size() > 0) {
            GridSquare gridSquare = (GridSquare)gridSqaures.get(0); // We happend to know theres only 1 in this case = @todo make this work for multiple ones.
            analysisValues = gridSquare.getAnalysisValues();
        } else {
            analysisValues = new ArrayList();
        }
        return analysisValues;
    }


    private void writeCabbage(CSVWriter out, CabbageAgent cabbage, List analysisValues, RectangularCoordinate relativeLocation,RectangularCoordinate outputCoord, ScaledDistance outputScale, String experimentId, long currentIteration, long currentReplicant) {
        CabbageRow row = new CabbageRow();
        row.setExperimentId(experimentId);
        row.setIterationId(FORMAT.format(new Long(currentIteration)));
        row.setReplicateId(FORMAT.format(new Long(currentReplicant)));
        row.setCabbageId(cabbage.getResourceId());
        row.setLocation(cabbage.getLocation().getCoordinate());

        row.setRelativeLocation(relativeLocation);
        row.setScaledLocation(outputCoord);
        row.setScale(outputScale);

        row.setEggCount(cabbage.getEggCount());

        if (analysisValues.size()>0) {
            AnalysisValue value = (AnalysisValue)analysisValues.get(0);
            row.setIsolationGroupCode(value.getCode());
            row.setIsolationGroupDesc(value.getDescription());
        }

        out.writeRow(row);

    }

    public CabbageStatistics getStats() {
        return _stats;
    }



    private Format FORMAT= new DecimalFormat("000");
    private String _experimentalGridName;
    private CabbageStatistics _stats;
    private ScaledDistance _outputScale;
}
