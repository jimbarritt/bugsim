/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : Passed to derived Parameter calculations to enable them to get at their parameters.
 * Created     : Jan 27, 2007 @ 1:57:03 PM by jim
 */
class SourceParameterMap implements ISourceParameterMap {

    public static Parameter getParameter(Map params, String name) {
        if (!params.containsKey(name)) {
            throw new IllegalArgumentException("Could not find parameter '" + name + "' in map: " + params.keySet());
        }
        return (Parameter)params.get(name);
    }

    public SourceParameterMap(DerivedParameter parent) {
        _parent = parent;
    }

    public SourceParameterMap(DerivedParameter parent, List parameters) {
        _parent = parent;
        setParameters(parameters, parent.getCalculation());
        _forwardingMap = new SourceParameterForwardingMap(this, _parameterReferences, _calculation);
    }

    public static SourceParameterMap copyConstruct(SourceParameterMap source) {
        SourceParameterMap copyMap = new SourceParameterMap(new ArrayList(source._parameterNames));
        for (Iterator itr = source.getParameterReferences().iterator(); itr.hasNext();) {
            ParameterReference src = (ParameterReference)itr.next();

            ParameterReference copy = new ParameterReference(src.getName(), src.getReference());
            copyMap._parameterReferences.add(copy);
            copyMap._parameterReferenceMap.put(copy.getName(), copy);

        }
        return copyMap;
    }

    private SourceParameterMap(List parameterNames) {
        _parameterNames = parameterNames;
    }

    /**
     * Pass in a list of real parameters and this will turn them into ParameterReference's
     *
     * @param sourceParameters
     */
    public void setParameters(List sourceParameters, IDerivedParameterCalculation calculation) {
        if (log.isDebugEnabled()) {
            log.debug("[setParameters] : " + getParent().getFullyQualifiedName());
        }
        _parameterNames = new ArrayList();
        _parameterReferences = new ArrayList();
        _parameterReferenceMap = new HashMap();
        _calculation = calculation;


        for (Iterator itr = sourceParameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            addParameter(parameter);
        }





    }


    public void addParameter(Parameter parameter) {
        ParameterReference reference = (parameter instanceof ParameterReference) ? (ParameterReference)parameter : new ParameterReference(parameter, _parent);

        _parameterReferences.add(reference);
        _parameterNames.add(parameter.getName());
        _parameterReferenceMap.put(reference.getName(), reference);

        reference.setParentDerivedParameter(_parent);


    }


    public Parameter getParameter(String name) {

        return getParameterReference(name).dereference();

    }

    public Parameter getParameter(int index) {
        if (index > _parameterReferences.size() - 1) {
            throw new IllegalArgumentException("Could not find parameter index '" + index + "' in List : " + _parameterReferences + " : parent=" + _parent.getFullyQualifiedName());
        }
        return ((ParameterReference)_parameterReferences.get(index)).dereference();
    }


    private boolean hasParameterReference(String name) {
        String key = ParameterReference.createReferenceName(name);
        return _parameterReferenceMap.containsKey(key);
    }

    public ParameterReference getParameterReference(String name) {
        String key = ParameterReference.createReferenceName(name);
        if (!_parameterReferenceMap.containsKey(key)) {
            throw new IllegalArgumentException("Could not find parameter '" + name + "' : key=" + key + "' in SPMap : " + _parameterReferenceMap.keySet() + " : parent=" + _parent.getFullyQualifiedName());
        }
        return (ParameterReference)_parameterReferenceMap.get(key);
    }

    public List getParameterReferences() {
        return _parameterReferences;
    }

    public List getParameterNames() {
        return _parameterNames;
    }

    public DerivedParameter getParent() {
        return _parent;
    }

    public ParameterMap getParentParameterMap() {
        return _parent.getParameterMap();
    }

    public String toString() {
        return "[SourceParameterMap] : size=" + _parameterNames.size() + " : parameters=" + _parameterReferences;
    }

    public Parameter getFirstParameter() {
        return getParameter(0);
    }

    public void dereference() {
        for (Iterator itr = _parameterReferences.iterator(); itr.hasNext();) {
            ParameterReference reference = (ParameterReference)itr.next();
            reference.dereference();
        }
        if (_forwardingMap != null) {
            _forwardingMap.dereference();
        }
    }

    public SourceParameterForwardingMap getForwardingMap() {
        return _forwardingMap;
    }


    public void bind() {
        if (log.isDebugEnabled()) {
            log.debug("[bind] : " + _parent.getFullyQualifiedName() + " : calculation=" + _calculation.getClass().getName());
        }
        dereference();
        if (_forwardingMap == null) {
            _forwardingMap = new SourceParameterForwardingMap(this, _parameterReferences, _calculation);
        }
        _forwardingMap.bind();
    }

    public void unbind() {
        if (_forwardingMap != null) {
            if (log.isDebugEnabled()) {
                log.debug("[unbind] : " + _parent.getFullyQualifiedName() + " unbinding forwarding map.");
            }

            _forwardingMap.unbind();
        }

    }

    void setParent(DerivedParameter derivedParameter) {
        _parent = derivedParameter;
        for (Iterator itr = _parameterReferences.iterator(); itr.hasNext();) {
            ParameterReference parameterReference = (ParameterReference)itr.next();
            parameterReference.setParentDerivedParameter(_parent);
        }
    }

    private static final Logger log = Logger.getLogger(SourceParameterMap.class);
    private List _parameterNames = new ArrayList();
    private List _parameterReferences = new ArrayList();
    private Map _parameterReferenceMap = new HashMap();

    private DerivedParameter _parent;

    private IDerivedParameterCalculation _calculation;
    private SourceParameterForwardingMap _forwardingMap;
}
