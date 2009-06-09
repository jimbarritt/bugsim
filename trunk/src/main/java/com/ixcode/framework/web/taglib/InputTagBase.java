/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import java.util.Locale;

/**
 * Base Class for any input tags. which are anything that creates bound parameters in the form.
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: InputTagBase.java,v 1.4 2004/09/17 13:35:58 rdjbarri Exp $
 */
public abstract class InputTagBase extends BoundTagBase implements IInputTag {

    public int doEndTag() throws JspException {
        PageContextHandler.registerPropertyAsUsed(getPageContext(), getModelBinding(), getPropertyName());
        return super.doEndTag();
    }

    public void setTagClass(String tagClass) {
        _tagClass = tagClass;
    }

    public Locale getLocale() {
        return super.getLocale();
    }

    public String getPropertyName() throws JspException {
        return super.getPropertyName();
    }

    public String getTabIndex() {
        return _tabIndex;
    }

    public PageContext getPageContext() {
        return super.getPageContext();
    }

    public String getTagClass() {
        return _tagClass;
    }

    public ServletRequest getRequest() {
        return pageContext.getRequest();
    }

    public Tag getTagInstance() {
        return this;
    }

    public boolean isHidden() {
        return _hidden;
    }

    public boolean isPassword() {
        return _password;
    }

    /**
     * Override this if you want to append any extra Style classes.
     */
    public void appendExtraClasses(StringBuffer sb) {

    }


    public void setTabIndex(String tabIndex) {
        _tabIndex = tabIndex;
    }







    public String getMaxLength() {
        return _maxLength;
    }

    public void setMaxLength(String maxLength) {
        _maxLength = maxLength;
    }

    public String getSize() {
        return _size;
    }

    public void setSize(String size) {
        _size = size;
    }

    public boolean isReadOnly() {
        return _readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        _readOnly = readOnly;
    }

    public void setPassword(boolean password) {
        _password = password;
    }

    public void setHidden(boolean hidden) {
        _hidden = hidden;
    }

    public String getStyle() {
        return _style;
    }

    public void setStyle(String style) {
        _style = style;
    }

    public void setStyleClass(String styleClass) {
        _styleClass = styleClass;
    }

    protected  void appendDefaultAttributes(StringBuffer sb) {
        if (_style != null) {
            sb.append(" style='").append(_style).append("'");
        }

        if (_styleClass != null) {
            sb.append(" class='").append(_styleClass).append("'");
        }

    }


    public String getAlignment() {
        return _alignment;
    }

    public void setAlignment(String alignment) {
        _alignment = alignment;
    }


    private String _tagClass;

    private String _tabIndex;

    protected String _maxLength = "50";
    protected String _size;
    protected boolean _readOnly;
    private boolean _password;
    private boolean _hidden;
    private String _style;
    private String _styleClass;
    private String _alignment;
}
