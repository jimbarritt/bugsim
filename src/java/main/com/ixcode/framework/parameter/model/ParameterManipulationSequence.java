/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * Description : ${CLASS_DESCRIPTION}
 * Created     : Feb 8, 2007 @ 3:57:26 PM by jim
 */
public class ParameterManipulationSequence {

    public ParameterManipulationSequence(ParameterMap parameterMap, List manipulations) {
        _parameterMap = parameterMap;
        _manipulations = manipulations;
        refreshSummaries();
    }

    public void refreshSummaries() {
        _parameters = new ArrayList();
        initParameterSummary(_manipulations);
        initSequenceSummary(_manipulations);
    }

    private void initParameterSummary(List manipulations) {
        StringBuffer sb = new StringBuffer();
        if (manipulations.size() > 1) {
            appendManipulationNames(sb, manipulations, " : ");
        } else if (manipulations.size() == 1) {
            IParameterManipulation manipulation = (IParameterManipulation)manipulations.get(0);
            if (manipulation instanceof MultipleParameterManipulation) {
                sb.append("{");
                appendManipulationNames(sb, ((MultipleParameterManipulation)manipulation).getManipulations(), " , ");
                sb.append("}");
            } else {
                appendManipulationName(manipulation, sb);
            }

        } else {
            sb.append("<empty>");
        }

        _parameterSummary = sb.toString();
    }


    private void appendManipulationNames(StringBuffer sb, List manipulations, String delimiter) {
        if (isSameParameter(manipulations)) {
            appendManipulationName((IParameterManipulation)manipulations.get(0), sb);
        } else {
            for (Iterator itr = manipulations.iterator(); itr.hasNext();) {
                IParameterManipulation manipulation = (IParameterManipulation)itr.next();
                appendManipulationName(manipulation, sb);
                if (itr.hasNext()) {
                    sb.append(delimiter);
                }
            }
        }
    }

    private void appendManipulationName(IParameterManipulation manipulation, StringBuffer sb) {

        if (manipulation instanceof MultipleParameterManipulation) {
            sb.append("{");
            appendManipulationNames(sb, ((MultipleParameterManipulation)manipulation).getManipulations(), ", ");
            sb.append("}");
        } else {
            Parameter manipulatedP = ((ParameterManipulation)manipulation).getParameter();
            _parameters.add(manipulatedP);
            sb.append(manipulatedP.getName());
        }
    }

    private boolean isSameParameter(List manipulations) {
        boolean isSameParameter = true;
        StringBuffer firstId = new StringBuffer();
        appendId(firstId, (IParameterManipulation)manipulations.get(0));
        String firstIdString = firstId.toString();
        for (Iterator itr = manipulations.iterator(); itr.hasNext();) {
              IParameterManipulation manip = (IParameterManipulation)itr.next();
            StringBuffer currId = new StringBuffer();
            appendId(currId, manip);
            if (!firstIdString.equals(currId.toString()))  {
                 isSameParameter = false;
                break;
            }
        }

        return isSameParameter;
    }

    private void appendId(StringBuffer id, IParameterManipulation m) {


        if (m instanceof MultipleParameterManipulation) {
            MultipleParameterManipulation mpm = (MultipleParameterManipulation)m;
            for (Iterator itr = mpm.getManipulations().iterator(); itr.hasNext();) {
                IParameterManipulation mchild = (IParameterManipulation)itr.next();
                appendId(id, mchild);
            }
        }   else {
            id.append(((ParameterManipulation)m).getParameter().getName());
        }

    }


    private void initSequenceSummary(List manipulations) {
        StringBuffer sb = new StringBuffer();
        for (Iterator itr = manipulations.iterator(); itr.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itr.next();
            if (manipulation instanceof MultipleParameterManipulation) {
                appendMultipleParameterSummary(sb, (MultipleParameterManipulation)manipulation);
            } else {
                appendManipulationValue(sb, manipulation);
            }
            if (itr.hasNext()) {
                sb.append(" : ");
            }
        }

        _summary = sb.toString();
    }

    private void appendManipulationValue(StringBuffer sb, IParameterManipulation manipulation) {
        Object value = ((ParameterManipulation)manipulation).getValueToSet();
        if (value instanceof Parameter) {
            value = ((Parameter)value).getName();
        }
        sb.append(value);
    }

    private void appendMultipleParameterSummary(StringBuffer sb, MultipleParameterManipulation mpm) {
        sb.append("{");


        for (Iterator itrChild = mpm.getManipulations().iterator(); itrChild.hasNext();) {
            IParameterManipulation manipulation = (IParameterManipulation)itrChild.next();
            if (manipulation instanceof MultipleParameterManipulation) {
                appendMultipleParameterSummary(sb, mpm);
            } else {
                appendManipulationValue(sb, manipulation);
            }
            if (itrChild.hasNext()) {
                sb.append(", ");
            }

        }

        sb.append("}");
    }

    public ParameterMap getParameterMap() {
        return _parameterMap;
    }

    public List getManipulations() {
        return _manipulations;
    }

    public List getParameters() {
        return _parameters;
    }

    public void addManipulation(IParameterManipulation manipulation) {

        _manipulations.add(manipulation);
    }
    public void addManipulation(int index, IParameterManipulation manipulation) {
        int insertPos = _manipulations.size();
        if (index > -1) {
            insertPos = index;
        }
        _manipulations.add(insertPos, manipulation);
    }

    public void replaceManipulation(IParameterManipulation oldManipulation, IParameterManipulation newManipulation) {
        throw new IllegalStateException("Not yet implementted replace manipulations");
    }

    public String getParameterSummary() {
        return _parameterSummary;
    }

    public String getSummary() {
        return _summary;
    }

    public IParameterManipulation removeManipulation(int manipulationIndex) {
        return (IParameterManipulation)_manipulations.remove(manipulationIndex);
    }

    public void removeManipulations(int[] maniplulationIndexes) {
        boolean addTemplateManipulation = (maniplulationIndexes.length == _manipulations.size());

        List toRemove = new ArrayList();
        for (int i=0;i<maniplulationIndexes.length;++i) {
            toRemove.add(_manipulations.get(maniplulationIndexes[i]));
        }
        _manipulations.removeAll(toRemove);

        if (addTemplateManipulation) {
            _manipulations.add(createTemplateManipulation());
        }
    }

    public IParameterManipulation createTemplateManipulation() {
        IParameterManipulation newManipulation = null;
        List parameters = getParameters();
        if (parameters.size() > 1) {
            MultipleParameterManipulation mp = new MultipleParameterManipulation();
            for (Iterator itr = parameters.iterator(); itr.hasNext();) {
                Parameter parameter = (Parameter)itr.next();
                mp.addParameterManipulation(new ParameterManipulation(parameter,  parameter.getValue()));
            }
            newManipulation = mp;
        } else if (parameters.size() >0) {
            Parameter manipulatedP = (Parameter)parameters.get(0);
            newManipulation = new ParameterManipulation(manipulatedP, manipulatedP.getValue());
        } else {
            throw new IllegalStateException("No Parameters!!");
        }
        return  newManipulation;
    }

    private ParameterMap _parameterMap;
    private List _manipulations;

    private String _parameterSummary;
    private List _parameters = new ArrayList();
    private String _summary;

    public static final String P_PARAMETER_SUMMARY = "parameterSummary";
    public static final String P_SUMMARY = "summary";
}
