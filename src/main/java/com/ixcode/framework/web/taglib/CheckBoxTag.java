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
 * @version $Revision: 1.4 $
 *          $Id: CheckBoxTag.java,v 1.4 2004/09/17 13:35:58 rdjbarri Exp $
 */
public class CheckBoxTag extends InputTagBase {

    public CheckBoxTag() {
    }

    protected int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        return InputTagHtmlWriter.writeCheckBoxContent(modelAdapter, binding, sb, this);
    }

    public String calculateAlignment() {
        return null;
    }


}
