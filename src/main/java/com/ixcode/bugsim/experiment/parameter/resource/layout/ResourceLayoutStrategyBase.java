/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.experiment.parameter.resource.layout;

import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.CentredBoundaryLocationDerivedCalculation;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyFactory;
import org.apache.log4j.Logger;


import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 25, 2007 @ 11:23:39 AM by jim
 */
public abstract class ResourceLayoutStrategyBase extends StrategyDefinition {


    protected ResourceLayoutStrategyBase(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

        StrategyDefinition boundaryStrategy = createStrategyDefinition(P_LAYOUT_BOUNDARY, getLayoutBoundaryS());
        super.addStrategyDefinition(P_LAYOUT_BOUNDARY, boundaryStrategy);

    }


    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        StrategyDefinition strategy = null;
        if (parameterName.equals(P_LAYOUT_BOUNDARY)) {
            strategy = BoundaryStrategyFactory.createBoundaryStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            strategy = super.createStrategyDefinition(parameterName, strategyS);
        }
        return strategy;
    }

    public BoundaryStrategyBase getLayoutBoundary() {
        return (BoundaryStrategyBase)super.getStrategyDefinition(P_LAYOUT_BOUNDARY);
    }

    public void setLayoutBoundary(BoundaryStrategyBase boundary) {
        super.replaceStrategyDefinition(P_LAYOUT_BOUNDARY, boundary);
    }

    public StrategyDefinitionParameter getLayoutBoundaryS() {
        return super.getParameter(P_LAYOUT_BOUNDARY).getStrategyDefinitionValue();
    }


    public Parameter getLayoutBoundaryP() {
        return super.getParameter(P_LAYOUT_BOUNDARY);
    }


    public String getLayoutName() {
        return super.getParameter(P_LAYOUT_NAME).getStringValue();
    }

    public void setLayoutName(String layoutName) {
        super.getParameter(P_LAYOUT_NAME).setValue(layoutName);
    }


    public long getExpectedEggCount() {
        if (!super.hasParameter(P_EXPECTED_EGG_COUNT)) {
            super.getStrategyS().addParameter(new Parameter(P_EXPECTED_EGG_COUNT, 0L));
        }
        return super.getParameter(P_EXPECTED_EGG_COUNT).getLongValue();
    }

    public void setExpectedEggCount(long eggCount) {
        super.getParameter(P_EXPECTED_EGG_COUNT).setValue(eggCount);
    }


    public static void centreOnLandscape(ResourceLayoutStrategyBase layoutStrategy, LandscapeCategory landscapeCategory) {
        centreOnLandscape(layoutStrategy, layoutStrategy.getLayoutBoundary(), landscapeCategory);
    }

    public static void centreOnLandscape(ResourceLayoutStrategyBase layoutStrategy, BoundaryStrategyBase boundaryStrategy, LandscapeCategory landscapeCategory) {

        Parameter outerBoundaryP = landscapeCategory.getExtent().getOuterBoundaryP();

        List sp = CentredBoundaryLocationDerivedCalculation.createSourceParameters(outerBoundaryP, layoutStrategy.getLayoutBoundaryP());
        DerivedParameter locationDP = new DerivedParameter(BoundaryStrategyBase.P_LOCATION, sp, new CentredBoundaryLocationDerivedCalculation());

        if (log.isInfoEnabled()) {
            log.info("[setLocationCentred] : newLocationP=" + locationDP);
        }

        boundaryStrategy.setLocationP(locationDP);

    }

    /**
     * @return
     * @todo refactor to have a special base class function which does this and takes a default.
     * @todo make ints work properly!
     */
    public int getSearchGridResolution() {
        if (!super.hasParameter(P_SEARCH_GRID_RESOLUTION)) {
            super.getStrategyS().addParameter(new Parameter(P_SEARCH_GRID_RESOLUTION, 10L));

        }
        return (int)super.getParameter(P_SEARCH_GRID_RESOLUTION).getLongValue();

    }


    public void setSearchGridResolution(int resolution) {
        super.getParameter(P_SEARCH_GRID_RESOLUTION).setValue(resolution);
    }

    public static Parameter findExpectedEggCountP(ParameterMap parameterMap) {
        return parameterMap.findParameter(BugsimParameterMap.C_ENVIRONMENT + "." + ResourceCategory.C_RESOURCE + "." + ResourceCategory.P_RESOURCE_LAYOUT + ".*." + ResourceLayoutStrategyBase.P_EXPECTED_EGG_COUNT);         
    }

    private static final Logger log = Logger.getLogger(ResourceLayoutStrategyBase.class);

    public static final String P_LAYOUT_BOUNDARY = "layoutBoundary";

    public static final String P_LAYOUT_NAME = "layoutName";
    public static final String P_EXPECTED_EGG_COUNT = "expectedEggCount";
    public static final String P_SEARCH_GRID_RESOLUTION = "searchGridResolution";


}
