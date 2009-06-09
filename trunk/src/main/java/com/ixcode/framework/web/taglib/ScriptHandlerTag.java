/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * If you want all your JScript to get output nicely then wrap all your tags with this one.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ScriptHandlerTag.java,v 1.1 2004/08/11 12:08:22 rdjbarri Exp $
 */
public class ScriptHandlerTag extends BodyTagSupport {

    public int doAfterBody() throws JspException {
        try {
            getBodyContent().write("<h1>Here is where all my lovelly JScript is going to go -->");
            getBodyContent().writeOut(getPreviousOut());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

}
