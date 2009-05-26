/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib.html;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.info.LookupInfo;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.model.lookup.Lookup;
import com.ixcode.framework.model.lookup.LookupContext;
import com.ixcode.framework.model.lookup.LookupHandler;
import com.ixcode.framework.model.validation.ModelValidationBinding;
import com.ixcode.framework.model.validation.PropertyValidationBinding;
import com.ixcode.framework.model.validation.ValidationBindingMap;
import com.ixcode.framework.web.request.RequestHandler;
import com.ixcode.framework.web.taglib.IInputTag;
import com.ixcode.framework.web.taglib.PageContextHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: TextBoxTagHtmlWriter.java,v 1.2 2004/09/17 13:35:59 rdjbarri Exp $
 */
public class TextBoxTagHtmlWriter extends HtmlWriterBase {

    public TextBoxTagHtmlWriter() {
        super(CascadingStyleSheetClasses.TEXTBOX);
    }

    public int writeTagContent(StringBuffer sb, IInputTag inputTag, ModelBinding modelBinding, IModelAdapter modelAdapter) throws JspException {
        boolean isValid = true;
        Object invalidValue = null;
        if (RequestHandler.hasValidationBindingMap(inputTag.getRequest(), modelBinding.getFormContext())) {
            ValidationBindingMap validationMap = RequestHandler.getValidationBindingMap(inputTag.getRequest(), modelBinding.getFormContext());
            ModelValidationBinding validationBinding = validationMap.getModelValidationBinding(modelBinding.getXpath());

            if (validationBinding != null && validationBinding.hasPropertyValidationBinding(inputTag.getPropertyName())) {
                PropertyValidationBinding propertyValidationBinding = validationBinding.getPropertyValidationBinding(inputTag.getPropertyName());
                isValid = propertyValidationBinding.getValidationResult().isValid();
                invalidValue = propertyValidationBinding.getInvalidValue();
            }
        }

        String valueInModel = null;
        try {
            valueInModel = modelAdapter.getPropertyValueAsString(modelBinding.getModel(), inputTag.getPropertyName(), inputTag.getLocale());
        } catch (IOException e) {
            throw new JspException(e);
        }
        String valueToDisplay = (isValid) ? valueInModel : "" + invalidValue;


        openInputHtmlTag(sb, inputTag);

        appendCoreInputAttributes(sb, modelAdapter, modelBinding, inputTag);

        appendValueAttribute(valueToDisplay, sb);
        appendSizeAttribute(sb, modelAdapter, modelBinding, inputTag);
        appendMaxLengthAttribute(sb, inputTag);

        appendStyleClasses(sb, inputTag, modelAdapter, modelBinding, isValid);
        appendStyle(sb, inputTag, modelAdapter, modelBinding);
        appendOnChangeEvent(sb, modelBinding);
        closeInputHtmlTag(sb, valueToDisplay);

        if (!inputTag.isReadOnly() && modelAdapter.isPropertyLookup(modelBinding.getModel(), inputTag.getPropertyName())) {
            appendLookupHtmlTags(sb, modelBinding, modelAdapter, inputTag);
        }




        return TagSupport.SKIP_BODY;

    }

    protected void openInputHtmlTag(StringBuffer sb, IInputTag inputTag) {
        String boxType = "textbox";

        if (inputTag.isHidden()) {
            boxType = "hidden";
        } else if (inputTag.isPassword()) {
            boxType = "password";
        }
        sb.append("<input type='").append(boxType).append("' ");
    }

    /**
     *
     * @param value may be required by subclasses (e.g. TextAreaTagHtmlWriter)
     */
    protected void closeInputHtmlTag(StringBuffer sb, String value) {
        sb.append("/>");
    }

    private static void appendOnChangeEvent(StringBuffer sb, ModelBinding modelBinding) {
        String parentXPath = BindingUrlFormatter.extractParentCollectionXPath(modelBinding.getXpath());
        String collectionIndex = BindingUrlFormatter.extractCollectionIndex(modelBinding.getXpath());
//        sb.append(" onchange=\"fireOnChangeEvent('").append(parentXPath).append("'");
//        sb.append(",'").append(_property).append("'");
//        sb.append(",").append(collectionIndex);
//        sb.append(")\"");
    }


