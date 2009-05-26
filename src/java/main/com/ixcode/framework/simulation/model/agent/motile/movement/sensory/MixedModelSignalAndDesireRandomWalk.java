/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement.sensory;

import com.ixcode.bugsim.model.agent.butterfly.ExponentialMotivationStrategy;
import com.ixcode.bugsim.model.agent.butterfly.SignalSensorOlfactoryStrategy;
import com.ixcode.bugsim.model.experiment.parameter.simulation.SimulationCategory;
import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.random.MonteCarloRandom;
import com.ixcode.framework.math.random.UniformRandom;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.experiment.parameter.SignalCRWParameters;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.GaussianAzimuthGenerator;
import com.ixcode.framework.simulation.model.agent.motile.movement.RandomWalkStrategy;
import com.ixcode.framework.simulation.model.agent.motile.movement.Move;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.Location;
import com.ixcode.framework.simulation.model.landscape.information.SignalSample;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : Gives you a random walk that is influenced by sensory information and "desire"
 */
public class MixedModelSignalAndDesireRandomWalk extends RandomWalkStrategy {

    public MixedModelSignalAndDesireRandomWalk() {
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);

        Simulation simulation = SimulationCategory.getSimulation(initialisationObjects);


        _startSampleTheta = -180;
        _endSampleTheta = 180;
        _sampleInterval = 20;

        if (!(super.getAzimuthGenerator() instanceof GaussianAzimuthGenerator)) {
            throw new IllegalStateException("Must specify a gaussian generator for MMSDRandomWalk you specified: " + super.getAzimuthGenerator().getClass().getName());
        }
        double angleOfTurnSD = ((GaussianAzimuthGenerator)super.getAzimuthGenerator()).getStdDeviation();
        _baseMonteCarlo = MonteCarloRandom.generateNormalDistribution(simulation.getRandom(), _startSampleTheta, _endSampleTheta, _sampleInterval, 0, angleOfTurnSD);



