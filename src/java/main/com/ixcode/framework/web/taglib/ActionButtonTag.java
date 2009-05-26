/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.message.IMessageSource;
import com.ixcode.framework.web.action.ActionEvent;
import com.ixcode.framework.web.action.ActionInfoBinding;
import com.ixcode.framework.web.action.ActionModel;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Renders a button on the page, you can give it the action event id and a display name.
 *
 * Gives it a class of "action-button"
 *
 * Maybe at some point we can hook this into some kind of action model so that you can enable / disable actions ?
 *
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ActionButtonTag.java,v 1.2 2004/08/31 02:25:00 rdjbarri Exp $
 */
public class ActionButtonTag extends TagSupport {

    public ActionButtonTag() {
    }

    public int doStartTag() throws JspException {
        StringBuffer sb = new StringBuffer();

        ActionModel actionModel = PageContextHandler.getActionModel(pageContext);

        ActionInfoBinding actionBinding = actionModel.getActionInfoBinding(_actionEvent);

        if (actionBinding.isVisible()) {
            sb.append("<input type='submit'");
            sb.append(" class='actionButton ");
            if (!actionBinding.isEnabled()) {
                sb.append(" disabled");
            }
            sb.append("'");

            sb.append(" name='").append(_actionEvent.getEventId()).append("'");

            IMessageSource messageSource = PageContextHandler.getMessageSource(pageContext);
            String displayName = messageSource.getMessage(PageContextHandler.getLocale(pageContext), _actionEvent.getMessageKey());
            displayName = (displayName == null) ? _actionEvent.getMessageKey() : displayName; 
            sb.append(" value='").append(displayName).append("'");
            sb.append("/>");
        }

        PageContextHandler.printOut(pageContext, sb);
        return SKIP_BODY;
    }

    public void setActionEvent(ActionEvent actionEvent) {
        _actionEvent = actionEvent;
    }

    public void release() {
        _actionEvent = null;
        super.release();
    }

    private ActionEvent _actionEvent;
}
