/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import java.text.DecimalFormat;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: DoubleFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class DoubleFormat extends SimpleNumberFormat {

    public String format(Object value) {
        if (value instanceof Double) {
            return FORMAT.format(((Double)value).doubleValue());
        } else {
            return super.format(value);
        }
    }

    public Object parse(String value) throws JavaBeanParseException {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new JavaBeanParseNumberException(value);
        }
    }

    private static final DecimalFormat FORMAT = new DecimalFormat("0.00000");
}
