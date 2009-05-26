/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: DebugContextTag.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class DebugContextTag extends TagSupport {

    public DebugContextTag() {
    }

    public int doStartTag() throws JspException {
        int scopeId = getScopeId(_scope);

        StringBuffer sb = new StringBuffer();
        outputStartTable(sb);
        for(Enumeration enum = pageContext.getAttributeNamesInScope(scopeId);enum.hasMoreElements();) {
            String name = (String)enum.nextElement();
            outputTableRow(sb, name, pageContext.getAttribute(name, scopeId));
        }

        outputEndTable(sb);

        try {
            pageContext.getOut().print(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }



    protected void outputStartTable(StringBuffer sb) throws JspException {
        sb.append("<table style='font-size=x-small'>\n");
    }


    protected void outputEndTable(StringBuffer sb) throws JspException{
        sb.append("</table>\n");
    }

    protected void outputTableRow(StringBuffer sb, String name, Object attribute) {

        sb.append("<tr>\n");
        sb.append("<td");
        sb.append(CELL_STYLE);
        sb.append(">");
        sb.append(name).append("</td>");
        sb.append("<td");
        sb.append(CELL_STYLE);
        sb.append(">");
        sb.append(attribute).append("</td>");
        sb.append("</tr>\n");
    }



    private int getScopeId(String scope) throws JspException {
        if (scope.equals("application")) {
            return PageContext.APPLICATION_SCOPE;
        } else if (scope.equals("session")) {
            return PageContext.SESSION_SCOPE;
        } else if (scope.equals("request")) {
            return PageContext.REQUEST_SCOPE;
        }  else if (scope.equals("page")) {
            return PageContext.PAGE_SCOPE;
        } else {
            throw new JspException("Could not identify scope '" + scope + "'");
        }

    }

    public String getScope() {
        return _scope;
    }

    public void setScope(String scope) {
        _scope = scope;
    }

    private String _scope;
    private static final String CELL_STYLE = " style='border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: #9900CC;'";
}
