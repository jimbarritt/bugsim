/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.web.taglib.IInputTag;

import javax.servlet.jsp.JspException;

/**
 * This interface is implemented by all the different writers.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: IHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public interface IHtmlWriter {

    int writeTagContent(StringBuffer sb, IInputTag inputTag, ModelBinding modelBinding, IModelAdapter modelAdapter) throws JspException;
}
