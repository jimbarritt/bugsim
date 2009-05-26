package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.*;


/**
 * @todo add a check to make sure you can be owned by an algorithm and a category at the same time - WHY ?
 */
public class Parameter implements IParameterModel {
    public boolean isDerived() {
        return false;
    }

    public IParameterModel findParentCalled(String name) {
        IParameterModel found = null;
        if (_name.equals(name)) {
            found = this;       
        } else if (hasParent()) {
            found = getParent().findParentCalled(name);
        }
        return found;
    }

    public IParameterModel getParent() {
        IParameterModel parent = null;
        if (getParentCategoryC() != null) {
            parent = getParentCategoryC();
        } else if (getParentStrategy() != null) {
            parent = getParentStrategy();
        }
        return parent;
    }

    public Class getParameterType() {
        Class type = Object.class;
        if (getValue() != null) {
            type = getValue().getClass();
        }
        return type;
    }


    public static final String P_NAME = "name";
    public static final String P_VALUE = "value";
    public static final String P_COMMENT = "comment";

    public Parameter() {

    }


    public Parameter(String name, boolean value, String comment) {
        this(name, new Boolean(value), comment);
    }

    public Parameter(String name, int value, String comment) {
        this(name, new Integer(value), comment);
    }

    public Parameter(String name, double value, String comment) {
        this(name, new Double(value), comment);
    }

    public Parameter(String name, long value, String comment) {
        this(name, new Long(value), comment);
    }

    public Parameter(String name, StrategyDefinitionParameter value, String comment) {
        _name = name;
        _value = value;
        value.setParentParameter(this);

        _comment = comment;
    }

    public Parameter(String name, StrategyDefinitionParameter value) {
        _name = name;
        _value = value;
        value.setParentParameter(this);

    }


    public Parameter(String name, Object value) {
        _name = name;
        _value = value;
    }


    public Parameter(String name, Object value, String comment) {
        _name = name;
        _value = value;
        _comment = comment;
    }

    public Parameter(String name, boolean value) {
        this(name, new Boolean(value));
    }

    public Parameter(String name, int value) {
        this(name, new Integer(value));
    }

    public Parameter(String name, double value) {
        this(name, new Double(value));
    }

    public Parameter(String name, long value) {
        this(name, new Long(value));
    }


    public ParameterMap getParameterMap() {
        ParameterMap parameterMap = null;
        if (_parentStrategyS != null) {
            parameterMap = _parentStrategyS.getParameterMap();
        } else if (_parentCategoryC != null) {
            parameterMap = _parentCategoryC.getParameterMap();
        } else if (_parentManipulation != null) {
            parameterMap = _parentManipulation.getParameterMap();
        }
        return parameterMap;
    }


    public boolean isParameterManipulation() {
        return (_parentManipulation != null);
    }

    public void setParentManipulation(ParameterManipulation manipulation) {
        _parentManipulation = manipulation;
    }

    public boolean isConnectedToParameterMap() {
        return getParameterMap() != null;
    }


    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public Object getValue() {
        return _value;
    }


    public void setValue(long value) {
        setValue(new Long(value));
    }

    public void setValue(String value) {
        setValue((Object)value);
    }

    public void setValue(boolean value) {
        setValue(new Boolean(value));
    }

    public void setValue(Object newValue) {
        Object oldValue = _value;

        if (newValue instanceof StrategyDefinitionParameter) {
            replaceStrategyS(oldValue, newValue);
        } else {
            _value = newValue;
        }

        fireParameterValueChangedEvent(oldValue, _value, new Stack());

    }

    /**
     * Have to make sure you set up the new value before disconnecting the old value - when you disconnect it will cause everything to re-bind so it
     * needs to have the parameter map set up in the new state.
     *
     * @param oldValue
     * @param newValue
     */
    private void replaceStrategyS(Object oldValue, Object newValue) {
        if (log.isDebugEnabled()) {
            log.debug("[replaceStrategyS]: parameter=" + getFullyQualifiedName() + " : oldValue=" + oldValue + " : newValue=" + newValue);
        }


        StrategyDefinitionParameter newStrategyS = (StrategyDefinitionParameter)newValue;
        StrategyDefinitionParameter oldStrategyS = (StrategyDefinitionParameter)oldValue;

        List observers = null;
        if (oldStrategyS != null) {
            observers = oldStrategyS.getAllObservers(); // Do this after we have unbound so only get stuff outside the hierarchy...
            oldStrategyS.unbind();
        } else {
            observers = new ArrayList();
        }

//        oldStrategyS.removeParents(); // maybe we can just remove from the top level...


        _value = newStrategyS;
        newStrategyS.setParentParameter(this);
//        newStrategyS.reparentChildren(); // Have to do this first so that everything is set up to bind ...
        newStrategyS.bind();

        if (observers.size() > 0) {
            fireRebindEvent(observers, oldStrategyS, newStrategyS);
        }

    }

