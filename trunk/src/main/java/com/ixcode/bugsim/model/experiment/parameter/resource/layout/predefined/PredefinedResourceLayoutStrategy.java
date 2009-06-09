/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined;

import com.ixcode.bugsim.model.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 *  Description : ${CLASS_DESCRIPTION}
 *  Created     : Jan 25, 2007 @ 11:46:24 AM by jim
 */
public class PredefinedResourceLayoutStrategy extends ResourceLayoutStrategyBase {
    public PredefinedResourceLayoutStrategy(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }
    public PredefinedResourceLayoutStrategy(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(sparam, params, forwardEvents);

    }
    public static StrategyDefinitionParameter createDefaultStrategyS() {
        return createStrategyS(new CartesianDimensions(100, 100), new ArrayList(), "", 10L, 0L);
    }

    public static StrategyDefinitionParameter createStrategyS(CartesianDimensions boundarySize, List resources, String layoutName, long searchGridResolution, long expectedEggCount) {
        StrategyDefinitionParameter predefinedS = new StrategyDefinitionParameter(STRATEGY_NAME, PredefinedResourceLayout.class.getName());
        Parameter boundaryDistanceP = new Parameter(P_BOUNDARY_DISTANCE, 0d);
        Parameter resourcesP = new Parameter(P_RESOURCES, resources);
        predefinedS.addParameter(boundaryDistanceP);
        predefinedS.addParameter(resourcesP);

        StrategyDefinitionParameter boundaryS = RectangularBoundaryStrategy.createRectangularBoundaryS(boundarySize);

        predefinedS.addParameter(new Parameter(P_LAYOUT_BOUNDARY, boundaryS));
        predefinedS.addParameter(new Parameter(P_LAYOUT_NAME, layoutName));
        predefinedS.addParameter(new Parameter(P_EXPECTED_EGG_COUNT, expectedEggCount));
        predefinedS.addParameter(new Parameter(P_SEARCH_GRID_RESOLUTION, searchGridResolution));
        predefinedS.addParameter(new Parameter(P_FIXED_RESOURCE_RADIUS, 0d));
        predefinedS.addParameter(new Parameter(P_APPLY_FIXED_RESOURCE_RADIUS, false));
//        IDerivedParameterCalculation resourceDimensionCalc = new PredefinedResourceDimensionCalculation();
//        BoundaryStrategyFactory.createBoundaryS(boundaryShape, )

        return predefinedS;
    }



    public static void register(StrategyRegistry registry) {
        registry.registerStrategy(PredefinedResourceLayoutStrategy.class, PredefinedResourceLayout.class, STRATEGY_NAME, L_PREDEFINED);
    }


    public double getBoundaryDistance() {
        return super.getParameter(P_BOUNDARY_DISTANCE).getDoubleValue();
    }

    public void setBoundaryDistance(double distance) {
        super.setParameterValue(P_BOUNDARY_DISTANCE, distance);
    }

    public List getResources() {
        return (List)super.getParameter(P_RESOURCES).getValue();
    }

    public void setResources(List resources) {
        super.setParameterValue(P_RESOURCES, resources);
    }

    public void setParameterValue(String name, Object value) {
        if (name.equals(P_LAYOUT_BOUNDARY)) {
            StrategyDefinitionParameter sdp = (StrategyDefinitionParameter)value;
            if (log.isInfoEnabled()) {
                log.info("updatingBoundary: " + name + " : " + sdp.getFullyQualifiedName());
            }
        }
        super.setParameterValue(name, value);
    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {
        super.parameterValueChanged(event);
    }


    public double getFixedResourceRadius() {
        return super.getParameter(P_FIXED_RESOURCE_RADIUS).getDoubleValue();
    }

    public void setFixedResourceRadius(double radius) {
        super.getParameter(P_FIXED_RESOURCE_RADIUS).setValue(radius);
    }

    public boolean getApplyFixedResourceRadius() {
        return super.getParameter(P_APPLY_FIXED_RESOURCE_RADIUS).getBooleanValue();
    }

    public void setApplyFixedResourceRadius(boolean apply) {
        super.getParameter(P_APPLY_FIXED_RESOURCE_RADIUS).setValue(apply);
    }

    private static String getDefaultResourceLayoutFilename() {
        String defaultFileName =   "/Users/Jim/Work/Projects/bugsim/experimental-data/published/chapter-4/resource-layouts/Field E - Resource Layout.csv";
        File local = new File("./field-resource-layouts/Field E - Resource Layout.csv");
        if (!local.exists()) {
            defaultFileName = System.getProperty("bugsim.defaultResourceLayoutFilename", defaultFileName);
        } else {
            defaultFileName = local.getAbsolutePath();
        }
        if (log.isInfoEnabled()) {
            log.info("Using default resource layout filename: " + defaultFileName);
        }
        return defaultFileName;
    }



    private static final Logger log = Logger.getLogger(PredefinedResourceLayoutStrategy.class);
    public static final String STRATEGY_NAME = "predefinedLayout";
    public static final String L_PREDEFINED = "Predefined";


    public static final String P_BOUNDARY_DISTANCE = "boundaryDistance";
    public static final String P_RESOURCES = "resources";

    public static final String P_FIXED_RESOURCE_RADIUS = "fixedResourceRadius";
    public static final String P_APPLY_FIXED_RESOURCE_RADIUS = "applyFixedResourceRadius";
    public static final String DEFAULT_RESOURCE_LAYOUT_FILENAME = getDefaultResourceLayoutFilename();
}

