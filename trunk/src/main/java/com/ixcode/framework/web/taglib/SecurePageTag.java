/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.security.ISecurityService;
import com.ixcode.framework.security.SecurityRequestAttributeKeys;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Stick this on top of any pages that need to be secured.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: SecurePageTag.java,v 1.1 2004/08/11 12:08:22 rdjbarri Exp $
 */
public class SecurePageTag extends TagSupport {

    public SecurePageTag() {
    }

    public int doEndTag() throws JspException {
        try {
            ISecurityService securityService = PageContextHandler.getSecurityService(pageContext);
            if (securityService.isUserAuthenticated(pageContext.getSession())) {
                return EVAL_PAGE;
            } else {
                String referrer = ((HttpServletRequest)pageContext.getRequest()).getRequestURI().toString();
                pageContext.getRequest().setAttribute(SecurityRequestAttributeKeys.ATTR_REFRRAL_URL, referrer);
                pageContext.forward(securityService.getLoginForwardPath());
                return SKIP_PAGE;
            }
        } catch (ServletException e) {
            throw new JspException(e);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public void setLoginUrl(String loginUrl) {
        _loginUrl = loginUrl;
    }

    private String _loginUrl;
}
