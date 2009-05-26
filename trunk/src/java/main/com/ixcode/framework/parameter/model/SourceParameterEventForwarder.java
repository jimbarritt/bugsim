/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.Stack;

/**
 *  Description : Forwards Parameter Events To a Destination Parameter
 *  Created     : Jan 29, 2007 @ 12:05:26 AM by jim
 */
class SourceParameterEventForwarder implements IParameterListener, IParameterContainerListener {

    public SourceParameterEventForwarder(SourceParameterForwardingMap parent, ParameterReference source) {
        if (source == null) {
            throw new IllegalArgumentException("Source is Null!");
        }
        _source = source;
        _parent = parent;
    }

    public void parameterConnected(ParameterEvent evt) {
        log.warn("Parameter Connected event " + toString() + " - don't know what to do - bind ?");
    }

    public void parameterDisconnected(ParameterDisconnectedEvent event) {
//        log.warn("ParameterDisconnected: " + toString() );
//        log.warn("RebindingForwardingMap...");
//       if (log.isInfoEnabled()) {
//            log.info("[rebindingSourceParameters] : " + _parent.getParent().getParent().getFullyQualifiedName() + " because: " + event.getEventPath());
//        }
//        _parent.rebind();
          //only want to rebind when the update listener changes
    }

    public void parameterValueChanged(ParameterValueChangedEvent event) {
//        if (log.isInfoEnabled()) {
//            log.info("[forwardEvent] : " + event.getEventPath());
//        }

        if (!event.pathContains(_destination)) { // means havent yet fired it. (stops circularity)
            _destination.fireParameterValueChangedEvent(event.getOldValue(), event.getNewValue(), event.getParameterPath());
        }

    }

    public void parameterRebound(Parameter source, Parameter oldValue, Parameter newValue) {
         _parent.unbind();

        if (_parent.getParentDerivedParameter().isConnectedToParameterMap()) {
            if (log.isDebugEnabled()) {
                log.debug("[rebind]: [rebindingForwardingMap] : derivedParameter=" + _parent.getParentDerivedParameter().getFullyQualifiedName() + " : source=" + _source.getFullyQualifiedName() );
            }
            _parent.bind();

            // If we dont do this things that are listening to us wont know that we have changed...
            Object newDerivedValue = _parent.getParentDerivedParameter().getValue();
            _parent.getParentDerivedParameter().fireParameterValueChangedEvent(null, newDerivedValue, new Stack());
        } else {
            if (log.isInfoEnabled()) {
                log.info(_parent.getParentDerivedParameter().getFullyQualifiedName() + " : No longer connected to map not binding");
            }
        }

    }

    public void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP) {
//        if (_destination instanceof IParameterContainer) {
//            ((IParameterContainer)_destination).fireParameterReplacedEvent(oldP, newP);
//        }
    }

    /**
     * Always call unbind first!
     * @param destination
     */
    public void bind(Parameter destination) {
        _destination = destination;

        // Doing this in the ForwarderMap now ...
//        if (_source instanceof ParameterReference){
            //This needs to happen after we have been rebound but before we get added as a listener - this seems to be the only place i can do it...
            // although it would be nice to have it happen inside the Parameter reference itself
//            ((ParameterReference)_source).dereference();
//        }
        _source.addParameterListener(this);
//        if (_source instanceof IParameterContainer) {
//            ((IParameterContainer)_source).addParameterContainerListener(this);
//        }
    }

    public void unbind() {
        _source.removeParameterListener(this);
//        if (_source instanceof IParameterContainer) {
//            ((IParameterContainer)_source).removeParameterContainerListener(this);
//        }
        _destination = null;
    }

    public Parameter getDestination() {
        return _destination;
    }

    public String toString() {

        String destName = (_destination == null) ? "unbound"  : _destination.getName();
        String destfQName = (_destination == null) ? "unbound"  : _destination.getFullyQualifiedName();
        String sourceName = (_source instanceof ParameterReference) ? ((ParameterReference)_source).getReference() : _source.getFullyQualifiedName();
        return "[eventForwarder] : " + _source.getName() + "->" + destName+ " : srcFQN=" + sourceName + " : destFQName=" + destfQName;
    }

    private static final Logger log = Logger.getLogger(SourceParameterEventForwarder.class);
    private ParameterReference _source;
    private Parameter _destination;

    private SourceParameterForwardingMap _parent;
}
