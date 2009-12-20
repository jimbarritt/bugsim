/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.forager.behaviour.foraging;

import com.ixcode.bugsim.agent.butterfly.EggLayingForagingStrategy;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 5, 2007 @ 1:31:04 PM by jim
 */
public class EggLayingForagingStrategyDefinition extends ForagingStrategyBase {

    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(1, true, false, true);
    }

    public static StrategyDefinitionParameter createStrategyS(long numberOfEggs, boolean landOnCabbage, boolean layOnCurrentResource, boolean optimiseSearch) {
        StrategyDefinitionParameter eggLayingS = new StrategyDefinitionParameter(EggLayingForagingStrategyDefinition.S_EGG_LAYING_FORAGING, EggLayingForagingStrategy.class.getName());
        eggLayingS.addParameter(new Parameter(EggLayingForagingStrategyDefinition.P_NUMBER_OF_EGGS, numberOfEggs));
        eggLayingS.addParameter(new Parameter(EggLayingForagingStrategyDefinition.P_LAND_ON_RESOURCE, landOnCabbage));
        eggLayingS.addParameter(new Parameter(EggLayingForagingStrategyDefinition.P_LAY_ON_CURRENT_REOURCE, layOnCurrentResource));
        eggLayingS.addParameter(new Parameter(P_OPTIMISE_SEARCH, optimiseSearch));
        return eggLayingS;
    }

    public EggLayingForagingStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        super(sparam, params);
    }

    public EggLayingForagingStrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);
    }

    public long getNumberOfEggs() {
        return super.getParameter(P_NUMBER_OF_EGGS).getLongValue();
    }

    public void setNumberOfEggs(long numberOfEggs) {
        super.setParameterValue(P_NUMBER_OF_EGGS, numberOfEggs);
    }

    public boolean isLandOnResource() {
        return super.getParameter(P_LAND_ON_RESOURCE).getBooleanValue();
    }

    public void setLandOnResource(boolean landOnCabbage) {
        super.setParameterValue(P_LAND_ON_RESOURCE, landOnCabbage);
    }

    public boolean isLayOnCurrentResource() {
        return super.getParameter(P_LAY_ON_CURRENT_REOURCE).getBooleanValue();
    }

    public void setLayOnCurrentResource(boolean layOnResource) {
        super.getParameter(P_LAY_ON_CURRENT_REOURCE).setValue(layOnResource);
    }

    public boolean isOptimiseSearch() {
        boolean optimise = true;
        if (!super.hasParameter(P_OPTIMISE_SEARCH)) {
            super.getStrategyS().addParameter(new Parameter(P_OPTIMISE_SEARCH, true));    
        }
        optimise = super.getParameter(P_OPTIMISE_SEARCH).getBooleanValue();


        return optimise;
    }

    public void setOptimiseSearch(boolean optimise) {
        if (!super.hasParameter(P_OPTIMISE_SEARCH)) {
             super.getStrategyS().addParameter(new Parameter(P_OPTIMISE_SEARCH, optimise));
        } else {
            super.getParameter(P_OPTIMISE_SEARCH).setValue(optimise);
        }
    }

    public static final String S_EGG_LAYING_FORAGING = "eggLaying";


    public static final String P_NUMBER_OF_EGGS = "numberOfEggs";
    public static final String P_LAND_ON_RESOURCE = "landOnResource";
    public static final String P_LAY_ON_CURRENT_REOURCE = "layOnCurrentResource";
    public static final String P_OPTIMISE_SEARCH = "optimiseSearch";
}
