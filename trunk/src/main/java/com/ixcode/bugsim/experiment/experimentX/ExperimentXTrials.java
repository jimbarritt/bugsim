/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.experiment.experimentX;

import com.ixcode.bugsim.experiment.parameter.BugsimParameterMap;
import com.ixcode.bugsim.experiment.parameter.ButterflyParameters;
import com.ixcode.bugsim.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.bugsim.experiment.parameter.simulation.timescale.DiscreteTimescaleStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.SimulationParameters;
import com.ixcode.bugsim.experiment.parameter.forager.ForagerCategory;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.foraging.EggLayingForagingStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.SensoryRandomWalkMovementStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.azimuth.VonMisesAzimuthStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.movelength.FixedMoveLengthStrategy;
import com.ixcode.bugsim.experiment.parameter.forager.behaviour.BehaviourCategory;
import com.ixcode.bugsim.experiment.parameter.forager.population.PopulationCategory;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.InitialImmigrationStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.ImmigrationStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.RandomReleaseImmigrationPatternStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.immigration.pattern.FixedLocationImmigrationStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.LimitedAgeAndEggsAdultMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.population.mortality.DirectLarvalMortalityStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.SensorCategory;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.OlfactorySensorStrategyBase;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.olfactory.SignalSensorOlfactoryStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.forager.sensor.visual.FieldOfViewVisualStrategyDefinition;
import com.ixcode.bugsim.experiment.parameter.landscape.LandscapeCategory;
import com.ixcode.bugsim.experiment.parameter.resource.CabbageParameters;
import com.ixcode.bugsim.experiment.parameter.resource.ResourceCategory;
import com.ixcode.bugsim.experiment.parameter.resource.signal.MultipleSurfaceSignalStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.signal.SignalStrategyBase;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.FunctionalSignalSurfaceStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.SignalFunctionStrategyBase;
import com.ixcode.bugsim.experiment.parameter.resource.signal.surface.function.GaussianSignalFunctionStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.layout.predefined.PredefinedResourceLayoutStrategy;
import com.ixcode.bugsim.experiment.parameter.resource.layout.ResourceLayoutStrategyBase;
import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.CartesianDimensions;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.ShapeLocationType;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import com.ixcode.framework.parameter.model.MultipleParameterManipulation;
import com.ixcode.framework.parameter.model.Parameter;
import com.ixcode.framework.parameter.model.ParameterManipulation;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.boundary.extent.DistancedExtentStrategyFactory;
import com.ixcode.framework.simulation.experiment.parameter.boundary.strategy.RectangularBoundaryStrategy;
import com.ixcode.framework.simulation.model.landscape.boundary.BoundaryShape;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a trial Arena for trying out different agents and mechanisms
 * <p/>
 * Description : R=5,  L = 5, S = 10, A = 20, P = CORNER_CENTRE, B = {0, 5, 10, 20, 50, 100, 200}
 * <p/>
 * Revision: $Revision$
 */
public class ExperimentXTrials {

    public static final String TRIAL_A = "TrA";
    public static final String TRIAL_B = "TrB";
    public static final String TRIAL_C = "TrC";
    public static final String TRIAL_D = "TrD";
    public static final String TRIAL_X = "TrX";
    public static final String TRIAL_X1 = "TrX1";

    public static final String METHOD_TRIAL_X = "addManipulationsTrialX";
    public static final String METHOD_TRIAL_X1 = "addManipulationsTrialX1";
    public static final String METHOD_TRIAL_TRD1_D = "addManipulationsTrialD1D";
    public static final String METHOD_TRIAL_DEFAULT = "configureSimpleDefault";

    public static final String TEMPLATE_TRX = "expX-trX";
    public static final String TEMPLATE_TRX1 = "expX-trX1";
    public static final String TEMPLATE_TRD1_D = "expX-trD1-D";
    public static final String TEMPLATE_DEFAULT = "default";

    public static void configureSimpleDefault(ExperimentPlan plan) {
        plan.setTrialName("Default");
        plan.setDescription("Simple trial with the most basic parameters");
        plan.setTemplateName(TEMPLATE_DEFAULT);

    }

