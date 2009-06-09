/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.bugsim.controller.experiment;

import com.ixcode.bugsim.controller.experiment.properties.ExperimentProperties;
import com.ixcode.framework.simulation.model.agent.IAgent;
import com.ixcode.framework.simulation.model.landscape.Landscape;
import com.ixcode.framework.simulation.model.NullSimulationDisruptor;
import com.ixcode.framework.simulation.model.Simulation;
import com.ixcode.bugsim.view.landscape.LandscapeFrame;
import com.ixcode.bugsim.view.landscape.OpenSimulationAction;
import com.ixcode.bugsim.view.landscape.SaveCabbageExperimentResultAction;
import com.ixcode.framework.math.scale.DistanceUnitRegistry;
import com.ixcode.framework.math.scale.ScaledDistance;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Description : ${CLASS_DESCRIPTION}
 * @deprecated 
 */
public abstract class ExperimentBase implements IExperiment, Runnable, PropertyChangeListener {
    public void iterationCompleted(Simulation simulation) {

    }

    protected ExperimentBase(String name, Simulation simulation, OpenSimulationAction openSimulationAction, long maxNumberOfTimesteps, long timestepDelay, ExperimentProperties properties, IExperimentReporter reporter) {
        _simulation = simulation;
        _openSimulationAction = openSimulationAction;
        _maxNumberOfTimesteps = maxNumberOfTimesteps;
        _name = name;
        _simulation.addSimulationListener(this);
        _timestepDelay = timestepDelay;

        setProperties(properties);

        setReporter(reporter);




    }

