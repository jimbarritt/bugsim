/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

import java.util.List;

/**
 * Description : Public interface to Parameter map so things cant get at the internals.
 * Created     : Feb 2, 2007 @ 10:56:43 AM by jim
 */
public interface ISourceParameterMap {
    
    void addParameter(Parameter parameter);

    Parameter getParameter(String name);

    Parameter getParameter(int index);

    List getParameterNames();

    ParameterMap getParentParameterMap();

    /**
     * Useful if you know theres only 1...
     *
     * @return
     */
    Parameter getFirstParameter();

    List getParameterReferences();
}
