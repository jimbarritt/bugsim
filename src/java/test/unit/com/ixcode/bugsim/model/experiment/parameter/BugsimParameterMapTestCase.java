/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.bugsim.model.experiment.parameter;

import com.ixcode.bugsim.model.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import junit.framework.TestCase;

/**
 * TestCase for class : BugsimParameterMap
 * Created     : Jan 29, 2007 @ 2:56:38 PM by jim
 */
public class BugsimParameterMapTestCase extends TestCase {


    public void testForwardingOfBoundaryEvents() {
        ParameterMap params = createTestParams();


        ParameterMapDebug.debugParams(params);


        Parameter resourceDimensionP = params.findParameter(R_RESOURCE_DIMENSION);
        assertNotNull(R_RESOURCE_DIMENSION, resourceDimensionP);
        Parameter innerDimensionP = params.findParameter(R_INNER_DIMENSION);
        assertNotNull(R_INNER_DIMENSION , innerDimensionP);
        Parameter outerDimensionP = params.findParameter(R_OUTER_DIMENSION);
        assertNotNull(R_OUTER_DIMENSION, outerDimensionP);

        TestParameterListener l = new TestParameterListener();
        outerDimensionP.addParameterListener(l);

//        innerDimensionP.setValue(new CartesianDimensions(500));
        resourceDimensionP.setValue(new CartesianDimensions(500));

        assertTrue(l.isParamValueChanged());



    }

    private ParameterMap createTestParams() {
        ParameterMap params = new ParameterMap();
        Category  environmentC = new Category(EnvironmentParameters.C_ENVIRONMENT);

        Parameter resourceBoundaryP = addResourceCategory(environmentC);
        addLandscapeCategory(environmentC, resourceBoundaryP);





        params.addCategory(environmentC);
        return params;
    }

    private void addLandscapeCategory(Category environmentC, Parameter sourceBoundaryP) {
        Category landscapeC = new Category(LandscapeCategory.C_LANDSCAPE);


        StrategyDefinitionParameter extentS = DistancedExtentStrategyFactory.createDistancedExtentS(BoundaryShape.RECTANGULAR, sourceBoundaryP, 20, ShapeLocationType.BOTTOM_LEFT );
        Parameter extentP = new Parameter(LandscapeCategory.P_EXTENT, extentS);

        landscapeC.addParameter(extentP);

        environmentC.addSubCategory(landscapeC);
    }

    private Parameter addResourceCategory(Category environmentC) {
        Category resourceC = new Category(ResourceCategory.C_RESOURCE);
        StrategyDefinitionParameter resourceLayoutS = new StrategyDefinitionParameter(PredefinedResourceLayoutStrategy.STRATEGY_NAME, PredefinedResourceLayout.class.getName());

        StrategyDefinitionParameter resourceBoundaryS = RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(200));
        Parameter resourceBoundaryP = new Parameter(ResourceLayoutStrategyBase.P_LAYOUT_BOUNDARY, resourceBoundaryS);
        resourceLayoutS.addParameter(resourceBoundaryP);
        Parameter  resourceLayoutP = new Parameter(ResourceCategory.P_RESOURCE_LAYOUT, resourceLayoutS);


        resourceC.addParameter(resourceLayoutP);

        environmentC.addSubCategory(resourceC);

        return resourceBoundaryP;
    }

    public static final String R_RESOURCE_DIMENSION = EnvironmentParameters.C_ENVIRONMENT + "." + ResourceCategory.C_RESOURCE+ "." + ResourceCategory.P_RESOURCE_LAYOUT+ "." + PredefinedResourceLayoutStrategy.STRATEGY_NAME + "." + PredefinedResourceLayoutStrategy.P_LAYOUT_BOUNDARY + "."+ RectangularBoundaryStrategy.S_RECTANGULAR + "." + RectangularBoundaryStrategy.P_DIMENSIONS;
    public static final String R_INNER_DIMENSION = EnvironmentParameters.C_ENVIRONMENT + "." + LandscapeCategory.C_LANDSCAPE + "." + LandscapeCategory.P_EXTENT + "." + DistancedExtentStrategy.S_DISTANCED_EXTENT + "." + DistancedExtentStrategy.P_INNER_BOUNDARY + "."+ RectangularBoundaryStrategy.S_RECTANGULAR + "." + RectangularBoundaryStrategy.P_DIMENSIONS;
    public static final String R_OUTER_DIMENSION = EnvironmentParameters.C_ENVIRONMENT + "." + LandscapeCategory.C_LANDSCAPE + "." + LandscapeCategory.P_EXTENT + "." + DistancedExtentStrategy.S_DISTANCED_EXTENT + "." + DistancedExtentStrategy.P_OUTER_BOUNDARY + "."+ RectangularBoundaryStrategy.S_RECTANGULAR + "." + RectangularBoundaryStrategy.P_DIMENSIONS;

}
