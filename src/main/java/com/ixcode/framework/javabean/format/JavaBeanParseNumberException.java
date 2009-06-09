/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import com.ixcode.framework.javabean.message.JavaBeanMessages_enGB;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: JavaBeanParseNumberException.java,v 1.2 2004/08/31 13:23:18 rdacomle Exp $
 */
public class JavaBeanParseNumberException extends JavaBeanParseException{
    public JavaBeanParseNumberException(String value) {
        super(JavaBeanFormatMessageKeys.KEY_PARSE_NUMBER_FAILURE, JavaBeanMessages_enGB.TEXT_PARSE_NUMBER_FAILURE, value);
    }

    private static final String __SSRES__USER = "Not used";

    private static final String EXCEPTION_ID = "2000-9";
}
