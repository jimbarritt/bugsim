/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.cabbage;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageStatisticRow extends ExperimentRow {


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 6;

    public static final int COL_ANALYSIS_CODE = ExperimentRow.columnCount() + 0;
    public static final int COL_ANALYSIS_DESC = ExperimentRow.columnCount() + 1;
    public static final int COL_EGGS_PER_PLANT = ExperimentRow.columnCount() + 2;
    public static final int COL_NUMBER_OF_PLANTS = ExperimentRow.columnCount() + 3;
    public static final int COL_EGG_TOTAL = ExperimentRow.columnCount() + 4;
    public static final int COL_EGG_PER_PLANT_RATIO = ExperimentRow.columnCount() + 5;

    public static final CSVRow HEADER_ROW = new CSVRow(COLUMN_COUNT);

    static {
        HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");

        HEADER_ROW.setString(COL_ANALYSIS_CODE, "Code");
        HEADER_ROW.setString(COL_ANALYSIS_DESC, "Description");
        HEADER_ROW.setString(COL_EGGS_PER_PLANT, "Eggs Per Plant");
        HEADER_ROW.setString(COL_NUMBER_OF_PLANTS, "No Plants");
        HEADER_ROW.setString(COL_EGG_TOTAL, "Total Eggs");
        HEADER_ROW.setString(COL_EGG_PER_PLANT_RATIO, "Egg / Plant Ratio");
    }

    public CabbageStatisticRow() {
        super(COLUMN_COUNT);

    }

    public void setAnalysisCode(String code) {
        super.setString(COL_ANALYSIS_CODE, code);
    }
    public void setAnalysisDesc(String desc) {
        super.setString(COL_ANALYSIS_DESC, desc);
    }

    public void setEggsPerPlant(double eggsPerPlant) {
        super.setDouble(COL_EGGS_PER_PLANT, eggsPerPlant);
    }

    public void setNumberOfPlants(long numberPlants) {
        super.setLong(COL_NUMBER_OF_PLANTS, numberPlants);
    }

    public void setEggTotal(long eggTotal) {
        super.setLong(COL_EGG_TOTAL, eggTotal);
    }


    public void setEggsPerPlantRatio(double ratio) {
        super.setDouble(COL_EGG_PER_PLANT_RATIO, ratio);
    }
}
