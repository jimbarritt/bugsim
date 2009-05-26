/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.web.taglib.IInputTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: CheckBoxTagHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class CheckBoxTagHtmlWriter extends HtmlWriterBase {

    public CheckBoxTagHtmlWriter() {
        super(CascadingStyleSheetClasses.CHECKBOX);
    }

    public int writeTagContent(StringBuffer sb, IInputTag inputTag, ModelBinding modelBinding, IModelAdapter modelAdapter) throws JspException {
        Object value = null;
        try {
            value = modelAdapter.getPropertyValue(modelBinding.getModel(), inputTag.getPropertyName());
        } catch (IOException e) {
            throw new JspException(e);
        }


        if (!IntrospectionUtils.isBoolean(value.getClass())) {
            throw new JspException("Tried to use a check box with a property that was not a boolen, you used type '" + value.getClass().getName() + "'");
        }

        String bindingUrl = BindingUrlFormatter.createPropertyBindingUrl(modelBinding, inputTag.getPropertyName());
        String scriptUrl = BindingUrlFormatter.createPropertyScriptIdUrl(modelBinding, inputTag.getPropertyName());


        sb.append("<input type='checkbox'");
        sb.append(" name='").append(bindingUrl).append("'");
        sb.append(" id='").append(scriptUrl).append("'");

        boolean boolValue = ((Boolean)value).booleanValue();
        if (boolValue) {
            sb.append(" checked");
        }

        sb.append(" value='true'");

        appendStyle(sb, inputTag, modelAdapter, modelBinding);
        appendStyleClasses(sb, inputTag, modelAdapter, modelBinding, true);
        sb.append("/>");

        sb.append("<input type='hidden' name='").append(BindingUrlFormatter.getCheckBoxMarkerId()).append("_").append(bindingUrl).append("'");
        sb.append(" value='").append(bindingUrl).append("'");
        sb.append("/>");



        return TagSupport.SKIP_BODY;
    }
}
