/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.motile.movement;

import com.ixcode.bugsim.experiment.parameter.forager.behaviour.movement.SensoryRandomWalkMovementStrategy;
import com.ixcode.framework.math.DiscreetValueMap;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.CourseChange;
import com.ixcode.framework.math.geometry.DirectionOfChange;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.math.random.MonteCarloRandom;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.motile.IMotileAgent;
import com.ixcode.framework.simulation.model.agent.motile.IOlfactoryAgent;
import com.ixcode.framework.simulation.model.agent.motile.IVisualAgent;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.OlfactorySignal;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.OlfactorySignalProcessor;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignal;
import com.ixcode.framework.simulation.model.agent.motile.movement.sensory.VisualSignalProcessor;
import com.ixcode.framework.simulation.model.landscape.Location;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Random;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class SensoryRandomWalkStrategy extends RandomWalkStrategy {

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        super.initialise(strategyP, params, initialisationObjects);

        SensoryRandomWalkMovementStrategy srw = new SensoryRandomWalkMovementStrategy(strategyP, params, false);
        boolean visionEnabled = srw.isVisionEnabled();
        boolean olfactionEnabled = srw.isOlfactionEnabled();
        int refactoryPeriod = srw.getRefactoryPeriod();

        _visualSignalProcessor = new VisualSignalProcessor(visionEnabled);
        _olfactorySignalProcessor = new OlfactorySignalProcessor(olfactionEnabled);

        //@todo implement Refactory Period!
        _refactoryPeriod = refactoryPeriod;




    }

    public String getParameterSummary() {
        return super.getParameterSummary();
    }


    /**
     * @param simulation
     * @param agent
     * @return
     *
     */
    public Move calculateNextLocation(Simulation simulation, IMotileAgent agent) {

        double azimuth = agent.getAzimuth();
        IAzimuthGenerator azimuthGenerator = super.getAzimuthGenerator();
        IMoveLengthGenerator moveLengthGenerator = super.getMoveLengthGenerator();
              
        VisualSignal visualSignal = _visualSignalProcessor.processVisualSignalInputs(agent);


        if (visualSignal.isRecievingSignal()) {
            azimuth = calculateAzimuthFromVisualSignal(visualSignal.getSignalInputs());
            azimuthGenerator = azimuthGenerator.modifyAzimuthGenerator(visualSignal, azimuth);
            moveLengthGenerator = updateMoveLengthGenerator(moveLengthGenerator, visualSignal);
        } else {
            OlfactorySignal olfactorySignal = _olfactorySignalProcessor.processOlfactorySignals((IOlfactoryAgent)agent, simulation.getLandscape()); // done here as no need if there is a visual signal

            if (olfactorySignal.isReceivingSignal()) {
                double oldAzimuth = azimuth;
                azimuth = AzimuthCoordinate.applyAzimuthChange(azimuth, olfactorySignal.getAzimuthDelta());
                azimuthGenerator = updateAzimuthGenerator(azimuthGenerator, olfactorySignal, azimuth);
                moveLengthGenerator = updateMoveLengthGenerator(moveLengthGenerator, olfactorySignal);
                if (log.isDebugEnabled()) {
                    log.debug("OldAz=" + F.format(oldAzimuth) + ", NewAz=" + F.format(azimuth) + ", Signal: " + olfactorySignal);
                }
            }

        }


        CourseChange courseChange;
        if (agent.getAge() > 0) {
            courseChange = azimuthGenerator.generateCourseChange(azimuth);
            azimuth = courseChange.getNewAzimuth();
        } else {
            courseChange = new CourseChange(azimuth, DirectionOfChange.NONE, 0);
        }



        double mvlen = moveLengthGenerator.calculateMoveLength(agent);



        RectangularCoordinate current = agent.getLocation().getCoordinate();
        RectangularCoordinate next = current.moveTo(new AzimuthCoordinate(azimuth, mvlen));


        if (log.isDebugEnabled()) {
            log.debug("Moving From: " + current + " to " + next + " : azimuth=" + azimuth + " : mvlen=" + mvlen);
        }
        return  new Move(new Location(next), courseChange, mvlen);
        
    }

    private IMoveLengthGenerator updateMoveLengthGenerator(IMoveLengthGenerator moveLengthGenerator, OlfactorySignal olfactorySignal) {
        return moveLengthGenerator;
    }

    private IMoveLengthGenerator updateMoveLengthGenerator(IMoveLengthGenerator moveLengthGenerator, VisualSignal visualSignal) {
        return moveLengthGenerator;
    }




    private IAzimuthGenerator updateAzimuthGenerator(IAzimuthGenerator azimuthGenerator, OlfactorySignal olfactorySignal, double calculatedAzimuth) {
        return azimuthGenerator;

    }

    private double calculateAzimuthFromVisualSignal(DiscreetValueMap signalInputs) {
        MonteCarloRandom visualRandom = new MonteCarloRandom(signalInputs.normalise(), super.getRandom());
        return visualRandom.nextDouble();
        
    }


    private double calculateAzimuthFromOlfactorySignal(IOlfactoryAgent olfactoryAgent) {
        return 0;
    }


    private IOlfactoryAgent getOlfactoryAgent(IMotileAgent agent) {
        if (!(agent instanceof IOlfactoryAgent)) {
            throw new IllegalStateException("Agent must implement " + IVisualAgent.class.getName() + ", to be used with " + SensoryRandomWalkStrategy.class.getName());
        }
        return (IOlfactoryAgent)agent;
    }

    public SensoryRandomWalkStrategy() {
    }

    public SensoryRandomWalkStrategy(Random random, double stdDeviation, double moveLength) {
        super(random, stdDeviation, moveLength);
    }

    public boolean isOlfactionEnabled() {
        return _olfactorySignalProcessor.isEnabled();
    }

    public boolean isVisionEnabled() {
        return _visualSignalProcessor.isEnabled();
    }


    private static final Logger log = Logger.getLogger(SensoryRandomWalkStrategy.class);
    private double _gamma;
    private DiscreetValueMap _visualInputTemplate;
    private VisualSignalProcessor _visualSignalProcessor;
    private OlfactorySignalProcessor _olfactorySignalProcessor;
    private IAzimuthGenerator _defaultVisualNoiseAzimuthGenerator;
    private int _refactoryPeriod;
    public static final DecimalFormat F = new DecimalFormat("0.00");
}
