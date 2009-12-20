package com.ixcode.bugsim.experiment.parameter.landscape;

import com.ixcode.bugsim.experiment.parameter.*;
import com.ixcode.framework.math.geometry.*;
import com.ixcode.framework.math.scale.*;
import com.ixcode.framework.parameter.model.Category;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.*;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.*;
import com.ixcode.framework.simulation.model.landscape.boundary.*;
import org.apache.log4j.*;

import java.util.*;


public class LandscapeCategory extends CategoryDefinition {

    public LandscapeCategory(Category category, ParameterMap params, boolean forwardEvents) {
        super(category, params, forwardEvents);

        StrategyDefinition extentStrategy = createStrategyDefinition(P_EXTENT, getExtentS());
        super.addStrategyDefinition(P_EXTENT, extentStrategy);

        BoundaryStrategyBase boundary = (BoundaryStrategyBase)createStrategyDefinition(P_RELEASE_BOUNDARY, getReleaseBoundaryS());
        super.addStrategyDefinition(P_RELEASE_BOUNDARY, boundary);
    }

    public static Category createLandscapeCategory(double dimension) {
        Category landscapeC = new Category(C_LANDSCAPE);

        double centre = dimension / 2;
        StrategyDefinitionParameter releaseBoundaryS = CircularBoundaryStrategy.createCircularBoundaryS(new RectangularCoordinate(centre, centre), dimension, ShapeLocationType.CENTRE);

        StrategyDefinitionParameter shapeS = RectangularBoundaryStrategy.createRectangularBoundaryS(new CartesianDimensions(dimension));
        StrategyDefinitionParameter fixedExtent = FixedExtentStrategy.createFixedExtentStrategyP(shapeS);

        Parameter scaleP = new Parameter(P_SCALE, new ScaledDistance(1, DistanceUnitRegistry.centimetres()));
        landscapeC.addParameter(scaleP);

        Parameter extentP = new Parameter(P_EXTENT, fixedExtent);
        landscapeC.addParameter(extentP);

        landscapeC.addParameter(new Parameter(P_RELEASE_BOUNDARY, releaseBoundaryS));
        return landscapeC;
    }

    private StrategyDefinitionParameter getExtentS() {
        return (StrategyDefinitionParameter)super.getParameter(P_EXTENT).getStrategyDefinitionValue();
    }

