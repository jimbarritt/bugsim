/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import com.ixcode.framework.simulation.model.Simulation;

import java.beans.PropertyChangeListener;

public interface IAgent {
    /**
     *
     * @return a unique id for this agent
     */
    public int getId();

    /**
     *
     * @return the "class" of this agent, e.g. "ButterflyAgent" used by things like renderers
     */
    String getAgentClassId();


    void executeTimeStep(Simulation simulation);

    void addPropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(PropertyChangeListener listener);

    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    void removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener);

    void registerSimulation(Simulation simulation);

    void removeAllListeners();

    void tidyUp(Simulation simulation);

    boolean hasListeners();
}
