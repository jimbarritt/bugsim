/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Declares an XPath reference to an object which is in the request at a known location.
 * <p/>
 * The location must be owned by the domain taglib because we dont want it to depend on anything else.
 *
 * @author Jim Barritt
 * @respect.to Alan Stafford
 * @version $Revision: 1.2 $
 *          $Id: UseModelTag.java,v 1.2 2004/08/31 02:25:01 rdjbarri Exp $
 */
public class UseModelTag extends BodyTagSupport {

    public int doStartTag() throws JspException {
        PageContextHandler.addModelBinding(pageContext, _formContext, _alias, _path);

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


    public String getAlias() {
        return _alias;
    }

    /**
     * @param alias The name of the domain to alias in the root domain
     */
    public void setAlias(String alias) {
        _alias = alias;
    }

    public String getPath() {
        return _path;
    }

    /**
     * @param path The path to the domain to alias in the root domain object graph
     */
    public void setPath(String path) {
        if (path == null || path.length() == 0) {
            _path = "/";
        } else {
            _path = path;
        }
    }

    public String getFormContext() {
        return _formContext;
    }

    public void setFormContext(String formContext) {
        _formContext = formContext;
    }

    private String _formContext;
    private String _alias;
    private String _path;


}
