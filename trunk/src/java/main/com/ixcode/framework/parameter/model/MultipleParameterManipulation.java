/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

import com.ixcode.framework.experiment.model.ExperimentPlan;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class MultipleParameterManipulation implements IParameterManipulation {

    public MultipleParameterManipulation() {

    }

    public ParameterMap getParameterMap() {
        if (_parent != null) {
            return _parent.getParameterTemplate();
        } else {
            return null;
        }
    }

    public void setParent(ExperimentPlan parent) {
        _parent = parent;
        for (Iterator itr = _manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            manipulation.setParent(parent);
        }
    }

    public MultipleParameterManipulation(List parameterManipulations) {
        _manipulations = parameterManipulations;
    }

    public void configure(ParameterMap currentParameters) {
        for (Iterator itr = _manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            manipulation.configure(currentParameters);
        }
    }

    public void rewireParameterReferences(ParameterMap parameterMap) {
        for (Iterator itr = _manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            manipulation.rewireParameterReferences(parameterMap);
        }
    }

    public void addParameterManipulation(IParameterManipulation parameterManipulation) {
        _manipulations.add(parameterManipulation);
    }

    public List getManipulations() {
        return _manipulations;
    }


    public boolean isParameterStructureChanged() {
        boolean changed = false;
        for (Iterator itr = _manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            if (manipulation.isParameterStructureChanged()){
                changed = true;
                break;
            }
        }
        return changed;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[multipleManipulation] : ");
        for (Iterator itr = _manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            if (manipulation instanceof ParameterManipulation) {
                ParameterManipulation parameterManipulation = (ParameterManipulation)manipulation;
                sb.append(parameterManipulation.getParameter().getName());
                if (parameterManipulation.getValueToSet() instanceof StrategyDefinitionParameter) {
                    sb.append("=").append(((StrategyDefinitionParameter)parameterManipulation.getValueToSet()).getName());                    
                } else {
                    sb.append("=").append(parameterManipulation.getValueToSet());
                }
                if (itr.hasNext()) {
                    sb.append(" : ");
                }
            }

        }
        return sb.toString();


    }



    private List _manipulations = new ArrayList();

    private ExperimentPlan _parent;
}
