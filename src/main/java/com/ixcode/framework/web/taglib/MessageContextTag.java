/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This tag sits inside the message tag and adds a context object to it.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: MessageContextTag.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class MessageContextTag extends BodyTagSupport {

    public MessageContextTag() {
    }

    public int doStartTag() throws JspException {
        _parent= (MessageTag)TagSupport.findAncestorWithClass(this, MessageTag.class);
        if (_parent == null) {
            throw new JspException("An instance of the MessageContextTag was not placed nested inside a MessageTag - it must be inside a message Tag");
        }
        return super.doStartTag();
    }

    public int doAfterBody() throws JspException {

        String value = bodyContent.getString();
        bodyContent.clearBody();
        _parent.addContextParameter(value);
        return SKIP_BODY;
    }

    public void release() {
        _parent = null;
        super.release();
    }

    MessageTag _parent;
}
