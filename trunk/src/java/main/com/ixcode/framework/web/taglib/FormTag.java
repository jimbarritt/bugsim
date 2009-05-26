/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Does a HTML <form> element for us so that we can find our form later.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: FormTag.java,v 1.2 2004/08/30 11:29:40 rdjbarri Exp $
 */
public class FormTag extends BodyTagSupport {


    /**
     * @todo consider making the referrer URL a single parameter ? trouble is it conflicts with the security one (SecurityRequestAttributeKeys.ATTR_REFRRAL_URL) which can also be set as an attribute which is why they are currently all explicit.
     *
     */
    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();
        sb.append("<form ");
        PageContextHandler.appendParam(sb, "id", _id);
//        PageContextHandler.appendParam(sb, "name", _id);
        PageContextHandler.appendParam(sb, "action", PageContextHandler.createContextUrl(pageContext, _action));
        PageContextHandler.appendParam(sb, "method", _method);

        sb.append(">\n");

        HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        String referrerUrl = response.encodeURL(request.getRequestURI());


        PageContextHandler.appendHiddenInput(sb, RequestHandler.PARAM_SESSION_ID, pageContext.getSession().getId());
        PageContextHandler.appendHiddenInput(sb, RequestHandler.PARAM_EXPIRED_SESSION_REFERRER, referrerUrl);
        PageContextHandler.appendHiddenInput(sb, RequestHandler.PARAM_FORM_SUBMISSION_TOKEN, "" + PageContextHandler.getNextFormSubmissionId(pageContext, _id));
        PageContextHandler.appendHiddenInput(sb, RequestHandler.PARAM_FORM_EXPIRED_REFERRER, referrerUrl);
        PageContextHandler.appendHiddenInput(sb, RequestHandler.PARAM_FORM_ID, _id);


        PageContextHandler.printOut(pageContext, sb);
        return EVAL_BODY_INCLUDE;
    }


    public int doEndTag() throws JspException {
        PageContextHandler.printOut(pageContext, "</form>\n");
        return super.doEndTag();
    }


    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }


    public void setAction(String action) {
        _action = action;
    }

    public void setMethod(String method) {
        _method = method;
    }



    private String _id;
    private String _action;
    private String _method = "POST";
    
}
