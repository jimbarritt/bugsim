/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.parameter.model;

/**
 * Description : Provides a base class for implementing an object model to wrap the generic catagories
 */
public abstract class CategoryDefinition extends StrategyDefinitionContainerBase {
    public CategoryDefinition(Category category, ParameterMap params, boolean forwardEvents) {
        super(params);
        _category = category;

        _forwardEvents = forwardEvents;

    }

    public Category getCategory() {
        return _category;
    }

    public Parameter getParameter(String name) {
        return getCategory().findParameter(name);
    }

    public Category getSubCategory(String name) {
        return _category.findSubCategory(name);
    }



    public void setParameterValue(String name, boolean value) {
        setParameterValue(name, new Boolean(value));
    }
    public void setParameterValue(String name, long value) {
        setParameterValue(name, new Long(value));
    }
    public void setParameterValue(String name, Object value) {
        Parameter p = getParameter(name);
        p.setValue(value);
    }

    public Object getParameterValue(String parameterName) {
        return getParameter(parameterName).getValue();
    }

    public boolean isForwardEvents() {
        return _forwardEvents;
    }

    public String toString() {
        return getContainerName();
    }

    private Category _category;

    private boolean _forwardEvents;
}
