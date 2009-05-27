/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Used when processing the request - it is a map of properties based on the xpath of the models to which they belong
 * 
 * @author Jim Barritt
 * @version $Revision: 1.6 $
 *          $Id: PropertyBindingMap.java,v 1.6 2004/09/02 08:23:11 rdjbarri Exp $
 */
class PropertyBindingMap {


    public PropertyBindingMap(String formContext) {
        _formContext = formContext;
    }

    public void addBoundPropertyValue(String modelXPath, String propertyName, String propertyValue) {
        if (!_propertyMap.containsKey(modelXPath)) {
            _modelXPaths.add(modelXPath);
            _propertyMap.put(modelXPath, new ArrayList());
        }
        List boundValues = (List)_propertyMap.get(modelXPath);
        BoundPropertyValue boundValue = new BoundPropertyValue(propertyName,  propertyValue);
        boundValues.add(boundValue);
        _boundValueIndex.put(modelXPath + ":"+ propertyName, boundValue);
    }

    public boolean hasBoundValue(String modelXPath, String propertyName) {
        return _boundValueIndex.containsKey(modelXPath + ":" + propertyName);
    }

    public List getModelXPaths() {
        return _modelXPaths;
    }

    public String getFormContext() {
        return _formContext;
    }

    /**
     *
     * @param modelXPath
     * @return a list of {@link com.ixcode.framework.model.BoundPropertyValue} objects.
     * @throws JspException
     */
    public List getBoundPropertyValues(String modelXPath) throws ServletException {
        if (!_propertyMap.containsKey(modelXPath)) {
            throw new ServletException("Could not find a list of bound property values for modelXPath '" + modelXPath + "'");
        }
        return (List)_propertyMap.get(modelXPath);
    }



    private List _modelXPaths = new ArrayList();
    private Map _propertyMap = new HashMap();
    private Map _boundValueIndex = new HashMap();
    private String _formContext;

}
