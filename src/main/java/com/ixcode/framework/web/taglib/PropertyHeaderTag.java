/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Allows you to define a property header section without having to reference
 * the class name for each of your propertyLabel children.
 *
 * All it really does is act as a parent for the property bundle tags so they dont have
 * to be configured themselves.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: PropertyHeaderTag.java,v 1.2 2004/08/30 11:29:41 rdjbarri Exp $
 */
public class PropertyHeaderTag extends TagSupport {

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public Class getModelClass() {
        return _modelKlass;
    }

    public void setModelClass(Class modelClass) {
        _modelKlass = modelClass;
    }



    public void setModelClassName(String modelClassName) throws JspException {
        _modelKlass = PageContextHandler.loadClass(modelClassName);
    }

    public String getModelType() {
        return _modelType;
    }

    public void setModelType(String modelType) {
        _modelType = modelType;
    }

    private Class _modelKlass;
    private String _modelType;
}
