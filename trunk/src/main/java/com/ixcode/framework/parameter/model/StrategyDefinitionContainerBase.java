/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.model.ModelBase;
import org.apache.log4j.Logger;

import java.beans.PropertyChangeEvent;
import java.util.*;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Jan 28, 2007 @ 11:10:29 PM by jim
 */
public abstract class StrategyDefinitionContainerBase extends ModelBase implements IParameterContainer, IStrategyDefinitionContainer, IParameterListener, IParameterContainerListener {


    public StrategyDefinitionContainerBase(ParameterMap params) {
        super();
//        if (log.isInfoEnabled()) {
//            log.info("[" + IntrospectionUtils.getShortClassName(this.getClass()) + "@" + IntrospectionUtils.getObjectId(this) + "] <init>");
//        }
        _params = params;

    }

    public String getContainerName() {
        return "[" + IntrospectionUtils.getShortClassName(this.getClass()) + "@" + IntrospectionUtils.getObjectId(this) + "]";
    }

    /**
     * We Assume that the strategy contained by the Strategy Definition is already in the map.
     *
     * @param parameterName
     * @param strategyDefinition
     */
    protected void addStrategyDefinition(String parameterName, StrategyDefinition strategyDefinition) {
        if (log.isDebugEnabled()) {
            log.debug(getContainerName() + " : [addStrategyDefinition] paramName=" + parameterName + " : definition=" + strategyDefinition);
        }
        _strategyDefinitionMap.put(parameterName, strategyDefinition);
//        if (!strategyDefinition.getStrategyS().isConnectedToParameterMap()) {
//            throw new IllegalArgumentException("Adding a Strategy Definition which is not yet connected to the parameter map: " + strategyDefinition);
//        }
    }

    public StrategyDefinition getStrategyDefinition(String parameterName) {
        if (!_strategyDefinitionMap.containsKey(parameterName)) {
            throw new IllegalArgumentException("No StrategyDefinition called '" + parameterName + "' in Map: " + _strategyDefinitionMap.keySet());
        }
        return (StrategyDefinition)_strategyDefinitionMap.get(parameterName);
    }

    /**
     * @param parameterName
     * @param strategyS
     * @todo could in theory Cache the Strateghy Definitions ....?
     * @todo this is all a bit messy as its replicated from the replaceStrategyDefinition - problem is we need to set the SDParameter first in this case.
     */
    public void replaceStrategyDefinitionParameter(String parameterName, StrategyDefinitionParameter strategyS) {
        getParameter(parameterName).setValue(strategyS);
        StrategyDefinition newStrategyDefinition = createStrategyDefinition(parameterName, strategyS);
         StrategyDefinition old = null;
        if (_strategyDefinitionMap.containsKey(parameterName)) {
            old = (StrategyDefinition)_strategyDefinitionMap.get(parameterName);
        }
        if (log.isDebugEnabled()) {
            log.debug(getContainerName() + " : [replaceStrategyDefinition]: paramName=" + parameterName + " : old=" + old + " : new=" + newStrategyDefinition);
        }

        _strategyDefinitionMap.put(parameterName, newStrategyDefinition);


        fireStrategyDefinitionReplaced(parameterName, old, newStrategyDefinition);

    }

    protected StrategyDefinition createStrategyDefinition(String parameterName, StrategyDefinitionParameter strategyS) {
        throw new IllegalStateException(getContainerName() + " needs to implement 'createStrategyDefinition' method! for parameter: " + parameterName);
    }


    /**
     * @param parameterName
     * @param strategyDefinition
     */
    public void replaceStrategyDefinition(String parameterName, StrategyDefinition strategyDefinition) {

        StrategyDefinition old = null;
        if (_strategyDefinitionMap.containsKey(parameterName)) {
            old = (StrategyDefinition)_strategyDefinitionMap.get(parameterName);
        }
        if (log.isDebugEnabled()) {
            log.debug(getContainerName() + " : [replaceStrategyDefinition]: paramName=" + parameterName + " : old=" + old + " : new=" + strategyDefinition);
        }

        _strategyDefinitionMap.put(parameterName, strategyDefinition);
        getParameter(parameterName).setValue(strategyDefinition.getStrategyS());

        fireStrategyDefinitionReplaced(parameterName, old, strategyDefinition);
    }


    private void fireStrategyDefinitionReplaced(String parameterName, StrategyDefinition old, StrategyDefinition strategyDefinition) {
        for (Iterator itr = _strategyContainerListeners.iterator(); itr.hasNext();) {
            IStrategyDefinitionContainerListener listener = (IStrategyDefinitionContainerListener)itr.next();
            listener.strategyDefinitionReplaced(this, parameterName, old, strategyDefinition);
        }
    }

    public void addStrategyDefinitionContainerListener(IStrategyDefinitionContainerListener listener) {
        _strategyContainerListeners.add(listener);
    }

    public void removeStrategyDefinitionContainerListener(IStrategyDefinitionContainerListener listener) {
        _strategyContainerListeners.remove(listener);
    }

    protected void setParameterMap(ParameterMap params) {
        _params = params;
    }


    public ParameterMap getParameterMap() {
        return _params;
    }

    public void fireParameterChangeEvent(String parameterName, Object oldValue, Object newValue) {
        super.firePropertyChangeEvent(parameterName, oldValue, newValue);
    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {

        String parameterName = event.getSource().getName();
        PropertyChangeEvent evt = new PropertyChangeEvent(this, event.getSource().getName(), event.getOldValue(), event.getNewValue());

        // Only do this if it is a derived parameter which has changed...
        if (_strategyDefinitionMap.containsKey(parameterName) && (getParameter(parameterName) instanceof DerivedParameter)) {
            if (log.isDebugEnabled()) {
                log.debug("[strategyParameterChanged] : " + parameterName + " : newValue: " + event.getNewValue());
            }
            StrategyDefinitionParameter newStrategyS = (StrategyDefinitionParameter)event.getNewValue();
            StrategyDefinition newStrateyD = createStrategyDefinition(parameterName, newStrategyS);


            // Need to unbind the old one and rebind the new one i think... either that or cache them somehow...
            _strategyDefinitionMap.put(parameterName, newStrateyD);
        }
        super.firePropertyChangeEvent(evt);

    }

    public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {
        //.. not sure about this        
    }


    public void parameterDisconnected(ParameterDisconnectedEvent event) {
        // not sure if we are going to forward these or not
    }

    public void parameterConnected(ParameterEvent evt) {

    }

    public void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP) {

    }

    public void addParameterContainerListener(IParameterContainerListener listener) {

    }

    public void removeParameterContainerListener(IParameterContainerListener listener) {

    }

    public void fireParameterReplacedEvent(Parameter oldP, Parameter newP) {

    }


    private static final Logger log = Logger.getLogger(StrategyDefinitionContainerBase.class);

    private ParameterMap _params;

    private Map _strategyDefinitionMap = new HashMap();
    private List _strategyContainerListeners = new ArrayList();
}
