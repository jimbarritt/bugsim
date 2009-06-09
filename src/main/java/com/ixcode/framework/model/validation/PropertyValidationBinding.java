/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.validation;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: PropertyValidationBinding.java,v 1.1 2004/08/02 16:01:46 rdjbarri Exp $
 */
public class PropertyValidationBinding {

    public PropertyValidationBinding(String propertyName, Object invalidValue, ValidationResult validationResult) {
        _propertyName = propertyName;
        _invalidValue = invalidValue;
        _validationResult = validationResult;
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public Object getInvalidValue() {
        return _invalidValue;
    }

    public ValidationResult getValidationResult() {
        return _validationResult;
    }


    private String _propertyName;
    private Object _invalidValue;
    private ValidationResult _validationResult;
}
