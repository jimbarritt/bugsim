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
 *          $Id: BooleanFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class BooleanFormat implements IJavaBeanValueFormat {

    public String format(Object value) {
        return value.toString();
    }

    public Object parse(String value) throws JavaBeanParseException {
        return Boolean.valueOf(value);
    }
}
