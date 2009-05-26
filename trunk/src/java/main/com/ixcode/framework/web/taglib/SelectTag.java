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
 * The problem with check boxes is that if you dont check them they dont appear in the request!!
 * so their value is allways "true"
 * 
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: SelectTag.java,v 1.5 2004/09/17 10:58:05 rdjbarri Exp $
 */
public class SelectTag extends InputTagBase {

    public SelectTag() {
    }

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        return InputTagHtmlWriter.writeSelectTagContent(modelAdapter, binding, sb, this);
    }


}
