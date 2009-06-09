/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

/**
 * This class defines the keys that we will use for any messages.
 *
 * It is the responsibility of the IMessageSource that is given to the JavaBeanModelAdapter to
 * resolve these keys into messages for us.
 *
 * The default is to use the message.JavaMessageSourceBase
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: JavaBeanFormatMessageKeys.java,v 1.1 2004/08/27 10:27:24 rdjbarri Exp $
 */
public class JavaBeanFormatMessageKeys {

    public static final String PREFIX = "com.ixcode.framework.javabean.format";
    public static final String KEY_PARSE_FAILURE = PREFIX + "PARSE_FAILURE";
    public static final String KEY_PARSE_NUMBER_FAILURE = PREFIX + "PARSE_NUMBER_FAILURE";

    private JavaBeanFormatMessageKeys() {
    }



}
