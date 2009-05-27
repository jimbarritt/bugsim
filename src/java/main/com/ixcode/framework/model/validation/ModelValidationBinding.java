/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the binding of a validation result to a particular model. These will be results
 * from the model level validation (i.e. when you call {@link com.ixcode.framework.model.IModelAdapter } validateModel
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ModelValidationBinding.java,v 1.1 2004/08/02 16:01:46 rdjbarri Exp $
 */
public class ModelValidationBinding {

    public ModelValidationBinding(String modelXPath, Object model) {
        _modelXPath = modelXPath;
        _model = model;
    }


    public String getModelXPath() {
        return _modelXPath;
    }

    public ValidationResult getModelLevelResult() {
        return _modelLevelResult;
    }

    public void setModelLevelResult(ValidationResult modelLevelResult) {
        _modelLevelResult = modelLevelResult;
        if (!modelLevelResult.isValid()) {
            _isValid = false;
        }
    }

    public boolean hasModelLevelResult() {
        return _modelLevelResult != null;
    }

    public void addPropertyValidationBinding(PropertyValidationBinding propertyValidationBinding) {
        _propertyValidationBindings.add(propertyValidationBinding);
        _propertyValidationBindingMap.put(propertyValidationBinding.getPropertyName(), propertyValidationBinding);
        if (!propertyValidationBinding.getValidationResult().isValid()) {
            _isValid = false;
            _hasInvalidProperties = true;
        }
    }

    public List getPropertyValidationBindings() {
        return _propertyValidationBindings;
    }

    public boolean isValid() {
        return _isValid;
    }

    public Object getModel() {
        return _model;
    }

    public boolean hasInvalidProperties() {
        return _hasInvalidProperties;
    }

    public boolean hasPropertyValidationBinding(String name) {
        return _propertyValidationBindingMap.containsKey(name);
    }

    public PropertyValidationBinding getPropertyValidationBinding(String name) {
        return (PropertyValidationBinding)_propertyValidationBindingMap.get(name);
    }

    private String _modelXPath;
    private ValidationResult _modelLevelResult;
    private List _propertyValidationBindings = new ArrayList();
    private Map _propertyValidationBindingMap = new HashMap();
    private boolean _isValid = true;
    private Object _model;
    private boolean _hasInvalidProperties = false;

}
