/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.experiment.report;

import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryCrossing;

/**
 * Description : Extends CSV Row to encapsulate all the indexes
 */
public class BoundaryCrossingRow extends ExperimentRow {


    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 29;

    public static CSVRow HEADER_ROW = new CSVRow(BoundaryCrossingRow.COLUMN_COUNT);


    public static final int COL_BOUNDARY = ExperimentRow.columnCount();
    public static final int COL_IS_CIRCULAR = ExperimentRow.columnCount() + 1;
    public static final int COL_START_X = ExperimentRow.columnCount() + 2;
    public static final int COL_START_Y = ExperimentRow.columnCount() + 3;
    public static final int COL_END_X = ExperimentRow.columnCount() + 4;
    public static final int COL_END_Y = ExperimentRow.columnCount() + 5;
    public static final int COL_INTERSECTION_X = ExperimentRow.columnCount() + 6;
    public static final int COL_INTERSECTION_Y = ExperimentRow.columnCount() + 7;
    public static final int COL_HEADING = ExperimentRow.columnCount() + 8;
    public static final int COL_DISTANCE = ExperimentRow.columnCount() + 9;
    public static final int COL_TYPE = ExperimentRow.columnCount() + 10;

    public static final int COL_I = ExperimentRow.columnCount() + 11;
    public static final int COL_R = ExperimentRow.columnCount() + 12;
    public static final int COL_Q = ExperimentRow.columnCount() + 13;
    public static final int COL_B = ExperimentRow.columnCount() + 14;
    public static final int COL_L = ExperimentRow.columnCount() + 15;
    public static final int COL_A = ExperimentRow.columnCount() + 16;
    public static final int COL_S = ExperimentRow.columnCount() + 17;


    public static final int COL_DISTANCE_TO_BOUNDARY = ExperimentRow.columnCount() + 18;
    public static final int COL_DISTANCE_TO_CENTER = ExperimentRow.columnCount() + 19;

    public static final int COL_BOUNDARY_X = ExperimentRow.columnCount() + 20;
    public static final int COL_BOUNDARY_Y = ExperimentRow.columnCount() + 21;
    public static final int COL_BOUNDARY_W = ExperimentRow.columnCount() + 22;
    public static final int COL_BOUNDARY_H = ExperimentRow.columnCount() + 23;
    public static final int COL_BOUNDARY_CENTRE_X = ExperimentRow.columnCount() + 24;
    public static final int COL_BOUNDARY_CENTRE_Y = ExperimentRow.columnCount() + 25;
    public static final int COL_BOUNDARY_R = ExperimentRow.columnCount() + 26;

    public static final int COL_END_DISTANCE_TO_BOUNDARY = ExperimentRow.columnCount() + 27;
    public static final int COL_END_DISTANCE_TO_CENTER = ExperimentRow.columnCount() + 28;


    static {
        BoundaryCrossingRow.HEADER_ROW.setString(COL_EXPERIMENT_ID, "experiment");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_ITERATION_ID, "iteration");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_REPLICATE_ID, "replicant");

        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY, "boundary");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_IS_CIRCULAR, "isCircular");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_START_X, "startX");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_START_Y, "startY");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_END_X, "endX");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_END_Y, "endY");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_INTERSECTION_X, "intersectionX");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_INTERSECTION_Y, "intersectionY");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_HEADING, "h");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_DISTANCE, "d");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_TYPE, "crossingType");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_I, "I");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_R, "r");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_Q, "q");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_B, "B");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_L, "L");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_A, "A");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_S, "S");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_DISTANCE_TO_BOUNDARY, "d.toBoundary");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_DISTANCE_TO_CENTER, "d.toCentre");

        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_X, "boundaryX");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_Y, "boundaryY");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_W, "boundaryW");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_H, "boundaryH");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_CENTRE_X, "boundaryCentreX");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_CENTRE_Y, "boundaryCentreY");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_BOUNDARY_R, "boundaryR");

        BoundaryCrossingRow.HEADER_ROW.setString(COL_END_DISTANCE_TO_BOUNDARY, "d.end.toBoundary");
        BoundaryCrossingRow.HEADER_ROW.setString(COL_END_DISTANCE_TO_CENTER, "d.end.toCentre");


    }

    public BoundaryCrossingRow() {
        super(BoundaryCrossingRow.COLUMN_COUNT);

    }


    public void setStartCoord(RectangularCoordinate coord) {
        super.setDouble(COL_START_X, coord.getDoubleX());
        super.setDouble(COL_START_Y, coord.getDoubleY());
    }

    public void setEndCoord(RectangularCoordinate coord) {
        super.setDouble(COL_END_X, coord.getDoubleX());
        super.setDouble(COL_END_Y, coord.getDoubleY());
    }

    public void setCrossing(BoundaryCrossing crossing) {

        setBoundaryBounds(crossing.getBoundaryBounds());
        super.setBoolean(COL_IS_CIRCULAR, crossing.isCircular());
        setStartCoord(crossing.getStartCoord());
        setEndCoord(crossing.getEndCoord());
        super.setDouble(COL_INTERSECTION_X, crossing.getIntersection().getDoubleX());
        super.setDouble(COL_INTERSECTION_Y, crossing.getIntersection().getDoubleY());
        super.setDouble(COL_HEADING, crossing.getHeading());
        super.setDouble(COL_DISTANCE, crossing.getDistance());
        super.setString(COL_TYPE, "" + crossing.getCrossingType());
        super.setDouble(COL_I, crossing.getI());
        super.setDouble(COL_R, crossing.getR());
        super.setDouble(COL_Q, crossing.getQ());
        super.setDouble(COL_DISTANCE_TO_BOUNDARY, crossing.getDistanceToBoundary());
        super.setDouble(COL_DISTANCE_TO_CENTER, crossing.getDistanceToCenter());

        super.setDouble(COL_END_DISTANCE_TO_BOUNDARY, crossing.getEndDistanceToBoundary());
        super.setDouble(COL_END_DISTANCE_TO_CENTER, crossing.getEndDistanceToCenter());

    }

    private void setBoundaryBounds(CartesianBounds boundary) {
        super.setString(COL_BOUNDARY, "" + boundary); //@todo replace with a real boundary and then we can put the name here!
        super.setDouble(COL_BOUNDARY_X, boundary.getDoubleX());
        super.setDouble(COL_BOUNDARY_Y,  boundary.getDoubleY());
        super.setDouble(COL_BOUNDARY_W, boundary.getDoubleWidth());
        super.setDouble(COL_BOUNDARY_H, boundary.getDoubleHeight());
        super.setDouble(COL_BOUNDARY_CENTRE_X, boundary.getDoubleCentreX());
        super.setDouble(COL_BOUNDARY_CENTRE_Y,  boundary.getDoubleCentreY());
        super.setDouble(COL_BOUNDARY_R, boundary.getRadiusOfInnerCircle());
    }

    public void setB(double B) {
        super.setDouble(COL_B, B);
    }

    public void setL(double L) {
        super.setDouble(COL_L, L);
    }

    public void setA(double A) {
        super.setDouble(COL_A, A);
    }

    public void setS(double S) {
        super.setDouble(COL_S, S);
    }


}
