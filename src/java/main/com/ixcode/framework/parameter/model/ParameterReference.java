/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.Stack;

/**
 * Description : This is used when you want to create a temporary reference to another parameter
 * but only know the fully qualified name of it and don't have access to the actual instance.
 * <p/>
 * We have to implement the IParameterContainer aswell because we could be a reference to a StrategyDefinitionParameter and need to behave like one.
 */
public class ParameterReference extends Parameter implements IParameterListener {


    public static final String P_FQ_NAME = "fullyQualifiedName";

    public ParameterReference() {
    }

    public static String createReferenceName(String parameterName) {
        if (parameterName.startsWith("#REF:")) {
            return parameterName;
        }
        return "#REF:" + parameterName + "#";
    }

    public ParameterReference(String name, String referenceFullyQualifiedName) {
        super(createReferenceName(name), ReferenceValue.INSTANCE);
        _originalReference = referenceFullyQualifiedName;
    }

    public ParameterReference(String name, String referenceFullyQualifiedName, DerivedParameter parent) {
        super(createReferenceName(name), ReferenceValue.INSTANCE);
        _parentDerivedParameter = parent;
        _originalReference = referenceFullyQualifiedName;
    }

    /**
     * in some cases, you might be given a parameter before it is attatched to a parameter map...
     * <p/>
     * in this case we store the originalParameter so we can use that until it is attatched - as soon as we find its attatched we drop our reference to it...
     * <p/>
     * yeah a bit complex but at least its all handled in here...
     * <p/>
     * Its basically deferring the calculation - LazyLoading Pattern
     *
     * @param sourceParam
     */
    public ParameterReference(Parameter sourceParam, DerivedParameter parent) {
        super(createReferenceName(sourceParam.getName()), ReferenceValue.INSTANCE);
        _parentDerivedParameter = parent;
        if (_parentDerivedParameter.isConnectedToParameterMap() && sourceParam.isConnectedToParameterMap()) {
            _originalReference = getReference(sourceParam);
        } else {
            _originalParameter = sourceParam;
            _currentParameter = _originalParameter;
            bind(_currentParameter);
            updateCurrentReference();
        }
    }


    /**
     * Propagates events to anything that is listening to us. Now we just need to manage when we are connected to the source parameter and which one it is!
     *
     * @param event
     */
    public void parameterValueChanged(ParameterValueChangedEvent event) {
        super.fireParameterValueChangedEvent(event.getOldValue(), event.getNewValue(), event.getParameterPath());
    }

    public void parameterDisconnected(ParameterDisconnectedEvent event) {
//        if (log.isInfoEnabled()) {
//            log.info("[disconnected]: fqName=" + event.getFullyQualifiedName() + " : parentDerived=" + _parentDerivedParameter.getFullyQualifiedName() + " : REF=" + getReference() + " : eventPath=" + event.getEventPath());
//        }

        super.fireParameterDisconnectedEvent(event.getFullyQualifiedName(), event.getParameterPath());
    }

    public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {
        super.fireRebindEvent(super.getAllObservers(), oldValue, newValue);
    }

    public void parameterConnected(ParameterEvent event) {
        if (_currentReference == null) {
            _currentReference = getReference();
        }
    }


    /**
     * A little bit complex but essentially you give it a real parameter which may or may not be connected to the parameter map.
     * <p/>
     * If it is connected, it should have set the value of _currentReference which will give it a fully qualified search name to look for in the parameter map.
     * <p/>
     * Only should be used by things in our package.
     *
     * @return
     */
    Parameter dereference() {
//        if (log.isInfoEnabled()) {
//            log.info("[dereference]: parentConnected=" + isParentConnected() + " : paramConnected=" + isParameterConnected() + " : " + getReference());
//        }
        Parameter dereferenced = _originalParameter;

        String searchName = null;
        if (isParentConnected()) {

            if (_currentReference != null) {
                searchName = _currentReference;
            } else if (isCurrentParameterConnected()) {
                searchName = getReference(_currentParameter);
            } else if (isOriginalParameterConnected())
            { // This should never be the case unless somehow this methdo gets called and no connected event was passed.
                searchName = getReference(_originalParameter);
            } else if (_originalReference != null) {
                searchName = _originalReference;
            } else if (_currentParameter != null) {
                searchName = getReference(_currentParameter);
            } else {
                throw new IllegalStateException("Could not dereference parameter : " + getReference() + " : currentReference=" + _currentReference + " : currentParameter=" + _currentParameter);
            }
            if (searchName != null) {
                ParameterMap map = getParameterMap();
                dereferenced = map.findParameter(searchName);
                if (dereferenced == null) {
//                    log.warn("[#!dereferenceFailed!#] : searchName=" + searchName + " : parent=" + _parentDerivedParameter.getFullyQualifiedName() + " : isParentConnected=" + isParentConnected() + " : isParameterConnected:" + isOriginalParameterConnected());
                    dereferenced = (_currentParameter != null) ? _currentParameter : _originalParameter;
                }

            }

        }
        if (dereferenced == null && searchName != null) {
            dereferenced = tryToDereferenceFromBranch(searchName, getParameterMap());
        }
        if (dereferenced == null) {
            throw new IllegalStateException("Could not dereference parameter : (SearchString=" + searchName + ", this=" + toString());
        }


        if (_currentParameter != dereferenced) {
//            if (log.isInfoEnabled()) {
//                log.info("[updateReference]: dereferenced=" + dereferenced + " : current="+ _currentParameter);
//            }
            unbind(_currentParameter);
            bind(dereferenced);
            _currentParameter = dereferenced;
            updateCurrentReference();
        }
        return dereferenced;

    }

