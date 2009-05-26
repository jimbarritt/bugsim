/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.web.taglib.html.InputTagHtmlWriter;

import javax.servlet.jsp.JspException;

/**
 * Specialises the Input tag. Note we had to make some methods protected to allow the behaviour to be
 * overriden - there might be a neater way to do this but this works, so ...
 *
 * Note that you cannot include your own content in the text area.
 *
 * To do this you need to create a special tag or just hard code it in the page.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: TextAreaTag.java,v 1.2 2004/09/17 10:58:05 rdjbarri Exp $
 * @respect.to Alan Stafford
 */
public class TextAreaTag extends InputTagBase {

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        return InputTagHtmlWriter.writeTextAreaTagContent(sb, binding, modelAdapter, this);
    }





}
