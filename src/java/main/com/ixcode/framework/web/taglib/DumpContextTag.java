/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Dumps everything in the context into your page to help you see what is going on.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: DumpContextTag.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class DumpContextTag extends TagSupport {

    public DumpContextTag() {
    }

    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();
        try {
            if (_scope != null) {
                int scopeId = _contextInstrument.getScopeId(_scope);
                _contextInstrument.dumpContext(sb, pageContext, scopeId);
            } else {
                _contextInstrument.dumpAllContexts(sb, pageContext);
            }


            pageContext.getOut().print(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }

    public String getScope() {
        return _scope;
    }

    public void setScope(String scope) {
        _scope = scope;
    }

    private String _scope;
    ContextInstrument _contextInstrument = new ContextInstrument();
}
