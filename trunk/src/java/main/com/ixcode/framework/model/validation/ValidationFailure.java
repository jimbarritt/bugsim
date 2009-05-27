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
 *          $Id: ValidationFailure.java,v 1.1 2004/08/02 16:01:46 rdjbarri Exp $
 */
public class ValidationFailure {

    public ValidationFailure(String message) {
        _message = message;
    }

    public String getMessage() {
        return _message;
    }

    public String toString() {
        return _message;
    }

    private String _message;

}
