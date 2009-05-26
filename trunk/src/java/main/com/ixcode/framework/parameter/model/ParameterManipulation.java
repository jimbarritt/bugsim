package com.ixcode.framework.parameter.model;

import com.ixcode.framework.experiment.model.ExperimentPlan;

public class ParameterManipulation implements IParameterManipulation {


    public static final String P_PARAMETER = "parameter";
    public static final String P_VALUE_TO_SET = "valueToSet";
    public static final String P_STRUCTURE_CHANGED = "parameterStructureChanged";

    public ParameterManipulation() {
    }

    public ParameterManipulation(Parameter parameter, boolean valueToSet) {
        this(parameter, new Boolean(valueToSet));

    }
    public ParameterManipulation(Parameter parameter, double valueToSet) {
        this(parameter, new Double(valueToSet));

    }
    public ParameterManipulation(Parameter parameter, long valueToSet) {
        this(parameter, new Long(valueToSet));

    }
    public ParameterManipulation(Parameter parameter, Object valueToSet) {
        _parameter = parameter;
        _parameter.setParentManipulation(this);
        _valueToSet = valueToSet;



    }

    public void configure(ParameterMap currentParameters) {
        Parameter parameter = getParameter();
        if (!parameter.isConnectedToParameterMap())  {
            throw new IllegalStateException("Parameter " + parameter.getName() + " is not connected to the map in our manipulation!");
        }
        Parameter toConfig = currentParameters.findParameter(parameter.getFullyQualifiedName());
        if (toConfig == null) {
            throw new RuntimeException("Could not find parameter: " + getParameter().getFullyQualifiedName() + " to configure");
        }
        toConfig.setValue(getValueToSet());

    }

    public void setParent(ExperimentPlan parent) {
        _parent = parent;
    }

    public Parameter getParameter() {
        return _parameter;
    }

    public Object getValueToSet() {
        return _valueToSet;
    }

    public boolean isParameterStructureChanged() {
        return (_valueToSet instanceof StrategyDefinitionParameter);
    }

    public void setParameter(Parameter parameter) {
        _parameter = parameter;
        _parameter.setParentManipulation(this);
    }

    public void setValueToSet(Object valueToSet) {
        _valueToSet = valueToSet;
    }



    public void rewireParameterReferences(ParameterMap parameterMap) {
        if (_parameter instanceof ParameterReference) {
            ParameterReference ref = (ParameterReference)_parameter;
            String fqName = ref.getReference();
            _parameter = parameterMap.findParameter(fqName);
        }else if (_parameter instanceof StrategyDefinitionParameter){
            ((StrategyDefinitionParameter)_parameter).rewireParameterReferences(parameterMap);
        }   else if (_parameter.containsStrategy()) {
            StrategyDefinitionParameter strategy = _parameter.getStrategyDefinitionValue();
            strategy.rewireParameterReferences(parameterMap);
        }
        if (_valueToSet instanceof Parameter) {
            ((Parameter)_valueToSet).setParentManipulation(this);
        }


    }

    public String toString() {
        return "[manipulation]  parameter=" +  _parameter.getName() + " : valueToSet=" + _valueToSet + " : structureChanged=" + isParameterStructureChanged();
    }


    public ParameterMap getParameterMap() {
        if (_parent != null) {
            return _parent.getParameterTemplate();
        } else {
            return null;
        }
    }

    private Parameter _parameter;
    private Object _valueToSet;


    private ExperimentPlan _parent;
}
 