    /**
     * Manually search back up our parent branch to find a parameter that is called the first name in out search Name and then come
     * back down until we find the rigth parameter...
     *
     * @param searchName
     * @return
     */
    private Parameter tryToDereferenceFromBranch(String searchName, ParameterMap params) {
        String firstParamName = searchName.substring(0, searchName.indexOf("."));

        IParameterModel first = _parentDerivedParameter.findParentCalled(firstParamName);
        if (first == null) {
            throw new IllegalStateException("Could not find parent called '" + firstParamName + "'");
        }

        Parameter found = null;
        if (first instanceof Parameter) {
            Parameter firstP = (Parameter)first;


            Stack nameStack = ParameterMap.createQualifiedNameStack(searchName);
            found = (Parameter)firstP.findObject(nameStack);
        }

        return found;
    }


    private void updateCurrentReference() {

        if (_currentParameter.isConnectedToParameterMap()) {
//            if (log.isInfoEnabled()) {
//                log.info("[updateCurrentReference] : " + getReference(_currentParameter));
//            }
            _currentReference = getReference(_currentParameter);

        }
    }

    public void unbind() {
        unbind(_currentParameter);
    }


    private void unbind(Parameter param) {
        if (param != null) {
            param.removeParameterListener(this);
        }
    }


    private void bind(Parameter param) {
        if (param != null) {
            param.addParameterListener(this);
        }
    }

    /**
     * Just make everything go through here so as we change it to wildcard references and back its all controlled
     *
     * @param parameter
     * @return
     */
    private String getReference(Parameter parameter) {
        return parameter.getWildCardName();
    }

    public String getReference() {
        return (_currentReference != null) ? _currentReference : (_originalParameter != null) ? getReference(_originalParameter) : _originalReference;
    }

    private boolean isOriginalParameterConnected() {
        return (_originalParameter != null) && _originalParameter.isConnectedToParameterMap();
    }

    private boolean isCurrentParameterConnected() {
        return (_currentParameter != null) && _currentParameter.isConnectedToParameterMap();
    }

    private boolean isParentConnected() {
        return _parentDerivedParameter.isConnectedToParameterMap();
    }


    public void setValue(Object value) {
        throw new IllegalAccessError("You cannot set the value of a parameter Reference");
    }

    public void setValue(StrategyDefinitionParameter param) {
        throw new IllegalAccessError("You cannot set the value of a parameter Reference");
    }

    public DerivedParameter getParentDerivedParameter() {
        return _parentDerivedParameter;
    }

    public void setParentDerivedParameter(DerivedParameter parentDerivedParameter) {
        _parentDerivedParameter = parentDerivedParameter;
    }

    public String getFullyQualifiedName() {
        return _parentDerivedParameter.getFullyQualifiedName() + "." + super.getName();
    }


    public ParameterMap getParameterMap() {
        if (getParentManipulation() != null) {
            return getParentManipulation().getParameterMap();
        } else {
            return _parentDerivedParameter.getParameterMap();
        }
    }

    public String toString() {
        String ref = getReference();
        return "#REF: " + ref + "#" + " : isParentconnected=" + isParentConnected() + " : isParameterConnected=" + isOriginalParameterConnected() + " : parent=" + getParentDerivedParameter().getFullyQualifiedName();
    }


    private static class ReferenceValue {
        private ReferenceValue() {
        }

        public String toString() {
            return "#PARAMETER_REFERENCE#";
        }

        public static final ReferenceValue INSTANCE = new ReferenceValue();
    }

    private static final Logger log = Logger.getLogger(ParameterReference.class);
    private String _originalReference;
    private DerivedParameter _parentDerivedParameter;

    private Parameter _originalParameter;

    private ParameterReferenceState _state;
    private Parameter _currentParameter;
    private String _currentReference;
}
