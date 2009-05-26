/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.bugsim.view.simulation.experiment.EdgeEffectParametersPropertySheet;

import java.util.ArrayList;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public class RandomWalkEdgeEffectProperties extends ExperimentProperties {



    public static final String PARAM_POPULATION_SIZE = "populationSize";
    public static final String PARAM_NUMBER_OF_BUTTERFLIES = "numberOfButterflies";
    public static final String PARAM_BUTTERFLY_MOVE_LENGTH  = "butterflyMoveLength";
    public static final String PARAM_BUTTERFLY_ANGLE_OF_TURN_SD = "butterflyAngleOfTurnSD";
    public static final String PARAM_BUTTERFLY_NUMBER_OF_EGGS = "butterflyNumberOfEggs";


    public RandomWalkEdgeEffectProperties() {
        super(PROPERTY_SHEET_NAMES);
        super.addProperty(PARAM_NUMBER_OF_BUTTERFLIES, Long.class);
        super.addProperty(PARAM_POPULATION_SIZE, Long.class);        
        super.addProperty(PARAM_BUTTERFLY_MOVE_LENGTH, Integer.class);
        super.addProperty(PARAM_BUTTERFLY_ANGLE_OF_TURN_SD, Integer.class);
        super.addProperty(PARAM_BUTTERFLY_NUMBER_OF_EGGS, Long.class);

    }

    public void setPopulationSize(long size) {
        super.setPropertyValue(PARAM_POPULATION_SIZE, new Long(size));
        
    }

    public long getPopulationSize() {
        return super.getLongPropertyValue(PARAM_POPULATION_SIZE);
    }

    public long getButterflyNumberOfEggs() {
        return super.getLongPropertyValue(PARAM_BUTTERFLY_NUMBER_OF_EGGS);
    }
    public void setButterflyNumberOfEggs(long numberOfEggs) {
        super.setPropertyValue(PARAM_BUTTERFLY_NUMBER_OF_EGGS, new Long(numberOfEggs));
    }

    public void setNumberOfButterflies(long numberOfButterflies) {
        super.setPropertyValue(PARAM_NUMBER_OF_BUTTERFLIES, new Long(numberOfButterflies));
    }
    public long getNumberOfButterflies() {
        return super.getLongPropertyValue(PARAM_NUMBER_OF_BUTTERFLIES);
    }

    public void setButterflyMoveLength(int moveLength) {
        super.setPropertyValue(PARAM_BUTTERFLY_MOVE_LENGTH, new Integer(moveLength));
    }

    public int getButterflyMoveLength() {
        return super.getIntPropertyValue(PARAM_BUTTERFLY_MOVE_LENGTH);
    }

    public void setButterflyAngleOfTurnSD(int angleOfTurnSD) {
        super.setPropertyValue(PARAM_BUTTERFLY_ANGLE_OF_TURN_SD, new Integer(angleOfTurnSD));
    }


    public int getButterflyAngleOfTurnSD() {
        return super.getIntPropertyValue(PARAM_BUTTERFLY_ANGLE_OF_TURN_SD);
    }


     private static final List PROPERTY_SHEET_NAMES = new ArrayList();
    static {
        PROPERTY_SHEET_NAMES.add(EdgeEffectParametersPropertySheet.class.getName());
        PROPERTY_SHEET_NAMES.add(com.ixcode.bugsim.view.simulation.experiment.EdgeEffectResultsPropertySheet.class.getName());
    }


}
