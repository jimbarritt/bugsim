/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Provides an "instrument" for diagnosing problems with the context
 * <p/>
 * The context can be the Servlet, Request Session or Page contexts.
 * <p/>
 * Mostly this prints out information in HTML format about the requests.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ContextInstrument.java,v 1.2 2004/08/12 13:15:47 rdjbarri Exp $
 */
public class ContextInstrument {

    public ContextInstrument() {
    }

    public void dumpContext(StringBuffer sb, PageContext pageContext, int scopeId) throws IOException {
        switch (scopeId) {
            case PageContext.APPLICATION_SCOPE:
                dumpContext(sb, pageContext.getServletContext());
                break;
            case PageContext.SESSION_SCOPE:
                dumpContext(sb, pageContext.getSession());
                break;
            case PageContext.REQUEST_SCOPE:
                dumpContext(sb, pageContext.getRequest());
                break;
            case PageContext.PAGE_SCOPE:
                dumpContext(sb, pageContext);
                break;
            default:
                throw new IOException("Unrecognised scope id" + scopeId);
        }
    }

    public void dumpAllContexts(StringBuffer sb, HttpServletRequest request) {
        dumpContext(sb, request.getSession().getServletContext());
        dumpContext(sb, request.getSession());
        dumpContext(sb, request);
    }

    public void dumpAllContexts(StringBuffer sb, PageContext pageContext) {
        dumpAllContexts(sb, (HttpServletRequest)pageContext.getRequest());
        dumpContext(sb, pageContext);
    }

    public void dumpContext(StringBuffer sb, ServletRequest request) {
        outputStartTable(sb, "Request Attributes:");
        for (Enumeration enumeration = request.getAttributeNames(); enumeration.hasMoreElements();) {
            String name = (String)enumeration.nextElement();
            outputTableRow(sb, name, request.getAttribute(name));
        }
        outputEndTable(sb);
        sb.append("&nbsp");
        outputStartTable(sb, "Request Parameters :");
        for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String name = (String)enumeration.nextElement();
            outputTableRow(sb, name, request.getParameter(name));
        }
        outputEndTable(sb);
    }

    public void dumpContext(StringBuffer sb, ServletContext servletContext) {
        outputStartTable(sb, "ServletContext Attributes:");
        for (Enumeration enumeration = servletContext.getAttributeNames(); enumeration.hasMoreElements();) {
            String name = (String)enumeration.nextElement();
            outputTableRow(sb, name, servletContext.getAttribute(name));
        }
        outputEndTable(sb);
        sb.append("&nbsp");
    }

    public void dumpContext(StringBuffer sb, HttpSession session) {
        outputStartTable(sb, "Session Attributes:");
        for (Enumeration enumeration = session.getAttributeNames(); enumeration.hasMoreElements();) {
            String name = (String)enumeration.nextElement();
            outputTableRow(sb, name, session.getAttribute(name));
        }
        outputEndTable(sb);
        sb.append("&nbsp");
    }

    public void dumpContext(StringBuffer sb, PageContext pageContext) {
        outputStartTable(sb, "Page Attributes:");
        for (Enumeration enumeration = pageContext.getAttributeNamesInScope(PageContext.PAGE_SCOPE); enumeration.hasMoreElements();) {
            String name = (String)enumeration.nextElement();
            outputTableRow(sb, name, pageContext.getAttribute(name));
        }
        outputEndTable(sb);
        sb.append("&nbsp");
    }

    public int getScopeId(String scope) throws JspException {
        if (scope.equals("application")) {
            return PageContext.APPLICATION_SCOPE;
        } else if (scope.equals("session")) {
            return PageContext.SESSION_SCOPE;
        } else if (scope.equals("request")) {
            return PageContext.REQUEST_SCOPE;
        } else if (scope.equals("page")) {
            return PageContext.PAGE_SCOPE;
        } else {
            throw new JspException("Could not identify scope '" + scope + "'");
        }

    }


    protected void outputStartTable(StringBuffer sb, String title) {
        sb.append("<div style='width:100%'><h2>").append(title).append("</h2></div>");
        sb.append("<table style='font-size=x-small; width:100%;'>");
    }


    protected void outputEndTable(StringBuffer sb) {
        sb.append("</table>");
    }

    protected void outputTableRow(StringBuffer sb, String name, Object attribute) {
        sb.append("<tr>");
        sb.append("<td style='").append(CELL_STYLE).append("'>");
        sb.append(name);
        sb.append("</td>");

        sb.append("<td style='").append(CELL_STYLE).append("'>");
        sb.append(attribute);
        sb.append("</td>");
        sb.append("\n");
    }


    private static final String CELL_STYLE = "border-bottom-width: 1px;border-bottom-style: solid;border-bottom-color: #9900CC;";

}
