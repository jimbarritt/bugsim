/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Provides the basic dat formatting in short date form.
 *
 * 
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: SimpleDateFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class SimpleDateFormat implements IJavaBeanValueFormat {

    public SimpleDateFormat(Locale locale) {
        _locale = locale;
        _dateStyle = DateFormat.SHORT;
    }

    public SimpleDateFormat(int dateStyle, Locale locale) {
        _locale = locale;
        _dateStyle =dateStyle;
    }

    public String format(Object value) {
        return DateFormat.getDateInstance(_dateStyle, _locale).format(value);
    }

    public Object parse(String value) throws JavaBeanParseException {
        try {
            return DateFormat.getDateInstance(DateFormat.SHORT, _locale).parse(value);
        } catch (ParseException e) {
            throw new JavaBeanParseException("", "Could not parse date {0} ", value, e);
        }
    }


    private Locale _locale;
    private int _dateStyle;
}
