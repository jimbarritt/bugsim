/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.info.PropertyBundle;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.7 $
 *          $Id: PropertyBundleTagBase.java,v 1.7 2004/09/17 10:58:05 rdjbarri Exp $
 */
public abstract class PropertyBundleTagBase extends BoundTagBase {
    protected PropertyBundleTagBase() {
    }

    /**
     * This tag has 2 modes - one where we are bound to a model and one where there is no model, but
     * the developer knows the model type of the model but has no instances.
     */
    public int doStartTag() throws JspException {
        resolvePropertyHeaderInfo();
        if ((_modelType == null) && (_modelClass == null)) {
            super.doStartTag();
        } else {
            writePropertyFromModelType();
        }


        return SKIP_BODY;
    }

    /**
     * See if we cant get the _modelClass and _modelType from a parent tag
     */
    private void resolvePropertyHeaderInfo() {
        PropertyHeaderTag parent = (PropertyHeaderTag)TagSupport.findAncestorWithClass(this, PropertyHeaderTag.class);
        if (parent != null) {
            _modelClass = (_modelClass == null) ? parent.getModelClass() : _modelClass;
            _modelType = (_modelType == null) ? parent.getModelType() : _modelType;
        }
    }

    /**
     * It assumes that if you havent made them different then you dont deal with "soft" types
     * and so you can just set the class name.
     */
    private void writePropertyFromModelType() throws JspException {
        if (_modelType == null) {
            _modelType = _modelClass.getName();
        }

        StringBuffer sb = new StringBuffer();
        Class modelClass = _modelClass;
        IModelAdapter modelAdapter = PageContextHandler.getModelAdapter(pageContext, modelClass);
        PropertyBundle propertyBundle = modelAdapter.getPropertyBundle(_modelType, getPropertyName(), getLocale());
        writeTagContent(sb, propertyBundle);
        PageContextHandler.printOut(pageContext, sb);
    }



    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        PropertyBundle propertyBundle = modelAdapter.getPropertyBundle(binding.getModelType(), getPropertyName(), getLocale());
        writeTagContent(sb, propertyBundle);
        return SKIP_BODY;

    }

    public void setName(String name) {
        super.setProperty(name);
    }


    public void setModelClassName(String name) throws JspException {
        _modelClass = PageContextHandler.loadClass(name);
    }

    public void setModelClass(Class klass) {
        _modelClass = klass;
    }

    protected abstract void writeTagContent(StringBuffer sb, PropertyBundle propertyBundle);

    public String getStyle() {
        return _style;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    protected  void appendDefaultAttributes(StringBuffer sb) {
        if (_style != null) {
            sb.append(" style='").append(_style).append("'");
        }

        if (_styleClass != null) {
            sb.append(" class='").append(_styleClass).append("'");
        }

    }

    /**
     * Override this if you want to do something spexial @see TableCellInputTag.
     *
     * @return
     */
    public String calculateAlignment() {
        return _alignment;
    }

    public String getAlignment() {
        return _alignment;
    }

    public void setAlignment(String alignment) {
        _alignment = alignment;
    }

    private String _modelType;

    private Class _modelClass;
    private String _style;
    private String _styleClass;
    private String _alignment;
}
