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
 * This is passed to the {@link InputTagHTMLWriter} to call back on to get come context.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: IInputTag.java,v 1.2 2004/09/17 13:35:58 rdjbarri Exp $
 */
public interface IInputTag {

    String getPropertyName() throws JspException;

    Locale getLocale();

    PageContext getPageContext();
    
    ServletRequest getRequest();

    boolean isHidden();

    boolean isPassword();

    Tag getTagInstance();

    String getTagClass();

    boolean isReadOnly();

    void appendExtraClasses(StringBuffer sb);

    String getMaxLength();

    String getSize();

    String getTabIndex();

    String getAlignment();

    String getStyle();


}
