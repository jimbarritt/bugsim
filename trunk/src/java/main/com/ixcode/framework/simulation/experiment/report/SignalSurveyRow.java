/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.simulation.model.agent.surveyor.SignalSurveyingAgent;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class SignalSurveyRow extends CSVRow{


    public static final int COLUMN_COUNT = 11;

    public static CSVRow HEADER_ROW = new CSVRow(SignalSurveyRow.COLUMN_COUNT);


    public static final int COL_SIGNAL_SURFACE_NAME = 0;
    public static final int COL_MIN_X = 1;
    public static final int COL_MAX_X = 2;
    public static final int COL_INTERVAL_X = 3;
    public static final int COL_COUNT_X = 4;
    public static final int COL_MIN_Y = 5;
    public static final int COL_MAX_Y = 6;
    public static final int COL_INTERVAL_Y = 7;
    public static final int COL_COUNT_Y = 8;
    public static final int COL_SIGNAL_SURFACE_SD = 9;
    public static final int COL_SIGNAL_SURFACE_MAGNIFICATION =10;


    static {
//        SignalSurveyRow.HEADER_ROW.setString(COL_EXPERIMENT_ID, "experiment");
//        SignalSurveyRow.HEADER_ROW.setString(COL_ITERATION_ID, "iteration");
//        SignalSurveyRow.HEADER_ROW.setString(COL_REPLICATE_ID, "replicate");

        SignalSurveyRow.HEADER_ROW.setString(COL_SIGNAL_SURFACE_NAME, "signalSurfaceName");
        SignalSurveyRow.HEADER_ROW.setString(COL_MIN_X, "minX");
        SignalSurveyRow.HEADER_ROW.setString(COL_MAX_X, "maxX");
        SignalSurveyRow.HEADER_ROW.setString(COL_INTERVAL_X, "intervalX");
        SignalSurveyRow.HEADER_ROW.setString(COL_COUNT_X, "countX");

        SignalSurveyRow.HEADER_ROW.setString(COL_MIN_Y, "minY");
        SignalSurveyRow.HEADER_ROW.setString(COL_MAX_Y, "maxY");
        SignalSurveyRow.HEADER_ROW.setString(COL_INTERVAL_Y, "intervalY");
        SignalSurveyRow.HEADER_ROW.setString(COL_COUNT_Y, "countY");
        SignalSurveyRow.HEADER_ROW.setString(COL_SIGNAL_SURFACE_SD, "surfaceSD");
        SignalSurveyRow.HEADER_ROW.setString(COL_SIGNAL_SURFACE_MAGNIFICATION, "surfaceMagnification");

    }

    public SignalSurveyRow() {
        super(SignalSurveyRow.COLUMN_COUNT);

    }

    public void setSurveyor(SignalSurveyingAgent surveyor) {
        super.setString(COL_SIGNAL_SURFACE_NAME, surveyor.getSignalSurfaceName());

        super.setDouble(COL_MIN_X, surveyor.getMinX());
        super.setDouble(COL_MAX_X, surveyor.getMaxX());
        super.setDouble(COL_INTERVAL_X, surveyor.getResolution());
        super.setLong(COL_COUNT_X, surveyor.getCountX());


        super.setDouble(COL_MIN_Y, surveyor.getMinY());
        super.setDouble(COL_MAX_Y, surveyor.getMaxY());
        super.setDouble(COL_INTERVAL_Y, surveyor.getResolution());
        super.setLong(COL_COUNT_Y, surveyor.getCountY());

        super.setDouble(COL_SIGNAL_SURFACE_SD, surveyor.getSurfaceSD());
        super.setDouble(COL_SIGNAL_SURFACE_MAGNIFICATION, surveyor.getSurfaceMag());


    }


}
