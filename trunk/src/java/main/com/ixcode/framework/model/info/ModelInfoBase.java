/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import java.util.*;

/**
 * Provides a default implementation that you can use if you like to
 * create simple modelinfo's
 * 
 * @author Jim Barritt
 * @version $Revision: 1.11 $
 *          $Id: ModelInfoBase.java,v 1.11 2004/09/17 13:36:01 rdjbarri Exp $
 */
public abstract class ModelInfoBase implements IModelInfo {

    protected void addPropertyInfo(IPropertyInfo propertyInfo) {
        _properties.put(propertyInfo.getName(), propertyInfo);
    }


    public Class getPropertyHtmlWriter(Object model, String propertyName) {
        return getPropertyInfo(propertyName).getHtmlWriter();
    }

    public ValueListInfo getValueListInfo(Object model, String propertyName) {
        return getPropertyInfo(propertyName).getValueListInfo();
    }

    public boolean isPropertyLookup(Object model, String propertyName) {
        return getPropertyInfo(propertyName).isLookup();
    }

    public boolean isPropertyNumeric(Object model, String propertyName) {
        return getPropertyInfo(propertyName).isNumeric();
    }

    public String getPropertyStyleClasses(Object model, String propertyName) {
        return getPropertyInfo(propertyName).getStyleClasses();
    }

    public String getPropertyStyle(Object model, String propertyName) {
        return getPropertyInfo(propertyName).getStyle(model);
    }

    public LookupInfo getLookupInfo(Object model, String propertyName) {
        return getPropertyInfo(propertyName).getLookupInfo();
    }


    public boolean isPropertyMandatory(Object model, String propertyName) {
        return getPropertyInfo(propertyName).isMandatory(model);
    }

    public boolean isPropertyReadOnly(Object model, String propertyName) {
        return getPropertyInfo(propertyName).isReadonly(model);
    }

    public boolean isPropertyHidden(Object model, String propertyName) {
        return getPropertyInfo(propertyName).isHidden(model);
    }

    public boolean isPropertyValueList(Object model, String propertyName) {
        return getPropertyInfo(propertyName).hasValueListInfo();
    }

    public boolean hasPropertyInfo(String name) {
        return _properties.containsKey(name);
    }
    protected IPropertyInfo getPropertyInfo(String name) {
        if (!_properties.containsKey(name)) {
            return DEFAULT_INFO;
        }
        return (IPropertyInfo)_properties.get(name);
    }

    public List getPropertyNames(String modelType) {
        if (_propertyNames == null) {
            _propertyNames = new ArrayList(_properties.keySet());
        }
        return _propertyNames;
    }

    public List getPropertyNames() {
        return _propertyNames;
    }

    protected void setPropertyNames(List propertyNames) {
        _propertyNames = propertyNames;
    }

    public ModelBundle getModelBundle(String modelType, Locale locale) {
        return null;
    }

    public String getModelType(Object model) {
        return this.getClass().getName();
    }

    public PropertyBundle getPropertyBundle(String modelType, String propertyName, Locale locale) {
        return null;
    }

    public ValueListBundle getValueListBundle(Object model, String propertyName, Locale locale) {
        return null;
    }


    private static final IPropertyInfo DEFAULT_INFO = new PropertyInfo("default");
    private Map _properties = new HashMap();
    private List _propertyNames;
}