    protected void fireRebindEvent(List observers, Parameter oldParameter, Parameter newParameter) {

        for (Iterator itr = observers.iterator(); itr.hasNext();) {
            IParameterListener observer = (IParameterListener)itr.next();
            if (log.isDebugEnabled()) {
                log.debug("[fireBindEvent] -> " + observer);
            }
            observer.parameterRebound(this, oldParameter, newParameter);
        }
    }

    /**
     * Return a list of all the observers who are interested in us... for re-binding purposes
     *
     * @return
     */
    public List getAllObservers() {
        return new ArrayList(_listeners);
    }

    /**
     * Hook up any event listeners to the model - will be called when the ParameterMap is in a complete state.
     */
    public void bind() {
        if (containsStrategy()) {
            StrategyDefinitionParameter sdp = getStrategyDefinitionValue();
            sdp.bind();
        }
    }

    public void reparentChildren() {
        if (containsStrategy()) {
            StrategyDefinitionParameter sdp = getStrategyDefinitionValue();
            sdp.setParentParameter(this);
        }
    }

    /**
     * UnHook any event listeners to the model - will be called when the ParameterMap is in a complete state.
     */
    public void unbind() {
        if (containsStrategy()) {
            StrategyDefinitionParameter sdp = getStrategyDefinitionValue();
            sdp.unbind();
        }
    }


    public Category getParentCategoryC() {
        return _parentCategoryC;
    }


    public long getLongValue() {
        return ((Long)getValue()).longValue();
    }

    public double getDoubleValue() {
        return ((Double)getValue()).doubleValue();
    }

    public StrategyDefinitionParameter getParentStrategy() {
        return _parentStrategyS;
    }


    public Parameter findParameter(String name) {
        Parameter found = null;
        if (_value instanceof StrategyDefinitionParameter) {
            found = ((StrategyDefinitionParameter)getValue()).findParameter(name);
        }

        if (found == null && _name.equals(name)) {
            found = this;
        }
        return found;
    }

    public String getStringValue() {
        return (String)_value;
    }

    public boolean containsStrategy() {
        Object value = getValue();
        return (value instanceof StrategyDefinitionParameter);
    }

    public boolean isValueNull() {
        return (getValue() == null);
    }


    /**
     * We allow WILDCARDS only when we contain a strategy definition so that you dont have to know the name of it.
     * Idea being you know this parameter contains a strategy, and that IT has a parameter, say, Bounds but it could have several different
     * implementations....
     *
     * @param nameStack
     * @return
     * @see Parameter.getWildCardName()
     */
    public Object findObject(Stack nameStack) {
        String currentLevelName = (String)nameStack.peek();
        Object found = null;
        if (currentLevelName.equals(_name)) {
            nameStack.pop();
            if (nameStack.size() > 0 && containsStrategy()) {
                StrategyDefinitionParameter strategyS = getStrategyDefinitionValue();
                if (nameStack.peek().equals(WILDCARD)) {
                    nameStack.pop();
                    nameStack.push(strategyS.getName());
                }

                found = strategyS.findObject(nameStack);
            } else {
                found = this;
            }
        }
        return found;
    }

    public IParameterManipulation getParentManipulation() {
        return _parentManipulation;
    }

    public String getFullyQualifiedName() {
        String name = _name;
        if (_parentStrategyS != null) {
            name = _parentStrategyS.getFullyQualifiedName() + "." + _name;
        } else if (_parentCategoryC != null) {
            name = _parentCategoryC.getFullyQualifiedName() + "." + _name;
        }
        return name;
    }

    public String getWildCardName() {
        String name = _name;
        if (_parentStrategyS != null) {
            name = _parentStrategyS.getWildCardName() + "." + _name;
        } else if (_parentCategoryC != null) {
            name = _parentCategoryC.getFullyQualifiedName() + "." + _name;
        }
        return name;
    }

    public String toString() {
        if (containsStrategy()) {
            return _name + "=" + ((StrategyDefinitionParameter)_value).getName() + " [strategy] : fqName=" + getFullyQualifiedName();
        } else if (_value != null && Collection.class.isAssignableFrom(_value.getClass())) {
            return _name + "=" + _value.getClass().getName() + " : size=" + ((Collection)_value).size() + " [collection]";
        } else {
            return _name + " = " + _value + " : fqName=" + getFullyQualifiedName();
        }
    }

