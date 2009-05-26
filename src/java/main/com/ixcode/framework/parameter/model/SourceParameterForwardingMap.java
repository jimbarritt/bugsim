/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.*;

/**
 * Description : Tells us how event forwarding should be configured.
 * Created     : Jan 30, 2007 @ 12:53:46 PM by jim
 */
class SourceParameterForwardingMap implements ISourceParameterForwardingMap {

    public SourceParameterForwardingMap(SourceParameterMap parent, List sourceParameterReferences, IDerivedParameterCalculation calculation) {
        _parameterReferences = sourceParameterReferences;
        _parameterReferenceMap = mapParameters(sourceParameterReferences);
        _parent = parent;
        _calculation = calculation;
        bind();
    }



    /**
     * Synchronized because we dont want multiple event threads coming in and messing with us!!
     */
    public synchronized void bind() {

        resetLists();
        _calculation.initialiseForwardingParameters(this);
//        if (log.isInfoEnabled()) {
//            log.info("[bind] : adding : " + _updateSourceReferences.size() + " update Listeners and " + _eventForwarders.size() + " forwarders and " + _newReferences.size() + " new Refs.");
//        }
        // Update all the references....
        for (Iterator itr = _newReferences.iterator(); itr.hasNext();) {
            ParameterReference parameterReference = (ParameterReference)itr.next();
            parameterReference.dereference();
//            if (log.isInfoEnabled()) {
//                log.info("[dereferenced] : " + parameterReference);
//            }
        }

        for (Iterator itr = _updateSourceReferences.iterator(); itr.hasNext();) {
            ParameterReference parameterRef = (ParameterReference)itr.next();
            SourceParameterUpdateListener updateListener = new SourceParameterUpdateListener(this);

            updateListener.bind(parameterRef);
//            if (log.isInfoEnabled()) {
//                log.info("[bound] : [udpateListener] :" + updateListener.getSource().getReference());
//            }
            _updateListeners.add(updateListener);
        }
        DerivedParameter parent = _parent.getParent();
        for (Iterator itr = _eventForwarders.iterator(); itr.hasNext();) {
            SourceParameterEventForwarder forwarder = (SourceParameterEventForwarder)itr.next();
            forwarder.bind(parent);
//            if (log.isInfoEnabled()) {
//                log.info("[bound] : " + forwarder);
//            }
        }


//        if (log.isInfoEnabled()) {
//            log.info("Bound: " + toString());
//        }
    }

    /**
     * Synchronized because we dont want multiple event threads coming in and messing with us!!
     */
    public synchronized void unbind() {

//        if (log.isInfoEnabled()) {
//            log.info("[unbind] : remove : " + _updateListeners.size() + " update Listeners and " + _eventForwarders.size() + " forwarders and " + _newReferences.size() + " new Refs.");
//        }
        for (Iterator itr = _updateListeners.iterator(); itr.hasNext();) {
            SourceParameterUpdateListener listener = (SourceParameterUpdateListener)itr.next();
//            if (log.isInfoEnabled()) {
//                log.info("[unbind] : " + listener);
//            }
            listener.unbind();
        }
        for (Iterator itr = _eventForwarders.iterator(); itr.hasNext();) {
            SourceParameterEventForwarder forwarder = (SourceParameterEventForwarder)itr.next();
//            if (log.isInfoEnabled()) {
//                log.info("[unbind] : " + forwarder);
//            }
            forwarder.unbind();

        }
        for (Iterator itr = _newReferences.iterator(); itr.hasNext();) {
            ParameterReference reference = (ParameterReference)itr.next();
//            if (log.isInfoEnabled()) {
//                log.info("[unbind] : " + reference);
//            }
            reference.unbind();
        }
        resetLists();
    }

    private void resetLists() {
        _updateSourceReferences = new ArrayList();
        _updateListeners = new ArrayList();
        _eventForwarders = new ArrayList();
        _newReferences = new ArrayList();
    }

    public void addForwardFromFirstParameterReference() {
        addForward(getFirstParameterReference());
    }

    public void addForward(String name) {
        addForward(getParameterReference(name));
    }

    public void addForward(int index) {
        addForward(getParameterReference(index));
    }


