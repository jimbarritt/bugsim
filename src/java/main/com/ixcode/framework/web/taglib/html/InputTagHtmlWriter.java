/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.web.taglib.IInputTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * this class containsCoord all the methods needed to write out <input /> tags, check boxes and text areas
 * so that the functionality can be shared amonsgt tags.
 * <p/>
 * For example our <wf:input /> tag needs to be able to write out a control of any type based on the type of the property.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: InputTagHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class InputTagHtmlWriter extends TagSupport {

    public static final IHtmlWriter SELECT_TAG_WRITER = new SelectTagHtmlWriter();
    public static final IHtmlWriter CHECKBOX_TAG_WRITER = new CheckBoxTagHtmlWriter();
    public static final IHtmlWriter TEXTBOX_TAG_WRITER = new TextBoxTagHtmlWriter();
    public static final IHtmlWriter TEXTAREA_TAG_WRITER = new TextAreaTagHtmlWriter();


    public static int writeSelectTagContent(IModelAdapter modelAdapter, ModelBinding binding, StringBuffer sb, IInputTag inputTag) throws JspException {
        return SELECT_TAG_WRITER.writeTagContent(sb, inputTag, binding, modelAdapter);
    }

    public static int writeCheckBoxContent(IModelAdapter modelAdapter, ModelBinding binding, StringBuffer sb, IInputTag inputTag) throws JspException {
        return CHECKBOX_TAG_WRITER.writeTagContent(sb, inputTag, binding, modelAdapter);
    }

    public static int writeTextBoxContent(ModelBinding modelBinding, IModelAdapter modelAdapter, StringBuffer sb, IInputTag inputTag) throws JspException {
        return TEXTBOX_TAG_WRITER.writeTagContent(sb, inputTag, modelBinding, modelAdapter);
    }

    public static int writeTextAreaTagContent(StringBuffer sb, ModelBinding modelBinding, IModelAdapter modelAdapter, IInputTag inputTag) throws JspException {
        return TEXTAREA_TAG_WRITER.writeTagContent(sb, inputTag, modelBinding, modelAdapter);
    }

}