    public static void addManipulationsTrialX(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_X);
        plan.setDescription("Default Setup");
        plan.setTemplateName(TEMPLATE_TRX);
        plan.setExperimentName("Default Experiment");
        plan.setOutputDirName("default");
        double K = 6;
        double MAX_K = 100;
        double N = 360; // divisions of circle arcs
        double L = 100;
        long maxAge = 1000;
        double radius = 10d;
        double[] B = new double[]{800}; // Distance from resources...
        BugsimParameterMap bugsimParameters = new BugsimParameterMap(plan.getParameterTemplate(), false);
        ScaledDistance landscapeScale = bugsimParameters.getLandscapeCategory().getScale();
        ScaledDistance patchSize = new ScaledDistance(36, DistanceUnitRegistry.metres());
        List cabbages = CabbageParameters.loadCabbageInitialisationParameters(PredefinedResourceLayoutStrategy.DEFAULT_RESOURCE_LAYOUT_FILENAME, radius, DistanceUnitRegistry.metres(), landscapeScale);

        initialiseTrialX(plan, bugsimParameters, maxAge, K, MAX_K, N, L, B, cabbages, landscapeScale, patchSize, true);
    }

    public static void addManipulationsTrialD1D(ExperimentPlan plan) {
        plan.setTrialName("TrD1-D");
        plan.setName("exp3");
        plan.setDescription("Boundary Effect (0 vs 800 High Reps - RELEASE IN ZONE)");
        plan.setTemplateName(TEMPLATE_TRD1_D);
        plan.setExperimentName("boundary");
        plan.setOutputDirName("sim");
        double K = 6;
        double MAX_K = 100;
        double N = 360; // divisions of circle arcs
        double L = 100;
        long maxAge = 1000;
        double radius = 10d;
        double[] B = new double[]{800}; // Distance from resources...
        BugsimParameterMap bugsimParameters = new BugsimParameterMap(plan.getParameterTemplate(), false);
        ScaledDistance landscapeScale = bugsimParameters.getLandscapeCategory().getScale();
        ScaledDistance patchSize = new ScaledDistance(36, DistanceUnitRegistry.metres());
        List cabbages = CabbageParameters.loadCabbageInitialisationParameters(PredefinedResourceLayoutStrategy.DEFAULT_RESOURCE_LAYOUT_FILENAME, radius, DistanceUnitRegistry.metres(), landscapeScale);

        initialiseTrialX(plan, bugsimParameters, maxAge, K, MAX_K, N, L, B, cabbages, landscapeScale, patchSize, false);

        Parameter releaseDistanceP = bugsimParameters.getLandscapeCategory().getReleaseDistanceP();
        Parameter releaseInZoneP = null;
        ImmigrationStrategyBase is = bugsimParameters.getForagerCategory().getPopulationCategory().getImmigrationStrategy();
        if (is instanceof InitialImmigrationStrategyDefinition) {
            InitialImmigrationStrategyDefinition iis = (InitialImmigrationStrategyDefinition)is;
            if (iis.getImmigrationPattern() instanceof RandomReleaseImmigrationPatternStrategyDefinition) {
                RandomReleaseImmigrationPatternStrategyDefinition rrs = (RandomReleaseImmigrationPatternStrategyDefinition)iis.getImmigrationPattern();
                releaseInZoneP = rrs.getReleaseInZoneP();
            }

        }

        if (releaseInZoneP != null) {
            MultipleParameterManipulation mpm1 = new MultipleParameterManipulation();

            mpm1.addParameterManipulation(new ParameterManipulation(releaseDistanceP, 80d));
            mpm1.addParameterManipulation(new ParameterManipulation(releaseInZoneP, true));

            MultipleParameterManipulation mpm2 = new MultipleParameterManipulation();

            mpm2.addParameterManipulation(new ParameterManipulation(releaseDistanceP, 800d));
            mpm2.addParameterManipulation(new ParameterManipulation(releaseInZoneP, false));

            List sequence = new ArrayList();
            sequence.add(mpm1);
            sequence.add(mpm2);
            plan.addParameterManipulationSequence(sequence);

        }
    }


    public static void addManipulationsTrialX1(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_X1);
        plan.setDescription("Test Arena For Visual And Olfactory Movement");
        plan.setTemplateName(TEMPLATE_TRX1);
        plan.setExperimentName("Test Experiment");
        plan.setOutputDirName("test");

        double K = 6;
        double MAX_K = 100;
        double N = 360; // divisions of circle arcs
        double L = 100;
        long maxAge = 1000;
        double radius = 10d;
        double[] B = new double[]{50}; // Distance from resources...
        BugsimParameterMap bugsimParameters = new BugsimParameterMap(plan.getParameterTemplate(), false);
        ScaledDistance landscapeScale = bugsimParameters.getLandscapeCategory().getScale();
        ScaledDistance patchSize = new ScaledDistance(2, DistanceUnitRegistry.metres());
        List cabbageLocations = createCabbageLocationsTrialA();

        List cabbages = CabbageParameters.createCabbageInitialisationParameters(cabbageLocations, radius);
        initialiseTrialX(plan, bugsimParameters, maxAge, K, MAX_K, N, L, B, cabbages, landscapeScale, patchSize, true);
    }

    private static void initialiseTrialX(ExperimentPlan plan, BugsimParameterMap bugsimParameters, long maxAge, double K, double MAX_K, double N, double L, double B[], List resources, ScaledDistance landscapeScale, ScaledDistance patchSize, boolean fixedReleaseNoResources ) {
        long replicates = 1;
        long maxEggs = 10; //613; // same as field results
        long maxButterflies = 100000;
        long numberOfEggsPerForager = 1;
        int refactoryPeriod = 0;
        long generations = 1;
        long generationTimeLimit = 100000;
        double larvalMortalityRate = 1;



        // -------------------------------------------------------------------------------------------------------------
        // Simulation
        SimulationCategory simulationC = bugsimParameters.getSimulationCategory();

        simulationC.setNumberOfReplicates(replicates);
        simulationC.setArchiveEscapedAgents(false);
        simulationC.setArchiveRemovedAgents(false);
        simulationC.setRecordBoundaryCrossings(false);

        if (simulationC.getTimescale() instanceof DiscreteTimescaleStrategyDefinition) {
            DiscreteTimescaleStrategyDefinition sgt = (DiscreteTimescaleStrategyDefinition)simulationC.getTimescale();
            sgt.setGenerationLimit(generations);
            sgt.setTimestepLimit(generationTimeLimit);
        }

        // -------------------------------------------------------------------------------------------------------------
        // Resources:
        ResourceCategory resourceC = bugsimParameters.getResourceCategory();

        resourceC.setIncludeFinalEggCount(false);
        resourceC.setEggCountInterval(1);

        // Resource Boundary Size
        double resourceBoundaryDimensions = patchSize.convertToLogicalDistance(landscapeScale);

        // Resource Layout Strategy
        PredefinedResourceLayoutStrategy predefinedStrategy = (PredefinedResourceLayoutStrategy)resourceC.getResourceLayout();

        // Cabbages


        predefinedStrategy.setLayoutName("E"); // default layout - really need to make sure this is the same as the file we loaded..

        if (!fixedReleaseNoResources) {
            predefinedStrategy.setResources(resources);
        }
        ((RectangularBoundaryStrategy)predefinedStrategy.getLayoutBoundary()).setDimensions(new CartesianDimensions(resourceBoundaryDimensions));
        Parameter resourceBoundaryP = resourceC.getResourceLayout().getLayoutBoundaryP();

        // Signal Surface (Can use default for now....
//        SignalStrategyBase signalStrategy = null;
//        resourceC.setSignalStrategy(signalStrategy);
        MultipleSurfaceSignalStrategy mss = (MultipleSurfaceSignalStrategy)resourceC.getResourceSignal();
        FunctionalSignalSurfaceStrategy fss = (FunctionalSignalSurfaceStrategy)mss.getSignalSurfaces().get(0); // Only deal with 1 at the moment!!
        fss.setSurfaceName(ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME);

        // -------------------------------------------------------------------------------------------------------------
        // Foragers...
        //
        ForagerCategory foragerC = bugsimParameters.getForagerCategory();

        // Sensory Perception:
        SensorCategory sensorC = foragerC.getSensorCategory();
        FieldOfViewVisualStrategyDefinition visualS = (FieldOfViewVisualStrategyDefinition)sensorC.getVisualSensorStrategy();
        visualS.setFieldDepth(2000);
        visualS.setFieldWidth(120);

        OlfactorySensorStrategyBase olfactoryStrategy = sensorC.getOlfactorySensorStrategy();
        if (olfactoryStrategy instanceof SignalSensorOlfactoryStrategyDefinition) {
            SignalSensorOlfactoryStrategyDefinition sso = (SignalSensorOlfactoryStrategyDefinition)olfactoryStrategy;
            List sensors = SignalSensorOlfactoryStrategyDefinition.createTwoFortyDegreeSensors(5, ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME, Double.MIN_VALUE, Double.MAX_VALUE);
            sso.setSensorStrategiesFromParameters(sensors);
        }

        // Population Parameters:
        PopulationCategory populationC = foragerC.getPopulationCategory();

        if (fixedReleaseNoResources) {
            StrategyDefinitionParameter immigrationPatternS = FixedLocationImmigrationStrategyDefinition.createStrategyS(new RectangularCoordinate(20, 20), maxEggs, maxButterflies, false, 0, true);
            StrategyDefinitionParameter immigrationS = InitialImmigrationStrategyDefinition.createStrategyS(immigrationPatternS);
            populationC.setImmigrationStrategyS(immigrationS);
        } else {
            // For random release around border....
            StrategyDefinitionParameter immigrationPatternS = RandomReleaseImmigrationPatternStrategyDefinition.createStrategyS(maxEggs, maxButterflies, false, true);
            StrategyDefinitionParameter immigrationS = InitialImmigrationStrategyDefinition.createStrategyS(immigrationPatternS);
            populationC.setImmigrationStrategyS(immigrationS);
        }

        StrategyDefinitionParameter mortalityStrategy = LimitedAgeAndEggsAdultMortalityStrategyDefinition.createStrategyS(maxAge);
        populationC.setAdultMortalityS(mortalityStrategy);

        DirectLarvalMortalityStrategyDefinition dsd = (DirectLarvalMortalityStrategyDefinition)populationC.getLarvalMortality();
        dsd.setMortalityRate(larvalMortalityRate);

        //Behaviour Parameters:
        BehaviourCategory behaviour = foragerC.getBehaviourCategory();
        behaviour.setRecordLifeHistory(false);

        // Foraging Parameters:
        EggLayingForagingStrategyDefinition foragingStrategy = (EggLayingForagingStrategyDefinition)behaviour.getForagingStrategy();
        foragingStrategy.setNumberOfEggs(numberOfEggsPerForager);
        foragingStrategy.setLandOnResource(false);

        // Movement Parameters:
        StrategyDefinitionParameter vmisesS = VonMisesAzimuthStrategy.createStrategyS(K, N, 1, MAX_K);
        StrategyDefinitionParameter moveLengthS = FixedMoveLengthStrategy.createStrategyS(L);

        StrategyDefinitionParameter sensoryRandomWalkS = SensoryRandomWalkMovementStrategy.createStrategyS(refactoryPeriod, vmisesS, moveLengthS, true, true);
        behaviour.setMovementS(sensoryRandomWalkS);

        // -------------------------------------------------------------------------------------------------------------
        // Landscape: At the moment has to come last so you can derived the landscape bounds from the release boundary...
        LandscapeCategory landscapeC = bugsimParameters.getLandscapeCategory();

        StrategyDefinitionParameter releaseBoundaryS = LandscapeCategory.createDistancedReleaseBoundaryS(BoundaryShape.CIRCULAR, resourceBoundaryP, B[0], ShapeLocationType.CENTRE);
        landscapeC.setReleaseBoundaryS(releaseBoundaryS);
        Parameter releaseBoundaryP = landscapeC.getReleaseBoundaryP();

        // Distanced Boundary (make it the same as the release boundary)

        StrategyDefinitionParameter extentS = DistancedExtentStrategyFactory.createDistancedExtentS(BoundaryShape.CIRCULAR, releaseBoundaryP, 0, ShapeLocationType.BOTTOM_LEFT); //resourceBoundaryP
        landscapeC.setExtentS(extentS);

        // -------------------------------------------------------------------------------------------------------------
        // Make the resource boundary centred on the landscape (should be able to do this with Parameter References (i.e. defer the implementation) ...
        // @todo make this a forward parameter reference (just put in the fQN to the landscape bounds before you create it)
        LandscapeCategory.centreReleaseBoundaryOnLandscape(landscapeC, landscapeC.getReleaseBoundary(), landscapeC);

        ResourceLayoutStrategyBase.centreOnLandscape(resourceC.getResourceLayout(), landscapeC);
        if (fixedReleaseNoResources) {
            InitialImmigrationStrategyDefinition immigrationStrategy = (InitialImmigrationStrategyDefinition)populationC.getImmigrationStrategy();
            FixedLocationImmigrationStrategyDefinition fixedPattern = (FixedLocationImmigrationStrategyDefinition)immigrationStrategy.getImmigrationPattern();
            FixedLocationImmigrationStrategyDefinition.centreOnLandscape(fixedPattern, landscapeC);
        }

        // -------------------------------------------------------------------------------------------------------------
        // Manipulations
//        Parameter angleOfTurnP = behaviour.getMovementStrategy().getAzimuthGenerator().getAngleOfTurnP();
//        plan.addParameterManipulationSequence(angleOfTurnP, new double[]{1, 2, 3, 4, 5, 6, 7});
//
//        Parameter moveLengthP = behaviour.getMovementStrategy().getMoveLengthGenerator().getMoveLengthP();
//        plan.addParameterManipulationSequence(moveLengthP, new double[]{50, 100, 150, 200});
//        SignalFunctionStrategyBase sfsb = fss.getSignalFunction();
//        GaussianSignalFunctionStrategy gsf = (GaussianSignalFunctionStrategy)sfsb;
//        Parameter signalWidthP = gsf.getStandardDeviationP();
//        plan.addParameterManipulationSequence(signalWidthP, new double[]{100, 500, 1500});

    }


    private static void addTestManipulations(LandscapeCategory landscapeC, ExperimentPlan plan, long landscapeBorder) {
        MultipleParameterManipulation mpm = new MultipleParameterManipulation();
        Parameter landscapeScaleP = landscapeC.getParameter(LandscapeCategory.P_SCALE);
        Parameter moveLengthP = ButterflyParameters.getMoveLengthParameter(plan);
        Parameter movementP = ButterflyParameters.getMovementBehaviourP(plan.getParameterTemplate());
        mpm.addParameterManipulation(new ParameterManipulation(landscapeScaleP, new ScaledDistance(2, DistanceUnitRegistry.centimetres())));
        mpm.addParameterManipulation(new ParameterManipulation(moveLengthP, new Double(44)));
        mpm.addParameterManipulation(new ParameterManipulation(movementP, movementP.getValue()));

        List sequence = new ArrayList();
        sequence.add(mpm);     // cant load these back in at the moment...
//        plan.addParameterManipulationSequence(sequence);

        List sequence2 = new ArrayList();
        sequence2.add(new ParameterManipulation(moveLengthP, new Double(33)));
        sequence2.add(new ParameterManipulation(landscapeScaleP, new ScaledDistance(4, DistanceUnitRegistry.centimetres())));
        plan.addParameterManipulationSequence(sequence2);
//        SimulationParameters.addLandscapeBoundaryManipulations(plan, landscapeBorder, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);
    }

    private static void initialiseTrialX_Simple(ExperimentPlan plan, long maxAge, double K, double N, double L, long landscapeBorder) {
        long replicants = 1;
        long populationSize = 1;
        long numberOfReplicates = 10;
        long maxButterflies = 1;
        long maxEggs = 20;
        double LANDSCAPE_D = 50;
        long numberOfEggs = 20;

        BugsimParameterMap bugsimParameters = new BugsimParameterMap(plan.getParameterTemplate(), false);

        // -------------------------------------------------------------------------------------------------------------
        // Simulation
        SimulationCategory simulationC = bugsimParameters.getSimulationCategory();
        simulationC.setNumberOfReplicates(numberOfReplicates);
        simulationC.setArchiveEscapedAgents(false);

        // -------------------------------------------------------------------------------------------------------------
        // Resources:
        ResourceCategory resourceC = bugsimParameters.getResourceCategory();

        // Resource Boundary Size
        ScaledDistance landscapeScale = bugsimParameters.getLandscapeCategory().getScale();
        ScaledDistance patchSize = new ScaledDistance(2, DistanceUnitRegistry.metres());
        double resourceBoundaryDimensions = patchSize.convertToLogicalDistance(landscapeScale);

        // Resource Layout Strategy
        PredefinedResourceLayoutStrategy predefinedStrategy = (PredefinedResourceLayoutStrategy)resourceC.getResourceLayout();

        // Cabbages
        List cabbageLocations = createCabbageLocationsTrialA();
        double radius = 5d;
        List cabbages = CabbageParameters.createCabbageInitialisationParameters(cabbageLocations, radius);
        predefinedStrategy.setResources(cabbages);
        ((RectangularBoundaryStrategy)predefinedStrategy.getLayoutBoundary()).setDimensions(new CartesianDimensions(resourceBoundaryDimensions));

        // Signal Surface (Can use default for now....
//        SignalStrategyBase signalStrategy = null;
//        resourceC.setSignalStrategy(signalStrategy);

        // -------------------------------------------------------------------------------------------------------------
        // Landscape:
        LandscapeCategory landscapeC = bugsimParameters.getLandscapeCategory();

        // Distanced Boundary:
        Parameter resourceBoundaryP = resourceC.getResourceLayout().getLayoutBoundaryP();
        StrategyDefinitionParameter extentS = DistancedExtentStrategyFactory.createDistancedExtentS(BoundaryShape.RECTANGULAR, resourceBoundaryP, 10d, ShapeLocationType.BOTTOM_LEFT);
        landscapeC.setExtentS(extentS);

        // -------------------------------------------------------------------------------------------------------------
        // Make the resource boundary centred on the landscape (should be able to do this with Parameter References (i.e. defer the implementation) ...
        // @todo make this a forward parameter reference (just put in the fQN to the landscape bounds before you create it)
        ResourceLayoutStrategyBase.centreOnLandscape(resourceC.getResourceLayout(), landscapeC);

        // -------------------------------------------------------------------------------------------------------------
        // Butterflies
        // @todo This should be moved into the ButterflyCategory!
        Parameter lifehistory = plan.getParameterTemplate().findParameter(ButterflyParameters.BUTTERFLY_RECORD_BUTTERFLY_LIFE_HISTORY);
        lifehistory.setValue(new Boolean(false));

        ButterflyParameters.setVisualFieldDepth(plan.getParameterTemplate(), 100);
        ButterflyParameters.setVisualFieldWidth(plan.getParameterTemplate(), 100);


        RectangularCoordinate releasePoint = new RectangularCoordinate(110, 130);

        ButterflyParameters.setReleaseFromFixedLocation(plan.getParameterTemplate(), releasePoint, 0);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);

        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);


        ButterflyParameters.setVonMisesAzimuthGenerator(plan.getParameterTemplate(), K, N);

        ButterflyParameters.addAngleOfTurnManipulation(plan, new double[]{K, 20});
        ButterflyParameters.addMoveLengthManipulation(plan, new double[]{L, 30});

        OlfactorySensorStrategyBase olfactoryStrategy = bugsimParameters.getForagerCategory().getSensorCategory().getOlfactorySensorStrategy();
        if (olfactoryStrategy instanceof SignalSensorOlfactoryStrategyDefinition) {
            SignalSensorOlfactoryStrategyDefinition sso = (SignalSensorOlfactoryStrategyDefinition)olfactoryStrategy;
            // @todo move the signal surface name into a parameter.
            List sensors = SignalSensorOlfactoryStrategyDefinition.createTwoFortyDegreeSensors(5, ResourceCategory.CABBAGE_SIGNAL_SURFACE_NAME, Double.MIN_VALUE, Double.MAX_VALUE);
            sso.setSensorStrategiesFromParameters(sensors);
        }

        // This is a bit stupid but is there to test the xml out properly...
        MultipleParameterManipulation mpm = new MultipleParameterManipulation();
        Parameter landscapeScaleP = landscapeC.getParameter(LandscapeCategory.P_SCALE);
        Parameter moveLengthP = ButterflyParameters.getMoveLengthParameter(plan);
        Parameter movementP = ButterflyParameters.getMovementBehaviourP(plan.getParameterTemplate());
        mpm.addParameterManipulation(new ParameterManipulation(landscapeScaleP, new ScaledDistance(2, DistanceUnitRegistry.centimetres())));
        mpm.addParameterManipulation(new ParameterManipulation(moveLengthP, new Double(44)));
        mpm.addParameterManipulation(new ParameterManipulation(movementP, movementP.getValue()));

        List sequence = new ArrayList();
        sequence.add(mpm);
        plan.addParameterManipulationSequence(sequence);

        List sequence2 = new ArrayList();
        sequence2.add(new ParameterManipulation(moveLengthP, new Double(33)));
        sequence2.add(new ParameterManipulation(landscapeScaleP, new ScaledDistance(4, DistanceUnitRegistry.centimetres())));
        plan.addParameterManipulationSequence(sequence2);