    public void timestepCompleted(Simulation simulation) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentEscaped(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private void setProperties(ExperimentProperties properties) {
        _properties = properties;
        if (properties != null) {
            _properties.setExperiment(this);
        }
    }


    public boolean hasExperimentReporter() {
        return _reporter != null;
    }

    public IExperimentReporter getExperimentReporter() {
        return _reporter;
    }

    public void setReporter(IExperimentReporter reporter) {
        if (_reporter != null) {
            removeExperimentListener(_reporter);
        }
        _reporter = reporter;
        if (reporter != null) {
            addExperimentListener(_reporter);
        }
    }

    public void agentDeath(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void agentAdded(Simulation simulation, IAgent agent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void propertyChange(PropertyChangeEvent evt) {


    }


    public void addExperimentListener(IExperimentListener listener) {
        if (!_listeners.contains(listener)) {
            _listeners.add(listener);
        }
    }

    public void removeExperimentListener(IExperimentListener listener) {
         if (_listeners.contains(listener)) {
            _listeners.remove(listener);
        }
    }
    private void fireTimestepExecutedEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.timeStepExecuted(this, _simulation.getExecutedTimesteps());
        }
    }

    public void resetExperiment() {

        _simulation.clean();
        
        initialiseExperiment(_simulation, _properties);
        _endOfExperiment = false;
        _isPaused = false;
        fireExperimentResetEvent();

        //@todo aaahhrrgh! dont want to do this every time, just afet we have initialised them for the first time.
//        _properties.addPropertyChangeListener(this);

    }

    private void fireExperimentResetEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.experimentReset(this);
        }
    }

    public long getMaxNumberOfTimesteps() {
        return _maxNumberOfTimesteps;
    }

    public String getName() {
        return _name;
    }

    public LandscapeFrame createLandscapeFrame() {
        LandscapeFrame frame = createLandscapeFrame(_simulation, _openSimulationAction);
        customiseView(frame);
        return frame;

    }


    public void run() {
//        System.out.println("Executing Experiment : max timesteps = " + _maxNumberOfTimesteps);
        _isPaused = false;
        calculateEndOfExperiment();


        fireExperimentStartedEvent();

        while (_endOfExperiment == false) {

            try {

                executeTimeStep();
//            System.out.println("Executing Timestep : " + _simulation.getExecutedTimesteps() );

                Thread.sleep(_timestepDelay);
            } catch (InterruptedException e) {
                _endOfExperiment = true;
            }

        }
        if (!isExperimentComplete()) {
            fireExperimentStoppedEvent();
        }
    }


    public void runFor(long timesteps) {
//         System.out.println("Executing Experiment : for  timesteps = " + timesteps);
        _isPaused = false;
        calculateEndOfExperiment();

        fireExperimentStartedEvent();

        for (long iStep = 0; (iStep < timesteps) && !_endOfExperiment; ++iStep) {

            try {
                executeTimeStep();
                Thread.sleep(_timestepDelay);
            } catch (InterruptedException e) {
                _endOfExperiment = true;
            }
        }
        fireExperimentStoppedEvent();
    }

    private void fireExperimentStartedEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.experimentStarted(this);
        }
    }

    private void calculateEndOfExperiment() {
//             = (isExperimentComplete() || (_simulation.getExecutedTimesteps() > _maxNumberOfTimesteps) || _isPaused);

        _endOfExperiment = false;
        _experimentCompleted = false;

        if (isExperimentComplete()) {
            fireExperimentCompleteEvent();
            _experimentCompleted = true;
            _endOfExperiment = true;
        }

        if ((_simulation.getExecutedTimesteps() > _maxNumberOfTimesteps) || _isPaused) {
            fireExperimentStoppedEvent();

            _endOfExperiment = true;
        }


    }

    public boolean isComplete() {
        return _experimentCompleted;
    }


    private void fireExperimentStoppedEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.experimentStopped(this);
        }
    }

    private void fireExperimentCompleteEvent() {
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IExperimentListener listener = (IExperimentListener)itr.next();
            listener.experimentComplete(this);
        }
    }


    public void executeTimeStep() {

        if (!_endOfExperiment) {
            _simulation.executeTimeStep(NullSimulationDisruptor.INSTANCE);

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fireTimestepExecutedEvent();
                }
            }
            );
            calculateEndOfExperiment();
        }

    }


    /**
     * By default it just keeps running until all the timesteps are executed.
     *
     * @return
     */
    protected boolean isExperimentComplete() {
        return false;
    }


    protected void customiseView(LandscapeFrame frame) {

    }

    protected LandscapeFrame createLandscapeFrame(Simulation simulation, OpenSimulationAction openSim) {
        simulation.setTitle(getName());
        ScaledDistance width = new ScaledDistance(400, DistanceUnitRegistry.centimetres());
        ScaledDistance height = new ScaledDistance(400, DistanceUnitRegistry.centimetres());

        Landscape landscape = new Landscape(simulation, width, height);
        simulation.setLandscape(landscape);

        LandscapeFrame frame = new LandscapeFrame(landscape, openSim);

        if (log.isInfoEnabled()) {
            log.info("Landscape logical size = (" + landscape.getExtentX() + ", " + landscape.getExtentY() + ")");
        }

        String gridName = initialiseExperiment(simulation, _properties);
        if (gridName != null) {
            frame.addActionToToolbar(new SaveCabbageExperimentResultAction(frame.getLandscapeView(), gridName));
        }
        return frame;
    }

    protected abstract String initialiseExperiment(Simulation simulation, ExperimentProperties parameters);

    public Simulation getSimulation() {
        return _simulation;
    }

    public ExperimentProperties getProperties() {
        return _properties;
    }

    public void setMaximumTimestep(long maxNumberOfTimesteps) {
        _maxNumberOfTimesteps = maxNumberOfTimesteps;
        if (!isExperimentComplete() && _maxNumberOfTimesteps > _simulation.getExecutedTimesteps() && !_isPaused) {
            _endOfExperiment = false;
        }
    }

    public long getTimestepDelay() {
        return _timestepDelay;
    }

    public void setTimestepDelay(long timestepDelay) {
        _timestepDelay = timestepDelay;
    }


    public void stopExperiment() {
        _isPaused = true;
    }

    public void setExperimentProperties(ExperimentProperties properties) {
        _properties = properties;

    }


    public boolean isExperimentCompleted() {
        return _experimentCompleted;
    }

    private Simulation _simulation;
    private OpenSimulationAction _openSimulationAction;

    private long _maxNumberOfTimesteps;
    private String _name;
    private List _listeners = new ArrayList();
    private long _timestepDelay = 10;

    private ExperimentProperties _properties;
    private boolean _endOfExperiment;
    private boolean _isPaused;

    private IExperimentReporter _reporter;

    private boolean _experimentCompleted;
    private static final Logger log = Logger.getLogger(ExperimentBase.class);
}
