/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter.resource;

import com.ixcode.bugsim.model.agent.cabbage.layout.CalculatedResourceLayout;
import com.ixcode.bugsim.model.agent.cabbage.layout.PredefinedResourceLayout;
import com.ixcode.bugsim.model.experiment.input.CabbageLoader;
import com.ixcode.bugsim.model.experiment.parameter.EnvironmentParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.CabbageInitialisationParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutType;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.calculated.CalculatedResourceLayoutStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.MultipleSurfaceSignalStrategy;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.SignalSurfaceStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.resource.signal.surface.function.GaussianSignalFunctionStrategy;
import com.ixcode.framework.datatype.analysis.AnalysisCategory;
import com.ixcode.framework.datatype.analysis.AnalysisValue;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.CoordinateDistributor;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.scale.IDistanceUnit;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.*;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class CabbageParameters {
    public static void addCabbageParameters(Category environment) {
        //This is for the standard 80 grid inter edge seperation
        double radius = 5;
        CoordinateDistributor d = new CoordinateDistributor();
        double interPointSeperation = d.calculateSeparationForBounds(4, 80, true);
        double interEdgeSeparation = interPointSeperation - (radius * 2);


        StrategyDefinitionParameter edgeEffectLayoutDef = PredefinedResourceLayoutStrategy.createStrategyS(new CartesianDimensions(100), new ArrayList(), "", 10, 613);
        Parameter cabbageLayout = new Parameter(ResourceCategory.P_RESOURCE_LAYOUT, edgeEffectLayoutDef);
        StrategyDefinitionParameter signalS = MultipleSurfaceSignalStrategy.createDefaultSignalS();


        Category resourceC = ResourceCategory.createDefaultC();

        environment.addSubCategory(resourceC);
    }


    public static StrategyDefinitionParameter createCalculatedLayoutCabbageFactoryStrategyP(double radius, double interEdgeSeparation) {
        StrategyDefinitionParameter calculatedLayoutStrategyP = new StrategyDefinitionParameter(S_CALCULATED_LAYOUT, CalculatedResourceLayout.class.getName());

        Parameter radiusParam = new Parameter(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS, radius);
        calculatedLayoutStrategyP.addParameter(radiusParam);

        Parameter interEdgeSeparationParam = new Parameter(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION, interEdgeSeparation);
        calculatedLayoutStrategyP.addParameter(interEdgeSeparationParam);

        IDerivedParameterCalculation cabbagePatchBoundsCal = createPatchBoundsCalculation();
        DerivedParameter cabbagePatchRectangle = new DerivedParameter(P_RESOURCE_PATCH_BOUNDS, cabbagePatchBoundsCal);
        cabbagePatchRectangle.addSourceParameter(interEdgeSeparationParam);
        cabbagePatchRectangle.addSourceParameter(radiusParam);
        calculatedLayoutStrategyP.addParameter(cabbagePatchRectangle);

        Parameter layoutType = new Parameter(CalculatedResourceLayoutStrategy.P_CALCULATED_LAYOUT_TYPE, CalculatedResourceLayoutType.CORNER_EDGE_CENTRE);
        calculatedLayoutStrategyP.addParameter(layoutType);

//        Parameter signalSurfaceStrategies = new Parameter(MultipleSurfaceSignalStrategy.P_SIGNAL_SURFACES, new ArrayList());
//        calculatedLayoutStrategyP.addParameter(signalSurfaceStrategies);

//        Parameter includeSignalSurveyP = new Parameter(SignalSurfaceStrategyBase.P_INCLUDE_SURVEY, false);
//        calculatedLayoutStrategyP.addParameter(includeSignalSurveyP);

        return calculatedLayoutStrategyP;
    }

    public static StrategyDefinitionParameter createPredefinedLayoutCabbageFactoryStrategyP(List cabbageInitialisationParameters, CartesianBounds patchBounds) {
        StrategyDefinitionParameter predefinedLayoutStrategyP = new StrategyDefinitionParameter(S_PREDEFINED_LAYOUT, PredefinedResourceLayout.class.getName());

        Parameter initParamsParam = new Parameter(PredefinedResourceLayoutStrategy.P_RESOURCES, cabbageInitialisationParameters);
        predefinedLayoutStrategyP.addParameter(initParamsParam);


        Parameter cabbagePatchRectangle = new Parameter(P_RESOURCE_PATCH_BOUNDS, patchBounds);
        Parameter cabbagePatchDimensions = new Parameter(P_RESOURCE_PATCH_DIMENSIONS, patchBounds.getDimensions());
        Parameter cabbagePatchLocation = new Parameter(P_RESOURCE_PATCH_LOCATION, patchBounds.getLocation());

        predefinedLayoutStrategyP.addParameter(cabbagePatchRectangle);
        predefinedLayoutStrategyP.addParameter(cabbagePatchDimensions);
        predefinedLayoutStrategyP.addParameter(cabbagePatchLocation);

//        Parameter signalSurfaceStrategiesP = new Parameter(MultipleSurfaceSignalStrategy.P_SIGNAL_SURFACES, new ArrayList());
//        predefinedLayoutStrategyP.addParameter(signalSurfaceStrategiesP);

//        Parameter includeSignalSurveyP = new Parameter(SignalSurfaceStrategyBase.P_INCLUDE_SURVEY, false);
//        predefinedLayoutStrategyP.addParameter(includeSignalSurveyP);
        return predefinedLayoutStrategyP;
    }

    public static void addSignalSurfaceNormalGaussian(ParameterMap params, double standardDeviation, double magnification) {

//        StrategyDefinitionParameter signalFunctionSP = new StrategyDefinitionParameter(SIGNAL_FUNCTION, GaussianDistributionFunction.class.getName());
//        Parameter sdP = new Parameter(GaussianSignalFunctionStrategy.P_STANDARD_DEVIATION, standardDeviation);
//        Parameter magnificationP = new Parameter(GaussianSignalFunctionStrategy.P_MAGNIFICATION, magnification);
//        signalFunctionSP.addParameter(sdP);
//        signalFunctionSP.addParameter(magnificationP);
//
//        StrategyDefinitionParameter surfaceCalculatorSP = new StrategyDefinitionParameter(SIGNAL_SURFACE_CALCULATOR_STRATEGY, FunctionalSurfaceCalculatorFactory.class.getName());
//        surfaceCalculatorSP.addParameter(signalFunctionSP);
//
//        StrategyDefinitionParameter signalSurfaceSP = new StrategyDefinitionParameter(FunctionalSignalSurfaceStrategy.S_FUNCTIONAL_SIGNAL_SURFACE, FunctionalSignalSurfaceFactory.class.getName());
//        signalSurfaceSP.addParameter(surfaceCalculatorSP);
//
//        StrategyDefinitionParameter cabbageFactoryStrategyP = getCabbageFactoryStrategyP(params);
//        List strategies = getSignalSurfaceStrategies(cabbageFactoryStrategyP);
//        strategies.add(signalSurfaceSP);
//
//        setIncludeSignalSurvey(params, true);


    }


    public static Parameter getCabbageLayoutP(ParameterMap params) {
        return params.findParameter(ENVIRONMENT_RESOURCE_LAYOUT);
    }


    public static void setCabbageFactoryStrategyP(ParameterMap params, Parameter cabbageFactorySP) {
        getCabbageLayoutP(params).setValue(cabbageFactorySP);
    }

    public static StrategyDefinitionParameter getCabbageFactoryStrategyP(ParameterMap params) {
        return (StrategyDefinitionParameter)getCabbageLayoutP(params).getValue();
    }


    public static List loadCabbageInitialisationParameters(String filename, double radius, IDistanceUnit inputUnits, ScaledDistance logicalScale) {
        File f = new File(filename);
        if (!f.exists()) {
            throw new RuntimeException("Could not find cabbage parameter file '" + f.getAbsolutePath() + "'");
        }

        try {
            CabbageLoader loader = new CabbageLoader();
            return loader.loadCabbageInitialisationParameters(filename, radius, inputUnits, logicalScale);
        } catch (IOException e) {
            throw new RuntimeException("IOException loading cabbage initialisation parameters: " + e.getMessage(), e);
        }
    }


    public static IDerivedParameterCalculation createPatchBoundsCalculation() {
        return new IDerivedParameterCalculation() {
            public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
                forwardingMap.addForward(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION);
                forwardingMap.addForward(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS);
            }

            public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
                Parameter interEdge = sourceParams.getParameter(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION);
                Parameter radius = sourceParams.getParameter(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS);
                double interPoint = interEdge.getDoubleValue() + (radius.getDoubleValue() * 2);
                return _d.calculateBoundsForSeparation(4, 4, interPoint, true);
            }

            CoordinateDistributor _d = new CoordinateDistributor();
        };
    }

    public static void setEdgeEffectLayoutType(ParameterMap params, CalculatedResourceLayoutType typeCalculated) {
        Parameter layoutType = params.findParameter(CabbageParameters.LAYOUT_EDGE_EFFECT_PATCH_TYPE);
        layoutType.setValue(typeCalculated);

    }

    public static Parameter getCabbagePatchBoundsP(ParameterMap params) {
        Parameter layout = params.findParameter(CabbageParameters.ENVIRONMENT_RESOURCE_LAYOUT);

        StrategyDefinitionParameter layoutStragegyParam = (StrategyDefinitionParameter)layout.getValue();
        return layoutStragegyParam.findParameter(P_RESOURCE_PATCH_BOUNDS);
    }

    public static Parameter getCabbagePatchDimensionsP(ParameterMap params) {
        Parameter layout = params.findParameter(CabbageParameters.ENVIRONMENT_RESOURCE_LAYOUT);

        StrategyDefinitionParameter layoutStragegyParam = (StrategyDefinitionParameter)layout.getValue();
        return layoutStragegyParam.findParameter(P_RESOURCE_PATCH_DIMENSIONS);
    }

    public static Parameter getCabbagePatchLocationP(ParameterMap params) {
           Parameter layout = params.findParameter(CabbageParameters.ENVIRONMENT_RESOURCE_LAYOUT);

        StrategyDefinitionParameter layoutStragegyParam = (StrategyDefinitionParameter)layout.getValue();
        return layoutStragegyParam.findParameter(P_RESOURCE_PATCH_LOCATION);
    }

    public static List getPredefinedInitialisations(StrategyDefinitionParameter StrategyParameter) {
        return (List)StrategyParameter.findParameter(PredefinedResourceLayoutStrategy.P_RESOURCES).getValue();
    }

    public static CartesianBounds getCabbagePatchBounds(StrategyDefinitionParameter strategyParameter) {
        return (CartesianBounds)strategyParameter.findParameter(P_RESOURCE_PATCH_BOUNDS).getValue();
    }

    public static double getCabbageRadiusOfDetection(StrategyDefinitionParameter layoutStrategy) {
        return layoutStrategy.findParameter(CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS).getDoubleValue();
    }

    public static CalculatedResourceLayoutType getLayoutType(StrategyDefinitionParameter strategyParameter) {
        return (CalculatedResourceLayoutType)strategyParameter.findParameter(CalculatedResourceLayoutStrategy.P_CALCULATED_LAYOUT_TYPE).getValue();
    }

    public static double getInterEdgeSeparation(StrategyDefinitionParameter layoutStrategy) {
        return layoutStrategy.findParameter(CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION).getDoubleValue();
    }

    public static List getSignalSurfaceStrategies(StrategyDefinitionParameter strategyParam) {
        Object value =strategyParam.findParameter(MultipleSurfaceSignalStrategy.P_SIGNAL_SURFACES).getValue();
        if (!(value instanceof List)) {
            throw new ClassCastException("Found: " + value.getClass() + " expected: " + List.class.getName());
        }
        return (List)value;
    }

    public static StrategyDefinitionParameter getSignalSurfaceCalculator(StrategyDefinitionParameter strategyParameter) {
        return (StrategyDefinitionParameter)strategyParameter.findParameter(SIGNAL_SURFACE_CALCULATOR_STRATEGY);
    }

    public static StrategyDefinitionParameter getSignalFunctionStrategyP(StrategyDefinitionParameter strategyParameter) {
        return (StrategyDefinitionParameter)strategyParameter.findParameter(SIGNAL_FUNCTION);
    }

    /**
     * @param strategyParameter
     * @return
     * @todo move all the signal stuff into the framework.
     */
    public static double getSignalSD(StrategyDefinitionParameter strategyParameter) {
        return strategyParameter.findParameter(GaussianSignalFunctionStrategy.P_STANDARD_DEVIATION).getDoubleValue();
    }

    public static double getSignalMagnification(StrategyDefinitionParameter strategyParameter) {
        return strategyParameter.findParameter(GaussianSignalFunctionStrategy.P_MAGNIFICATION).getDoubleValue();
    }

    public static List createCabbageInitialisationParameters(List cabbageLocations, double radius) {
        List cabbages = new ArrayList();
        long id = 0;
        for (Iterator itr = cabbageLocations.iterator(); itr.hasNext();) {
            RectangularCoordinate coordinate = (RectangularCoordinate)itr.next();
            CabbageInitialisationParameters params = new CabbageInitialisationParameters(++id, coordinate, radius);
            cabbages.add(params);
        }
        return cabbages;
    }

    public static boolean getIncludeSignalSurvey(ParameterMap params) {
        return getIncludeSignalSurveyP(params).getBooleanValue();
    }

    public static void setIncludeSignalSurvey(ParameterMap params, boolean include) {
        getIncludeSignalSurveyP(params).setValue(include);
    }

    private static Parameter getIncludeSignalSurveyP(ParameterMap params) {
        return getCabbageFactoryStrategyP(params).findParameter(SignalSurfaceStrategyBase.P_INCLUDE_SURVEY);
    }



    public static final String S_CALCULATED_LAYOUT = "edgeEffectLayout";
    public static final String S_PREDEFINED_LAYOUT = "predefinedLayout";

    public static final String P_RESOURCE_PATCH_BOUNDS = "resourcePatchBounds";
    public static final String P_RESOURCE_PATCH_DIMENSIONS = "dimensions";
    public static final String P_RESOURCE_PATCH_LOCATION = "location";


    public static final String ENVIRONMENT_RESOURCE_LAYOUT = EnvironmentParameters.C_ENVIRONMENT + "." + ResourceCategory.C_RESOURCE + "." + ResourceCategory.P_RESOURCE_LAYOUT;
    public static final String ENVIRONMENT_RESOURCE_LAYOUT_PREDEFINED = ENVIRONMENT_RESOURCE_LAYOUT + "." + S_PREDEFINED_LAYOUT;
    public static final String ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED = EnvironmentParameters.C_ENVIRONMENT + "." + ResourceCategory.P_RESOURCE_LAYOUT + "." + S_CALCULATED_LAYOUT;
    public static final String ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED_CABBAGE_RADIUS = ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED + "." + CalculatedResourceLayoutStrategy.P_RESOURCE_RADIUS;
    public static final String LAYOUT_EDGE_EFFECT_INTER_EDGE_SEPARATION = ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED + "." + CalculatedResourceLayoutStrategy.P_INTER_EDGE_SEPARATION;
    public static final String LAYOUT_EDGE_EFFECT_PATCH_BOUNDS = ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED + "." + P_RESOURCE_PATCH_BOUNDS;
    public static final String LAYOUT_EDGE_EFFECT_PATCH_TYPE = ENVIRONMENT_RESOURCE_LAYOUT_CALCULATED + "." + CalculatedResourceLayoutStrategy.P_CALCULATED_LAYOUT_TYPE;

    private static final Logger log = Logger.getLogger(CabbageParameters.class);
    public static AnalysisCategory ISOLATION_CATEGORY = new AnalysisCategory("Isolation Level", "ISOLEVEL");
    public static final AnalysisValue ISOLATION_VALUE_CENTRE;
    public static final AnalysisValue ISOLATION_VALUE_EDGE;
    public static final AnalysisValue ISOLATION_VALUE_CORNER;
    public static final String ISOLATION_VALUE_NAME_CENTRE = "Centre";
    public static final String ISOLATION_VALUE_NAME_EDGE = "Edge";
    public static final String ISOLATION_VALUE_NAME_CORNER = "Corner";
    public static final String ISOLATION_VALUE_CODE_CENTRE = "1";
    public static final String ISOLATION_VALUE_CODE_CORNER = "2";
    public static final String ISOLATION_VALUE_CODE_EDGE = "3";

    static {
        ISOLATION_VALUE_CENTRE = ISOLATION_CATEGORY.addValue(ISOLATION_VALUE_CODE_CENTRE, ISOLATION_VALUE_NAME_CENTRE);
        ISOLATION_VALUE_CORNER = ISOLATION_CATEGORY.addValue(ISOLATION_VALUE_CODE_CORNER, ISOLATION_VALUE_NAME_CORNER);
        ISOLATION_VALUE_EDGE = ISOLATION_CATEGORY.addValue(ISOLATION_VALUE_CODE_EDGE, ISOLATION_VALUE_NAME_EDGE);
    }


    private static final String SIGNAL_SURFACE_CALCULATOR_STRATEGY = "signalSurfaceCalculator";
    private static final String SIGNAL_FUNCTION = "signalFunction";

    private static final String P_AGENT_CLASS = "agentClass";
}
