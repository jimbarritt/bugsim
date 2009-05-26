/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.Stack;

/**
 * Description : Encapsulates the binding between a source parameter map and those parameters it is listening for updates from
 * Created     : Feb 1, 2007 @ 11:19:46 AM by jim
 */
class SourceParameterUpdateListener implements IParameterContainerListener, IParameterListener {

    public SourceParameterUpdateListener(SourceParameterForwardingMap parameterMap) {
        _eventForwardingMap = parameterMap;
    }


    /**
     * Will be fired if the any of the parameters we are listening to for updates is replaced
     * <p/>
     * Need this incase you are monitoring a StrategyParameter and one of its child parameters gets replaced.
     *
     * @param source
     * @param oldP
     * @param newP
     */
    public void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP) {
//        if (log.isInfoEnabled()) {
//            log.info("[parameterReplaced]: [lazyRebind] :derivedParameter: " + _eventForwardingMap.getParentDerivedParameter().getFullyQualifiedName());
//        }
//        _eventForwardingMap.getParentDerivedParameter().lazyRebind();
        throw new IllegalStateException("HSould not be using Parameter Replaced Event");
    }

    public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {
//        if (_eventForwardingMap.getParentDerivedParameter().getFullyQualifiedName().endsWith("layoutBoundary.rectangularBoundaryStrategy.location")) {
//            if (log.isInfoEnabled()) {
//                log.info("REBINDING LOCATION!!");
//            }
//        }


        _eventForwardingMap.unbind();

        if (_eventForwardingMap.getParentDerivedParameter().isConnectedToParameterMap()) {
            if (log.isInfoEnabled()) {
                log.info("[rebind]: [rebindingForwardingMap] : derivedParameter=" + _eventForwardingMap.getParentDerivedParameter().getFullyQualifiedName() + " : source=" + _sourceParameterReference);
            }
            _eventForwardingMap.bind();

            // If we dont do this things that are listening to us wont know that we have changed...
            Object newDerivedValue = _eventForwardingMap.getParentDerivedParameter().getValue();
            _eventForwardingMap.getParentDerivedParameter().fireParameterValueChangedEvent(null, newDerivedValue, new Stack());
        } else {
            if (log.isInfoEnabled()) {
                log.info(_eventForwardingMap.getParentDerivedParameter().getFullyQualifiedName() + " : No longer connected to map not binding");
            }
        }
    }

    /**
     * @param event
     */
    public void parameterDisconnected(ParameterDisconnectedEvent event) {
//        if (log.isInfoEnabled()) {
//            log.info("[disconnected]: [lazyRebind] :derivedParameter: " + _eventForwardingMap.getParent().getParent().getFullyQualifiedName() + " : eventPath=" + event.getEventPath());
//        }
//        _eventForwardingMap.getParentDerivedParameter().lazyRebind();
    }

    public void parameterConnected(ParameterEvent event) {
        // Shouldnt NEED to do anything - in theory the parameter references we have already should be ok
    }


    /**
     * In theory we only get attatched to Parameters which contain strategys but you could attatch to anything....
     *
     * @param event
     */
    public void parameterValueChanged(ParameterValueChangedEvent event) {
//        if (log.isInfoEnabled()) {
//            log.info("[valueChanged]: [lazyRebind] :derivedParameter: " + _eventForwardingMap.getParent().getParent().getFullyQualifiedName() + " : eventPath=" + event.getEventPath());
//        }

    }

    public void bind(ParameterReference parameterRef) {
//        if (log.isInfoEnabled()) {
//            log.info("Binding: " + parameterRef);
//        }
        parameterRef.addParameterListener(this);
//        Parameter dereferenced = parameterRef.dereference();
//        if (dereferenced instanceof IParameterContainer) {
//            ((IParameterContainer)dereferenced).addParameterContainerListener(this);
//        }
        _sourceParameterReference = parameterRef;

    }

    public void unbind() {
        _sourceParameterReference.removeParameterListener(this);
//        Parameter dereferenced = _sourceParameterReference.dereference();
//        if (dereferenced instanceof IParameterContainer) {
//            ((IParameterContainer)dereferenced).removeParameterContainerListener(this);
//
//        }
        _sourceParameterReference = null;
    }


    public String toString() {
        String paramName = _sourceParameterReference != null ? _sourceParameterReference.getName() + " : " + _sourceParameterReference.getFullyQualifiedName() : "unbound";
        return "[SourceParameterUpdateListener] ->" + paramName;
    }

    public ParameterReference getSource() {
        return _sourceParameterReference;
    }


    private static final Logger log = Logger.getLogger(SourceParameterUpdateListener.class);
    private SourceParameterForwardingMap _eventForwardingMap;
    private ParameterReference _sourceParameterReference;
}
