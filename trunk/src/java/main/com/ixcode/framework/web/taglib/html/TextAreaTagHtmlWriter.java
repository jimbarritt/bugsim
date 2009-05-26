/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.web.taglib.IInputTag;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: TextAreaTagHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class TextAreaTagHtmlWriter extends TextBoxTagHtmlWriter {

     protected void openInputHtmlTag(StringBuffer sb, IInputTag inputTag) {
        sb.append("<textarea ");
    }

    protected void closeInputHtmlTag(StringBuffer sb, String value) {
        sb.append("/>").append(value).append("</textarea>");
    }
}
