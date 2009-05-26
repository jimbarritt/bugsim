/**
 * (c) planet-ix ltd 2007
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : Common interface for all objects in the the Hierarchy to enable us to deal with them generically
 *
 * Created     : Mar 4, 2007 @ 2:55:10 PM by jim
 */
public interface IParameterModel {

    public boolean hasParent();
    public IParameterModel getParent();

    String getName();

    String getFullyQualifiedName();

    public IParameterModel findParentCalled(String name);
    
}
