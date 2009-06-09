package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;


public class StrategyDefinitionParameter extends Parameter implements IParameterContainer{


    public StrategyDefinitionParameter() {
    }

    public StrategyDefinitionParameter(String name, Object value) {
        super(name, value);
    }

    public String getImplementingClassName() {
        return super.getStringValue();
    }


    public String getFullyQualifiedName() {
        String fqName;
        if (_parentParameter != null) {
            fqName = _parentParameter.getFullyQualifiedName() + "." + super.getName();
        } else {
            fqName = super.getFullyQualifiedName();
        }
        return fqName;
    }

    public String getWildCardName() {
        String fqName;
        if (_parentParameter != null) {
            fqName = _parentParameter.getWildCardName() + "." + WILDCARD;
        } else {
            fqName = super.getFullyQualifiedName();
        }
        return fqName;
    }


    public ParameterMap getParameterMap() {
        ParameterMap parameterMap = null;
        if (_parentParameter != null) {
            parameterMap = _parentParameter.getParameterMap();
        } else {
            parameterMap = super.getParameterMap();
        }
        return parameterMap;
    }

    /**
     * @param name
     * @return
     * @todo - work out a way of sharing this with the Category class - maybe have a ParameterCollection ?...
     */
    public Parameter findParameter(String name) {
        Parameter found = super.findParameter(name);

        if (found == null) {
            for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
                Parameter parameter = (Parameter)itr.next();
                if (parameter.findParameter(name) != null) {
                    found = parameter;
                    break;
                }
            }
        }
        return found;
    }

    public Object findObject(Stack nameStack) {
        Object found = super.findObject(nameStack); // found ourselves
        if ((found != null) && (nameStack.size() > 0)) { // still more to find underneath us
            for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
                Parameter parameter = (Parameter)itr.next();
                found = parameter.findObject(nameStack);
                if (found != null) {
                    break;
                }
            }
        }
        return found;
    }


    public List getAllObservers() {
        List observers =super.getAllObservers();
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter p = (Parameter)itr.next();
            List childObservers = p.getAllObservers();
            for (Iterator itrChilde = childObservers.iterator(); itrChilde.hasNext();) {
                IParameterListener listener = (IParameterListener)itrChilde.next();
                if (!observers.contains(listener)) { // only need to add them once!
                    observers.add(listener);
                }
            }

        }
        return new ArrayList(observers);
    }


    public List getParameters() {
        return _parameters;
    }

    public boolean hasParameter(String name) {
        return findParameter(name) != null;
    }

    /**
     * @param parameter
     */
    public void addParameter(Parameter parameter) {
        _parameters.add(parameter);
        parameter.setParentStrategyS(this);
    }


    /**
     * The important feature here is that we need to bind and unbind whilst the Parameter map is in a complete and known state....
     * SO we have to build the hierarchy, and THEN bind.... cant be done at the same time via events...
     * @param oldParameter
     * @param newParameter
     */
    public void replaceParameter(Parameter oldParameter, Parameter newParameter) {
        if (log.isDebugEnabled()) {
           log.debug("[replaceParameter] : name=" + getName() + " : fqName=" + getFullyQualifiedName() + " : old=" + oldParameter.getFullyQualifiedName() + " : new=" + newParameter.getFullyQualifiedName() );
        }
        int index = _parameters.indexOf(oldParameter);
        if (index == -1) {
            throw new IllegalArgumentException("Tried to replace a newParameter that does not exist!");
        }



        Parameter oldP = (Parameter)_parameters.get(index);

        List observers = oldP.getAllObservers();
        if (log.isDebugEnabled()) {
            log.debug("[unbindOldParameter] : " + oldP.getFullyQualifiedName());
        }
        oldP.unbind();
       // Dont remove parents because it messes up the parameter references and then they don;t know who they are after the rebind
        // @todo we mmight be able to unparent them after firing the rebind event but dont have time to try it.

        _parameters.remove(index);

        _parameters.add(index, newParameter);
        newParameter.setParentStrategyS(this);

        if (log.isDebugEnabled()) {
            log.debug("[bindNewParameter] : " + newParameter.getFullyQualifiedName());
        }
        newParameter.bind();

        if (observers.size() >0) {
            fireRebindEvent(observers, oldP, newParameter);
        }
    }


    /**
     * Try to guarantee that everything is the way it should be whenver unbind is called so remover parents after wards
     */
    public void unbind() {
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.unbind();
        }
    }


    protected void removeParents() {
        super.removeParents();
        _parentParameter = null;
    }

    public void bind() {
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.bind();
        }
    }

    public void reparentChildren() {
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.setParentStrategyS(this);
            parameter.reparentChildren();
        }
    }

    public void removeParameter(String name) {
        Parameter toRemove = null;
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameter.getName().equals(name)) {
                toRemove = parameter;
                break;
            }
        }

        if (toRemove != null) {
            _parameters.remove(toRemove);
        }
    }

    public String toString() {
        return getName() + " [strategy] : implementingClass=" + getImplementingClassName();
    }

    public void rewireParameterReferences(ParameterMap parameterMap) {
        List toRewire = new ArrayList();
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameter instanceof StrategyDefinitionParameter) {
                ((StrategyDefinitionParameter)parameter).rewireParameterReferences(parameterMap);
            } else if (parameter.containsStrategy()) {
                parameter.getStrategyDefinitionValue().rewireParameterReferences(parameterMap);
            } else if (parameter instanceof ParameterReference) {
                toRewire.add(parameter);
            }

        }
        for (Iterator itr = toRewire.iterator(); itr.hasNext();) {
            ParameterReference ref = (ParameterReference)itr.next();
            Parameter realParameter = parameterMap.findParameter(ref.getFullyQualifiedName());
            int index = _parameters.indexOf(ref);
            _parameters.remove(index);
            _parameters.add(index, realParameter);
        }
    }

    public void addParameterContainerListener(IParameterContainerListener l)  {
        _containerListeners.add(l);
    }

    public void removeParameterContainerListener(IParameterContainerListener l) {
        _containerListeners.remove(l);
    }

    protected void fireParameterDisconnectedEvent(String fullyQualifiedName, Stack parameterPath) {
        super.fireParameterDisconnectedEvent(fullyQualifiedName, parameterPath);
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.fireParameterDisconnectedEvent(fullyQualifiedName,  new Stack());
        }
    }

    protected void fireParameterConnectedEvent(Stack parameterPath) {
        super.fireParameterConnectedEvent(parameterPath);
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.fireParameterConnectedEvent(new Stack());
        }
    }

    public void fireParameterReplacedEvent(Parameter oldP, Parameter newP) {
        for (Iterator itr = _containerListeners.iterator(); itr.hasNext();) {
            IParameterContainerListener l= (IParameterContainerListener)itr.next();
            l.parameterReplaced(this, oldP, newP);
        }

    }

    public void addPropertyChangeListener(String parameterName, PropertyChangeListener listener) {
        throw new IllegalArgumentException("Cannot add property change listener to a StrategyDefinitionParamertert");
    }

    public Object getParameterValue(String parameterName) {
        return getParameter(parameterName).getValue();
    }

    public void setParameterValue(String parameterName, Object value) {
        getParameter(parameterName).setValue(value);
    }

    public Parameter getParameter(String parameterName) {
        Parameter found = null;
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (parameterName.equals(parameter.getName())) {
                found = parameter;
                break;
            }
        }
        return found;
    }

    public IParameterModel getParent() {
        if (_parentParameter != null) {
            return _parentParameter;
        } else {            
            return super.getParent();
        }
    }

    public boolean hasParent() {
        if (_parentParameter != null) {
            return true;
        } else {
            return super.hasParent();
        }
    }

    /**
     *
     * @return
     */
    public boolean hasParentParameter() {
        return (_parentParameter != null);
    }

    public Parameter getParentParameter() {
        return _parentParameter;
    }


    public void setParentParameter(Parameter parameter) {
        _parentParameter = parameter;

    }

    /**
     * DOnt know about which way round to do this - parent or children first!
     * seems logical to disconnect the parent first.
     *
     * One works the first time and one the second....
     * I think it stops the things that are derived from us and below us having problems de-referencing themselves - as they are no longer connected they just use their original references
     * which doesnt matter as they are being removed nayway.
     */
    public void disconnect() {

        super.disconnect();

        _parentParameter = null;
        for (Iterator itr = _parameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            parameter.disconnect();;
        }




    }


    private List _containerListeners = new ArrayList();
    private List _parameters = new ArrayList();

    private Parameter _parentParameter;
    private static final Logger log = Logger.getLogger(StrategyDefinitionParameter.class);
}
 
