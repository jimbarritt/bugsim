/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.9 $
 *          $Id: PropertyInfo.java,v 1.9 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class PropertyInfo implements IPropertyInfo {

    public PropertyInfo(String name) {
        _name = name;
    }


    public boolean isLookup() {
        return _lookupInfo != null;
    }

    public LookupInfo getLookupInfo() {
        return _lookupInfo;
    }

    public void setLookupInfo(LookupInfo lookupInfo) {
        _lookupInfo = lookupInfo;
    }

    /**
     * We dont specify what this should be but this provides you a way to pass
     * custom formatting for a particular property around.
     *
     * It will be the implementation of {@link com.ixcode.framework.model.IModelAdapter IModelAdapter } which uses this
     */
    public String getFormatName() {
        return _formatName;
    }

    public void setFormatName(String name) {
        _formatName = name;
    }

    public String getFormatPattern() {
        return _formatPattern;
    }

    public void setFormatPattern(String formatPattern) {
        _formatPattern = formatPattern;
    }


    public boolean isMandatory(Object model) {
        return _mandatory;
    }

    public void setMandatory(boolean mandatory) {
        _mandatory = mandatory;
    }

    public boolean isReadonly(Object model) {
        return _readonly;
    }

    public void setReadonly(boolean readonly) {
        _readonly = readonly;
    }

    public ValueListInfo getValueListInfo() {
        return _valueListInfo;
    }

    public void setValueListInfo(ValueListInfo valueListInfo) {
        _valueListInfo = valueListInfo;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public boolean hasValueListInfo() {
        return _valueListInfo != ValueListInfo.NULL_LIST;
    }

    public void setHidden(boolean hidden) {
        _hidden = hidden;
    }

    public boolean isHidden(Object model) {
        return _hidden;
    }

    public String getStyleClasses() {
        return _styleClasses;
    }

    public String getStyle(Object model) {
        return _style;
    }

    public void setNumeric(boolean numeric) {
        _isNumeric = numeric;
    }

    public void setStyleClasses(String styleClasses) {
        _styleClasses = styleClasses;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public boolean isNumeric() {
        return _isNumeric;
    }

    public Class getHtmlWriter() {
        return _htmlWriter;
    }

    public void setHtmlWriter(Class htmlWriter) {
        _htmlWriter = htmlWriter;
    }

    private boolean _readonly;
    private boolean _mandatory;
    private boolean _isNumeric = false;
    private ValueListInfo _valueListInfo = ValueListInfo.NULL_LIST;
    private String _name;
    private LookupInfo _lookupInfo;


    private String _formatName;
    private String _formatPattern;
    private boolean _hidden;
    private String _styleClasses;
    private String _style;
    private Class _htmlWriter;
}
