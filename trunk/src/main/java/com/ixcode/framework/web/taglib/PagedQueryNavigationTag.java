/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.model.query.PagedQueryEvents;
import com.ixcode.framework.model.query.PagedQueryHandler;
import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.Iterator;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: PagedQueryNavigationTag.java,v 1.5 2004/09/07 16:10:57 rdjbarri Exp $
 */
public class PagedQueryNavigationTag extends BodyTagSupport {

    /**
     * This class gets put on all the elements so that you can style them.
     */
    public static final String STYLE_CLASS = "pagedQuery";

    /**
     * agin to change this in the stylesheet you do input.disabled {}
     */
    public static final String DISABLED_CLASS = "disabled";

    public PagedQueryNavigationTag() {
    }

  

    public int doEndTag() throws JspException {
                StringBuffer sb = new StringBuffer();

        appendJavaScriptController(sb);

        IPagedQuery query = RequestHandler.getPagedQuery(pageContext.getRequest());

        sb.append("<table class='").append(STYLE_CLASS).append("' >");
        PageContextHandler.appendln(sb, "");
        PageContextHandler.appendln(sb, 1, "<tr>");

        if (query.hasPreviousServerPage()) {
            PageContextHandler.appendln(sb, 2, "<td class=\""+ STYLE_CLASS + "\" ><input type=\"button\" class=\""+ STYLE_CLASS + "\" onclick=\"submitPagingCommand('" +  PagedQueryEvents.FIRST_SERVER_PAGE + "')\" value=\""+ _firstLabel + "\" /></td>");
            if (!query.isForwardOnly()) {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><input type=\"button\" class=\"" + STYLE_CLASS + "\" onclick=\"submitPagingCommand('" +  PagedQueryEvents.PREVIOUS_SERVER_PAGE + "')\" value=\""+ _previousLabel + "\" /></td>");
            }
        } else {
            PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><span class=\"" + DISABLED_CLASS + "\">" + _firstLabel + "</span></td>");
            if (!query.isForwardOnly()) {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><span class=\"" + DISABLED_CLASS + "\">" + _previousLabel + "</span></td>");
            }
        }

        for (Iterator itr = query.getDisplayPageNumbers().iterator(); itr.hasNext();) {
            Integer pageNumber = (Integer)itr.next();
            if (pageNumber.intValue() == query.getCurrentDisplayPageNumber()) {
                PageContextHandler.appendln(sb, 2, "<td  class=\"" + STYLE_CLASS + "\" ><span class=\"" + DISABLED_CLASS + "\">" +  pageNumber + "</span></td>");
            } else {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><input type=\"button\" class=\"" + STYLE_CLASS + "\" style=\"width:30px;\" onclick=\"setDisplayPage('" + pageNumber + "');submitPagingCommand('" + PagedQueryEvents.GOTO_DISPLAY_PAGE + "');\" value=\"" + pageNumber + "\" /></td>");
            }
        }

        if (query.getDisplayPageCount() < query.getDisplayPagesPerServerPage()) {
            int extras = query.getDisplayPagesPerServerPage() - query.getDisplayPageCount();
            for (int i=0;i<extras;++i) {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\" >&nbsp;</td>");
            }
        }

        if (query.hasNextServerPage()) {
            PageContextHandler.appendln(sb, 2, "<td class=\""+ STYLE_CLASS + "\" ><input type=\"button\" class=\""+ STYLE_CLASS + "\" onclick=\"submitPagingCommand('" +  PagedQueryEvents.NEXT_SERVER_PAGE + "')\" value=\""+ _nextLabel + "\" /></td>");
            if (query.supportsRowCount()) {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><input type=\"button\" class=\"" + STYLE_CLASS + "\" onclick=\"submitPagingCommand('" +  PagedQueryEvents.LAST_SERVER_PAGE + "')\" value=\""+ _lastLabel + "\" /></td>");
            }
        } else {
            PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><span class=\"" + DISABLED_CLASS + "\">" + _nextLabel + "</span></td>");
            if (query.supportsRowCount()) {
                PageContextHandler.appendln(sb, 2, "<td class=\"" + STYLE_CLASS + "\"><span class=\"" + DISABLED_CLASS + "\">" + _lastLabel + "</span></td>");
            }
        }

        if (_includeCancelButton) {
            PageContextHandler.appendln(sb, 2, "<td style=\"width:auto;text-align:right;\"><input type=\"submit\" class=\""+ STYLE_CLASS + "\" value=\""+ _cancelLabel + "\" name=\"cancel\" /></td>");
        }

        PageContextHandler.appendln(sb, 1, "</tr>");
        PageContextHandler.appendln(sb, "</table>");
        
        PageContextHandler.appendln(sb, "<input type=\"hidden\" name=\"" + PagedQueryHandler.PARAM_PAGING_COMMAND  + "\" value=\"\" />");
        PageContextHandler.appendln(sb, "<input type=\"hidden\" name=\"" + PagedQueryHandler.PARAM_DISPLAY_PAGE + "\" value=\"\" />");


        PageContextHandler.printOut(pageContext, sb);
        return SKIP_BODY;
    }

    private void appendJavaScriptController(StringBuffer sb) throws JspException {
        String formId = PageContextHandler.getFormId(this);

        String formReference = "document.forms['" +  formId + "']";

        PageContextHandler.appendln(sb, "<script language=\"javascript\">");
        PageContextHandler.appendln(sb, "    function submitPagingCommand(command) {");

        sb.append("    ").append(formReference);
        PageContextHandler.appendln(sb, "['" + PagedQueryHandler.PARAM_PAGING_COMMAND +"'].value=command;");

        sb.append("    ").append(formReference);
        PageContextHandler.appendln(sb, ".submit();");

        PageContextHandler.appendln(sb, "}");
        PageContextHandler.appendln(sb, "function setDisplayPage(pageNumber) {");

        sb.append("    ").append(formReference);
        PageContextHandler.appendln(sb, "['" + PagedQueryHandler.PARAM_DISPLAY_PAGE +"'].value=pageNumber;");

        PageContextHandler.appendln(sb, "}");
        PageContextHandler.appendln(sb, "</script>");
    }



    public void setCancelLabel(String cancelLabel) {
        _cancelLabel = cancelLabel;
    }

    public void setFirstLabel(String firstLabel) {
        _firstLabel = firstLabel;
    }



    public void setIncludeCancelButton(String includeCancelButton) {
        _includeCancelButton = Boolean.valueOf(includeCancelButton).booleanValue();
    }

    public void setLastLabel(String lastLabel) {
        _lastLabel = lastLabel;
    }

    public void setNextLabel(String nextLabel) {
        _nextLabel = nextLabel;
    }

    public void setPreviousLabel(String previousLabel) {
        _previousLabel = previousLabel;
    }



    private String _firstLabel = "<<";
    private String _previousLabel = "<";

    private String _nextLabel = ">";
    private String _lastLabel = ">>";

    private String _cancelLabel = "Cancel";
    private boolean _includeCancelButton;
}
