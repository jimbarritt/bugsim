/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter.landscape;

import com.ixcode.bugsim.model.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.FixedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.BoundaryStrategyBase;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryBase;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class LandscapeParameters {


    public static void addLandscapeParameters(Category environment, double dimension) {
        Category landscapeC = LandscapeCategory.createLandscapeCategory(dimension);


        environment.addSubCategory(landscapeC);
    }


    /**
     * @deprecated pass in bugsim params!!
     * @param landscape
     * @param params
     */
    public static void initialiseLandscape(Landscape landscape, ParameterMap params) {
        initialiseLandscape(landscape, new BugsimParameterMap(params, false));
    }
    /**
     * @param landscape
     * @param params
     * @todo this is the same as in edge effect - maybe make this derive from edge effect.
     */
    public static void initialiseLandscape(Landscape landscape, BugsimParameterMap params) {

        LandscapeCategory landscapeC = params.getLandscapeCategory();

        BoundaryStrategyBase boundaryS = landscapeC.getExtent().getOuterBoundary();

        Map initObjects = StrategyDefinition.createInitialisationObjects(false);
        BoundaryBase boundary = boundaryS.createBoundary(initObjects);

        CartesianBounds b = boundary.getBounds();
        BoundaryShape shape = boundary.getShape();


        boolean isCircular = (shape == BoundaryShape.CIRCULAR);

        landscape.setLogicalBounds(b);
        landscape.setCircular(isCircular);
    }

    private static BoundaryShape getLandscapeShape(ParameterMap params) {
        return getLandscapeShapeP(params);
    }

    private static BoundaryShape getLandscapeShapeP(ParameterMap params) {
        Parameter boundaryP = params.findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
        StrategyDefinitionParameter boundaryStrategy = (StrategyDefinitionParameter)boundaryP.getValue();

        return (BoundaryShape)boundaryStrategy.findParameter(BoundaryParameters.P_BOUNDARY_SHAPE).getValue();
    }

    private static CartesianBounds getLandscapeBounds(ParameterMap params) {
        return (CartesianBounds)getLandscapeBoundsP(params).getValue();
    }

    public static String getParameterSummary(ParameterMap params) {
        String summary = "";
        Parameter borderDef = params.findParameter(ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
        StrategyDefinitionParameter borderAlg = (StrategyDefinitionParameter)borderDef.getValue();
        if (borderAlg.getName().equals(DistancedExtentStrategy.S_DISTANCED_EXTENT)) {
            Parameter distance = borderAlg.findParameter(DistancedExtentStrategy.P_DISTANCE);
            summary = "B=" + D2.format(distance.getDoubleValue());
        }
        return summary;
    }

    public static void setLandscapeBounds(ExperimentPlan plan, CartesianBounds bounds) {
        ParameterMap params = plan.getParameterTemplate();


        Parameter borderDefinition = params.findParameter(ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);

        StrategyDefinitionParameter borderAlgorithm = (StrategyDefinitionParameter)borderDefinition.getValue();
        if (!borderAlgorithm.getName().equals(FixedExtentStrategy.S_FIXED_EXTENT)) {
            throw new RuntimeException("Tried to set the border parameters for the landscape but its not a fixed border!");
        }
        Parameter borderBounds = borderAlgorithm.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
        borderBounds.setValue(bounds);


    }

    public static Parameter getLandscapeBoundaryDefinitionP(ParameterMap params) {
        return params.findParameter(ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
    }
    public static Parameter getLandscapeBoundsP(ParameterMap params) {
        Parameter borderDefinition = params.findParameter(ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);

        StrategyDefinitionParameter boundaryStrategy = (StrategyDefinitionParameter)borderDefinition.getValue();
        return BoundaryParameters.getBoundaryBoundsP(boundaryStrategy);

    }

    public static DerivedParameter createLandscapeCentredLocation(ParameterMap params, double verticalAdjustment, double horizontalAdjustment) {
        final Parameter landscapeBoundsP = LandscapeParameters.getLandscapeBoundsP(params);

        final String landscapeBoundsPN = landscapeBoundsP.getName();
        DerivedParameter location = new DerivedParameter(P_LOCATION, new IDerivedParameterCalculation() {

            public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
                //@todo Need to pass in the parent of the landscape bounds i think so we can monitor it for any updates ...
                forwardingMap.addForward(landscapeBoundsPN);
                forwardingMap.addForward(P_VERTICAL_ADJUSTMENT);
                forwardingMap.addForward(P_HORIZONTAL_ADJUSTMENT);
            }

            public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
                Parameter landscapeBounds = sourceParams.getParameter(landscapeBoundsPN);
                double verticalAdjustment = sourceParams.getParameter(P_VERTICAL_ADJUSTMENT).getDoubleValue();
                double horizontalAdjustment = sourceParams.getParameter(P_HORIZONTAL_ADJUSTMENT).getDoubleValue();
                CartesianBounds bounds = (CartesianBounds)landscapeBounds.getValue();
                return new RectangularCoordinate(bounds.getDoubleCentreX() + horizontalAdjustment, bounds.getDoubleCentreY() + verticalAdjustment);
            }
        });


        Parameter verticalAdjustmentP = new Parameter(P_VERTICAL_ADJUSTMENT, verticalAdjustment);
        Parameter horizontalAdjustmentP = new Parameter(P_HORIZONTAL_ADJUSTMENT, horizontalAdjustment);
        location.addSourceParameter(landscapeBoundsP);
        location.addSourceParameter(verticalAdjustmentP);
        location.addSourceParameter(horizontalAdjustmentP);

        return location;
    }

    public static RectangularCoordinate getLocation(StrategyDefinitionParameter strategyParam) {
        return (RectangularCoordinate)strategyParam.findParameter(P_LOCATION).getValue();
    }



    public static final String ENVIRONMENT_LANDSCAPE = EnvironmentParameters.C_ENVIRONMENT + "." + LandscapeCategory.C_LANDSCAPE;
    public static final String ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF = ENVIRONMENT_LANDSCAPE + "." + BoundaryParameters.P_BOUNDARY;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_FIXED = ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF + "." + FixedExtentStrategy.S_FIXED_EXTENT;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_FIXED_BOUNDS = ENVIRONMENT_LANDSCAPE_BORDER_DEF_FIXED + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN = ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF + "." + BoundaryParameters.PROPORTIONAL_BOUNDARY;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN_SOURCE = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN + "." + DistancedExtentStrategy.P_INNER_BOUNDARY;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN_PROPORTION = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN + "." + BoundaryParameters.PROPORTION;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_PROP_BOUNDS = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DYN + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST = ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF + "." + DistancedExtentStrategy.S_DISTANCED_EXTENT;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST_SOURCE = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST + "." + DistancedExtentStrategy.P_INNER_BOUNDARY;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST_PROPORTION = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST + "." + DistancedExtentStrategy.P_DISTANCE;
    public static final String ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST_BOUNDS = ENVIRONMENT_LANDSCAPE_BORDER_DEF_DIST + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;
    private static final Logger log = Logger.getLogger(LandscapeParameters.class);


    private static final String P_LOCATION = "location";
    private static final String P_VERTICAL_ADJUSTMENT = "verticalAdjust";
    private static final String P_HORIZONTAL_ADJUSTMENT = "horizontalAdjust";

    private static final DecimalFormat D2 = new DecimalFormat("0.00");
}
