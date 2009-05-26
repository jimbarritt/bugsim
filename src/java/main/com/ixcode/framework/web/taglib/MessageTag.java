/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.message.IMessageSource;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Prints out a message from the message source.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: MessageTag.java,v 1.3 2004/08/27 11:13:26 rdjbarri Exp $
 */
public class MessageTag extends BodyTagSupport {

    public MessageTag() {
    }

     public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        Locale locale = PageContextHandler.getLocale(pageContext);
        IMessageSource messageSource = PageContextHandler.getMessageSource(pageContext);
        String pattern = messageSource.getMessage(locale, _key);

        if (pattern == null) {
            pattern = _key;
        }

        String message = MessageFormat.format(pattern, _contextParameters.toArray());

        PageContextHandler.printOut(pageContext, message);

        return super.doEndTag();
    }

    /**
     * In older versions of the servlet api this method is not called if there is no body,ie :
     * <wf:message />
     * as opposed to
     *
     * <wf:message>
     * </wf:message>
     */
//    public int doAfterBody() throws JspException {
//        Locale locale = PageContextHandler.getLocale(pageContext);
//        IMessageSource messageSource = PageContextHandler.getMessageSource(pageContext);
//        String pattern = messageSource.getMessage(locale, _key);
//
//        if (pattern == null) {
//            pattern = _key;
//        }
//
//        String message = MessageFormat.format(pattern, _contextParameters.toArray());
//
//        PageContextHandler.printOut(pageContext, message);
//        return SKIP_BODY;
//    }

    public void setKey(String key) {
        _key = key;
    }

    void addContextParameter(Object contextParameter) {
        _contextParameters.add(contextParameter);
    }

    private List _contextParameters = new ArrayList();
    private String _key;

}
