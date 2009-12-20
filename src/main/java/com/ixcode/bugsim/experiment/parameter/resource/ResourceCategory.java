/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.parameter.resource;

import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.signal.SignalStrategyBase;
import com.ixcode.bugsim.experiment.parameter.resource.signal.SignalStrategyFactory;
import com.ixcode.bugsim.experiment.parameter.resource.signal.MultipleSurfaceSignalStrategy;
import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.agent.cabbage.CabbageAgent;
import com.ixcode.framework.parameter.model.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 *
 * @todo - maybe move this into framework ?
 * @todo make a special Category /  strategy for Cabbages to include Egg Count interval
 */
public class ResourceCategory extends CategoryDefinition {

    public static Category createDefaultC() {
        StrategyDefinitionParameter resourceLayoutS = PredefinedResourceLayoutStrategy.createDefaultStrategyS();
        StrategyDefinitionParameter signalS = MultipleSurfaceSignalStrategy.createDefaultSignalS();
        return createC(resourceLayoutS, signalS, false, 1);

    }
    public static Category createC(StrategyDefinitionParameter resourceLayoutS, StrategyDefinitionParameter signalS, boolean includeFinalEggCount, long countInterval) {
        Category resourceC = new Category(ResourceCategory.C_RESOURCE);

        resourceC.addParameter(new Parameter(P_AGENT_CLASS, CabbageAgent.class.getName()));
        resourceC.addParameter(new Parameter(P_INCLUDE_FINAL_EGG_COUNT, includeFinalEggCount));
        resourceC.addParameter(new Parameter(P_EGG_COUNT_INTERVAL, countInterval));

        resourceC.addParameter(new Parameter(P_RESOURCE_LAYOUT, resourceLayoutS));
        resourceC.addParameter(new Parameter(P_RESOURCE_SIGNAL, signalS));

        return resourceC;

    }

    public ResourceCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);

        ResourceLayoutStrategyBase layoutStrategy = (ResourceLayoutStrategyBase)createStrategyDefinition(P_RESOURCE_LAYOUT, getResourceLayoutS());
        super.addStrategyDefinition(P_RESOURCE_LAYOUT,layoutStrategy);

        SignalStrategyBase signalStrategy = (SignalStrategyBase)createStrategyDefinition(P_RESOURCE_SIGNAL, getResourceSignalS());
        super.addStrategyDefinition(P_RESOURCE_SIGNAL,signalStrategy);


    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition strategy = null;
        if (parameterName.equals(P_RESOURCE_LAYOUT)) {
            strategy = ResourceLayoutStrategyFactory.createResourceLayoutStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        }  else if (parameterName.equals(P_RESOURCE_SIGNAL)) {
            strategy = SignalStrategyFactory.createSignalStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        }else {
            strategy = super.createStrategyDefinition(parameterName, strategyS);
        }
        return strategy;

    }

    private StrategyDefinitionParameter getResourceLayoutS() {
        return (StrategyDefinitionParameter)super.getParameter(P_RESOURCE_LAYOUT).getValue();
    }

    private StrategyDefinitionParameter getResourceSignalS() {
        return (StrategyDefinitionParameter)super.getParameter(P_RESOURCE_SIGNAL).getValue();
    }

    public String getAgentClass() {
        return super.getParameter(P_AGENT_CLASS).getStringValue();
    }

    public void setAgentClass(String agentClass) {
        super.setParameterValue(P_AGENT_CLASS, agentClass);
    }

    public ResourceLayoutStrategyBase getResourceLayout() {
        return (ResourceLayoutStrategyBase)super.getStrategyDefinition(P_RESOURCE_LAYOUT);
    }

    public void setResourceLayout(ResourceLayoutStrategyBase layoutStrategy) {
        super.replaceStrategyDefinition(P_RESOURCE_LAYOUT, layoutStrategy);
    }

    public void setResourceSignal(SignalStrategyBase signalStrategy) {
        super.replaceStrategyDefinition(P_RESOURCE_SIGNAL, signalStrategy);
    }

    public SignalStrategyBase getResourceSignal() {
        return (SignalStrategyBase)super.getStrategyDefinition(P_RESOURCE_SIGNAL);
    }

    public void setResourceLayoutS(StrategyDefinitionParameter layoutS) {
        super.replaceStrategyDefinitionParameter(P_RESOURCE_LAYOUT, layoutS);
    }


    public void setEggCountInterval(long interval) {
        super.getParameter(P_EGG_COUNT_INTERVAL).setValue(interval);
    }

    /**
     * @return
     */
    public long getEggCountInterval() {
        return super.getParameter(P_EGG_COUNT_INTERVAL).getLongValue();
    }

    public void setIncludeFinalEggCount(boolean includeFinalEggCount) {
        super.getParameter(P_INCLUDE_FINAL_EGG_COUNT).setValue(includeFinalEggCount);
    }

    public boolean isIncludeFinalEggCount() {
        return super.getParameter(P_INCLUDE_FINAL_EGG_COUNT).getBooleanValue();
    }



    public static boolean getIncludeFinalEggCount(ParameterMap params) {
        Category resourceC = params.findCategory(BugsimParameterMap.C_ENVIRONMENT).findSubCategory(C_RESOURCE);
        return resourceC.findParameter(P_INCLUDE_FINAL_EGG_COUNT).getBooleanValue();

    }

    public static final String C_RESOURCE = "resource";

    public static final String P_AGENT_CLASS = "agentClass";
    public static final String P_RESOURCE_LAYOUT = "resourceLayout";

    public static final String P_RESOURCE_SIGNAL = "resourceSignal";

    public static final String P_EGG_COUNT_INTERVAL = "eggCountInterval";
    public static final String P_INCLUDE_FINAL_EGG_COUNT = "includeFinalEggCount";



    public static final String CABBAGE_SIGNAL_SURFACE_NAME = "cabbageSignal";
}
