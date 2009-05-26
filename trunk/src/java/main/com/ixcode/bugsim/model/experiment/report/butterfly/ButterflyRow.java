/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.butterfly;

import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import com.ixcode.framework.simulation.model.landscape.grid.GridSquare;

import java.util.List;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class ButterflyRow extends ExperimentRow {


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 14;

    public static CSVRow HEADER_ROW = new CSVRow(ButterflyRow.COLUMN_COUNT);


    public static final int COL_ID = ExperimentRow.columnCount();
    public static final int COL_X = ExperimentRow.columnCount() + 1;
    public static final int COL_Y = ExperimentRow.columnCount() + 2;
    public static final int COL_INITIAL_X = ExperimentRow.columnCount() + 3;
    public static final int COL_INITIAL_Y = ExperimentRow.columnCount() + 4;

    public static final int COL_EGG_COUNT = ExperimentRow.columnCount() + 5;
    public static final int COL_LAST_BEHAVIOUR = ExperimentRow.columnCount() + 6;
    public static final int COL_DISPLACEMENT_DISTANCE = ExperimentRow.columnCount() + 7;
    public static final int COL_INITIAL_AZIMUTH = ExperimentRow.columnCount() + 8;
    public static final int COL_AGE = ExperimentRow.columnCount() + 9;
    public static final int COL_CABBAGE_ID = ExperimentRow.columnCount() + 10;
    public static final int COL_CABBAGE_ANALYSIS = ExperimentRow.columnCount() + 11;
    public static final int COL_CABBAGE_ANALYSIS_CODE = ExperimentRow.columnCount() + 12;
    public static final int COL_RELEASE_I = ExperimentRow.columnCount() + 13;


    static {
        ButterflyRow.HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        ButterflyRow.HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");
        ButterflyRow.HEADER_ROW.setString(COL_REPLICATE_ID, "Replicate");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_ID, "Id");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_X, "X");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_Y, "Y");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_INITIAL_X, "initialX");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_INITIAL_Y, "initialY");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_EGG_COUNT, "EggCount");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_LAST_BEHAVIOUR, "Behaviour");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_DISPLACEMENT_DISTANCE, "Displacement");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_INITIAL_AZIMUTH, "InitialAzimuth");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_AGE, "Age");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_CABBAGE_ID, "lastCabbage.id");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_CABBAGE_ANALYSIS, "lastCabbage.analysis");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_CABBAGE_ANALYSIS_CODE, "lastCabbage.analysis.code");
        ButterflyRow.HEADER_ROW.setString(ButterflyRow.COL_RELEASE_I, "I");

    }

    public ButterflyRow() {
        super(ButterflyRow.COLUMN_COUNT);

    }


    public void setButterflyId(long id) {
        super.setLong(ButterflyRow.COL_ID, id);
    }

    public void setX(double x) {
        super.setDouble(ButterflyRow.COL_X, x);
    }

    public void setY(double y) {
        super.setDouble(ButterflyRow.COL_Y, y);
    }

    public void setLocation(RectangularCoordinate coordinate) {
        setX(coordinate.getDoubleX());
        setY(coordinate.getDoubleY());
    }

    public void setInitialLocation(RectangularCoordinate coordinate) {
        setInitialX(coordinate.getDoubleX());
        setInitialY(coordinate.getDoubleY());
    }

    private void setInitialY(double y) {
        super.setDouble(ButterflyRow.COL_INITIAL_Y, y);
    }

    private void setInitialX(double x) {
        super.setDouble(ButterflyRow.COL_INITIAL_X, x);
    }

    public void setInitialAzimuth(double azimuth) {
        super.setDouble(COL_INITIAL_AZIMUTH, azimuth);
    }

    public void setEggCount(long eggCount) {
        super.setLong(ButterflyRow.COL_EGG_COUNT, eggCount);
    }

    public void setLastBehaviour(String behaviour) {
        super.setString(ButterflyRow.COL_LAST_BEHAVIOUR, behaviour);
    }

    public void setDisplacementDistance(double distance) {
        super.setDouble(ButterflyRow.COL_DISPLACEMENT_DISTANCE, distance);
    }


    public void setAge(long age) {
        super.setLong(COL_AGE, age);
    }

    public void setLastCabbage(CabbageAgent currentCabbage, Simulation simulation, String analysisGridName) {
        if (currentCabbage != null && analysisGridName != null) {
            Grid grid = simulation.getLandscape().getGrid(analysisGridName);
            List gridSqaures = grid.getContainingGridSquares(currentCabbage.getLocation().getCoordinate());
            GridSquare gridSquare = (GridSquare)gridSqaures.get(0); // We happend to know theres only 1 in this case = @todo make this work for multiple ones.
            List analysisValues = gridSquare.getAnalysisValues();

            super.setLong(COL_CABBAGE_ID, currentCabbage.getResourceId());
            super.setString(COL_CABBAGE_ANALYSIS_CODE, ((AnalysisValue)analysisValues.get(0)).getCode()); // also know theres only 1 analysis category
            super.setString(COL_CABBAGE_ANALYSIS, ((AnalysisValue)analysisValues.get(0)).getDescription()); // also know theres only 1 analysis category
        } else {
            super.setString(COL_CABBAGE_ANALYSIS, "Missed");
            super.setString(COL_CABBAGE_ANALYSIS_CODE, "0"); // also know theres only 1 analysis category
        }
    }


    public void setReleaseI(double I) {
        super.setDouble(COL_RELEASE_I, I);
    }

}
