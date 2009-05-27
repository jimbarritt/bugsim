/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: JavaBeanException.java,v 1.1 2004/08/11 12:08:26 rdjbarri Exp $
 */
public class JavaBeanException extends Exception {


    public JavaBeanException(Throwable cause) {
        super(cause);
    }

    public JavaBeanException(String message, Throwable cause) {
        super(message + " : " + cause.getMessage() , cause);
    }



    private static final String EXCEPTION_ID = "2000-2";
    private static final String __SSRES__USER = "JavaBean exception";
}
