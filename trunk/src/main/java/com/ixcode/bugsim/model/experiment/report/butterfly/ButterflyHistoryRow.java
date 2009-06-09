/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.report.butterfly;

import com.ixcode.bugsim.model.agent.butterfly.ForagingAgentBehaviour;
import com.ixcode.bugsim.model.agent.cabbage.CabbageAgent;
import com.ixcode.framework.io.csv.CSVRow;
import com.ixcode.framework.simulation.experiment.report.ExperimentRow;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.math.geometry.DirectionOfChange;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyHistoryRow extends ExperimentRow {



    public static final int COLUMN_COUNT = ExperimentRow.columnCount() + 13;

    public static final int COL_BUTTERFLY_ID = ExperimentRow.columnCount() + 0;
    public static final int COL_BUTTERFLY_AGE = ExperimentRow.columnCount() + 1;
    public static final int COL_X = ExperimentRow.columnCount() + 2;
    public static final int COL_Y = ExperimentRow.columnCount() + 3;
    public static final int COL_DIRECTION = ExperimentRow.columnCount() + 4;
    public static final int COL_BEHAVIOUR = ExperimentRow.columnCount() + 5;
    public static final int COL_EGG_COUNT = ExperimentRow.columnCount() + 6;
    public static final int COL_CURRENT_CABBAGE_ID = ExperimentRow.columnCount() + 7;
    public static final int COL_CURRENT_CABBAGE_X = ExperimentRow.columnCount() + 8;
    public static final int COL_CURRENT_CABBAGE_Y = ExperimentRow.columnCount() + 9;
    public static final int COL_LAST_ANGLE_OF_TURN = ExperimentRow.columnCount() + 10;
    public static final int COL_LAST_MOVE_LENGTH = ExperimentRow.columnCount() + 11;
    public static final int COL_LAST_MOVE_DIRECTION_OF_CHANGE = ExperimentRow.columnCount() + 12;


    public static final CSVRow HEADER_ROW = new CSVRow(COLUMN_COUNT);

    static {
        HEADER_ROW.setString(COL_EXPERIMENT_ID, "Experiment");
        HEADER_ROW.setString(COL_ITERATION_ID, "Iteration");
        HEADER_ROW.setString(COL_REPLICATE_ID, "Replicate");

        HEADER_ROW.setString(COL_BUTTERFLY_ID, "Id");
        HEADER_ROW.setString(COL_BUTTERFLY_AGE, "Age");
        HEADER_ROW.setString(COL_BEHAVIOUR, "Behaviour");
        HEADER_ROW.setString(COL_EGG_COUNT, "EggCount");
        HEADER_ROW.setString(COL_DIRECTION, "Azimuth");
        HEADER_ROW.setString(COL_X, "X");
        HEADER_ROW.setString(COL_Y, "Y");

        HEADER_ROW.setString(COL_CURRENT_CABBAGE_ID, "Cabbage.Id");
        HEADER_ROW.setString(COL_CURRENT_CABBAGE_X, "Cabbage.X");
        HEADER_ROW.setString(COL_CURRENT_CABBAGE_Y, "Cabbage.Y");
        /**
         * This might not be the same as the ACTUAL angle of turn - if the foraging strategy changes the location....
         * Maybe the foraging strategy needs to return a full Move object aswell so you can see the REAL angle of turn.
         * Only need to implement this though if we start using the "LandOnResource" parameter.
         */
        HEADER_ROW.setString(COL_LAST_ANGLE_OF_TURN, "LastAngleOfTurn");
        HEADER_ROW.setString(COL_LAST_MOVE_LENGTH, "LastMoveLength");
        HEADER_ROW.setString(COL_LAST_MOVE_DIRECTION_OF_CHANGE, "LastMoveDirectionOfChange");




    }

    public ButterflyHistoryRow() {
        super(COLUMN_COUNT);
    }

    public void setLastMove(Move lastMove) {
        if (lastMove != null) {
            double angle = lastMove.getCourseChange().getAngleOfChange();
//            angle = (lastMove.getCourseChange().getDirectionOfChange() == DirectionOfChange.ANTI_CLOCKWISE) ? -angle : angle;
            super.setDouble(COL_LAST_ANGLE_OF_TURN, angle);
            super.setDouble(COL_LAST_MOVE_LENGTH, lastMove.getDistance());
            super.setString(COL_LAST_MOVE_DIRECTION_OF_CHANGE, lastMove.getCourseChange().getDirectionOfChange().getName());
        } else {
            super.setString(COL_LAST_ANGLE_OF_TURN,"NA");
             super.setString(COL_LAST_MOVE_LENGTH,"NA");
             super.setString(COL_LAST_MOVE_DIRECTION_OF_CHANGE,"NA");
        }

    }

    public void setId(long id) {
        super.setLong(COL_BUTTERFLY_ID, id);
    }

    public void setBehaviour(ForagingAgentBehaviour behaviour) {
        super.setString(COL_BEHAVIOUR, behaviour.getName());
    }

    public void setEggCount(long eggCount) {
        super.setLong(COL_EGG_COUNT, eggCount);
    }

    public void setDirection(double direction) {
        super.setDouble(COL_DIRECTION, direction);
    }

    public void setLocation(Location location) {
        setX(location.getDoubleX());
        setY(location.getDoubleY());
    }

    public void setX(double x) {
        super.setDouble(COL_X, x);
    }

    public void setCabbage(CabbageAgent cabbage) {
        if (cabbage != null) {
            super.setLong(COL_CURRENT_CABBAGE_ID, cabbage.getId());
            super.setDouble(COL_CURRENT_CABBAGE_X, cabbage.getLocation().getDoubleX());
            super.setDouble(COL_CURRENT_CABBAGE_Y, cabbage.getLocation().getDoubleY());
        }
    }

    public void setY(double y) {
        super.setDouble(COL_Y, y);
    }

    public void setAge(long age) {
        super.setLong(COL_BUTTERFLY_AGE, age);
    }
}