    private static void appendValueAttribute(String value, StringBuffer sb) {
        sb.append(" value='");
        sb.append(value);
        sb.append("'");
    }


    /**
     * EG ::
     * <input type="hidden" name="lookup-referrer-0" value="/eprocurement/PurchaseOrder.do"/>
     * <input type="hidden" name="lookup-source-context-0" value="com.systemsunion.eprocurement.web.purchaseorder.PurchaseOrderWebFlowContext"/>
     * <input type="hidden" name="lookup-source-domain-reference-0" value="" />
     * <input type="hidden" name="lookup-source-property-0" value="supplierCode" />
     *
     * @param sb
     * @param binding
     * @param modelAdapter
     * @throws JspException
     */
    private static void appendLookupHtmlTags(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter, IInputTag inputTag) throws JspException {

        LookupContext lookupCtx = LookupHandler.getLookupContext(((HttpServletRequest)inputTag.getRequest()).getSession());
        int lookupId = lookupCtx.getNextLookupId();


        String formId = PageContextHandler.getFormId(inputTag.getTagInstance());

        renderLookupButton(sb, lookupId);

        LookupInfo lookupInfo = modelAdapter.getLookupInfo(binding.getModel(), inputTag.getPropertyName());
        Lookup lookup = new Lookup(lookupId, formId, binding.getFormContext(), binding.getModel(),
                inputTag.getPropertyName(), lookupInfo, binding.getXpath());
        lookupCtx.registerLookup(lookup);


    }

    /**
     * It is the responsibility of the page designer to provide a stylesheet that has a lookupButton style defined.
     * <input type="submit" value="" name="submit-lookup-0" class="lookup-button"/>
     *
     * @param sb
     * @param lookupId
     */
    private static void renderLookupButton(StringBuffer sb, int lookupId) {
        String lookupParameter = LookupHandler.formatLookupRequestParameter(lookupId);
        sb.append("\n<input type='button' value='' name='").append(lookupParameter).append("'");
        sb.append(" class='lookupButton' ");
        sb.append(" onclick='executeLookup(").append(lookupId).append(")'");
        sb.append("/>");
    }

    private static void renderHiddenInput(StringBuffer sb, String name, String value) {
        sb.append("\n<input type='hidden' name='").append(name).append("'");
        sb.append(" value='").append(value).append("' />");
    }






    private static void appendMaxLengthAttribute(StringBuffer sb, IInputTag inputTag) {
        if (inputTag.getMaxLength() != null) {
            sb.append(" maxLength='");
            sb.append(inputTag.getMaxLength());
            sb.append("'");
        }
    }

    private static void appendSizeAttribute(StringBuffer sb, IModelAdapter modelAdapter, ModelBinding modelBinding, IInputTag inputTag) throws JspException {

        sb.append(" size='");
        if (inputTag.getSize() != null) {
            sb.append(inputTag.getSize());
        } else {
            PropertyBundle propertyBundle = modelAdapter.getPropertyBundle(modelBinding.getModelType(), inputTag.getPropertyName(), inputTag.getLocale());
            sb.append(propertyBundle.getDisplayCharacterCount());
        }
        sb.append("'");
    }

    private static void appendCoreInputAttributes(StringBuffer sb, IModelAdapter adapter, ModelBinding modelBinding, IInputTag inputTag) throws JspException {

        String scriptingId = BindingUrlFormatter.createPropertyScriptIdUrl(modelBinding, inputTag.getPropertyName());

        if (!inputTag.isReadOnly()) {
            sb.append("name='");
            String bindingId = BindingUrlFormatter.createPropertyBindingUrl(modelBinding, inputTag.getPropertyName());
            sb.append(bindingId);
            sb.append("'");
        }
        sb.append(" id='");
        sb.append(scriptingId);
        sb.append("'");
        if ((inputTag.isReadOnly() || adapter.isPropertyReadOnly(modelBinding.getModel(), inputTag.getPropertyName())) && !adapter.isPropertyMandatory(modelBinding.getModel(), inputTag.getPropertyName())) {
            sb.append(" readonly");
            sb.append(" tabindex='-1'");
        } else if (inputTag.getTabIndex() != null) {
            sb.append(" tabindex='" + inputTag.getTabIndex() + "'");
        }
    }




}
