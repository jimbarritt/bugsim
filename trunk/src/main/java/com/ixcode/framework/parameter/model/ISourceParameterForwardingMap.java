/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : Public interface to the forwarding map so things cant get at the internaals.
 * Created     : Feb 2, 2007 @ 10:56:13 AM by jim
 */
public interface ISourceParameterForwardingMap {
    void addForwardFromFirstParameterReference();

    /**
     * Adds forwarding to one of the source parameter references
     *
     * @param name
     */
    void addForward(String name);

    /**
     * Adds another parameter to observe (is not in the original list .. i.e. it will be probably a child of the original source parameters
     *
     * @param parameter
     */
    void addForward(Parameter parameter);

    /**
     * Listen to this parameter for changes and update all the forwarders
     */
    void addUpdateListenerFromFirstParameterReference();

    /**
     * Listen to one of our existing source parameters for changes - whneit chnages we rebind...
     *
     * @param name
     */
    void addUpdateListener(String name);

    void addUpdateListener(int index);

    /**
     * Listens to some random parameter for changes...
     *
     * @param parameter
     */
    void addUpdateListener(Parameter parameter);

    void addUpdateListenerToParentOf(StrategyDefinitionParameter strategyS);

    ParameterReference getFirstParameterReference();

    ParameterMap getParentParameterMap();

    Parameter getParameter(String name);

    Parameter getParameter(int index);

    ParameterReference getParameterReference(int index);

    ParameterReference getParameterReference(String name);

    Parameter getFirstParameter();

    void addForward(int index);
}
