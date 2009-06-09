/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.validation;

import java.util.*;

/**
 * Binds the ValdiationResults to models and properties.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ValidationBindingMap.java,v 1.1 2004/08/02 16:01:46 rdjbarri Exp $
 */
public class ValidationBindingMap {


    public ValidationBindingMap(String formContext) {
        _formContext = formContext;
    }

    public void bindValidationResult(String modelXPath, Object model, String propertyName, Object propertyValue, ValidationResult propertyValidationResult) {
        ModelValidationBinding modelBinding = (ModelValidationBinding)_validationBindings.get(modelXPath);
        if  (modelBinding == null) {
            modelBinding = new ModelValidationBinding(modelXPath, model);
            _validationBindings.put(modelXPath, modelBinding);
            _orderedValidationBindings.add(modelBinding);
        }

        PropertyValidationBinding propertyBinding = new PropertyValidationBinding(propertyName, propertyValue, propertyValidationResult);
        modelBinding.addPropertyValidationBinding(propertyBinding);

    }



    public ModelValidationBinding getModelValidationBinding(String modelXPath) {
        return (ModelValidationBinding)_validationBindings.get(modelXPath);
    }

    public List getModelValidationBindings() {
        return _orderedValidationBindings;
    }

    public boolean isValid() {
        boolean isValid = true;
        for (Iterator itr = _orderedValidationBindings.iterator(); itr.hasNext();) {
            ModelValidationBinding binding = (ModelValidationBinding)itr.next();
            if (!binding.isValid()) {
                isValid = false;
            }
        }
        return isValid;
    }

    public String getFormContext() {
        return _formContext;
    }

    private Map _validationBindings = new HashMap();
    private List _orderedValidationBindings = new ArrayList();
    private String _formContext;

}
