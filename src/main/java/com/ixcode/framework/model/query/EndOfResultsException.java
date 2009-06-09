/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.query;

/**
 * This exception happens when the underlying data has changed so that
 * you are no longer on the server page you thought you were.
 *
 * We represent it as an exception to allow the application to choose what to do -
 * most common would be to simply start back at the beginning.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: EndOfResultsException.java,v 1.2 2004/08/31 13:23:17 rdacomle Exp $
 */
public class EndOfResultsException extends Exception {

    public EndOfResultsException() {
        super(__SSRES__USER);
    }
    public EndOfResultsException(Throwable cause) {
        super(__SSRES__USER, cause);
    }

    private static final String __SSRES__USER = "The paged query requested a start index beyond the maximim number of results";

    private static final String EXCEPTION_ID = "2000-10";
}
