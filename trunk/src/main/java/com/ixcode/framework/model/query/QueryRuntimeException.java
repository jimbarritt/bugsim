/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

/**
 * Rather than throwing IllegalStateException, this one allows us to
 * package up the cause.
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: QueryRuntimeException.java,v 1.3 2004/09/02 08:23:13 rdjbarri Exp $
 */
public class QueryRuntimeException extends RuntimeException {

    public QueryRuntimeException(String message) {
        super(message);
    }

    /**
     * Try to allways pass a useful message - this exception should
     * only be used if it is genuinely not possible for the exception to happen
     * other than from a coding error.
     */
    public QueryRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public QueryRuntimeException(Throwable cause) {
        super(cause);
    }


    private static final String __SSRES__USER = "Query runtime exception";

    private static final String EXCEPTION_ID = "2000-11";
}
