/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.experiment.parameter;

import com.ixcode.bugsim.model.agent.butterfly.*;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedAgeAndEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.mortality.LimitedEggsAdultMortalityStrategy;
import com.ixcode.bugsim.model.agent.butterfly.population.SimplePopulationFactory;
import com.ixcode.bugsim.model.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.foraging.EggLayingForagingStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.MovementStrategyBase;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.RandomWalkMovementStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.GaussianAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.behaviour.movement.movelength.FixedMoveLengthStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.PopulationCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedAgeAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.LimitedEggsAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.release.FixedLocationReleaseStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.release.PredefinedReleaseStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.release.RandomBoundaryReleaseStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.population.release.RandomZoneReleaseStrategy;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.FieldOfViewVisualStrategyDefinition;
import com.ixcode.bugsim.model.experiment.parameter.landscape.LandscapeParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.model.experiment.parameter.resource.ResourceCategory;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.io.csv.CSVReader;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.*;
import com.ixcode.framework.simulation.experiment.input.ReleaseLoadedFromFileCSVHandler;
import com.ixcode.framework.simulation.experiment.parameter.SensorParameters;
import com.ixcode.framework.simulation.experiment.parameter.SignalCRWParameters;
import com.ixcode.framework.simulation.experiment.parameter.boundary.BoundaryParameters;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategy;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.FixedExtentStrategy;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.boundary.BoundaryCrossingRecorderAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.*;
import com.ixcode.framework.simulation.model.agent.motile.release.*;
import com.ixcode.framework.simulation.model.agent.physical.AgentBehaviour;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import com.ixcode.framework.simulation.model.landscape.grid.Grid;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class ButterflyParameters {

    private static List getMotivationStrategies(ParameterMap params) {
        return (List)getMotivationP(params).getValue();
    }

    private static StrategyDefinitionParameter getOlfactionS(ParameterMap params) {
        return (StrategyDefinitionParameter)getSensoryApparatusC(params).findParameter(SensorCategory.P_OLFACTORY).getValue();
    }

    private static StrategyDefinitionParameter getVisionS(ParameterMap params) {
        return (StrategyDefinitionParameter)getSensoryApparatusC(params).findParameter(SensorCategory.P_VISUAL).getValue();
    }

    private static Category getSensoryApparatusC(ParameterMap params) {
        return getButterflyAgentC(params).findCategory(SensorCategory.C_SENSOR);
    }


    private static Category getButterflyAgentC(ParameterMap params) {
        return params.findCategory(ForagerCategory.CATEGORY_NAME);
    }

    public static void addExponentialMotivationalState(ParameterMap params, String name, AgentBehaviour keyBehaviour, double gamma, double maxLevel) {

        Parameter motivationP = getMotivationP(params);
        List motivationStrategies = (List)motivationP.getValue();

        StrategyDefinitionParameter motivationS = new StrategyDefinitionParameter(name, ExponentialMotivationStrategy.class.getName());
        motivationS.addParameter(new Parameter(P_ENABLED, false, "Controls wether the level is calculated or is always set to the max."));
        motivationS.addParameter(new Parameter(ButterflyParameters.KEY_AGENT_BEHAVIOUR, keyBehaviour));
        motivationS.addParameter(new Parameter(ButterflyParameters.MAX_LEVEL, maxLevel));
        motivationS.addParameter(new Parameter(ButterflyParameters.P_MOTIVATION_GAMMA, 1d));

        motivationStrategies.add(motivationS);

    }

    private static Parameter getMotivationP(ParameterMap params) {
        return getButterflyAgentC(params).findCategory(BehaviourCategory.CATEGORY_NAME).findParameter(BehaviourCategory.P_MOTIVATION);
    }

    public static void addButterflyAgentParameters(ExperimentPlan plan) {
        StrategyDefinitionParameter defaultAdultMortality = LimitedEggsAdultMortalityStrategyDefinition.createStrategyS();
        StrategyDefinitionParameter defaultLarvalMortality = DirectLarvalMortalityStrategyDefinition.createDefaultStrategyS();
        StrategyDefinitionParameter defaultImmigration= InitialImmigrationStrategyDefinition.createDefaultStrategyS();
        addButterflyAgentParameters(plan, defaultAdultMortality, defaultLarvalMortality, defaultImmigration);

    }

    public static void setLimitedAgeMortalityStrategy(ExperimentPlan plan, long maxAge) {
        Parameter mortalityP = plan.getParameterTemplate().findParameter(LIFECYCLE_MORTALITY);
        StrategyDefinitionParameter limitedAge = ButterflyParameters.createLimitedAgeMortality(maxAge);
        mortalityP.setValue(limitedAge);

    }


    public static void setLimitedAgeAndEggsMortalityStrategy(ExperimentPlan plan, long maxAge) {
        Parameter mortalityP = plan.getParameterTemplate().findParameter(LIFECYCLE_MORTALITY);
        StrategyDefinitionParameter limitedAge = ButterflyParameters.createLimitedAgeAndEggsMortality(maxAge);
        mortalityP.setValue(limitedAge);

    }


    public static void addButterflyAgentParameters(ExperimentPlan plan, StrategyDefinitionParameter adultMortalityS, StrategyDefinitionParameter larvalMortalityS, StrategyDefinitionParameter immigrationS) {



        Category foragerC = ForagerCategory.createForagerC(adultMortalityS, larvalMortalityS, immigrationS);



        plan.getParameterTemplate().addCategory(foragerC);

    }



    /**
     * @param params
     * @return
     */
    private static double getVisualFieldDepth(ParameterMap params) {
        return getVisualFieldDepthP(params).getDoubleValue();
    }

    private static Parameter getVisualFieldDepthP(ParameterMap params) {
        return getVisionS(params).findParameter(FieldOfViewVisualStrategyDefinition.P_FIELD_DEPTH);
    }

    public static void setVisualFieldDepth(ParameterMap params, double fieldDepth) {
        getVisualFieldDepthP(params).setValue(fieldDepth);
    }

    private static double getVisualFieldWidth(ParameterMap params) {
        return getVisualFieldWidthP(params).getDoubleValue();
    }

    private static Parameter getVisualFieldWidthP(ParameterMap params) {
        return getVisionS(params).findParameter(FieldOfViewVisualStrategyDefinition.P_FIELD_WIDTH);
    }

    public static void setVisualFieldWidth(ParameterMap params, double fieldWidth) {
        getVisualFieldWidthP(params).setValue(fieldWidth);
    }

    public static double getVisualFieldWidth(StrategyDefinitionParameter param) {
        return param.findParameter(FieldOfViewVisualStrategyDefinition.P_FIELD_WIDTH).getDoubleValue();
    }

    public static double getVisualFieldDepth(StrategyDefinitionParameter param) {
        return param.findParameter(FieldOfViewVisualStrategyDefinition.P_FIELD_DEPTH).getDoubleValue();
    }


    public static void setNumberOfEggs(ParameterMap params, long numberOfEggs) {
        getNumberOfEggsParam(params).setValue(numberOfEggs);
    }

    private static Parameter getNumberOfEggsParam(ParameterMap params) {
        return getForagingStrategy(params).findParameter(EggLayingForagingStrategyDefinition.P_NUMBER_OF_EGGS);
    }
    private static StrategyDefinitionParameter getForagingStrategy(ParameterMap params) {
        return (StrategyDefinitionParameter)params.findParameter(ForagerCategory.CATEGORY_NAME + "." + BehaviourCategory.CATEGORY_NAME + "." + BehaviourCategory.P_FORAGING).getStrategyDefinitionValue();
    }

    public static Parameter getRFMLParameter(ParameterMap params) {
        return getMovementStrategyP(params).findParameter(RANDOM_FIRST_MOVE_LENGTH);
    }


    private static StrategyDefinitionParameter createCorrelatedRandomWalkStrategy(double moveLength, double angleOfTurnSD) {
        StrategyDefinitionParameter randomWalk = new StrategyDefinitionParameter(RandomWalkMovementStrategy.S_RANDOM_WALK, RandomWalkStrategy.class.getName());
        addRandomWalkParameters(moveLength, randomWalk, angleOfTurnSD);

//        randomWalk.addParameter(createMoveLengthMaxParameter(releaseStrategy, moveLengthParam));
        return randomWalk;
    }

    private static void addRandomWalkParameters(double moveLength, StrategyDefinitionParameter randomWalk, double angleOfTurnSD) {


        StrategyDefinitionParameter azimuthS = new StrategyDefinitionParameter(GaussianAzimuthStrategy.STRATEGY_NAME, GaussianAzimuthGenerator.class.getName());
        azimuthS.addParameter(new Parameter(GaussianAzimuthStrategy.P_ANGLE_OF_TURN_SD, angleOfTurnSD));
        Parameter azimuthP = new Parameter(MovementStrategyBase.P_AZIMUTH_GENERATOR, azimuthS);
        randomWalk.addParameter(azimuthP);

        StrategyDefinitionParameter moveLengthS = createFixedMoveLengthS(moveLength);
        Parameter moveLengthGeneratorP = new Parameter(MovementStrategyBase.P_MOVE_LENGTH_GENERATOR, moveLengthS);
        randomWalk.addParameter(moveLengthGeneratorP);
    }

    private static StrategyDefinitionParameter createFixedMoveLengthS(double moveLength) {
        StrategyDefinitionParameter moveLengthS = new StrategyDefinitionParameter(FixedMoveLengthStrategy.S_FIXED_MOVELENGTH, FixedMoveLengthGenerator.class.getName());
        moveLengthS.addParameter(new Parameter(FixedMoveLengthStrategy.P_MOVE_LENGTH, moveLength));
        return moveLengthS;
    }

    public static StrategyDefinitionParameter createMixedModelSignalAndDesireRandomWalkStrategy(double moveLength, double angleOfTurnSD) {

        StrategyDefinitionParameter signalCRW = SignalCRWParameters.createSignalCRWStrategyP(OVIPOSITION_MOTIVATION);
        addRandomWalkParameters(moveLength, signalCRW, angleOfTurnSD);
        return signalCRW;
    }


    public static void setMixedModelSignalDesireMovementStrategy(ParameterMap params, double moveLength, double angleOfTurnSD, List signalSensors, double maxLevel, double gamma) {
        Parameter movementBehaviour = getMovementBehaviourP(params);
        StrategyDefinitionParameter irandomWalk = createMixedModelSignalAndDesireRandomWalkStrategy(moveLength, angleOfTurnSD);
        movementBehaviour.setValue(irandomWalk);

        StrategyDefinitionParameter signalSensorOlfactionS = getSignalSensorOlfactionS(params);

        SensorParameters.addSensorParameters(signalSensorOlfactionS, signalSensors);
        addExponentialMotivationalState(params, OVIPOSITION_MOTIVATION, ForagingAgentBehaviour.OVIPOSITING, gamma, maxLevel);

    }

    private static StrategyDefinitionParameter getSignalSensorOlfactionS(ParameterMap params) {
        return (StrategyDefinitionParameter)getSensoryApparatusC(params).findParameter(SensorCategory.P_OLFACTORY).getValue();
    }

    public static Parameter getMovementBehaviourP(ParameterMap params) {
        return params.findParameter(ForagerCategory.CATEGORY_NAME + "." + BehaviourCategory.CATEGORY_NAME + "." + BehaviourCategory.P_MOVEMENT);
    }


    public static StrategyDefinitionParameter getMovementStrategyP(ParameterMap params) {
        return getMovementBehaviourP(params).getStrategyDefinitionValue();
    }


    /**
     * @deprecated
     * @param parameterTemplate
     * @return
     */
    public static Parameter getReleaseP(ParameterMap parameterTemplate) {
        throw new IllegalStateException("This method is deprecated");
//        return parameterTemplate.findParameter(ForagerCategory.C_FORAGER + "." + LifecycleCategory.C_LIFECYCLE + "." + LifecycleCategory.P_RELEASE);
    }

    public static List loadButterflyInitialisationData(String filename) {
        CSVReader in = new CSVReader(1, true);
        ReleaseLoadedFromFileCSVHandler handler = new ReleaseLoadedFromFileCSVHandler();
        try {
            FileInputStream is = new FileInputStream(new File(filename));
            in.readCSVFile(is, handler);

            if (log.isInfoEnabled()) {
                log.info("Loaded " + handler.getParameters().size() + " birth parameters from file '" + filename + "'");
            }
            return handler.getParameters();
        } catch (IOException e) {
            throw new RuntimeException("Problem with file '" + filename + "' - " + e.getMessage(), e);
        }
    }

    /**
     * Has to be called after the release border parameter is set
     *
     * @param plan
     * @return
     */
//    private static Parameter createMoveLengthMaxParameter(final Parameter releaseS, final Parameter moveLengthP) {

//        DerivedParameter moveLengthMaxParam = new DerivedParameter(P_RANDOM_FIRST_MOVE_LENGTH_MAX, new IDerivedParameterCalculation() {
//
//            public void initialiseForwardingParameters(SourceParameterForwardingMap forwardingMap) {
//                List observable = new ArrayList();
//
//                forwardingMap.addForwarding(moveLengthP.getName());
//
//                StrategyDefinitionParameter srcReleaseS = (StrategyDefinitionParameter)forwardingMap.getParameter(releaseS.getName());
//
//
//
//
//                if (srcReleaseS.getName().equals(P_RANDOM_AROUND_BOUNDARY) || srcReleaseS.getName().equals(P_RANDOM_IN_ZONE)) {
//                    Parameter borderDefinition = srcReleaseS.findParameter(BoundaryParameters.P_BOUNDARY);
//                    StrategyDefinitionParameter borderAlgorithm = (StrategyDefinitionParameter)borderDefinition.getValue();
//                    if (borderAlgorithm.getName().equals(DistancedExtentStrategy.S_DISTANCED_EXTENT)) {
//                        Parameter distanceParam = borderAlgorithm.findParameter(DistancedExtentStrategy.P_DISTANCE);
//                        observable.add(distanceParam);
//                    }
//                }
//
//
//                return observable;
//            }
//
//            public Object calculateDerivedValue(SourceParameterMap sourceParams) {
//                Parameter srcMoveLengthP = sourceParams.getParameter(moveLengthP.getName());
//                StrategyDefinitionParameter srcReleaseS = (StrategyDefinitionParameter)sourceParams.getParameter(releaseS.getName());
//
//                double moveLengthMax = moveLengthP.getDoubleValue();
//                if (srcReleaseS.getName().equals(P_RANDOM_AROUND_BOUNDARY) || srcReleaseS.getName().equals(P_RANDOM_IN_ZONE)) {
//                    Parameter borderDefinition = srcReleaseS.findParameter(BoundaryParameters.P_BOUNDARY);
//                    StrategyDefinitionParameter borderAlgorithm = (StrategyDefinitionParameter)borderDefinition.getValue();
//                    if (borderAlgorithm.getName().equals(DistancedExtentStrategy.S_DISTANCED_EXTENT)) {
//                        Parameter distanceParam = borderAlgorithm.findParameter(DistancedExtentStrategy.P_DISTANCE);
//                        double distance = distanceParam.getDoubleValue();
//                        moveLengthMax = moveLengthMax * .2;
//                    }
//                } else {
//                    throw new IllegalArgumentException("Do not know how to calculate from release boundary: " + srcReleaseS.getName());
//                }
//                return new Double(moveLengthMax);
//            }
//        });
//
//        moveLengthMaxParam.addSourceParameter(moveLengthP);
//        moveLengthMaxParam.addSourceParameter(releaseS);
//
//
//        return moveLengthMaxParam;
//    }

    public static StrategyDefinitionParameter createLimitedEggMortality() {
        StrategyDefinitionParameter limitedEggs = new StrategyDefinitionParameter(LimitedEggsAdultMortalityStrategyDefinition.S_LIMITED_EGGS, LimitedEggsAdultMortalityStrategy.class.getName());
        return limitedEggs;
    }

    public static StrategyDefinitionParameter createLimitedAgeMortality(long maxAge) {
        StrategyDefinitionParameter limitedAge = new StrategyDefinitionParameter(LimitedAgeAdultMortalityStrategyDefinition.S_LIMITED_AGE, LimitedAgeAdultMortalityStrategy.class.getName());
        Parameter age = new Parameter(LimitedAgeAdultMortalityStrategyDefinition.P_MAX_AGE, maxAge);
        limitedAge.addParameter(age);
        return limitedAge;
    }

    public static StrategyDefinitionParameter createLimitedAgeAndEggsMortality(long maxAge) {
        StrategyDefinitionParameter limitedAgeAndEggs = new StrategyDefinitionParameter(LimitedAgeAdultMortalityStrategyDefinition.S_LIMITED_AGE, LimitedAgeAndEggsAdultMortalityStrategy.class.getName());
        Parameter age = new Parameter(LimitedAgeAdultMortalityStrategyDefinition.P_MAX_AGE, maxAge);
        limitedAgeAndEggs.addParameter(age);
        return limitedAgeAndEggs;
    }


    public static StrategyDefinitionParameter createBirthPredefinedAroundBorderStrategyP(List parameters) {

        StrategyDefinitionParameter birthPredefined = new StrategyDefinitionParameter(PredefinedReleaseStrategy.S_PREDEFINED, ReleasePredefinedStrategy.class.getName());


        Parameter parametersP = new Parameter(PredefinedReleaseStrategy.P_BIRTH_PREDEFINED_PARAMETERS, parameters);
        birthPredefined.addParameter(parametersP);


        StrategyDefinitionParameter fixedBorder = new StrategyDefinitionParameter(FixedExtentStrategy.S_FIXED_EXTENT, "bugsim.algorithm.FixedBorder");
        fixedBorder.addParameter(new Parameter(BoundaryParameters.P_BOUNDARY_BOUNDS, new CartesianBounds(80, 80, 240, 240)));

        Parameter borderDefinition = new Parameter(BoundaryParameters.P_BOUNDARY, fixedBorder);
        birthPredefined.addParameter(borderDefinition);
        return birthPredefined;
    }

    /**
     * @deprecated - use the strategy objects directly...
     * @return
     */
    public static StrategyDefinitionParameter createBirthRandomAroundFixedBorderStrategyP() {
//        StrategyDefinitionParameter randomBorder = new StrategyDefinitionParameter(RandomBoundaryReleaseStrategy.S_RANDOM_BOUNDARY, ReleaseRandomAroundBorderStrategy.class.getName());
//
//        StrategyDefinitionParameter fixedLocationS =
//        Parameter boundaryP = new Parameter(BoundaryParameters.P_BOUNDARY, fixedLocationS);
//        fixedLocationS.addParameter(new Parameter(BoundaryParameters.P_BOUNDARY_BOUNDS, new CartesianBounds(80, 80, 240, 240)));
//
//        randomBorder.addParameter(boundaryP);
//        return randomBorder;
        return FixedLocationReleaseStrategy.createStrategyS(new RectangularCoordinate(20, 20), 0);
    }

    public static void setReleaseFromFixedLocation(ParameterMap params, RectangularCoordinate location, double heading) {
        StrategyDefinitionParameter fixedLocation = new StrategyDefinitionParameter(FixedLocationReleaseStrategy.S_FIXED_LOCATION, ReleaseFixedLocationStrategy.class.getName());

        Parameter locationParameter = new Parameter(FixedLocationReleaseStrategy.P_LOCATION, location);
        fixedLocation.addParameter(locationParameter);

        Parameter headingP = new Parameter(FixedLocationReleaseStrategy.P_HEADING, heading);
        fixedLocation.addParameter(headingP);

        setReleaseStrategyP(params, fixedLocation);
    }

    public static void setReleaseStrategyP(ParameterMap params, StrategyDefinitionParameter releaseStrategy) {
        getReleaseDefinitionP(params).setValue(releaseStrategy);
    }

    public static Parameter getReleaseDefinitionP(ParameterMap params) {
        return params.findParameter(P_LIFECYCLE_RELEASE);
    }


    public static StrategyDefinitionParameter createBirthFromCentreOfLandscape(ParameterMap params) {
        StrategyDefinitionParameter fixedLocationSP = new StrategyDefinitionParameter(FixedLocationReleaseStrategy.S_FIXED_LOCATION, ReleaseFixedLocationStrategy.class.getName());

        Parameter landscapeBorderP = params.findParameter(LandscapeParameters.ENVIRONMENT_LANDSCAPE_BOUNDARY_DEF);
        StrategyDefinitionParameter boundaryS = (StrategyDefinitionParameter)landscapeBorderP.getValue();
        Parameter landscapeBoundsP = boundaryS.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
        final String landscapeBoundsName = landscapeBoundsP.getName();

        DerivedParameter derivedLocationDP = new DerivedParameter(FixedLocationReleaseStrategy.P_LOCATION, new IDerivedParameterCalculation() {
            public void initialiseForwardingParameters(ISourceParameterForwardingMap forwardingMap) {
                forwardingMap.addForward(landscapeBoundsName);

            }

            public Object calculateDerivedValue(ISourceParameterMap sourceParams) {
                Parameter sourceBoundsP = sourceParams.getParameter(landscapeBoundsName);
                CartesianBounds b = (CartesianBounds)sourceBoundsP.getValue();
                return b.getCentre();
            }
        });

        derivedLocationDP.addSourceParameter(landscapeBoundsP);
        fixedLocationSP.addParameter(derivedLocationDP);
        return fixedLocationSP;
    }

    /**
     * IT GOES :
     * <p/>
     * BIRTH (Parameter - contains algorithm)
     * |
     * |--getValue()-- BirthAlgorithm (Either Random Around border, fixed location or From File) Algorithm Parameter
     * |
     * |-- Parameter ALG_BORDER_DEFINITION (type of border, either FIXED or DISTANCED)
     *
     * @param plan
     * @return
     */
    public static Parameter getReleaseBoundaryParameter(ExperimentPlan plan) {
        return getReleaseBoundaryParameter(plan.getParameterTemplate());
    }

    public static Parameter getReleaseBoundaryParameter(ParameterMap params) {


        Parameter birth = params.findParameter(P_LIFECYCLE_RELEASE);
        StrategyDefinitionParameter birthAlgorithm = (StrategyDefinitionParameter)birth.getValue();
        return getReleaseBoundaryParameter(birthAlgorithm);

    }

    public static Parameter getReleaseBoundaryParameter(StrategyDefinitionParameter releaseStrategy) {
        Parameter borderParameter = null;
        if (releaseStrategy.hasParameter(BoundaryParameters.P_BOUNDARY)) {
            borderParameter = releaseStrategy.findParameter(BoundaryParameters.P_BOUNDARY);
        } else {
            throw new RuntimeException("Tried to retrieve the release border from a birth strategy which does not contain one : " + releaseStrategy.getFullyQualifiedName());
        }
        return borderParameter;
    }


    public static String getParameterSummary(ParameterMap params) {
        String summary = "";

        summary="ButterflyParameters.getParameterSummary() NEEDS UPDATING";

        return summary;
    }

    public static void setRandomFirstMoveLength(ParameterMap params, boolean rfml) {
        Parameter movementBehaviour = params.findParameter(BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR);
        StrategyDefinitionParameter alg = (StrategyDefinitionParameter)movementBehaviour.getValue();
        if (alg.getName().equals(RandomWalkMovementStrategy.S_RANDOM_WALK)) {
            Parameter rfmlParam = alg.findParameter(RANDOM_FIRST_MOVE_LENGTH);
            rfmlParam.setValue(rfml);
        }
    }

    public static BoundaryCrossingRecorderAgent initialiseBoundaryRecorder(Simulation simulation, ParameterMap params) {
        BoundaryCrossingRecorderAgent recorder = null;
        if (simulation.getLandscape().hasGrid(ReleaseRandomAroundBorderStrategy.ZERO_BOUNDARY_GRID)) {
            Grid zeroBoundaryGrid = simulation.getLandscape().getGrid(ReleaseRandomAroundBorderStrategy.ZERO_BOUNDARY_GRID);
            recorder = new BoundaryCrossingRecorderAgent(zeroBoundaryGrid, simulation, ForagingAgentFilter.INSTANCE);
            simulation.addAgent(recorder);

        }
        return recorder;

//        Parameter birthParam = params.findParameter(ButterflyParameters.LIFECYCLE_BIRTH);
//        AlgorithmParameter birthAlgorithm = (AlgorithmParameter)birthParam.getValue();
//        if (birthAlgorithm.getName().equals(ButterflyParameters.RANDOM_AROUND_BORDER)) {
//            Parameter borderDefinition = birthAlgorithm.findParameter(BorderParameters.BORDER_DEFINITION);
//            AlgorithmParameter borderAlgorithm = (AlgorithmParameter)borderDefinition.getValue();
//            if (borderAlgorithm.getName().equals(BorderParameters.DISTANCED_BORDER)) {
//                DerivedParameter borderBounds = (DerivedParameter)borderAlgorithm.findParameter(BorderParameters.DERIVED_BORDER_BOUNDS);
//
//                List sourceParams = borderBounds.getSourceParameters();
//                Parameter sourceBoundsParam = (Parameter)sourceParams.get(0);
//                Parameter distance = (Parameter)sourceParams.get(1);
//                Parameter shapeParam = (Parameter)sourceParams.get(2);
//                Parameter sourceShapeParam = (Parameter)sourceParams.get(3);
//
//                CartesianBounds sourceBounds = (CartesianBounds)sourceBoundsParam.getValue();
//                double d = 0; // This could potentiall be configurable but not sure how!!:)
//                BorderShape shape = (BorderShape)shapeParam.getValue();
//                BorderShape sourceShape = (BorderShape)sourceShapeParam.getValue();
//
//
//                CartesianBounds bounds = DistancedBorderCalculator.INSTANCE.calculateBorder(shape, sourceBounds, d, sourceShape);
//                CartesianBounds cpb = simulation.getLandscape().getLogicalBounds().centre(bounds); // Cabbage Patch bounds
//                BoundaryCrossingRecorderAgent recorder = new BoundaryCrossingRecorderAgent(cpb, shape == BorderShape.CIRCLE, simulation, ButterflyAgentFilter.INSTANCE);
//
//                simulation.addAgent(recorder);
//            }
//        }
    }

    /**
     * Wethere the butterflies actually move to the cabbage when foraging - only works if the foraging strategy is correct
     *
     * @param parameterTemplate
     * @param land
     */
    public static void setLandOnCabbage(ParameterMap params, boolean land) {
        Parameter landOnCabbages= getForagingStrategy(params).findParameter(EggLayingForagingStrategyDefinition.P_LAND_ON_RESOURCE);
        landOnCabbages.setValue(land);
    }


    public static ParameterManipulation createRFMLManipulation(ParameterMap params, boolean rfml) {
        Parameter rfmlParam = getRFMLParameter(params);
        if (rfmlParam == null) {
            throw new RuntimeException("Could not configure RFML as the parameters are not configured for a Correlated Random Walk");
        }
        return new ParameterManipulation(rfmlParam, rfml);

    }


    public static void setBirthPredefinedStrategy(ExperimentPlan plan, String filename) {
        File f = new File(filename);
        if (!f.exists()) {
            throw new RuntimeException("No File '" + filename + "'");
        }

        List parameters = loadButterflyInitialisationData(filename);
        setBirthPredefinedStrategy(plan, parameters);
    }


    public static void setBirthPredefinedStrategy(ExperimentPlan plan, List birthParameters) {

        ParameterMap params = plan.getParameterTemplate();
        Parameter birth = params.findParameter(P_LIFECYCLE_RELEASE);

        StrategyDefinitionParameter fromFileP = createBirthPredefinedAroundBorderStrategyP(birthParameters);

        birth.setValue(fromFileP);


    }

    public static void addPredefinedBirthManipulation(ExperimentPlan plan, int startI, int endI, int incrementI, int incrementH, boolean isCircular, double[] lValues) {
        throw new IllegalStateException("Method no longer supported!");
//        ParameterMap params = plan.getParameterTemplate();
//        Parameter birth = params.findParameter(P_LIFECYCLE_RELEASE);
//        Parameter maxButterfliesP = params.findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_BUTTERFLIES);
//
//        List manipulations = new ArrayList();
//        for (int i = 0; i < lValues.length; ++i) {
//            List birthParameters = DeterministicReleaseParameterGenerator.generateBirthParameters(startI, endI, incrementI, incrementH, isCircular, lValues[i]);
//
//            StrategyDefinitionParameter predefinedP = createBirthPredefinedAroundBorderStrategyP(birthParameters);
//
//            ParameterManipulation maxButterfliesM = new ParameterManipulation(maxButterfliesP, birthParameters.size());
//            ParameterManipulation predefinedM = new ParameterManipulation(birth, predefinedP);
//            MultipleParameterManipulation manip = new MultipleParameterManipulation();
//            manip.addParameterManipulation(maxButterfliesM);
//            manip.addParameterManipulation(predefinedM);
//            manipulations.add(manip);
//        }
//
//        plan.addParameterManipulationSequence(manipulations);

    }

    public static StrategyDefinitionParameter createReleaseRandomAroundCabbagePatchStrategy(ParameterMap params) {
        Parameter cabbagePatchBoundsP = CabbageParameters.getCabbagePatchBoundsP(params);

        StrategyDefinitionParameter randomBorderStrategy = new StrategyDefinitionParameter(RandomBoundaryReleaseStrategy.S_RANDOM_BOUNDARY, ReleaseRandomAroundBorderStrategy.class.getName());

        StrategyDefinitionParameter boundaryStrategy = BoundaryParameters.createDistancedBoundaryStrategy(cabbagePatchBoundsP, BoundaryShape.RECTANGULAR, 0, BoundaryShape.CIRCULAR);
        Parameter boundaryDefinition = new Parameter(BoundaryParameters.P_BOUNDARY, boundaryStrategy);

        randomBorderStrategy.addParameter(boundaryDefinition);

        return randomBorderStrategy;
    }

    public static StrategyDefinitionParameter createReleaseRandomInZoneAroundCabbagePatchStrategy(ParameterMap params, double distanceFromInnerBoundary) {
        Parameter cabbagePatchBoundsP = CabbageParameters.getCabbagePatchBoundsP(params);

        StrategyDefinitionParameter randomBorderStrategy = new StrategyDefinitionParameter(RandomZoneReleaseStrategy.S_RANDOM_IN_ZONE, ReleaseRandomInZoneStrategy.class.getName());

        StrategyDefinitionParameter innerBoundaryStrategy = BoundaryParameters.createDistancedBoundaryStrategy(cabbagePatchBoundsP, BoundaryShape.RECTANGULAR, 0, BoundaryShape.CIRCULAR);
        Parameter innerBoundaryP = new Parameter(BoundaryParameters.P_BOUNDARY, innerBoundaryStrategy);

        Parameter innerBoundsP = innerBoundaryP.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS);
        StrategyDefinitionParameter outerBoundaryStrategy = BoundaryParameters.createDistancedBoundaryStrategy(innerBoundsP, BoundaryShape.CIRCULAR, distanceFromInnerBoundary, BoundaryShape.CIRCULAR);
        Parameter outerBoundaryP = new Parameter(BoundaryParameters.P_RELEASE_ZONE_BOUNDARY, outerBoundaryStrategy);


        randomBorderStrategy.addParameter(innerBoundaryP);
        randomBorderStrategy.addParameter(outerBoundaryP);

        return randomBorderStrategy;
    }

    /**
     * @param algorithmParameter
     * @return
     * @todo sort out this mess!! boundary stuff all over the place
     */
    public static CartesianBounds getReleaseZoneBounds(StrategyDefinitionParameter algorithmParameter) {
        Parameter boundaryP = algorithmParameter.findParameter(BoundaryParameters.P_RELEASE_ZONE_BOUNDARY);
        StrategyDefinitionParameter boundaryStrategyP = (StrategyDefinitionParameter)boundaryP.getValue();
        return (CartesianBounds)boundaryStrategyP.findParameter(BoundaryParameters.P_BOUNDARY_BOUNDS).getValue();
    }

    public static BoundaryShape getReleaseZoneShape(StrategyDefinitionParameter algorithmParameter) {
        Parameter boundaryP = algorithmParameter.findParameter(BoundaryParameters.P_RELEASE_ZONE_BOUNDARY);
        StrategyDefinitionParameter boundaryStrategyP = (StrategyDefinitionParameter)boundaryP.getValue();
        return (BoundaryShape)boundaryStrategyP.findParameter(BoundaryParameters.P_BOUNDARY_SHAPE).getValue();
    }

    public static void addAngleOfTurnManipulation(ExperimentPlan plan, double[] values) {
        Parameter angleOfTurnParameter = getAngleOfTurnParameter(plan);
        SimulationParameters.addManipulation(plan, angleOfTurnParameter.getFullyQualifiedName(), values);
    }

    private static Parameter getAngleOfTurnParameter(ExperimentPlan plan) {
        Parameter angleOfTurnP =getMovementStrategyP(plan.getParameterTemplate()).findParameter(MovementStrategyBase.P_AZIMUTH_GENERATOR).findParameter(GaussianAzimuthStrategy.P_ANGLE_OF_TURN_SD);
        if (angleOfTurnP == null) {
            angleOfTurnP =getMovementStrategyP(plan.getParameterTemplate()).findParameter(MovementStrategyBase.P_AZIMUTH_GENERATOR).findParameter(VonMisesAzimuthStrategy.P_ANGLE_OF_TURN_K);
        }
        return angleOfTurnP;

    }

    public static Parameter getMoveLengthParameter(ExperimentPlan plan) {
        return getMovementStrategyP(plan.getParameterTemplate()).findParameter(MovementStrategyBase.P_MOVE_LENGTH_GENERATOR).findParameter(FixedMoveLengthStrategy.P_MOVE_LENGTH);
    }

    public static void addMoveLengthManipulation(ExperimentPlan plan, double[] values) {
        SimulationParameters.addManipulation(plan, getMoveLengthParameter(plan).getFullyQualifiedName(), values);

    }

    public static void addRFMLBetaDistManipulations(ExperimentPlan plan, double[] alphaValues, double[] betaValues, boolean[] rfmlValues) {
        String alphaParam = MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH_ALPHA;
        String betaParam = MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH_BETA;
        String rfmlParam = MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH;
        List manipulations = new ArrayList();
        Parameter parameterA = plan.getParameterTemplate().findParameter(alphaParam);
        Parameter parameterB = plan.getParameterTemplate().findParameter(betaParam);
        Parameter parameterC = plan.getParameterTemplate().findParameter(rfmlParam);

        if (parameterA == null || parameterB == null || parameterC == null) {
            throw new RuntimeException("Could not find parameter '" + alphaParam + "' or parameter '" + betaParam + "' or '" + parameterC + "'");
        }

        for (int i = 0; i < alphaValues.length; ++i) {
            MultipleParameterManipulation manip = new MultipleParameterManipulation();
            manip.addParameterManipulation(new ParameterManipulation(parameterC, rfmlValues[i]));
            manip.addParameterManipulation(new ParameterManipulation(parameterA, alphaValues[i]));
            manip.addParameterManipulation(new ParameterManipulation(parameterB, betaValues[i]));
            manipulations.add(manip);
        }

        plan.addParameterManipulationSequence(manipulations);
    }

    public static void addAgeManipulations(ExperimentPlan plan, long[] values) {
        SimulationParameters.addManipulation(plan, LIFECYCLE_MORTALITY_LIMITED_AGE_MAX, values);
    }

    public static double getAngleOfTurn(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(GaussianAzimuthStrategy.P_ANGLE_OF_TURN_SD).getDoubleValue();
    }

    public static double getMoveLength(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(FixedMoveLengthStrategy.P_MOVE_LENGTH).getDoubleValue();
    }

    public static boolean getRFML(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(RANDOM_FIRST_MOVE_LENGTH).getBooleanValue();
    }

    public static double getRFMLAlpha(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(RANDOM_FIRST_MOVE_LENGTH_ALPHA).getDoubleValue();
    }

    public static double getRFMLBeta(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(RANDOM_FIRST_MOVE_LENGTH_BETA).getDoubleValue();
    }

    public static void setTwoSensorSignalMovementStrategy(ParameterMap params, double moveLength, double angleOfTurnSD, int distanceFromAgent, double maxBeta, double gamma) {
        List signalSensors = SignalSensorOlfactoryStrategyDefinition.createTwoFortyDegreeSensors(distanceFromAgent, ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME, 0, Double.MAX_VALUE);
        setMixedModelSignalDesireMovementStrategy(params, moveLength, angleOfTurnSD, signalSensors, maxBeta, gamma);
    }

    public static RectangularCoordinate getReleaseLocation(StrategyDefinitionParameter strategyP) {
        return (RectangularCoordinate)strategyP.findParameter(FixedLocationReleaseStrategy.P_LOCATION).getValue();

    }

    public static boolean hasFixedHeading(StrategyDefinitionParameter strategyP) {
        return (strategyP.findParameter(FixedLocationReleaseStrategy.P_HEADING) != null);
    }

    public static double getReleaseHeading(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(FixedLocationReleaseStrategy.P_HEADING).getDoubleValue();
    }

    public static AgentBehaviour getKeyAgentBehaviour(StrategyDefinitionParameter strategyP) {
        return (AgentBehaviour)strategyP.findParameter(KEY_AGENT_BEHAVIOUR).getValue();
    }

    public static double getMaxBeta(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(MAX_LEVEL).getDoubleValue();
    }

    public static double getLuminosityGamma(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(FieldOfViewVisualStrategyDefinition.P_LUMINOSITY_GAMMA).getDoubleValue();
    }

    public static boolean getEnabled(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(P_ENABLED).getBooleanValue();
    }

    public static StrategyDefinitionParameter getMoveLengthS(StrategyDefinitionParameter strategyP) {
        return (StrategyDefinitionParameter)strategyP.findParameter(MovementStrategyBase.P_MOVE_LENGTH_GENERATOR).getValue();
    }

    public static StrategyDefinitionParameter getAzimuthS(StrategyDefinitionParameter strategyP) {
        return (StrategyDefinitionParameter)strategyP.findParameter(MovementStrategyBase.P_AZIMUTH_GENERATOR).getValue();
    }


    public static void setSensoryPerceptionRandomWalk(ParameterMap plan) {

    }

    public static void setVonMisesAzimuthGenerator(ParameterMap params, double K, double N) {
        StrategyDefinitionParameter  movementS = getMovementStrategyP(params);
        Parameter azimuthP = movementS.findParameter(MovementStrategyBase.P_AZIMUTH_GENERATOR);
        StrategyDefinitionParameter vonmisesS = createVonMisesAzimuthS(K, N);
        azimuthP.setValue(vonmisesS);
    }

    private static StrategyDefinitionParameter createVonMisesAzimuthS(double K, double N) {
        StrategyDefinitionParameter vonmisesS = new StrategyDefinitionParameter(VonMisesAzimuthStrategy.STRATEGY_NAME, VonMisesAzimuthGenerator.class.getName());
        vonmisesS.addParameter(new Parameter(VonMisesAzimuthStrategy.P_ANGLE_OF_TURN_K, K));
        vonmisesS.addParameter(new Parameter(VonMisesAzimuthStrategy.P_RESOLUTION_N, N));
        return vonmisesS;
    }

    public static double getAngleOfTurnK(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(VonMisesAzimuthStrategy.P_ANGLE_OF_TURN_K).getDoubleValue();
    }

    public static double getVonMisesN(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(VonMisesAzimuthStrategy.P_RESOLUTION_N).getDoubleValue();
    }

    public static double getVisualSignalThreshold(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(FieldOfViewVisualStrategyDefinition.P_SIGNAL_THRESHOLD).getDoubleValue();
    }

    public static double getMotivationalGamma(StrategyDefinitionParameter strategyP) {
        return strategyP.findParameter(P_MOTIVATION_GAMMA).getDoubleValue();
    }

    /**
     * @deprecated
     * @param f
     * @param simulation
     * @param params
     */
    public static void initialiseForagerFactory(SimplePopulationFactory f, Simulation simulation, ParameterMap params) {

        f.clean();
        throw new IllegalStateException("this method is nologner supported, initialise forager factory directly factory.initialise().");

//
//        Landscape landscape = simulation.getLandscape();
//        Map initialisationObjects = new HashMap();
//        SimulationParameters.setSimulation(initialisationObjects, simulation);
//
//        boolean recordHistory = params.findParameter(BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY).getBooleanValue();
//        f.setRecordHistory(recordHistory);
//        Parameter releaseP = params.findParameter(P_LIFECYCLE_RELEASE);
//        StrategyDefinitionParameter releaseStrategyP = (StrategyDefinitionParameter)releaseP.getValue();
//        IReleaseStrategy releaseStrategy = (IReleaseStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(releaseStrategyP, params, initialisationObjects);
//        f.setImmigrationPattern(releaseStrategy);
//
//        Parameter movement = params.findParameter(BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR);
//        StrategyDefinitionParameter movementStrategyP = movement.getStrategyDefinitionValue();
//        IMovementStrategy movementStrategy = (IMovementStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(movementStrategyP, params, initialisationObjects);
//        f.setMovementStrategy(movementStrategy);
//
//        StrategyDefinitionParameter foragingAlgorithmParam = (StrategyDefinitionParameter)params.findParameter(BUTTERFLY_AGENT_LIFECYCLE_FORAGING);
//        IForagingStrategy foragingStrategy = (IForagingStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(foragingAlgorithmParam, params, initialisationObjects);
//        f.setForagingStrategy(foragingStrategy);
//
//        Parameter mortality = params.findParameter(LIFECYCLE_MORTALITY);
//        StrategyDefinitionParameter mortalityParam = (StrategyDefinitionParameter)mortality.getValue();
//        IMortalityStrategy mortalityStrategy = (IMortalityStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(mortalityParam, params, initialisationObjects);
//        f.setMortalityStrategy(mortalityStrategy);
//
//
//        Parameter immediateRebirth = params.findParameter(LIFECYCLE_IMMEDIATE_REBIRTH);
//        f.setImmediateRerelease(immediateRebirth.getBooleanValue());
//
//
//        CartesianBounds escapeBounds = landscape.getLogicalBounds();
//        boolean isCircular = landscape.isCircular();
//
//        //This bit is horrible but dont have time for it DHTFI!!
//        Parameter birthP = params.findParameter(P_LIFECYCLE_RELEASE);
//        StrategyDefinitionParameter alg = (StrategyDefinitionParameter)birthP.getValue();
//        if (alg.getName().equals(PredefinedReleaseStrategy.S_PREDEFINED)) {
//            Grid zeroGrid = simulation.getLandscape().getGrid(ReleasePredefinedStrategy.ZERO_BOUNDARY_GRID);
//            escapeBounds = zeroGrid.getBounds();
//            isCircular = zeroGrid.isCircular();
//        }
//
//        f.setEscapingAgentCatcher(new EscapingAgentCatcher(simulation, escapeBounds, isCircular, ButterflyAgentFilter.INSTANCE));
//
//        f.setMaxNumberForagers(params.findParameter(SimulationCategory.SIMULATION_MAX_NUMBER_OF_BUTTERFLIES).getLongValue());
//        f.setForagersRemaining(f.getMaxNumberForagers());
//        f.setForagerPopulationSize(params.findParameter(SimulationCategory.SIMULATION_POPULATION_SIZE).getLongValue());
//
//
//        StrategyDefinitionParameter visionS = getVisionS(params);
//        IVisionStrategy visionStrategy = (IVisionStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(visionS, params, initialisationObjects);
//        f.setVisionStrategy(visionStrategy);
//
//        StrategyDefinitionParameter olfactionS = getOlfactionS(params);
//        IOlfactionStrategy olfactionStrategy = (IOlfactionStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(olfactionS, params, initialisationObjects);
//        f.setOlfactionStrategy(olfactionStrategy);
//
//        List motivationStrategyPs = getMotivationStrategies(params);
//        List motivationStrategies = new ArrayList();
//        for (Iterator itr = motivationStrategyPs.iterator(); itr.hasNext();) {
//            StrategyDefinitionParameter strategyP = (StrategyDefinitionParameter)itr.next();
//            IMotivationStrategy motivationS = (IMotivationStrategy)ParameterisedStrategyFactory.createParameterisedStrategy(strategyP, params, initialisationObjects);
//            motivationStrategies.add(motivationS);
//        }
//        f.setMotivationStrategies(motivationStrategies);
//
//        f.createForagerPopulation(simulation);


    }


    public static final DecimalFormat D2 = new DecimalFormat("0.00");


    public static final String RANDOM_FIRST_MOVE_LENGTH = "randomFirstMoveLength";
    public static final String RANDOM_FIRST_MOVE_LENGTH_ALPHA = "rfmlAlpha";
    public static final String RANDOM_FIRST_MOVE_LENGTH_BETA = "rfmlBeta";
    private static final String P_RANDOM_FIRST_MOVE_LENGTH_MAX = "randomFirstMoveLengthMax";


    public static final String BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR = ForagerCategory.CATEGORY_NAME + "." + BehaviourCategory.P_MOVEMENT;
    public static final String BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR + "." + RandomWalkMovementStrategy.S_RANDOM_WALK;
    public static final String BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK_ANGLE_OF_TURN = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + GaussianAzimuthStrategy.P_ANGLE_OF_TURN_SD;
    public static final String MOVEMENT_BEHAVIOUR_RANDOMWALK_MOVE_LENGTH = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + FixedMoveLengthStrategy.P_MOVE_LENGTH;
    public static final String MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + RANDOM_FIRST_MOVE_LENGTH;
    public static final String MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH_MAX = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + P_RANDOM_FIRST_MOVE_LENGTH_MAX;
    public static final String MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH_ALPHA = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + RANDOM_FIRST_MOVE_LENGTH_ALPHA;
    public static final String MOVEMENT_BEHAVIOUR_RANDOMWALK_RANDOM_FIRST_MOVE_LENGTH_BETA = BUTTERFLY_AGENT_MOVEMENT_BEHAVIOUR_RANDOMWALK + "." + RANDOM_FIRST_MOVE_LENGTH_BETA;

    public static final String BUTTERFLY_AGENT_LIFECYCLE = ForagerCategory.CATEGORY_NAME + "." + PopulationCategory.CATEGORY_NAME;

    public static final String BUTTERFLY_AGENT_LIFECYCLE_FORAGING = BUTTERFLY_AGENT_LIFECYCLE + "." + EggLayingForagingStrategyDefinition.S_EGG_LAYING_FORAGING;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_FORAGING_NUMBER_OF_EGGS = BUTTERFLY_AGENT_LIFECYCLE_FORAGING + "." + EggLayingForagingStrategyDefinition.P_NUMBER_OF_EGGS;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_FORAGING_LAND_ON_CABBAGE = BUTTERFLY_AGENT_LIFECYCLE_FORAGING + "." + EggLayingForagingStrategyDefinition.P_LAND_ON_RESOURCE;


    public static final String P_LIFECYCLE_RELEASE = BUTTERFLY_AGENT_LIFECYCLE + "." ;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_RANDOM_AROUND_BORDER = P_LIFECYCLE_RELEASE + "." + RandomBoundaryReleaseStrategy.S_RANDOM_BOUNDARY;

    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_PREDEFINED = P_LIFECYCLE_RELEASE + "." + PredefinedReleaseStrategy.S_PREDEFINED;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_PREDEFINED_PARAMETERS = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_PREDEFINED + "." + PredefinedReleaseStrategy.P_BIRTH_PREDEFINED_PARAMETERS;

    public static final String P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_RANDOM_AROUND_BORDER + "." + BoundaryParameters.P_BOUNDARY;

    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_FIXED = P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF + "." + FixedExtentStrategy.S_FIXED_EXTENT;
    public static final String LIFECYCLE_BIRTH_BORDER_DEF_FIXED_BOUNDS = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_FIXED + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP = P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF + "." + BoundaryParameters.PROPORTIONAL_BOUNDARY;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP_SOURCE = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP + "." + DistancedExtentStrategy.P_INNER_BOUNDARY;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP_PROPORTION = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP + "." + BoundaryParameters.PROPORTION;
    public static final String LIFECYCLE_BIRTH_BORDER_DEF_PROP_BOUNDS = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_PROP + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST = P_BUTTERFLY_AGENT_LIFECYCLE_RELEASE_BORDER_DEF + "." + DistancedExtentStrategy.S_DISTANCED_EXTENT;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST_SOURCE = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST + "." + DistancedExtentStrategy.P_INNER_BOUNDARY;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST_DISTANCE = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST + "." + DistancedExtentStrategy.P_DISTANCE;
    public static final String BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST_BOUNDS = BUTTERFLY_AGENT_LIFECYCLE_BIRTH_BORDER_DEF_DIST + "." + BoundaryParameters.P_BOUNDARY_BOUNDS;

    public static final String LIFECYCLE_BIRTH_FIXED_LOCATION = P_LIFECYCLE_RELEASE + "." + FixedLocationReleaseStrategy.S_FIXED_LOCATION;
    public static final String LIFECYCLE_BIRTH_FIXED_LOCATION_COORDS = LIFECYCLE_BIRTH_FIXED_LOCATION + "." + FixedLocationReleaseStrategy.P_LOCATION;

    public static final String LIFECYCLE_MORTALITY = BUTTERFLY_AGENT_LIFECYCLE + "." + PopulationCategory.P_ADULT_MORTALITY;
    private static final String MAX_EGG_COUNT = "maxEggCount";
    public static final String LIFECYCLE_MORTALITY_LIMITED_EGGS = LIFECYCLE_MORTALITY + "." + LimitedEggsAdultMortalityStrategyDefinition.S_LIMITED_EGGS;
    public static final String LIFECYCLE_MORTALITY_LIMITED_EGGS_MAX = LIFECYCLE_MORTALITY + "." + MAX_EGG_COUNT;

    public static final String LIFECYCLE_MORTALITY_LIMITED_AGE = LIFECYCLE_MORTALITY + "." + LimitedAgeAdultMortalityStrategyDefinition.S_LIMITED_AGE;
    public static final String LIFECYCLE_MORTALITY_LIMITED_AGE_MAX = LIFECYCLE_MORTALITY_LIMITED_AGE + "." + LimitedAgeAdultMortalityStrategyDefinition.P_MAX_AGE;



    public static final String BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY = ForagerCategory.CATEGORY_NAME + "." + ForagerCategory.CATEGORY_NAME ;

    private static final Logger log = Logger.getLogger(ButterflyParameters.class);


    private static final String KEY_AGENT_BEHAVIOUR = "keyAgentBehaviour";
    private static final String MAX_LEVEL = "maxLevel";

    private static final String OVIPOSITION_MOTIVATION = "oviposition";
    private static final String P_ENABLED = "enabled";

    private static final String P_MOTIVATION_GAMMA = "motivationGamma";
}
