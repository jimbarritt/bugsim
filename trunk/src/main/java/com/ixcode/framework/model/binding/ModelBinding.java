/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.binding;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
  * @version $Revision: 1.1 $
 *          $Id: ModelBinding.java,v 1.1 2004/08/11 12:08:23 rdjbarri Exp $
 */
public class ModelBinding {

    public ModelBinding(String aliasName, String formContext, String xpath, Object model, String modelType) {
        _aliasName = aliasName;
        _model = model;
        _xpath = xpath;
        _formContext= formContext;
        _modelType = modelType;
    }

    public String getAliasName() {
        return _aliasName;
    }

    public Object getModel() {
        return _model;
    }

    public String getXpath() {
        return _xpath;
    }

    public String getFormContext() {
        return _formContext;
    }

    public String getModelType() {
        return _modelType;
    }

    private String _aliasName;
    private String _xpath;
    private Object _model;
    private String _formContext;
    private String _modelType;
}
