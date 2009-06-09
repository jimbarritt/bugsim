/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.io.csv.CSVRow;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ExperimentRow extends CSVRow {
    public void setBigDecimal(int index, BigDecimal value) {
        super.setObject(index, value);
    }


    public static final int COL_EXPERIMENT_ID = 0;
    public static final int COL_ITERATION_ID = 1;
    public static final int COL_REPLICATE_ID = 2;

    public ExperimentRow(int columnCount) {
        super(columnCount);
    }

    public static int columnCount() {
        return 3;
    }

    public void setExperimentId(String experimentId) {
        super.setString(COL_EXPERIMENT_ID, experimentId);
    }

    /**
     * @deprecated
     * @param iterationId
     */
    public void setIterationId(String iterationId) {
        super.setString(COL_ITERATION_ID, iterationId);
    }

    /**
     * @deprecated
     * @param replicateId
     */
    public void setReplicateId(String replicateId) {
        super.setString(COL_REPLICATE_ID, replicateId);
    }

    public void setIteration(long iteration) {
        super.setString(COL_ITERATION_ID, FORMAT.format(iteration));
    }

    public void setReplicate(long replicate) {
        super.setString(COL_REPLICATE_ID, FORMAT.format(replicate));
    }

    private DecimalFormat FORMAT = new DecimalFormat("000");
}
