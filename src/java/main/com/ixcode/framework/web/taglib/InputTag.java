/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.web.taglib.html.IHtmlWriter;
import com.ixcode.framework.web.taglib.html.TextBoxTagHtmlWriter;
import com.ixcode.framework.web.taglib.html.WriterCache;

import javax.servlet.jsp.JspException;

/**
 * Writes a property based on your meta data definition
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: InputTag.java,v 1.1 2004/09/17 10:58:05 rdjbarri Exp $
 */
public class InputTag extends InputTagBase {

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        Class htmlWriterClass = modelAdapter.getPropertyHtmlWriter(binding.getModel(), getPropertyName());
        if (htmlWriterClass == null) {
            htmlWriterClass = TextBoxTagHtmlWriter.class;
        }
        IHtmlWriter writer = WriterCache.getInstance().getWriter(htmlWriterClass);
        return writer.writeTagContent(sb, this, binding, modelAdapter);
    }
}
