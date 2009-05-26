/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import org.apache.log4j.Logger;

import java.util.List;

/**
 * Description : A parameter whose value is derived from one or more other parameters. Handles all the nastiness of this including event forwarding.
 */
public class DerivedParameter extends Parameter {


    /**
     * Required for loading from XML
     *
     * @todo can create a factory instead so we can remove this
     */
    public DerivedParameter() {
        super("NAME_NOT_INITIALISED", DerivedValue.INSTANCE);
        _sourceParameterMap = new SourceParameterMap(this);
        _calculation = null;
    }

    public DerivedParameter(String name, IDerivedParameterCalculation calculation) {
        this(name, (List)null, calculation);
    }

    public DerivedParameter(String name, SourceParameterMap sourceParameters, IDerivedParameterCalculation calculation) {
        this(name, (List)null, calculation);
        _sourceParameterMap = sourceParameters;
        _sourceParameterMap.setParent(this);
    }

    public DerivedParameter(String name, List sourceParameters, IDerivedParameterCalculation calculation) {
        super(name, DerivedValue.INSTANCE);
        _calculation = calculation;
        if (sourceParameters == null) {
            _sourceParameterMap = new SourceParameterMap(this);
        } else {
            _sourceParameterMap = new SourceParameterMap(this, sourceParameters);
        }
    }


    public void bind() {
        _sourceParameterMap.unbind();
        _sourceParameterMap.bind();
    }

    public void unbind() {
        _sourceParameterMap.unbind();
    }


    /**
     * Tells us to rebind next time a value is asked for... saves rebinding too many times in response to millions of events.
     */
    public void lazyRebind() {
        // turning this off for a second
//        _lazyRebind = true;
    }


    /**
     * If you call this you must at some point call bind() aswell in order to set up the event binding... but
     * we dont force it here incase you are not ready yet (need entire Parameter map)
     * @param parameters
     */
    public void setSourceParameters(List parameters) {
        _sourceParameterMap.setParameters(parameters, _calculation);
    }

    public void addSourceParameter(Parameter parameter) {
        _sourceParameterMap.addParameter(parameter);
    }

    public ISourceParameterMap getSourceParameters() {
        return _sourceParameterMap;
    }

    public boolean isDerived() {
        return true;
    }

    public Object getValue() {
        return _calculation.calculateDerivedValue(_sourceParameterMap);
    }


    public void setCalculation(IDerivedParameterCalculation calculation) {
        _calculation = calculation;
    }

    public void setValue(Object value) {
        throw new IllegalAccessError("You cannot set the value of a derived parameter");

    }

    public void setValue(StrategyDefinitionParameter param) {
        throw new IllegalAccessError("You cannot set the value of a derived parameter");
    }

    public String toString() {
        String value = "#unable to calculate";
        try {
            value = "" + getValue();
//            value= "NOT IMPLEMENTED!";
        } catch (Throwable t) {
            t.printStackTrace();
            value += " : " + t.getClass().getName() + " : " + t.getMessage();
            value += "#";
        }

        String sourceParams = (_sourceParameterMap != null) ? _sourceParameterMap.toString() : "notInitialised";
        return getName() + " [derived] : calculatedValue={" + value + "} : sourceParams=" + sourceParams;
    }


    public IDerivedParameterCalculation getCalculation() {
        return _calculation;
    }


    /**
     * This should stop massive numbers of event forwarding...
     *
     * @param destination
     */
    public void addEventForwarder(Parameter destination) {
        throw new IllegalArgumentException("Cannot add a forwarder directly to a DerivedParameter need to add it to the source parameters: " + getFullyQualifiedName());
    }

    public void dereference() {
        _sourceParameterMap.dereference();
    }

    public static Parameter copyConstruct(DerivedParameter srcDerived) {
        SourceParameterMap copySourceParameters = SourceParameterMap.copyConstruct((SourceParameterMap)srcDerived.getSourceParameters());
        DerivedParameter derived = new DerivedParameter(srcDerived.getName(), copySourceParameters, srcDerived.getCalculation());


        return derived;

    }




    private static class DerivedValue {
        private DerivedValue() {
        }

        public String toString() {
            return "DERIVED_VALUE";
        }

        public static final DerivedValue INSTANCE = new DerivedValue();

    }

    private IDerivedParameterCalculation _calculation;

    private static final Logger log = Logger.getLogger(DerivedParameter.class);
    private SourceParameterMap _sourceParameterMap;

}
