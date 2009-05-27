/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Expects a value class which has a string constructor and a toString() method
 * which can be used to pass into the constructor for parsing.
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ToStringFormat.java,v 1.3 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class ToStringFormat implements IJavaBeanValueFormat {
    public ToStringFormat(Class valueKlass) {
        try {
            _valueKlass = valueKlass;
            _stringConstructor = _valueKlass.getConstructor(new Class[]{String.class});
        } catch (NoSuchMethodException e) {
//            if (log.isInfoEnabled()) {
//                log.debug("Constructing a ToStringFormat with class " + valueKlass.getName() + " which will be readonly - no string constructor - it will throw UnsupportedOperationException if you try a parse!");
//            }
        }
    }


    public String format(Object value) {
        return (value == null) ? "" : value.toString();
    }

    public Object parse(String value) throws JavaBeanParseException {
        validateConstructor();
        try {
            return _stringConstructor.newInstance(new Object[]{value});
        } catch (InstantiationException e) {
            throw new JavaBeanParseException("", "Could not instantiate class " + _valueKlass.getName() + " with a string ", value, e);
        } catch (IllegalAccessException e) {
            throw new JavaBeanParseException("", "Could not instantiate class " + _valueKlass.getName() + " with a string ", value, e);
        } catch (InvocationTargetException e) {
            throw new JavaBeanParseException("", "Could not instantiate class " + _valueKlass.getName() + " with a string ", value, e);
        }

    }

    private void validateConstructor() {
        if (_stringConstructor == null) {
            throw new UnsupportedOperationException("ToStringFormat.parse() is not supported for class " + _valueKlass.getName() + " because it does not have a string constructor.");
        }
    }


    private Class _valueKlass;
    private Constructor _stringConstructor;
    private static Log log = LogFactory.getLog(ToStringFormat.class);
}
