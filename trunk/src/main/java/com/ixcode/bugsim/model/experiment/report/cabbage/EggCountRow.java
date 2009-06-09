/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.report.cabbage;

import com.ixcode.framework.simulation.experiment.report.ExperimentRow;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.BigDecimalMath;
import com.ixcode.bugsim.model.agent.cabbage.EggCount;
import com.ixcode.bugsim.model.agent.cabbage.PlantCount;

import java.math.BigDecimal;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class EggCountRow extends CSVRow{



    public static final int COLUMN_COUNT = 3;

    public static CSVRow HEADER_ROW = new CSVRow(EggCountRow.COLUMN_COUNT);


    public static final int COL_COUNT_ID = 0;
    public static final int COL_CABBAGE_ID = 1;
    public static final int COL_EGG_COUNT = 2;


    static {
        EggCountRow.HEADER_ROW.setString(COL_COUNT_ID, "CountId");
        EggCountRow.HEADER_ROW.setString(COL_CABBAGE_ID, "CabbageId");
        EggCountRow.HEADER_ROW.setString(COL_EGG_COUNT, "EggCount");

    }

    public EggCountRow() {
        super(EggCountRow.COLUMN_COUNT);

    }

    public void setCountId(long countId) {
        super.setLong(COL_COUNT_ID, countId);
    }

    public void setPlantCount(PlantCount plantCount) {
        super.setLong(COL_CABBAGE_ID, plantCount.getPlantId());
        super.setLong(COL_EGG_COUNT, plantCount.getEggCount());
    }




}
