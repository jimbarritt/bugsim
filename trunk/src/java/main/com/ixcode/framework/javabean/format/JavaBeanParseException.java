/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import java.text.MessageFormat;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: JavaBeanParseException.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class JavaBeanParseException extends Exception {

    public JavaBeanParseException(String validationMessageKey, String defaultMessage, String value, Throwable cause) {
        super(createDefaultMessage(defaultMessage, value), cause);
        _validationMessageKey = validationMessageKey;
    }

    public JavaBeanParseException(String validationMessageKey, String defaultMessage, String value) {
        super(createDefaultMessage(defaultMessage, value));
        _validationMessageKey = validationMessageKey;
    }

    private static String createDefaultMessage(String defaultMessage, String value) {
        return MessageFormat.format(defaultMessage, new Object[] {value});
    }

    public String getMessageKey() {
        return _validationMessageKey;
    }

    private static final String EXCEPTION_ID = "2000-4";
    private static final String __SSRES__USER = "Not used";

    private  String _validationMessageKey;
}
