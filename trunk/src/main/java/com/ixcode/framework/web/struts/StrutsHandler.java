/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 * $Id: StrutsHandler.java,v 1.1 2004/08/27 11:13:33 rdjbarri Exp $
 */
public class StrutsHandler {
    private StrutsHandler() {
    }

    /**
     * Struts returns null if the forward name is not found so this wraps it to make sure we get an exception.
     */
    public static ActionForward findForward(ActionMapping mapping, String name) throws ServletException {
        ActionForward forward = mapping.findForward(name);
        if (forward == null) {
            if (log.isDebugEnabled()) {
                log.debug("<findForward> : Could not find a forward with the name '" + name + "'");
            }
            throw new ServletException("Could not find the action mapping with name '" + name + "' in struts config ActionMappings");
        }

        if (log.isDebugEnabled()) {
            log.debug("<findForward> : forward name = '" + name + "', returning a forward url of '" + forward.getPath() + "'");
        }
        return forward;
    }



    private static Log log = LogFactory.getLog(StrutsHandler.class);


}
