/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ValueListElementInfo.java,v 1.3 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class ValueListElementInfo {

    public ValueListElementInfo(Object value) {
        _value = value;
    }

    public Object getValue() {
        return _value;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueListElementInfo)) return false;

        final ValueListElementInfo valueListElement = (ValueListElementInfo)o;

        if (!_value.equals(valueListElement._value)) return false;

        return true;
    }

    public int hashCode() {
        return _value.hashCode();
    }

    public String toString() {
        return "" + _value;
    }

    private Object _value;
}
