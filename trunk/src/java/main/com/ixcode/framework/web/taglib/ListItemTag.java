/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Basically spits out some javascript which deals with handling a lookup popup.
 *
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: ListItemTag.java,v 1.1 2004/08/11 12:08:21 rdjbarri Exp $
 */
public class ListItemTag extends BodyTagSupport {

    public ListItemTag() {
    }

    public int doStartTag() throws JspException {
        ForEachModelTag parent = (ForEachModelTag)TagSupport.findAncestorWithClass(this, ForEachModelTag.class);
        boolean isFirstItem = false;
        int iList = 0;
        int iListItem = 0;
        if (parent != null) {
            isFirstItem = parent.isFirstModel();
            iListItem = parent.getXPathIndex()-1;
        }


        String modelBindingId = PageContextHandler.getModelXPath(pageContext, _model, this);
        StringBuffer sb = new StringBuffer();

        sb.append("<script language='javascript'>\n");

        if (isFirstItem) {
            sb.append("    listItemIds[").append(iList).append("] = new Array();\n");
            sb.append("    selectedItemIndex[").append(iList).append("] = 0;\n");
        }

        sb.append("    listItemIds[").append(iList).append("][").append(iListItem).append("] = '").append(modelBindingId).append("';\n");
        sb.append("</script>\n");

        sb.append("<").append(_htmlTag);
        sb.append(" id='").append(modelBindingId).append("'");

        String style = (isFirstItem) ? _selectedStyleClass : _styleClass;
        sb.append(" class='").append(style).append("'");
        sb.append(" onclick='submitItem(this.id)'");
//").append(iList).append(",


        // do some hacky IE stuff to make the items highlight ...
        if (PageContextHandler.getBrowserType(pageContext.getRequest()) == BrowserType.IE) {
            sb.append(" onmouseover=\"hiliteItem(").append(iList).append(", this, '").append(_hiliteStyleClass).append("', '").append(_selectedStyleClass).append("')\"");
            sb.append(" onmouseout=\"unhiliteItem(").append(iList).append(", this,'").append(_styleClass).append("', '").append(_selectedStyleClass).append("')\"");
        }

        sb.append(">\n");

        PageContextHandler.printOut(pageContext, sb);

        return EVAL_BODY_INCLUDE;
    }



    public int doEndTag() throws JspException {
        StringBuffer sb = new StringBuffer();
        sb.append("</").append(_htmlTag).append(">\n");
        PageContextHandler.printOut(pageContext, sb);
        return EVAL_PAGE;
    }


    public void setHtmlTag(String htmlTag) {
        _htmlTag = htmlTag;
    }

    public void setModel(String model) {
        _model = model;
    }

    public void setHiliteClass(String hiliteStyleClass) {
        _hiliteStyleClass = hiliteStyleClass;
    }

    public void setClass(String styleClass) {
        _styleClass = styleClass;
    }

    public void setSelectedStyleClass(String selectedStyleClass) {
        _selectedStyleClass = selectedStyleClass;
    }


    private String _model;
    private String _htmlTag = "div";


    private String _styleClass = "listItem";
    private String _hiliteStyleClass = "hilitedListItem";
    private String _selectedStyleClass= "selectedListItem";

}