    public ExtentStrategyBase getExtent() {
        return (ExtentStrategyBase)super.getStrategyDefinition(P_EXTENT);
    }


    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        if (parameterName.equals(P_EXTENT)) {
            Map initObjects = StrategyDefinition.createInitialisationObjects(super.isForwardEvents());
            return (ExtentStrategyBase)ParameterisedStrategyFactory.createParameterisedStrategy(strategyS, super.getParameterMap(), initObjects);
        } else if (parameterName.equals(P_RELEASE_BOUNDARY)) {
            return BoundaryStrategyFactory.createBoundaryStrategy(strategyS, super.getParameterMap(), super.isForwardEvents());
        } else {
            return super.createStrategyDefinition(parameterName, strategyS);
        }
    }

    public void setExtentS(StrategyDefinitionParameter extentS) {
        super.replaceStrategyDefinitionParameter(P_EXTENT, extentS);
    }


    public ScaledDistance getScale() {
        return (ScaledDistance)super.getParameter(P_SCALE).getValue();
    }

    public void setScale(ScaledDistance scale) {
        super.setParameterValue(P_SCALE, scale);
    }

    /**
     * Just use it until we get everything worked out to use BugsimParameterMap.... trouble is we only want one per ParameterMap
     *
     * @param params
     * @return
     */
    public static Parameter findLandscapeOuterBoundaryP(ParameterMap params) {
        Category environmentC = params.findCategory(EnvironmentParameters.C_ENVIRONMENT);
        Category landscapeC = environmentC.findCategory(C_LANDSCAPE);
        Parameter extentParameter = landscapeC.findParameter(P_EXTENT);
        return extentParameter.getStrategyDefinitionValue().findParameter(ExtentStrategyBase.P_OUTER_BOUNDARY);
    }

    public static LandscapeCategory findInParameterMap(ParameterMap parameterMap) {
        return new LandscapeCategory(parameterMap.findCategory(EnvironmentParameters.C_ENVIRONMENT).findCategory(C_LANDSCAPE), parameterMap, false);
    }

    public static ScaledDistance findLandscapeScale(ParameterMap parameterMap) {
        return (ScaledDistance)parameterMap.findCategory(EnvironmentParameters.C_ENVIRONMENT).findCategory(C_LANDSCAPE).findParameter(P_SCALE).getValue();
    }

    /**
     * @param landscapeC
     * @param boundaryStrategy
     * @param landscapeCategory
     * @todo move this into the boundary stuff or on landscape category... can do it now that we have generic parameter accessors.
     */
    public static void centreReleaseBoundaryOnLandscape(LandscapeCategory landscapeC, BoundaryStrategyBase boundaryStrategy, LandscapeCategory landscapeCategory) {

        Parameter outerBoundaryP = landscapeCategory.getExtent().getOuterBoundaryP();

        List sp = CentredBoundaryLocationDerivedCalculation.createSourceParameters(outerBoundaryP, landscapeC.getReleaseBoundaryP());
        DerivedParameter locationDP = new DerivedParameter(BoundaryStrategyBase.P_LOCATION, sp, new CentredBoundaryLocationDerivedCalculation());


        boundaryStrategy.setLocationP(locationDP);

    }


    public Parameter getReleaseBoundaryP() {
        return super.getParameter(P_RELEASE_BOUNDARY);
    }

    public StrategyDefinitionParameter getReleaseBoundaryS() {
        return getReleaseBoundaryP().getStrategyDefinitionValue();
    }

    public BoundaryStrategyBase getReleaseBoundary() {
        return (BoundaryStrategyBase)super.getStrategyDefinition(P_RELEASE_BOUNDARY);
    }

    public void setReleaseBoundary(BoundaryStrategyBase boundary) {
        super.replaceStrategyDefinition(P_RELEASE_BOUNDARY, boundary);
    }

    public void setReleaseBoundaryS(StrategyDefinitionParameter releaseBoundaryS) {
        super.replaceStrategyDefinitionParameter(P_RELEASE_BOUNDARY, releaseBoundaryS);
    }


    public static StrategyDefinitionParameter createDistancedReleaseBoundaryS(BoundaryShape shape, Parameter innerBoundaryP, double boundaryDistance, ShapeLocationType locationType) {

        Parameter distanceP = new Parameter(P_RELEASE_DISTANCE, boundaryDistance);

        StrategyDefinitionParameter boundaryS = null;
        if (shape.isCircular()) {
            boundaryS = CircularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP, locationType);
        } else if (shape.isRectangular()) {
            boundaryS = RectangularBoundaryStrategy.createDistancedDimensionBoundaryS(innerBoundaryP, distanceP);
        } else {
            throw new IllegalArgumentException("Cannot create a release boundary of shape " + shape + " yet.");
        }

        boundaryS.addParameter(distanceP);
        return boundaryS;


    }


    /**
     * @return
     * @todo this is not ideal - really we need a whole sub class of boundary strategies which are distanced boundarys but time time time ...
     */
    public double getReleaseDistance() {
        double d = 0;
        StrategyDefinitionParameter releaseBoundaryS = getReleaseBoundaryS();
        if (releaseBoundaryS.hasParameter(P_RELEASE_DISTANCE)) {
            d = getReleaseBoundaryS().getParameter(P_RELEASE_DISTANCE).getDoubleValue();
        }
        return d;
    }

    public void setReleaseDistance(double d) {
        StrategyDefinitionParameter releaseBoundaryS = getReleaseBoundaryS();
        if (releaseBoundaryS.hasParameter(P_RELEASE_DISTANCE)) {
            getReleaseBoundaryS().getParameter(P_RELEASE_DISTANCE).setValue(d);
        }
    }

    public Parameter getReleaseDistanceP() {
        StrategyDefinitionParameter releaseBoundaryS = getReleaseBoundaryS();
        if (releaseBoundaryS.hasParameter(P_RELEASE_DISTANCE)) {
           return getReleaseBoundaryS().getParameter(P_RELEASE_DISTANCE);
        } else {
            return null;            
        }

    }


    public static final String P_RELEASE_BOUNDARY = "releaseBoundary";
    public static final String P_RELEASE_DISTANCE = "releaseDistance";


    private static final Logger log = Logger.getLogger(LandscapeCategory.class);

    public static final String C_LANDSCAPE = "landscape";

    public static final String P_EXTENT = "extent";
    public static final String P_SCALE = "scale";


}
