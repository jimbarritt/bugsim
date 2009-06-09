/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.forager.behaviour;

import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.ForagingStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.ForagingStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.EggLayingForagingStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.MovementStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.MovementStrategyFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.SensoryRandomWalkMovementStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.GaussianAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.model.experiment.parameter.SimulationParameters;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.experiment.model.ExperimentPlan;

import java.util.ArrayList;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 5, 2007 @ 12:43:05 PM by jim
 */
public class BehaviourCategory extends CategoryDefinition {

    public BehaviourCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);

        MovementStrategyBase movementStrategy = (MovementStrategyBase)createStrategyDefinition(P_MOVEMENT, getMovementS());
        super.addStrategyDefinition(P_MOVEMENT, movementStrategy);


        ForagingStrategyBase foragingStrategy =   (ForagingStrategyBase)createStrategyDefinition(P_FORAGING, getForagingS());
        super.addStrategyDefinition(P_FORAGING, foragingStrategy);
    }

    public StrategyDefinition createStrategyDefinition(String paramName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition strategy = null;
        if (paramName.equals(P_MOVEMENT)) {
            strategy = MovementStrategyFactory.createMovementStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else  if (paramName.equals(P_FORAGING)){
            strategy = ForagingStrategyFactory.createForagingStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        }else {
            super.createStrategyDefinition(paramName,  strategyS);
        }
        return strategy;
    }




    private StrategyDefinitionParameter getForagingS() {
        StrategyDefinitionParameter foragingS = super.getParameter(P_FORAGING).getStrategyDefinitionValue();
        return foragingS;
    }


    public MovementStrategyBase getMovementStrategy() {
        return (MovementStrategyBase)super.getStrategyDefinition(P_MOVEMENT);
    }

    public StrategyDefinitionParameter getMovementS() {
        return super.getParameter(P_MOVEMENT).getStrategyDefinitionValue();
    }
    public void setMovementStrategy(MovementStrategyBase movementStrategyBase) {
        super.replaceStrategyDefinition(P_MOVEMENT, movementStrategyBase);
    }

    public StrategyDefinition getForagingStrategy() {
        return super.getStrategyDefinition(P_FORAGING);
    }

    public void setForagingStrategy(StrategyDefinition foragingStrategy) {
        super.replaceStrategyDefinition(P_FORAGING, foragingStrategy);
    }

    public void setMovementS(StrategyDefinitionParameter sensoryRandomWalkS) {
        super.replaceStrategyDefinitionParameter(P_MOVEMENT, sensoryRandomWalkS);
    }


    public boolean isRecordLifeHistory() {
        return super.getParameter(P_RECORD_LIFE_HISTORY).getBooleanValue();
    }

    public void setRecordLifeHistory(boolean record) {
        super.setParameterValue(P_RECORD_LIFE_HISTORY, record);
    }

    public static Category createDefaultC() {
        Category behaviourC = new Category(CATEGORY_NAME);

        Parameter recordButterflyLifeHistory = new Parameter(P_RECORD_LIFE_HISTORY, false);
        behaviourC.addParameter(recordButterflyLifeHistory);


        StrategyDefinitionParameter eggLayingS = EggLayingForagingStrategyDefinition.createDefaultStrategyS();

        Parameter foragingP = new Parameter(P_FORAGING, eggLayingS);

        behaviourC.addParameter(foragingP);

        StrategyDefinitionParameter randomWalk = SensoryRandomWalkMovementStrategy.createDefaultStrategyS();

        Parameter movementBehaviour = new Parameter(P_MOVEMENT, randomWalk);
        behaviourC.addParameter(movementBehaviour);


        Parameter motivationP = new Parameter(P_MOTIVATION, new ArrayList());
        behaviourC.addParameter(motivationP);
        return behaviourC;
    }

    public static boolean getRecordLifeHistory(ParameterMap params) {
        return params.findCategory(ForagerCategory.CATEGORY_NAME).findSubCategory(CATEGORY_NAME).findParameter(P_RECORD_LIFE_HISTORY).getBooleanValue();        
    }



    public static final String CATEGORY_NAME = "behaviour";
    public static final String P_MOVEMENT = "movement";
    public static final String P_FORAGING = "foraging";
    public static final String P_MOTIVATION = "motivation";
    public static final String P_RECORD_LIFE_HISTORY = "recordLifeHistory";

}
