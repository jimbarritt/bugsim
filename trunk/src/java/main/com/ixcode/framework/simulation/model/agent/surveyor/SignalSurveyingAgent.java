/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent.surveyor;

import com.ixcode.framework.experiment.model.ExperimentPlan;
import com.ixcode.framework.experiment.model.IExperiment;
import com.ixcode.framework.experiment.model.IExperimentListener;
import com.ixcode.framework.experiment.model.ExperimentProgress;
import com.ixcode.framework.math.geometry.CartesianBounds;
import com.ixcode.framework.math.geometry.RectangularCoordinate;
import com.ixcode.framework.parameter.model.ParameterMap;
import com.ixcode.framework.simulation.experiment.ISimulationExperiment;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.framework.simulation.model.agent.AgentBase;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.landscape.information.*;
import com.ixcode.framework.simulation.model.landscape.information.function.FunctionalSignalSurface;
import com.ixcode.framework.simulation.model.landscape.information.function.GaussianDistributionFunction;
import org.apache.log4j.Logger;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class SignalSurveyingAgent extends AgentBase implements IExperimentListener {


    public static final String AGENT_CLASS_ID = "InformationSurveyingAgent";
    public SignalSurveyingAgent(ISimulationExperiment experiment, String informationSurfaceName, double resolution) {
        super(AGENT_CLASS_ID);
        _resolution = resolution;
        _signalSurfaceName = informationSurfaceName;
        experiment.addExperimentListener(this);
        _experiment = experiment;
    }

    public String getSignalSurfaceName() {
        return _signalSurfaceName;
    }

    public void executeTimeStep(Simulation simulation) {

    }

    public void experimentInitialised(IExperiment source, ExperimentPlan plan) {

    }

    public void tidyUp(Simulation simulation) {
        super.tidyUp(simulation);
        _experiment.removeExperimentListener(this);
    }


    public void iterationInitialised(IExperiment source, ParameterMap currentParameters, ExperimentProgress experimentProgress) {
        ISimulationExperiment simulationExperiment = (ISimulationExperiment)source;

        if (log.isInfoEnabled()) {
            log.info("currentReplicate: " + experimentProgress.getCurrentReplicate());
        }
        if (experimentProgress.getCurrentReplicate() ==1) { // just do it once for each iteration
            _survey = calculateSurface(simulationExperiment.getSimulation());
        }

    }


    private SignalSample[][] calculateSurface(Simulation simulation) {

        Landscape landscape = simulation.getLandscape();
        CartesianBounds landscapeBounds = landscape.getLogicalBounds();

        _minX= landscapeBounds.getDoubleX();
        _minY= landscapeBounds.getDoubleY();
        _xCount = (int)Math.ceil(landscapeBounds.getDoubleWidth() / _resolution);
        _yCount = (int)Math.ceil(landscapeBounds.getDoubleHeight() / _resolution);

        _maxX=_xCount*_resolution;
        _maxY=_yCount*_resolution;

        if (log.isInfoEnabled()) {
            log.info("Calculating Information Survey : (" + _xCount + ", " + _yCount + ") Samples ...");
        }
        SignalSample[][] survey = new SignalSample[_xCount][_yCount];

        if (!landscape.hasInformationSurface(_signalSurfaceName)) {
            throw new IllegalStateException("Could not find the information surface called '" + _signalSurfaceName + "'");
        }
        ISignalSurface surface = landscape.getInformationSurface(_signalSurfaceName);
        if (surface instanceof FunctionalSignalSurface) {
            FunctionalSignalSurface fss = (FunctionalSignalSurface)surface;
            ISignalSurfaceCalculator ssc = fss.getCalculator();
            if (ssc instanceof FunctionalSignalSurfaceCalculator) {
                FunctionalSignalSurfaceCalculator fssc = (FunctionalSignalSurfaceCalculator)ssc;
                ISignalFunction sf  = fssc.getSignalFunction();
                if (sf instanceof GaussianDistributionFunction) {
                    GaussianDistributionFunction gsf = (GaussianDistributionFunction)sf;
                    _surfaceSD = gsf.getStandardDeviation();
                    _surfaceMag = gsf.getMagnification();
                }
            }

        }
        
        for (int y=0;y<_yCount;++y) {
            for (int x=0;x<_xCount;++x) {
                RectangularCoordinate coordinate = new RectangularCoordinate(_minX+ (x*_resolution), _minY+(y*_resolution));
                survey[x][y] = surface.getInformationSample(coordinate);
            }
        }

        if (log.isInfoEnabled()) {
            log.info("Complete.");
        }
        return survey;

    }

    public SignalSample[][] getSurvey() {
        return _survey;
    }

    public boolean hasSurvey() {
            return (_survey != null);
        }

    public double getResolution() {
        return _resolution;
    }

    public int getCountX() {
        return _xCount;
    }

    public int getCountY() {
        return _yCount;
    }

    public double getMinX() {
        return _minX;
    }

    public double getMinY() {
        return _minY;
    }

    public double getMaxX() {
        return _maxX;
    }

    public double getMaxY() {
        return _maxY;
    }

    public double getSurfaceSD() {
        return _surfaceSD;
    }

    public double getSurfaceMag() {
        return _surfaceMag;
    }

    private static final Logger log = Logger.getLogger(SignalSurveyingAgent.class);

    private SignalSample[][] _survey;
    private double _resolution;
    private String _signalSurfaceName;
    private int _xCount;
    private int _yCount;
    private double _minX;
    private double _minY;
    private double _maxX;
    private double _maxY;
    private double _surfaceSD;
    private double _surfaceMag;
    private ISimulationExperiment _experiment;
}
