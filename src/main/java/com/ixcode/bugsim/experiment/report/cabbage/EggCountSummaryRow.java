/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.report.cabbage;

import com.ixcode.framework.simulation.experiment.report.ExperimentRow;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.bugsim.model.agent.cabbage.EggCount;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class EggCountSummaryRow extends ExperimentRow {


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 12;

    public static CSVRow HEADER_ROW = new CSVRow(EggCountSummaryRow.COLUMN_COUNT);


    public static final int COL_COUNT_ID  = ExperimentRow.columnCount();
    public static final int COL_TIME = ExperimentRow.columnCount() + 1;
    public static final int COL_AGENT_CLASS = ExperimentRow.columnCount() + 2;
    public static final int COL_TOTAL_EGGS = ExperimentRow.columnCount() + 3;



    static {
        EggCountSummaryRow.HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        EggCountSummaryRow.HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");
        EggCountSummaryRow.HEADER_ROW.setString(COL_REPLICATE_ID, "Replicate");
        EggCountSummaryRow.HEADER_ROW.setString(EggCountSummaryRow.COL_COUNT_ID, "countId");
        EggCountSummaryRow.HEADER_ROW.setString(EggCountSummaryRow.COL_TIME, "time");
        EggCountSummaryRow.HEADER_ROW.setString(EggCountSummaryRow.COL_AGENT_CLASS, "agentClass");
        EggCountSummaryRow.HEADER_ROW.setString(EggCountSummaryRow.COL_TOTAL_EGGS, "totalEggs");
    }

    public EggCountSummaryRow() {
        super(EggCountSummaryRow.COLUMN_COUNT);

    }

    public void setEggCountId(long id) {
        super.setLong(COL_COUNT_ID, id);
    }

    public void setEggCount(EggCount eggCount) {
        super.setLong(COL_TIME, eggCount.getTime());
        super.setString(COL_AGENT_CLASS, IntrospectionUtils.getShortClassName(eggCount.getAgentClass()));
        super.setLong(COL_TOTAL_EGGS, eggCount.getTotalEggs());
    }

}