    public boolean getBooleanValue() {
        return ((Boolean)_value).booleanValue();
    }

    public boolean hasParent() {
        return ((_parentCategoryC != null) || (_parentStrategyS != null));
    }

    public StrategyDefinitionParameter getStrategyDefinitionValue() {
        if (!this.containsStrategy()) {
            throw new IllegalStateException("Someone thought this object contained a strategy definition but it doesnt it contains : " + getValue().getClass().getName() + " : " + getValue());
        }
        return (StrategyDefinitionParameter)getValue();
    }

    public int getIntValue() {
        return ((Integer)_value).intValue();
    }

    public void setValue(double fieldDepth) {
        setValue(new Double(fieldDepth));
    }


    public String getComment() {
        return _comment;
    }

    public void setComment(String comment) {
        _comment = comment;
    }

    public boolean hasComment() {
        return _comment != null;
    }


    public void addParameterListener(IParameterListener l) {

//        if (getFullyQualifiedName().endsWith("rectangularBoundaryStrategy.location")) {
//            if (log.isInfoEnabled()) {
//                log.info("Adding Listener to Layout location!");
//            }
//        }
        if (!_listeners.contains(l)) {
            _listeners.add(l);
        }
    }

    public void removeParameterListener(IParameterListener l) {
        _listeners.remove(l);
    }

    protected void fireParameterDisconnectedEvent(String fullyQualifiedName, Stack parameterPath) {
//Going to turn this off for a bit ...
//        ParameterDisconnectedEvent evt = new ParameterDisconnectedEvent(this, fullyQualifiedName,  parameterPath);
//        List listeners = new ArrayList(_listeners); // Incase any of the listeners want to remove themselves....
//        for (Iterator itr = listeners.iterator(); itr.hasNext();) {
//            IParameterListener listener = (IParameterListener)itr.next();
//            listener.parameterDisconnected(evt);
//        }
//        if (containsStrategy()) {
//            getStrategyDefinitionValue().fireParameterDisconnectedEvent(fullyQualifiedName,  parameterPath);
//        }
    }

    protected void fireParameterConnectedEvent(Stack parameterPath) {
        //Going to turn this off for a bit ...
//        ParameterEvent evt = new ParameterEvent(this, parameterPath);
//        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
//            IParameterListener listener = (IParameterListener)itr.next();
//            listener.parameterConnected(evt);
//        }
//        if (containsStrategy()) {
//            getStrategyDefinitionValue().fireParameterConnectedEvent(parameterPath);
//        }
    }

    public void fireParameterValueChangedEvent(Object oldValue, Object newValue, Stack parameterPath) {
        ParameterValueChangedEvent evt = new ParameterValueChangedEvent(this, oldValue, newValue, parameterPath);
        for (Iterator itr = _listeners.iterator(); itr.hasNext();) {
            IParameterListener listener = (IParameterListener)itr.next();
            listener.parameterValueChanged(evt);
        }
    }

    private static final Logger log = Logger.getLogger(Parameter.class);


    /**
     * When it is replaced (StrategyDefinitionParameter.replaceParameter)
     * It is removed from the hierarchy of the parameter map.
     * If we contain a strategy then we fire a change event
     */
    public void disconnect() {
        boolean disconnected = isConnectedToParameterMap();
        String fullyQualifiedName = getFullyQualifiedName();
        if (log.isDebugEnabled()) {
            log.debug("[disconnect] " + fullyQualifiedName);
        }
        removeParents();

        if (disconnected) {
            fireParameterDisconnectedEvent(fullyQualifiedName, new Stack());
        }
    }

    protected void removeParents() {
        _parentStrategyS = null;
        _parentCategoryC = null;
    }

    public void setParentCategory(Category parentCategoryC) {
        _parentCategoryC = parentCategoryC;
//        if (_parentCategoryC.isConnectedToParameterMap()) {
//            fireParameterConnectedEvent(new Stack());
//        }
    }

    public void setParentStrategyS(StrategyDefinitionParameter parentStrategyS) {
        _parentStrategyS = parentStrategyS;
//        if (_parentStrategyS.isConnectedToParameterMap()) {
//            fireParameterConnectedEvent(new Stack());
//        }
    }


    private List _listeners = new ArrayList();

    private String _name;
    private Object _value;
    private String _comment;
    private Category _parentCategoryC;
    private StrategyDefinitionParameter _parentStrategyS;


    protected static final String WILDCARD = "*";
    private ParameterManipulation _parentManipulation;
}
 