    public void addForward(Parameter parameter) {
        ParameterReference toAdd;
        if (!(parameter instanceof ParameterReference)) {
            toAdd = new ParameterReference(parameter, _parent.getParent());
            _newReferences.add(toAdd);
        } else {
            toAdd = (ParameterReference)parameter;
        }
        _eventForwarders.add(new SourceParameterEventForwarder(this, toAdd));
    }


    public void addUpdateListenerFromFirstParameterReference() {
        addUpdateListener(getFirstParameterReference());
    }


    public void addUpdateListener(String name) {
        addUpdateListener(getParameterReference(name));
    }

    public void addUpdateListener(int index){
        addUpdateListener(getParameterReference(index));
    }

    public void addUpdateListener(Parameter parameter) {
        Parameter toAdd = parameter;
        if (!(parameter instanceof ParameterReference)) {
            toAdd = new ParameterReference(parameter, _parent.getParent());
            _newReferences.add(toAdd);
        }
        _updateSourceReferences.add(toAdd);
    }

    public void addUpdateListenerToParentOf(StrategyDefinitionParameter strategyS) {
        if (!strategyS.hasParentParameter()) {
            throw new IllegalStateException("VOuld not find parent parameter for strategy:  " + strategyS);
        }
        addUpdateListener(strategyS.getParentParameter());

    }


    public ParameterReference getFirstParameterReference() {
        if (_parameterReferences.size() == 0) {
            throw new IllegalArgumentException("Tried to retrived first parameter from empty parameter list!");
        }
        return (ParameterReference)_parameterReferences.get(0);
    }


    public ParameterMap getParentParameterMap() {
        return _parent.getParentParameterMap();
    }

    public Parameter getParameter(String name) {
        return getParameterReference(name).dereference();
    }

    public Parameter getParameter(int index) {
        return getParameterReference(index).dereference();
    }

    public ParameterReference getParameterReference(int index) {
        if (index > _parameterReferences.size()-1 || index<0) {
            throw new IllegalArgumentException("No index: " + index + " in refs: " + _parameterReferences );
        }
       return (ParameterReference)_parameterReferences.get(index);
    }

    public ParameterReference getParameterReference(String name) {
        String key = ParameterReference.createReferenceName(name);
        if (!_parameterReferenceMap.containsKey(key)) {
            throw new IllegalArgumentException("No parameter called '" + name + "' - key'"  + key + "' in map: " + _parameterReferenceMap.keySet());
        }
        return (ParameterReference)_parameterReferenceMap.get(key);
    }




    private Map mapParameters(List sourceParameters) {
        Map params = new HashMap();
        for (Iterator itr = sourceParameters.iterator(); itr.hasNext();) {
            Parameter parameter = (Parameter)itr.next();
            if (params.containsKey(parameter.getName())) {
                throw new IllegalStateException("Duplicate source parameterName: need to work out how to deal with this!: " + parameter.getName());
            }
            params.put(parameter.getName(), parameter);
        }
        return params;
    }

    public String toString() {
        return "[eventForwardingMap] : parent=" + _parent.getParent().getFullyQualifiedName() + " : forwarderCount=" + _eventForwarders.size() + " : updateSourceCount=" + _updateSourceReferences.size()  + " : forwarders=" + _eventForwarders + " : updateSources=" + _updateSourceReferences;
    }

    public Parameter getFirstParameter() {
        return getFirstParameterReference().dereference();
    }


    /**
     * @todo need to get rid of this somehow but not sure how!
     */
    public void dereference() {
        for (Iterator itr = _newReferences.iterator(); itr.hasNext();) {
            ParameterReference reference = (ParameterReference)itr.next();
            reference.dereference();
        }
    }

    public List getEventForwarders() {
        return _eventForwarders;
    }

    public SourceParameterMap getParent() {
        return _parent;
    }

    public DerivedParameter getParentDerivedParameter() {
        return _parent.getParent();
    }


    private static final Logger log = Logger.getLogger(SourceParameterForwardingMap.class);
    private List _parameterReferences;
    private Map _parameterReferenceMap;
    private SourceParameterMap _parent;
    private IDerivedParameterCalculation _calculation;

    private List _newReferences = new ArrayList(); // references which were not part of original sourceParameters
    private List _eventForwarders = new ArrayList();
    private List _updateSourceReferences = new ArrayList();

    private List _updateListeners = new ArrayList();
}
