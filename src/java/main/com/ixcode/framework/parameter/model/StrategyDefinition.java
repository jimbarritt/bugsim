/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : Encapsulates access to and definition of strategies
 * @todo think it might be possible just to inherit directly from StrategyParam or category - could be interesting...
 */
public abstract class StrategyDefinition extends StrategyDefinitionContainerBase implements IParameterisedStrategy {


    public StrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params) {
        this(sparam, params, true);
    }

    public StrategyDefinition(StrategyDefinitionParameter sparam, ParameterMap params, boolean forwardEvents) {
        super(params);
        _forwardEvents = forwardEvents;
        if (sparam != null) {
            setStrategyS(sparam);
        }

    }

    protected StrategyDefinition() {
        this(null, null, false);
    }

    public void initialise(StrategyDefinitionParameter strategyP, ParameterMap params, Map initialisationObjects) {
        _forwardEvents = getForwardEvents(initialisationObjects);
        setStrategyS(strategyP);
        super.setParameterMap(params);
    }

    public static boolean getForwardEvents(Map initObjects) {
        if (!initObjects.containsKey("forwardEvents")) {
            throw new IllegalArgumentException("Need to set a value for 'forwardEvents' into the initialisation objects for this strategy");
        }
        return ((Boolean)initObjects.get("forwardEvents")).booleanValue();
    }

    public boolean isForwardEvents() {
        return _forwardEvents;
    }

    public static Map createInitialisationObjects(boolean forwardChangeEvents) {
        Map objects = new HashMap();
        objects.put("forwardEvents", new Boolean(forwardChangeEvents));
        return objects;
    }

    public void setParameter(Parameter parameter) {
           Parameter oldVal = null;
           if (_sparam.hasParameter(parameter.getName())) {
               oldVal = getParameter(parameter.getName());
               _sparam.replaceParameter(oldVal, parameter);
               if (_forwardEvents) {
                   oldVal.removeParameterListener(this);
               }
           } else {
               _sparam.addParameter(parameter);
           }

           if (_forwardEvents) {
               parameter.addParameterListener(this);
           }
       }



    public void setStrategyS(StrategyDefinitionParameter stratgeyP) {
        StrategyDefinitionParameter oldVal = _sparam;
        _sparam = stratgeyP;


        if (_forwardEvents) {
            if (oldVal!= null ) {
                oldVal.removeParameterContainerListener(this);
                _observedContainers.remove(oldVal);
                for (Iterator itr = oldVal.getParameters().iterator(); itr.hasNext();) {
                    Parameter parameter = (Parameter)itr.next();
                    parameter.removeParameterListener(this);
                    _observedParameters.remove(parameter);

                }
            }
            _sparam.addParameterContainerListener(this);
            _observedContainers.add(_sparam);
            for (Iterator itr = _sparam.getParameters().iterator(); itr.hasNext();) {
                Parameter parameter = (Parameter)itr.next();
                parameter.addParameterListener(this);
                _observedParameters.add(parameter);
            }
        }
    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {
        super.parameterValueChanged(event);
    }


    public String getParameterSummary() {
        return "";
    }


    public void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP) {
        super.parameterReplaced(source, oldP, newP);
        oldP.removeParameterListener(this);
        newP.addParameterListener(this);
    }

    public StrategyDefinitionParameter getStrategyS() {
        return _sparam;
    }

    public Parameter getParameter(String name) {
        Parameter p = _sparam.findParameter(name);
        if (p == null) {
            throw new IllegalArgumentException("No Parameter '" + name + "' Found in " + _sparam.getFullyQualifiedName());
        }
        return p;
    }

    public Object getParameterValue(String name) {
        return getParameter(name).getValue();
    }

    public String getName() {
        return _sparam.getName();
    }

    public void setName(String name) {
        _sparam.setName(name);
    }


     public void setParameterValue(String name, boolean value) {
        setParameterValue(name, new Boolean(value));
    }
     public void setParameterValue(String name, long value) {
        setParameterValue(name, new Long(value));
    }
    public void setParameterValue(String name, double value) {
        setParameterValue(name, new Double(value));
    }

    public void setParameterValue(String name, Object value) {
        getParameter(name).setValue(value);
    }


    public String toString() {
        return super.getContainerName() + " : " + _sparam.getFullyQualifiedName();
    }

    public void unbind() {
        for (Iterator itr = _observedContainers.iterator(); itr.hasNext();) {
            IParameterContainer container = (IParameterContainer)itr.next();
            container.removeParameterContainerListener(this);
        }
        _observedContainers = new ArrayList();
        for (Iterator itr = _observedParameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.removeParameterListener(this);
        }
        _observedParameters = new ArrayList();

    }

    public IParameterisedStrategy instantiateImplementedStrategy(Map initialisationObjects) {
        return ParameterisedStrategyFactory.createParameterisedStrategy(getStrategyS(), getParameterMap(), initialisationObjects);
    }

    public boolean hasParameter(String name) {
        return _sparam.hasParameter(name);
    }

    


    private static final Logger log = Logger.getLogger(StrategyDefinition.class);
    private StrategyDefinitionParameter _sparam;

    public static final String P_STRATEGY_NAME = "name";

    private boolean _forwardEvents;
    private List _observedContainers = new ArrayList();
    private List _observedParameters = new ArrayList();
}
