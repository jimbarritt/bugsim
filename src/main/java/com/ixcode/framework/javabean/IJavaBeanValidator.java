/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

import com.ixcode.framework.model.validation.ValidationResult;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: IJavaBeanValidator.java,v 1.1 2004/08/11 12:08:26 rdjbarri Exp $
 */
public interface IJavaBeanValidator {

    /**
     * Validates a potential value for a property.
     */
    public ValidationResult validateProperty(String propertyName, Object model, Object valueToValidate);

    /**
     * Validates the integrity of an entire model.
     */
    public ValidationResult validateModel(Object model);
}
