/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : ${INTERFACE_DESCRIPTION}
 * Created     : Jan 28, 2007 @ 11:47:47 PM by jim
 */
public interface IParameterContainerListener {

    void parameterReplaced(IParameterContainer source, Parameter oldP, Parameter newP);

}
