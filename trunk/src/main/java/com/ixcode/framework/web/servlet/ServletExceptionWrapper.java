/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.servlet;

import javax.servlet.ServletException;

/**
 * Needed because the standard {@link java.servlet.ServletException} doesnt
 * set the getCause() properly
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ServletExceptionWrapper.java,v 1.1 2004/04/07 11:04:24 rdjbarri Exp $
 */
public class ServletExceptionWrapper extends ServletException {

    public ServletExceptionWrapper() {
    }

    public ServletExceptionWrapper(String name) {
        super(name);
    }

    public ServletExceptionWrapper(String name, Throwable cause) {
        super(name, cause);
        _cause = cause;
    }

    public ServletExceptionWrapper(Throwable cause) {
        super(cause);
        _cause = cause;
    }

    public Throwable getCause() {
        return _cause;
    }

    private Throwable _cause;
}
