/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.info.ValueListBundle;
import com.ixcode.framework.model.info.ValueListElementInfo;
import com.ixcode.framework.model.info.ValueListInfo;
import com.ixcode.framework.web.taglib.IInputTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Iterator;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: SelectTagHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class SelectTagHtmlWriter extends HtmlWriterBase {

    public SelectTagHtmlWriter() {
        super(CascadingStyleSheetClasses.SELECT);
    }

    public int writeTagContent(StringBuffer sb, IInputTag inputTag, ModelBinding modelBinding, IModelAdapter modelAdapter) throws JspException {
        Object value = null;
        try {
            value = modelAdapter.getPropertyValue(modelBinding.getModel(), inputTag.getPropertyName());
        } catch (IOException e) {
            throw new JspException(e);
        }

        String bindingUrl = BindingUrlFormatter.createPropertyBindingUrl(modelBinding, inputTag.getPropertyName());
        String scriptUrl = BindingUrlFormatter.createPropertyScriptIdUrl(modelBinding, inputTag.getPropertyName());


        sb.append("<select ");
        sb.append(" name='").append(bindingUrl).append("'");
        sb.append(" id='").append(scriptUrl).append("'");

        appendStyle(sb, inputTag, modelAdapter, modelBinding);
        appendStyleClasses(sb, inputTag, modelAdapter, modelBinding, true);

        sb.append(">");



        ValueListInfo valueList = modelAdapter.getValueListInfo(modelBinding.getModel(), inputTag.getPropertyName());
        ValueListBundle vlBundle = modelAdapter.getValueListBundle(modelBinding.getModel(), inputTag.getPropertyName(), inputTag.getLocale());
        for (Iterator itr = valueList.getElements().iterator(); itr.hasNext();) {
            ValueListElementInfo element = (ValueListElementInfo)itr.next();
            sb.append("/n<option value='").append(element.getValue()).append("'");
            if (value != null && value.equals(element.getValue())) {
                sb.append(" selected");
            }
            sb.append(">");

            String optionText = (vlBundle == null) ? "" + element.getValue() : vlBundle.getLabel(element.getValue());
            sb.append(optionText);

            sb.append("</option>");
        }

        return TagSupport.SKIP_BODY;
    }
}
