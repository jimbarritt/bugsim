/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import java.util.Locale;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ValueFormatKey.java,v 1.1 2004/08/27 10:27:25 rdjbarri Exp $
 */
class ValueFormatKey {

    public ValueFormatKey(Locale locale, Class valueKlass) {
        _locale = locale;
        _valueKlassName = valueKlass.getName();
    }

    public Locale getLocale() {
        return _locale;
    }

    public String getValueKlassName() {
        return _valueKlassName;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ValueFormatKey)) return false;

        final ValueFormatKey valueFormatKey = (ValueFormatKey)o;

        if (!_locale.equals(valueFormatKey._locale)) return false;
        if (!_valueKlassName.equals(valueFormatKey._valueKlassName)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = _locale.hashCode();
        result = 29 * result + _valueKlassName.hashCode();
        return result;
    }

    private Locale _locale;
    private String _valueKlassName;

}
