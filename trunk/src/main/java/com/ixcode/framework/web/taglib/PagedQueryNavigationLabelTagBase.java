/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Used so we can have sub tags in the pagedWuryNavigation tag
 * to set up what we want displayed for the previous and next buttons.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: PagedQueryNavigationLabelTagBase.java,v 1.1 2004/08/30 11:29:40 rdjbarri Exp $
 */
public abstract class PagedQueryNavigationLabelTagBase extends BodyTagSupport {

    public int doStartTag() throws JspException {
        _parent = (PagedQueryNavigationTag)TagSupport.findAncestorWithClass(this, PagedQueryNavigationTag.class);
        if (_parent == null) {
            throw new IllegalStateException("The PagedQuery Labels must live inside a PagedQueryNaviagtionTag");
        }

        return super.doStartTag();
    }

    public int doAfterBody() throws JspException {
        if (getBodyContent() == null) {
            throw new IllegalStateException("No body content found for PagedQueryNaviagtionLabel : " + this.getClass().getName());
        }
        String content = getBodyContent().getString();
        getBodyContent().clearBody();
        updateLabel(_parent, content);
        return SKIP_BODY;
    }

    protected abstract void updateLabel(PagedQueryNavigationTag parent, String labelText);

    public void release() {
        _parent = null;
        super.release();
    }
    PagedQueryNavigationTag _parent;
}
