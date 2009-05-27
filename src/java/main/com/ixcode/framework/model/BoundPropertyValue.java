/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: BoundPropertyValue.java,v 1.4 2004/09/02 08:23:11 rdjbarri Exp $
 */
class BoundPropertyValue {

    public BoundPropertyValue(String propertyName, String propertyValue) {
        _propertyName = propertyName;
        _propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return _propertyName;
    }

    public String getPropertyValue() {
        return _propertyValue;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoundPropertyValue)) return false;

        final BoundPropertyValue boundPropertyValue = (BoundPropertyValue)o;

        if (!_propertyName.equals(boundPropertyValue._propertyName)) return false;
        if (!_propertyValue.equals(boundPropertyValue._propertyValue)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = _propertyName.hashCode();
        result = 29 * result + _propertyValue.hashCode();
        return result;
    }

    private String _propertyName;
    private String _propertyValue;
}