//            SimulationParameters.addLandscapeBoundaryManipulations(plan, landscapeBorder, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);

    }


    public static void addManipulationsTrialA(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_A);
        plan.setDescription("Test Arena for exploring visual agent behaviour");


        initialiseTrialA(plan, 100, 100, 100, 0);

    }

    public static void addManipulationsTrialB(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_B);
        plan.setDescription("Test Arena for exploring random agent behaviour");


        double A = 40;
        double L = 5;
        initialiseTrialB(plan, 20, A, L, 50);

    }

    public static void addManipulationsTrialC(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_C);
        plan.setDescription("Test Arena for exploring Random Walk Agent - Von Mises");


        double K = 6;
        double N = 360; // divisions of circle arcs
        double L = 5;
        initialiseTrialC(plan, 200, K, N, L, 50);

    }

    public static void addManipulationsTrialD(ExperimentPlan plan) {
        plan.setTrialName(TRIAL_D);
        plan.setDescription("Test Arena for exploring Sensory Agent - Visual Olfactory And Von Mises");


        double K = 6;
        double N = 360; // divisions of circle arcs
        double L = 5;
        initialiseTrialC(plan, 200, K, N, L, 50);

    }

    private static void initialiseTrialA(ExperimentPlan plan, long maxEggs, long numberOfEggs, long maxAge, long landscapeBorder) {
        long replicants = 1;
        long populationSize = 1;

        double A = 20;
        double L = 5;
        double LANDSCAPE_D = 50;


        long maxButterflies = 1;

        ScaledDistance patchSize = new ScaledDistance(2, DistanceUnitRegistry.metres());
        List cabbageLocations = createCabbageLocationsTrialA();


        List cabbages = CabbageParameters.createCabbageInitialisationParameters(cabbageLocations, 5);
        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants, patchSize, cabbages);

        CabbageParameters.addSignalSurfaceNormalGaussian(plan.getParameterTemplate(), 120, 1);

        ButterflyParameters.setVisualFieldDepth(plan.getParameterTemplate(), 100);
        ButterflyParameters.setVisualFieldWidth(plan.getParameterTemplate(), 100);


        RectangularCoordinate releasePoint = new RectangularCoordinate(110, 130);
        ButterflyParameters.setReleaseFromFixedLocation(plan.getParameterTemplate(), releasePoint, 0);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);
        ButterflyParameters.setTwoSensorSignalMovementStrategy(plan.getParameterTemplate(), L, A, 5, 10000, 1);
        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        SimulationParameters.addLandscapeBoundaryManipulations(plan, LANDSCAPE_D, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);

    }

    private static void initialiseTrialB(ExperimentPlan plan, long maxAge, double A, double L, long landscapeBorder) {
        long replicants = 1;
        long populationSize = 1;

        long maxButterflies = 1;

        ScaledDistance patchSize = new ScaledDistance(4, DistanceUnitRegistry.metres());

        List cabbages = new ArrayList();
        configureSimulation(plan, 1, populationSize, maxButterflies, replicants, patchSize, cabbages);


        RectangularCoordinate releasePoint = new RectangularCoordinate(250, 250);
        ButterflyParameters.setReleaseFromFixedLocation(plan.getParameterTemplate(), releasePoint, 0);

        ButterflyParameters.setLimitedAgeMortalityStrategy(plan, maxAge);

        ButterflyParameters.addAngleOfTurnManipulation(plan, new double[]{A});
        ButterflyParameters.addMoveLengthManipulation(plan, new double[]{L});


        SimulationParameters.addLandscapeBoundaryManipulations(plan, landscapeBorder, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);

    }

    private static void initialiseTrialC(ExperimentPlan plan, long maxAge, double K, double N, double L, long landscapeBorder) {
        long replicants = 1;
        long populationSize = 1;

        long maxButterflies = 1;
        long maxEggs = 20;
        double LANDSCAPE_D = 50;
        long numberOfEggs = 20;


        ScaledDistance patchSize = new ScaledDistance(2, DistanceUnitRegistry.metres());
        List cabbageLocations = createCabbageLocationsTrialA();


        List cabbages = CabbageParameters.createCabbageInitialisationParameters(cabbageLocations, 5);
        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants, patchSize, cabbages);

        CabbageParameters.addSignalSurfaceNormalGaussian(plan.getParameterTemplate(), 120, 1);

        ButterflyParameters.setVisualFieldDepth(plan.getParameterTemplate(), 100);
        ButterflyParameters.setVisualFieldWidth(plan.getParameterTemplate(), 100);


        RectangularCoordinate releasePoint = new RectangularCoordinate(110, 130);
        ButterflyParameters.setReleaseFromFixedLocation(plan.getParameterTemplate(), releasePoint, 0);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);

        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);


        ButterflyParameters.setVonMisesAzimuthGenerator(plan.getParameterTemplate(), K, N);

        ButterflyParameters.addAngleOfTurnManipulation(plan, new double[]{K});
        ButterflyParameters.addMoveLengthManipulation(plan, new double[]{L});


        SimulationParameters.addLandscapeBoundaryManipulations(plan, landscapeBorder, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);

    }

    private static void initialiseTrialD(ExperimentPlan plan, long maxAge, double K, double N, double L, long landscapeBorder) {
        long replicants = 1;
        long populationSize = 1;

        long maxButterflies = 1;
        long maxEggs = 20;
        double LANDSCAPE_D = 50;
        long numberOfEggs = 20;


        ScaledDistance patchSize = new ScaledDistance(2, DistanceUnitRegistry.metres());
        List cabbageLocations = createCabbageLocationsTrialA();


        List cabbages = CabbageParameters.createCabbageInitialisationParameters(cabbageLocations, 5);
        configureSimulation(plan, maxEggs, populationSize, maxButterflies, replicants, patchSize, cabbages);

        CabbageParameters.addSignalSurfaceNormalGaussian(plan.getParameterTemplate(), 120, 1);

        ButterflyParameters.setVisualFieldDepth(plan.getParameterTemplate(), 100);
        ButterflyParameters.setVisualFieldWidth(plan.getParameterTemplate(), 100);


        RectangularCoordinate releasePoint = new RectangularCoordinate(110, 130);
        ButterflyParameters.setReleaseFromFixedLocation(plan.getParameterTemplate(), releasePoint, 0);

        ButterflyParameters.setNumberOfEggs(plan.getParameterTemplate(), numberOfEggs);

        ButterflyParameters.setLimitedAgeAndEggsMortalityStrategy(plan, maxAge);
        ButterflyParameters.setLandOnCabbage(plan.getParameterTemplate(), false);

        //New Part!!!
        ButterflyParameters.setSensoryPerceptionRandomWalk(plan.getParameterTemplate());

        ButterflyParameters.setVonMisesAzimuthGenerator(plan.getParameterTemplate(), K, N);

        ButterflyParameters.addAngleOfTurnManipulation(plan, new double[]{K});
        ButterflyParameters.addMoveLengthManipulation(plan, new double[]{L});


        SimulationParameters.addLandscapeBoundaryManipulations(plan, landscapeBorder, BoundaryShape.RECTANGULAR, BoundaryShape.RECTANGULAR);

    }


    private static List createCabbageLocationsTrialA() {
        List locations = new ArrayList();

        locations.add(new RectangularCoordinate(40, 170));
        locations.add(new RectangularCoordinate(60, 170));
        locations.add(new RectangularCoordinate(50, 160));
        locations.add(new RectangularCoordinate(40, 150));
        locations.add(new RectangularCoordinate(50, 140));
        locations.add(new RectangularCoordinate(70, 160));
        locations.add(new RectangularCoordinate(70, 140));
        locations.add(new RectangularCoordinate(100, 110));
        locations.add(new RectangularCoordinate(80, 100));
        locations.add(new RectangularCoordinate(110, 180));
        locations.add(new RectangularCoordinate(20, 160));
        locations.add(new RectangularCoordinate(10, 110));

        return locations;

    }


    private static void configureSimulation(ExperimentPlan plan, long maxEggs, long populationSize, long maxButterflies, long replicants, ScaledDistance patchSize, List cabbages) {

        ScaledDistance landscapeScale = new ScaledDistance(1, DistanceUnitRegistry.centimetres());

        double dim = patchSize.convertToLogicalDistance(landscapeScale);


        CartesianBounds patchBounds = new CartesianBounds(0, 0, dim, dim);

//        StrategyDefinitionParameter predefinedDef = CabbageParameters.createPredefinedLayoutCabbageFactoryStrategyP(cabbages, patchBounds);


        StrategyDefinitionParameter predefinedDef = PredefinedResourceLayoutStrategy.createStrategyS(new CartesianDimensions(dim), cabbages, "E", 6, 613);

        CabbageParameters.setCabbageFactoryStrategyP(plan.getParameterTemplate(), predefinedDef);


        SimulationParameters.configureSimulationParameters(plan, replicants);

    }

    private static final Logger log = Logger.getLogger(ExperimentXTrials.class);

}
