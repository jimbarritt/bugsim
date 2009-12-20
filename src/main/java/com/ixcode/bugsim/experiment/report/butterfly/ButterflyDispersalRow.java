/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.butterfly;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.stats.DescriptiveStatistics;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;

/**
 *  Description : Extends CSV Row to encapsulate all the indexes
 */
public class ButterflyDispersalRow extends ExperimentRow {



    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 7;

    public static CSVRow HEADER_ROW = new CSVRow(ButterflyDispersalRow.COLUMN_COUNT);


    public static final int COL_AGE = ExperimentRow.columnCount() ;
    public static final int COL_DISPSQ_N = ExperimentRow.columnCount() +1;
    public static final int COL_DISPSQ_MEAN = ExperimentRow.columnCount() +2;
    public static final int COL_DISPSQ_VARIANCE = ExperimentRow.columnCount() +3;
    public static final int COL_DISPSQ_STD_ERROR = ExperimentRow.columnCount() +4;
    public static final int COL_DISPSQ_MEAN_MINUS_ERR = ExperimentRow.columnCount() +5;
    public static final int COL_DISPSQ_MEAN_PLUS_ERR = ExperimentRow.columnCount() +6;







    static {
        ButterflyDispersalRow.HEADER_ROW.setString(COL_EXPERIMENT_ID, "experiment");
        ButterflyDispersalRow.HEADER_ROW.setString(COL_ITERATION_ID, "iteration");
        ButterflyDispersalRow.HEADER_ROW.setString(COL_REPLICATE_ID, "replicant");

        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_AGE, "age");

        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_N, ButterflyStatistics.DISPSQ_N);
        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_MEAN, ButterflyStatistics.DISPSQ_MEAN);
        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_VARIANCE, ButterflyStatistics.DISPSQ_VARIANCE);
        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_STD_ERROR, ButterflyStatistics.DISPSQ_STD_ERROR);
        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_MEAN_MINUS_ERR, ButterflyStatistics.DISPSQ_MEAN_MINUS_ERR);
        ButterflyDispersalRow.HEADER_ROW.setString(ButterflyDispersalRow.COL_DISPSQ_MEAN_PLUS_ERR, ButterflyStatistics.DISPSQ_MEAN_PLUS_ERR);


    }

    public ButterflyDispersalRow() {
        super(ButterflyDispersalRow.COLUMN_COUNT);

    }




    public void setAge(Long age) {
        super.setObject(ButterflyDispersalRow.COL_AGE, age);
    }

    public void setDescriptiveStatistics(DescriptiveStatistics stats) {

        super.setLong(ButterflyDispersalRow.COL_DISPSQ_N, stats.getN());
        super.setDouble(ButterflyDispersalRow.COL_DISPSQ_MEAN, stats.getMean());
        super.setDouble(ButterflyDispersalRow.COL_DISPSQ_VARIANCE, stats.getVariance());
        super.setDouble(ButterflyDispersalRow.COL_DISPSQ_STD_ERROR, stats.getStdError());
        super.setDouble(ButterflyDispersalRow.COL_DISPSQ_MEAN_MINUS_ERR, stats.getMeanMinusErr());
        super.setDouble(ButterflyDispersalRow.COL_DISPSQ_MEAN_PLUS_ERR, stats.getMeanPlusErr());

    }


}
