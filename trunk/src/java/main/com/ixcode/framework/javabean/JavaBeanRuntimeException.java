/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: JavaBeanRuntimeException.java,v 1.2 2004/08/31 13:23:17 rdacomle Exp $
 */
public class JavaBeanRuntimeException extends RuntimeException {

    public JavaBeanRuntimeException(Throwable cause) {
        super(cause);
    }

    private static final String __SSRES__USER = "Runtime exception";

    private static final String EXCEPTION_ID = "2000-6";
}
