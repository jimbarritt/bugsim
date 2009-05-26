/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.info.ModelBundle;

import javax.servlet.jsp.JspException;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ModelBundleTagBase.java,v 1.3 2004/09/17 10:58:05 rdjbarri Exp $
 */
public abstract class ModelBundleTagBase extends BoundTagBase {

    protected ModelBundleTagBase() {
    }


    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        ModelBundle modelBundle = modelAdapter.getModelBundle(binding.getModelType(), getLocale());
        writeTagContent(sb, binding, modelBundle);
        return SKIP_BODY;
    }

    protected abstract void writeTagContent(StringBuffer sb, ModelBinding binding, ModelBundle modelBundle);


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

    private String _style;
    private String _styleClass;
    private String _alignment;
}
