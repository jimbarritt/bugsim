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
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: TextBoxTag.java,v 1.5 2004/09/17 10:58:05 rdjbarri Exp $
 * @respect.to Alan Stafford
 */
public class TextBoxTag extends InputTagBase {

    public TextBoxTag() {
    }

    protected int writeTagContent(StringBuffer sb, ModelBinding modelBinding, IModelAdapter modelAdapter) throws JspException {
        return InputTagHtmlWriter.writeTextBoxContent(modelBinding, modelAdapter, sb, this);
    }


}
