/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Put this tag inside a <forEachProperty> tag and it will filter out any properties
 * that have already been used.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: IgnoreHiddenPropertiesTag.java,v 1.2 2004/09/17 10:58:05 rdjbarri Exp $
 * @respect-to Alan Stafford
 */
public class IgnoreHiddenPropertiesTag extends BoundTagBase {

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        ForEachPropertyTag parent = (ForEachPropertyTag)TagSupport.findAncestorWithClass(this, ForEachPropertyTag.class);

        if (parent == null) {
            throw new JspException("IgnoreUsedPropertiesTag is not nested inside a ForEachPropertyTag - it must be in order to work.");
        }

        int tagResult = EVAL_BODY_INCLUDE;
        if (modelAdapter.isPropertyHidden(binding.getModel(), getPropertyName())) {
            return SKIP_BODY;
        }

        return tagResult;
    }


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
