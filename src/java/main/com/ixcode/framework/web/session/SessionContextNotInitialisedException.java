/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.session;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: SessionContextNotInitialisedException.java,v 1.2 2004/08/27 14:34:47 rdsward Exp $
 */
public class SessionContextNotInitialisedException extends Exception {

    public SessionContextNotInitialisedException(Class sessionContextClass) {
        super("No Session Context Initialised for context class " + sessionContextClass.getName());
    }

    private static final String __SSRES__USER = "Never seen";

    private static final String EXCEPTION_ID = "2000-3";
}
