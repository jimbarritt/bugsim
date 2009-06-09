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
 * Base class for the writers to give us some useful features.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: HtmlWriterBase.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public abstract class HtmlWriterBase implements IHtmlWriter {

    /**
     * @param tagStyleClass The style class to give this tag - i.e. textbox, textArea, checkbox etc. to allow stylesheets to customise particular inputs.
     */
    protected HtmlWriterBase(String tagStyleClass) {
        _tagStyleClass = tagStyleClass;
    }

    protected void appendStyle(StringBuffer sb, IInputTag inputTag, IModelAdapter modelAdapter, ModelBinding modelBinding) throws JspException {
        String alignment = inputTag.getAlignment();
        String adapterStyle = modelAdapter.getPropertyStyle(modelBinding.getModel(), inputTag.getPropertyName());

        if (adapterStyle != null || alignment != null || inputTag.getStyle() != null) {
            sb.append(" style='");
            if (alignment != null) {
                sb.append("text-align:").append(alignment.toString()).append(";");
            }

            if (inputTag.getStyle() != null) {
                sb.append(" ").append(inputTag.getStyle());
            }

            if (adapterStyle != null) {
                sb.append(" ").append(adapterStyle);
            }
            sb.append("'");
        }
    }

    protected void appendStyleClasses(StringBuffer sb, IInputTag inputTag, IModelAdapter modelAdapter, ModelBinding binding, boolean isValid) throws JspException {
        sb.append(" class='");
        if (inputTag.getTagClass() != null) {
            sb.append(inputTag.getTagClass());
        }

        sb.append(" ").append(_tagStyleClass);

        if (modelAdapter.isPropertyMandatory(binding.getModel(), inputTag.getPropertyName())) {
            sb.append(" mandatory");
        }


        if (modelAdapter.isPropertyNumeric(binding.getModel(), inputTag.getPropertyName())) {
            sb.append(" numeric");
        }

        appendReadOnlyAttribute(modelAdapter, binding, sb, inputTag);

        inputTag.appendExtraClasses(sb);

        String customClasses = modelAdapter.getPropertyStyleClasses(binding.getModel(), inputTag.getPropertyName());
        if (customClasses != null) {
            sb.append(" ").append(customClasses);
        }

        if (!isValid) {
            sb.append(" invalid");
        }

        sb.append("'");


    }

    protected void appendReadOnlyAttribute(IModelAdapter modelAdapter, ModelBinding binding, StringBuffer sb, IInputTag inputTag) throws JspException {
        if (inputTag.isReadOnly() || modelAdapter.isPropertyReadOnly(binding.getModel(), inputTag.getPropertyName())) {
            sb.append(" readonly");
        }
    }

    private String _tagStyleClass;

}
