/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.lookup.LookupHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Basically spits out some javascript which deals with handling a lookup popup.
 *
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: LookupSourceTag.java,v 1.2 2004/09/02 08:23:12 rdjbarri Exp $
 */
public class LookupSourceTag extends TagSupport {

    public LookupSourceTag() {
    }

    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();

        // Note that the IE popup version doesnt quite work as yet !!
//        if (PageContextHandler.getBrowserType(pageContext.getRequest()) == BrowserType.IE) {
//            appendIELookupInvoker(sb);
//        } else {
//            appendGenericLookupInvoker(sb);
//        }
        appendGenericLookupInvoker(sb);



        PageContextHandler.printOut(pageContext, sb);

        LookupHandler.initialseLookupContext(((HttpServletRequest)pageContext.getRequest()).getSession());


        return SKIP_BODY;
    }

    /**
     * For IE Browser - gives a flashy popup window
     * @param sb
     */
    private void appendIELookupInvoker(StringBuffer sb) {
        sb.append("<download ID=\"oDownload\" style=\"behavior:url(#default#download)\" />");
        sb.append("<script language=\"javascript\"");
//        sb.append(" src=").append(((HttpServletRequest)pageContext.getRequest()).getContextPath());
//        sb.append("/shared/script/invoke_lookup_ie.js");
        sb.append(">\n");

        sb.append("function executeLookup(lookupId) {\n");
        sb.append("    document.body.style.cursor = \"progress\";\n");
        sb.append("    window.status='Downloading Lookup Results ...';\n");
        sb.append("    oDownload.startDownload(");
        appendLookupResultsUrl(sb);
        sb.append(", onDownloadDone)\n");
        sb.append("}\n");

        // Create the popup as a global variable for predictable results

        sb.append("function onDownloadDone(popupContent) {\n");
        sb.append("    var oPopup = window.createPopup();\n");
        sb.append("    var oPopBody = oPopup.document.body;\n");
        sb.append("    oPopup.document.write(popupContent);\n");

//        // Parameters of the show method are in the following order: x-coordinate,
//        // y-coordinate, width, height, and the element to which the x,y
//        // coordinates are relative. Note that this popup object is displayed
//        // relative to the body of the document.
        sb.append("    oPopup.show(15, 150, ").append(_lookupWidth).append(", ").append(_lookupHeight).append(", document.body);\n");
        sb.append("    document.body.style.cursor = \"auto\";\n");


        sb.append("    window.status='Ready';\n");
        sb.append("}\n");
        sb.append("</script>\n");

    }

    /**
     * For normal browser types
     * @param sb
     */
    private void appendGenericLookupInvoker(StringBuffer sb) {
        sb.append("<script language=\"javascript\">\n");
        sb.append("    function executeLookup(lookupId) {\n");
        sb.append("        window.open(");
        appendLookupResultsUrl(sb);
        sb.append(", \"lookupresults\", \"height=").append(_lookupHeight).append(",width=").append(_lookupWidth).append(",resizable\");\n");
        sb.append("    }\n");
        sb.append("</script>");
    }

    private void appendLookupResultsUrl(StringBuffer sb) {
        sb.append("\"").append(PageContextHandler.createContextUrl(pageContext, _handlerUrl));
        sb.append("?").append(LookupHandler.PARAM_LOOKUP_ID).append("=\" + lookupId");
        sb.append(" +  \"&").append(LookupHandler.PARAM_INVOKE_LOOKUP).append("=true\"");
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


    public void setHandlerUrl(String redirectUrl) {
        _handlerUrl = redirectUrl;
    }


    public void setLookupHeight(String lookupHeight) {
        _lookupHeight = lookupHeight;
    }

    public void setLookupWidth(String lookupWidth) {
        _lookupWidth = lookupWidth;
    }

    private String _lookupHeight = "400";
    private String _lookupWidth = "700";

    private String _handlerUrl;


}
