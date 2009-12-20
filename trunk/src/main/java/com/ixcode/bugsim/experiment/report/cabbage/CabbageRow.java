/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.report.cabbage;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.BigDecimalMath;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;

import java.math.BigDecimal;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class CabbageRow extends ExperimentRow {


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 12;

    public static CSVRow HEADER_ROW = new CSVRow(COLUMN_COUNT);


    public static final int COL_ID = ExperimentRow.columnCount();
    public static final int COL_X = ExperimentRow.columnCount() + 1;
    public static final int COL_Y = ExperimentRow.columnCount() + 2;
    public static final int COL_REL_X = ExperimentRow.columnCount() + 3;
    public static final int COL_REL_Y = ExperimentRow.columnCount() + 4;
    public static final int COL_SCALED_REL_X = ExperimentRow.columnCount() + 5;
    public static final int COL_SCALED_REL_Y = ExperimentRow.columnCount() + 6;
    public static final int COL_SCALE_UNITS = ExperimentRow.columnCount() + 7;
    public static final int COL_SCALE_FACTOR = ExperimentRow.columnCount() + 8;


    public static final int COL_EGG_COUNT = ExperimentRow.columnCount() + 9;
    public static final int COL_ANALYSIS_1_CODE = ExperimentRow.columnCount() + 10;//"Isolation Group Code";
    public static final int COL_ANALYSIS_1_DESC = ExperimentRow.columnCount() + 11;//"Isolation Group";


    static {
        HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");
        HEADER_ROW.setString(COL_REPLICATE_ID, "Replicate");
        HEADER_ROW.setString(COL_ID, "Id");
        HEADER_ROW.setString(COL_X, "X");
        HEADER_ROW.setString(COL_Y, "Y");
        HEADER_ROW.setString(COL_REL_X, "relativeX");
        HEADER_ROW.setString(COL_REL_Y, "relativeY");
        HEADER_ROW.setString(COL_SCALED_REL_X, "scaledRelativeX");
        HEADER_ROW.setString(COL_SCALED_REL_Y, "scaledRelativeY");
        HEADER_ROW.setString(COL_SCALE_UNITS, "scaleUnits");
        HEADER_ROW.setString(COL_SCALE_FACTOR, "scaleFactor");
        HEADER_ROW.setString(COL_EGG_COUNT, "Egg Count");
        HEADER_ROW.setString(COL_ANALYSIS_1_CODE, "Isolation Group Code");
        HEADER_ROW.setString(COL_ANALYSIS_1_DESC, "Isolation Group");
    }

    public CabbageRow() {
        super(COLUMN_COUNT);

    }


    public void setCabbageId(long id) {
        super.setLong(COL_ID, id);
    }

    public void setX(double x) {
        super.setDouble(COL_X, x);
    }

    public void setY(double y) {
        super.setDouble(COL_Y, y);
    }

    public void setLocation(RectangularCoordinate coordinate) {
        setX(coordinate.getDoubleX());
        setY(coordinate.getDoubleY());
    }

    public void setRelativeLocation(RectangularCoordinate coordinate) {
        setRelativeX(coordinate.getDoubleX());
        setRelativeY(coordinate.getDoubleY());
    }

    private void setRelativeY(double y) {
        super.setDouble(COL_REL_Y, y);
    }

    private void setRelativeX(double x) {
        super.setDouble(COL_REL_X, x);
    }

    public void setScaledLocation(RectangularCoordinate outputCoord) {
        setScaledRelativeX(outputCoord.getDoubleX());
        setScaledRelativeY(outputCoord.getDoubleY());

    }

    public void setScale(ScaledDistance outputScale) {
        setScaleUnits(outputScale.getUnits());
        setScaleFactor(BigDecimalMath.accurateOut(outputScale.getDistance()));
    }


    private void setScaledRelativeX(double x) {
        super.setDouble(COL_SCALED_REL_X, x);
    }

    private void setScaledRelativeY(double y) {
        super.setDouble(COL_SCALED_REL_Y, y);
    }

    private void setScaleUnits(IDistanceUnit unit) {
        super.setString(COL_SCALE_UNITS, unit.getSymbol());

    }

    private void setScaleFactor(BigDecimal scaleFactor) {
        super.setBigDecimal(COL_SCALE_FACTOR, scaleFactor);
    }

    public void setEggCount(long eggCount) {
        super.setLong(COL_EGG_COUNT, eggCount);
    }

    public void setIsolationGroupCode(String code) {
        super.setString(COL_ANALYSIS_1_CODE, code);
    }

    public void setIsolationGroupDesc(String desc) {
        super.setString(COL_ANALYSIS_1_DESC, desc);
    }


}
