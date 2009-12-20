/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.model.agent.butterfly;

import com.ixcode.bugsim.model.experiment.parameter.forager.sensor.visual.FieldOfViewVisualStrategyDefinition;
import com.ixcode.framework.math.DoubleMath;
import com.ixcode.framework.math.geometry.AzimuthCoordinate;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.IParameterisedStrategy;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.parameter.model.StrategyDefinitionParameter;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.IAgentFilter;
import com.ixcode.framework.simulation.model.agent.physical.IPhysicalAgent;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Description : ${CLASS_DESCRIPTION}
 */
public class FieldOfViewVisualStrategy implements IVisionStrategy, IParameterisedStrategy {

    public FieldOfViewVisualStrategy() {
    }

    public FieldOfViewVisualStrategy(double visualFieldDepth, double visualFieldWidth) {
        _visualFieldDepth = visualFieldDepth;
        _visualFieldWidth = visualFieldWidth;
        _luminosityGamma = 0.05;
        _visualThreshold = 0;
    }

    public void initialise(StrategyDefinitionParameter strategyParameter, ParameterMap params, Map initialisationObjects) {
        FieldOfViewVisualStrategyDefinition strategy = new FieldOfViewVisualStrategyDefinition(strategyParameter, params, false);
        _visualFieldDepth = strategy.getFieldDepth();
        _visualFieldWidth = strategy.getFieldWidth();
        _luminosityGamma = strategy.getLuminosityGamma();
        _visualThreshold = strategy.getSignalThreshold();

    }

    public String getParameterSummary() {
        return "VFW=" + _visualFieldWidth + " : VFD=" + _visualFieldDepth + " : GAMMA=" + _luminosityGamma + " : THRESH=" + _visualThreshold;
    }

    public double getLuminosityGamma() {
        return _luminosityGamma;
    }

    public void setVisionEnabled(boolean visionEnabled) {
        _visionEnabled = visionEnabled;
    }

    /**
     * For the moment we assume all plants have the same apparency - can later extend this.
     * not sure if it should be in the agent or in here - apparency is both a function
     * of the thing that you are looking at and your own visual apparatus.
     *
     * Perha
     * @param agent
     * @return
     */
    public double calculateApparency(IPhysicalAgent agent) {

        return 1;

    }


    public void update(Simulation simulation, RectangularCoordinate coord, double heading,IAgentFilter filter) {
        if (_visionEnabled) {
            _visibleAgents = findAgentsInVisualField(coord,  heading, simulation.getLiveAgents(filter));
        } else {
            _visibleAgents = EMPTY_LIST;
        }
    }



    public List getVisibleAgents() {
        return _visibleAgents;
    }

    public double getVisualFieldWidth() {
        return _visualFieldWidth;
    }

    public double getVisualFieldDepth() {
        return _visualFieldDepth;
    }

    public void setVisualFieldWidth(double visualFieldWidth) {
        _visualFieldWidth = visualFieldWidth;
    }

    public void setVisualFieldDepth(double visualFieldDepth) {
        _visualFieldDepth = visualFieldDepth;
    }


    private List findAgentsInVisualField(RectangularCoordinate location, double heading, List candidates) {
        List agentsInField = new ArrayList();

        for (Iterator itr = candidates.iterator(); itr.hasNext();) {
            IPhysicalAgent agent = (IPhysicalAgent)itr.next();
            if (isAgentInVisualField(heading, location, agent)) {
                agentsInField.add(agent);
            }
        }

        return agentsInField;
    }

    /**
     * Find agents in the visual field, but don't include agents which we are inside the raidus of (we have allready been "caught") 
     * @param heading
     * @param location
     * @param agent
     * @return
     */
    private boolean isAgentInVisualField(double heading, RectangularCoordinate location, IPhysicalAgent agent) {
        AzimuthCoordinate azimuthCoordinate = location.calculateAzimuthCoordinateTo(agent.getLocation().getCoordinate());

        boolean inVisualField = false;
        if (log.isDebugEnabled()) {
            log.debug("AzimuthDistance: " + azimuthCoordinate.getDistance() + ", fieldDepth: " + _visualFieldDepth);
        }
        boolean distanceinRange  = DoubleMath.precisionLessThanEqual(azimuthCoordinate.getDistance(), _visualFieldDepth, DoubleMath.DOUBLE_PRECISION_DELTA);
        boolean withinRange = (distanceinRange && !agent.containsPoint(location)); // exclude agents we are "inside"


        if (withinRange) {
            double hTest = azimuthCoordinate.getAzimuth();
            double minH = AzimuthCoordinate.applyAzimuthChange(heading, - (_visualFieldWidth / 2));

            double angularDistanceFromMinH = azimuthCoordinate.angularDistanceFrom(minH);

            boolean withinField = DoubleMath.precisionLessThanEqual(angularDistanceFromMinH, _visualFieldWidth, DoubleMath.DOUBLE_PRECISION_DELTA);
            if (log.isDebugEnabled()) {
                log.debug("VisualSearch: within field :" + withinField + location + ", h: " + F2.format(heading) + ", hTest: " + F2.format(hTest) + ",  minH: " + minH + ", angularDistance: " + angularDistanceFromMinH + ", fieldWidth: " + _visualFieldWidth + ", location: " + agent.getLocation());
            }

            inVisualField = withinRange && withinField;
            if (inVisualField) {
                if (log.isDebugEnabled()) {
                    log.debug("Azimuth Coordinate to agent in view: " + azimuthCoordinate);
                }
            }
        }
        return inVisualField;
    }

    public double getVisualSignalThreshold() {
        return _visualThreshold;
    }

    private List _visibleAgents = new ArrayList();
    private static final Logger log = Logger.getLogger(FieldOfViewVisualStrategy.class);
    private double _visualFieldDepth;
    private double _visualFieldWidth;
       private static final DecimalFormat F2 = new DecimalFormat("0.00");
    private double _luminosityGamma;
    private double _visualThreshold;
    private boolean _visionEnabled;
    private static final List EMPTY_LIST = new ArrayList();
}
