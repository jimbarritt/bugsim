/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import java.math.BigDecimal;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: BigDecimalFormat.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class BigDecimalFormat extends SimpleNumberFormat {

    public Object parse(String value) throws JavaBeanParseException {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new JavaBeanParseNumberException(value);
        }
    }
}
