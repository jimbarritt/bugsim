/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: LongFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class LongFormat extends SimpleNumberFormat {

    public Object parse(String value) throws JavaBeanParseException {
        try {
            return Long.decode(value);
        } catch (NumberFormatException e) {
            throw new JavaBeanParseNumberException(value);
        }
    }
}
