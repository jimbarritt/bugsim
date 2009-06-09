/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.matchstick;

import com.ixcode.bugsim.model.agent.matchstick.MatchstickAgent;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class MatchstickIntersectionsRow extends ExperimentRow {
    public void setIntersection(RectangularCoordinate intersection) {
        if (intersection == null) {
            super.setObject(COL_INTERSECTION_X, null);
            super.setObject(COL_INTERSECTION_Y, null);
            super.setObject(COL_INTERSECTION_D, null);
        } else {
            setIntersectionX(intersection.getDoubleX());
            setIntersectionY(intersection.getDoubleY());
        }
    }


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 12;

    public static final int COL_MATCHSTICK_ID = ExperimentRow.columnCount() + 0;
    public static final int COL_HEADING = ExperimentRow.columnCount() + 1;
    public static final int COL_CENTRE_X = ExperimentRow.columnCount() + 2;
    public static final int COL_CENTRE_Y = ExperimentRow.columnCount() + 3;
    public static final int COL_START_X = ExperimentRow.columnCount() + 4;
    public static final int COL_START_Y = ExperimentRow.columnCount() + 5;
    public static final int COL_END_X = ExperimentRow.columnCount() + 6;
    public static final int COL_END_Y = ExperimentRow.columnCount() + 7;
    public static final int COL_LENGTH = ExperimentRow.columnCount() + 8;
    public static final int COL_INTERSECTION_X = ExperimentRow.columnCount() + 9;
    public static final int COL_INTERSECTION_Y = ExperimentRow.columnCount() + 10;
    public static final int COL_INTERSECTION_D = ExperimentRow.columnCount() + 11;


    public static final CSVRow HEADER_ROW = new CSVRow(MatchstickIntersectionsRow.COLUMN_COUNT);

    static {
        HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");
        HEADER_ROW.setString(COL_REPLICATE_ID, "Replicant");

        HEADER_ROW.setString(COL_MATCHSTICK_ID, "Id");
        HEADER_ROW.setString(COL_HEADING, "Heading");
        HEADER_ROW.setString(COL_CENTRE_X, "CentreX");
        HEADER_ROW.setString(COL_CENTRE_Y, "CentreY");

        HEADER_ROW.setString(COL_START_X, "StartX");
        HEADER_ROW.setString(COL_START_Y, "StartY");
        HEADER_ROW.setString(COL_END_X, "EndX");
        HEADER_ROW.setString(COL_END_Y, "EndY");
        HEADER_ROW.setString(COL_LENGTH, "Length");
        HEADER_ROW.setString(COL_INTERSECTION_X, "IntersectionX");
        HEADER_ROW.setString(COL_INTERSECTION_Y, "IntersectionY");
        HEADER_ROW.setString(COL_INTERSECTION_D, "IntersectionD");


    }

    public MatchstickIntersectionsRow() {
        super(MatchstickIntersectionsRow.COLUMN_COUNT);
    }


    public void setId(long id) {
        super.setLong(MatchstickIntersectionsRow.COL_MATCHSTICK_ID, id);
    }

    public void setHeading(double h) {
        super.setDouble(COL_HEADING, h);
    }

    public void setCentreX(double x) {
        super.setDouble(COL_CENTRE_X, x);
    }

    public void setCentreY(double y) {
        super.setDouble(COL_CENTRE_Y, y);
    }
    public void setStartX(double x) {
        super.setDouble(COL_START_X, x);
    }

    public void setStartY(double y) {
        super.setDouble(COL_START_Y, y);
    }

    public void setEndX(double x) {
        super.setDouble(COL_END_X, x);
    }

    public void setEndY(double y) {
        super.setDouble(COL_END_Y, y);
    }

    public void setLength(double length) {
        super.setDouble(COL_LENGTH, length);
    }

    public void setIntersectionX(double x) {
        super.setDouble(COL_INTERSECTION_X, x);
    }
    public void setIntersectionY(double y) {
        super.setDouble(COL_INTERSECTION_Y, y);
    }

    public void setIntersectionD(double d) {
        super.setDouble(COL_INTERSECTION_D, d);
    }

    public void setMatchStick(MatchstickAgent matchstick) {
        setId(matchstick.getId());
        setHeading(matchstick.getHeading());
        setStartX(matchstick.getStartCoord().getDoubleX());
        setStartY(matchstick.getStartCoord().getDoubleY());
        setEndX(matchstick.getEndCoord().getDoubleX());
        setEndY(matchstick.getEndCoord().getDoubleY());
        setLength(matchstick.getLength());
    }
}