        _useSoftMax = SignalCRWParameters.getUseSoftMaximum(strategyP);
        _motivationName = SignalCRWParameters.getMotivationName(strategyP);

    }

    public DiscreetValueMap getBasePDistribution() {
        return _baseMonteCarlo.getPDistribution();
    }


    public MixedModelSignalAndDesireRandomWalk(Random random, double stdDeviation, double moveLength) {
        super(random, stdDeviation, moveLength);
    }

    public Move calculateNextLocation(Simulation simulation, IMotileAgent agent) {
        double informationHeading = calculateHeadingFromSensors(agent, simulation.getLandscape());
        CourseChange courseChange = super.getAzimuthGenerator().generateCourseChange(informationHeading);
        double heading  = courseChange.getNewAzimuth();

        RectangularCoordinate current = agent.getLocation().getCoordinate();
        double mvlen =  super.getMoveLengthGenerator().calculateMoveLength(agent);
        RectangularCoordinate next = current.moveTo(new AzimuthCoordinate(heading,mvlen));
        return new Move(new Location(next), courseChange, mvlen);
    }

    private double calculateHeadingFromSensors(IMotileAgent agent, Landscape landscape) {
        double heading = 0;
        if (_useSoftMax) {
            heading = calculateHeadingSoftMaximum(agent, landscape);
        } else {
            heading = calculateHeadingHardMaximum(agent, landscape);
        }
        return heading;
    }

    /**
     * Implements a soft maximum by going through each sensor and then constructing a probability vector
     * for each modulated by the factor beta (which will magnify or reduce the effect of the signal)
     * <p/>
     * If beta is zero then everything tends to an equal proportion (exp(0) == 1)
     * <p/>
     * If beta is high - any peaks are accentuated.
     *
     * @param agent
     * @param landscape
     * @return
     */
    private double calculateHeadingSoftMaximum(IMotileAgent agent, Landscape landscape) {
        List samples = new ArrayList();

        double sumPSignal = 0;
        double precision = 1e-15;

        double beta = ExponentialMotivationStrategy.calculateMotivationalLevelForAgent(agent, _motivationName);

        List sensors = SignalSensorOlfactoryStrategy.getSensorsFromAgent(agent);

        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            SignalSample sample = sensor.getInformationSample(agent, landscape);
            Object[] sampleSensorPair = new Object[4];
            sampleSensorPair[0] = sensor;
            sampleSensorPair[1] = sample;
            samples.add(sampleSensorPair);
            double pSignal = Math.exp(beta * sample.getDoubleSignalValue());
            sumPSignal += pSignal;
            sampleSensorPair[2] = new Double(pSignal);
        }

        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (Iterator itr = samples.iterator(); itr.hasNext();) {
            Object[] sampleSensorPair = (Object[])itr.next();
            double pAction = ((Double)sampleSensorPair[2]).doubleValue() / sumPSignal;
            sampleSensorPair[3] = new Double(pAction);
            sb.append("[").append(i++).append("]=").append(pAction);
            if (itr.hasNext()) {
                sb.append(", ");
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("Sensor probabilities: " + sb.toString());
        }


        double rnd = super.getRandom().nextDouble();

        SignalSensor selectedSensor = null;

        double cumP = 0;
        for (Iterator itr = samples.iterator(); itr.hasNext();) {
            Object[] sampleSensorPair = (Object[])itr.next();
            cumP += ((Double)sampleSensorPair[3]).doubleValue();
            if (DoubleMath.precisionLessThanEqual(rnd, cumP, precision)) {
                selectedSensor = (SignalSensor)sampleSensorPair[0];

                break;
            }
        }


        if (selectedSensor == null) {
            throw new IllegalStateException("At least one of the sensors should have been chosen!");
        }
        return selectedSensor.getHeading(agent);
    }


    private double calculateHeadingHardMaximum(IMotileAgent agent, Landscape landscape) {
        double maxValue = Double.MIN_VALUE;
        double precision = 1e-15;//IxMath.DOUBLE_PRECISION_DELTA;
        List samples = new ArrayList();

        List sensors = SignalSensorOlfactoryStrategy.getSensorsFromAgent(agent);

        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            SignalSample sample = sensor.getInformationSample(agent, landscape);
            if (DoubleMath.precisionGreaterThanEqual(sample.getDoubleSignalValue(), maxValue, precision)) {
                maxValue = sample.getDoubleSignalValue();
                Object[] sampleSensorPair = new Object[2];
                sampleSensorPair[0] = sensor;
                sampleSensorPair[1] = sample;
                samples.add(sampleSensorPair);
            }
        }

        List maxSamples = new ArrayList();
        for (Iterator itr = samples.iterator(); itr.hasNext();) {
            Object[] sampleSensorPair = (Object[])itr.next();
            SignalSample sample = (SignalSample)sampleSensorPair[1];

            if (DoubleMath.precisionEquals(sample.getDoubleSignalValue(), maxValue, precision)) {
                maxSamples.add(sampleSensorPair);
            }
        }

        SignalSensor maxSensor = null;
        if (maxSamples.size() > 1) {
            if (log.isInfoEnabled()) {
                log.info("Choosing at random...");
            }
            maxSensor = pickRandomSensor(maxSamples);
        } else {
            Object[] sampleSensorPair = (Object[])maxSamples.get(0);
            maxSensor = (SignalSensor)sampleSensorPair[0];
        }

        return maxSensor.getHeading(agent);
    }

    private SignalSensor pickRandomSensor(List maxSamples) {

        int randomindex = UniformRandom.generateUniformRandomInteger(super.getRandom(), maxSamples.size());
        Object[] sampleSensorPair = (Object[])maxSamples.get(randomindex);
        return (SignalSensor)sampleSensorPair[0];
    }

    private SignalSensor pickFirstSensor(List maxSamples) {
        Object[] sampleSensorPair = (Object[])maxSamples.get(0);
        return (SignalSensor)sampleSensorPair[0];
    }

    public String getParameterSummary() {
        return super.getParameterSummary();
    }


    public DiscreetValueMap createActionDistribution(List sensors, IMotileAgent agent, Landscape landscape) {
        List valueMaps = new ArrayList();
        DiscreetValueMap predisposition = getBasePDistribution();
        DiscreetValueMap olfaction = createOlfactoryPDistribution(sensors,agent, landscape);
        valueMaps.add(predisposition);
        valueMaps.add(olfaction);
        return DiscreetValueMap.createSum(valueMaps);
    }

    public DiscreetValueMap createOlfactoryPDistribution(List sensors, IMotileAgent agent, Landscape landscape) {
        double sumPSignal = 0;
        double precision = 1e-15;

        DiscreetValueMap pdist = getBasePDistribution().copyStructure();


        double beta = ExponentialMotivationStrategy.calculateMotivationalLevelForAgent(agent, _motivationName);

//        calculateOldWay(agent, landscape, beta, sumPSignal, pdist);

        List samples = new ArrayList();
        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();

            SignalSample sample = sensor.getInformationSample(agent, landscape);

            Object[] sampleSensorPair = new Object[2];
            sampleSensorPair[0] = sensor;
            double pSignal = Math.exp(beta * sample.getDoubleSignalValue());
            sampleSensorPair[1] = new Double(pSignal);

            samples.add(sampleSensorPair);
        }

        double sumP = 0;
        double minP = Double.MAX_VALUE;
        for (Iterator itr = samples.iterator(); itr.hasNext();) {
            Object[] sampleSensorPair = (Object[])itr.next();
            double pSignal = ((Double)sampleSensorPair[1]).doubleValue();
            if (DoubleMath.precisionLessThanEqual(pSignal, minP, precision)) {
                minP = pSignal;
            }
            sumP += pSignal;
        }

        // Add the difference from the minimum.
        for (Iterator itr = samples.iterator(); itr.hasNext();) {
            Object[] sampleSensorPair = (Object[])itr.next();
            SignalSensor sensor = (SignalSensor)sampleSensorPair[0];
            double pSignal = ((Double)sampleSensorPair[1]).doubleValue();
            double h = sensor.getHeadingFromAgent();
            if (h > 180) {
                h = (h - 180) - 180;
            }
            int index = pdist.getIndexOfKey(h);
            double pSignalDist = ((pSignal / sumP) - (minP / sumP));
            pdist.setValue(index, pSignalDist);
        }

        return pdist;

    }

    public DiscreetValueMap createVisualPDistribution(IMotileAgent agent, Landscape landscape) {
        return null;
    }

    private void calculateOldWay(List sensors, IMotileAgent agent, Landscape landscape, double beta, double sumPSignal, DiscreetValueMap pdist, double precision) {
        List samples = new ArrayList();


        for (Iterator itr = sensors.iterator(); itr.hasNext();) {
            SignalSensor sensor = (SignalSensor)itr.next();
            SignalSample sample = sensor.getInformationSample(agent, landscape);
            Object[] sampleSensorPair = new Object[4];
            sampleSensorPair[0] = sensor;
            sampleSensorPair[1] = sample;
            samples.add(sampleSensorPair);
            double pSignal = Math.exp(beta * sample.getDoubleSignalValue());
            sumPSignal += pSignal;
            sampleSensorPair[2] = new Double(pSignal);
            double h = sensor.getHeadingFromAgent();
            if (h > 180) {
                h = (h - 180) - 180;
            }
            int index = pdist.getIndexOfKey(h);
            pdist.setValue(index, pSignal);
        }

//        return MonteCarloRandom.normaliseDistribution(pdist);
    }


    private static final Logger log = Logger.getLogger(MixedModelSignalAndDesireRandomWalk.class);

    private boolean _useSoftMax;

    private MonteCarloRandom _baseMonteCarlo;
    private double _sampleInterval;
    private double _endSampleTheta;
    private double _startSampleTheta;


    private String _motivationName;
}
