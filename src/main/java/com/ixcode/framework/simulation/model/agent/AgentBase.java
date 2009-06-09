/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.simulation.model.agent;

import com.ixcode.framework.simulation.model.Simulation;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public abstract class AgentBase implements IAgent {
      protected AgentBase(String agentClassId) {
        _agentClassId = agentClassId;
        _propertyChangeHelper = new PropertyChangeSupport(this);
        _id = nextId();
          _numberAgents++;
        
        
    }

    public void tidyUp(Simulation simulation) {
       removeAllListeners(); 
    }

    protected void finalize() throws Throwable {
        super.finalize();
        _numberAgents--;

    }

    public void registerSimulation(Simulation simulation) {

    }

    public String getAgentClassId() {
        return _agentClassId;
    }


    public void addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener) {
        _propertyChangeHelper.addPropertyChangeListener(propertyName, propertyChangeListener);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        _propertyChangeHelper.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener) {
        _propertyChangeHelper.removePropertyChangeListener(propertyName, propertyChangeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        _propertyChangeHelper.removePropertyChangeListener(listener);
    }

    protected void firePropertyChangeEvent(String propertyName, long oldValue, long newValue) {
        firePropertyChangeEvent(propertyName, new Long(oldValue), new Long(newValue));
    }
    protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
        _propertyChangeHelper.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void removeAllListeners() {
        PropertyChangeListener[] listeners = _propertyChangeHelper.getPropertyChangeListeners();
        for (int i = 0; i < listeners.length; i++) {
            PropertyChangeListener listener = listeners[i];
            _propertyChangeHelper.removePropertyChangeListener(listener);
        }

    }

    public boolean hasListeners() {
        return (_propertyChangeHelper.getPropertyChangeListeners().length != 0);
    }

    public int getId() {
        return _id;
    }

    synchronized protected static int nextId() {
        return ++NEXT_ID;
    }

    private void readObject(ObjectInputStream s) throws IOException  {

        try {
            s.defaultReadObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("ClassNotFound" + e.getMessage());
        }
        _propertyChangeHelper= new PropertyChangeSupport(this);
    }

    public static long getNumberOfAgentsExtant() {
        return _numberAgents;
    }

    private static int NEXT_ID = 0;
    private String _agentClassId;
    private transient PropertyChangeSupport _propertyChangeHelper;
    private int _id;
    private static final Logger log = Logger.getLogger(AgentBase.class);

    private static long _numberAgents;
}
