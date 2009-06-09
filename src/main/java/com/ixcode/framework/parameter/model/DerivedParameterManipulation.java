/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.experiment.model.ExperimentPlan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : This allows you to create a manipulation in which the value you set is derived in some way from from one of the other parameters.
 *                it looks very similar to the DerivedParameter but is subtly different in that you can overide any aspect of the way in which the parameter
 *                is configured using the IDerivedManipulationConfiguration interface.
 *                It is similar insomuch as it has a list of source parameters which get passed to the DerivedConfiguration.
 *
 * it looks like we are not really using this class as we have got away with using DerivedParameter most of the time, but you never know when we might need it!!
 *
 *
 * NOTE MAKE SURE YOU ADD THIS TO THE MANIPULATION AFTER THE SOURCE PARAMS!!
 * ALSO YOU CANT DERIVE FROM PARAMS WHICH ARE IN LEVELS BELOW YOU IN THE MANIP HIERARCHY!! OF COURSE!
 * @todo by using parameter references you could actually do that...
 */
public class DerivedParameterManipulation implements IParameterManipulation {

    public DerivedParameterManipulation(Parameter derivedParameter, IDerivedManipulationConfiguration derivedManipulationConfiguration) {
        this(derivedParameter, derivedManipulationConfiguration, new ArrayList());
    }

    public DerivedParameterManipulation(Parameter derivedParameter, IDerivedManipulationConfiguration derivedManipulationConfiguration, List sourceParameters) {
        _sourceParams = sourceParameters;

        _derivedParameter = derivedParameter;
        _derivedConfig = derivedManipulationConfiguration;
        

    }

    public void  addSourceParameter(Parameter sourceParam) {
        _sourceParams.add(sourceParam);
    }

    public ParameterMap getParameterMap() {
        if (_parent !=null) {
            return _parent.getParameterTemplate();
        } else {
            return null;
        }
    }

    public void setParent(ExperimentPlan parent) {
        _parent = parent;
    }

    public void configure(ParameterMap currentParameters) {
        List currentSourceParameters = new ArrayList();
        for (Iterator itr = _sourceParams.iterator(); itr.hasNext();) {
            Parameter source = (Parameter)itr.next();
            Parameter currentValue = currentParameters.findParameter(source.getFullyQualifiedName());
            currentSourceParameters.add(currentValue);
        }

        Parameter currentDerivedParameter = currentParameters.findParameter(_derivedParameter.getFullyQualifiedName());
        _derivedConfig.configureDerivedParameter(currentDerivedParameter, currentSourceParameters);
    }

    /**
     * this one is a little more tricky than the others - could be all kinds of parameter references in here!
     * @param parameterMap
     */
    public void rewireParameterReferences(ParameterMap parameterMap) {
        throw new IllegalStateException("Not implemented this yet!");
    }

    public boolean isParameterStructureChanged() {
        return _parameterStructureChanged;
    }

    private List _sourceParams = new ArrayList();
    private Parameter _derivedParameter;
    private IDerivedManipulationConfiguration _derivedConfig;
    private boolean _parameterStructureChanged;
    private ExperimentPlan _parent;
}
