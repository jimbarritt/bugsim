/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the result of some validation.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ValidationResult.java,v 1.1 2004/08/02 16:01:46 rdjbarri Exp $
 */
public class ValidationResult {

    public static final ValidationResult VALID_RESULT = new ValidationResult();

    public ValidationResult() {
        _isValid = true;
    }

    public ValidationResult(ValidationFailure failure) {
        _isValid = false;
        _validationFailures.add(failure);
    }
    public ValidationResult(boolean valid) {
        _isValid = valid;
    }

    public boolean isValid() {
        return _isValid;
    }

    public List getValidationFailures() {
        return _validationFailures;
    }

    public void addValidationFailure(ValidationFailure failure) {
        _validationFailures.add(failure);
    }



    private boolean _isValid;
    private List _validationFailures = new ArrayList();    



}
