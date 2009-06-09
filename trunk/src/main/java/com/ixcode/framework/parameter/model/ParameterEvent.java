/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.Stack;

/**
 *  Description : Base class for generic parameter events
 *  Created     : Feb 1, 2007 @ 10:41:46 AM by jim
 */
public class ParameterEvent {
    public ParameterEvent(Parameter source, Stack parameterPath) {
        _source = source;
        _parameterPath = new Stack();
        _parameterPath.addAll(parameterPath);
        _parameterPath.push(source);
    }

    public Parameter getSource() {
        return _source;
    }

    public Stack getParameterPath() {
        return _parameterPath;
    }

    public String getEventPath() {
        StringBuffer sb = new StringBuffer();
        for (int i=_parameterPath.size()-1;i>=0;--i) {
            Parameter p = (Parameter)_parameterPath.get(i);
            sb.append(p.getName());
            if (i >0) {
                sb.append(".");
            }
        }
        return sb.toString();

    }

    public String toString() {
        return getEventPath();
    }

    public boolean pathContains(Parameter parameter) {

        return _parameterPath.contains(parameter);
    }

    private Parameter _source;
    private Stack _parameterPath = new Stack();
}
