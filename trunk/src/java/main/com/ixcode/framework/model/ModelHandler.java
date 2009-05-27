/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model;

import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelXPathResolver;
import com.ixcode.framework.model.validation.ModelValidationBinding;
import com.ixcode.framework.model.validation.ValidationBindingMap;
import com.ixcode.framework.model.validation.ValidationResult;
import com.ixcode.framework.web.request.RequestHandler;
import com.ixcode.framework.web.servlet.ServletExceptionWrapper;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ModelHandler.java,v 1.3 2004/09/03 06:36:24 rdsward Exp $
 */
public class ModelHandler {
    public static boolean populateModel(HttpServletRequest request, Object model) throws ServletException {
        return populateModel(request, model, null);
    }

    public static boolean populateModel(HttpServletRequest request, Object rootModel, String formContext) throws ServletException {

        ModelXPathResolver resolver = new ModelXPathResolver(rootModel);

        PropertyBindingMap bindingMap = buildBindingMap(request, formContext);

        ValidationBindingMap validationMap = new ValidationBindingMap(formContext);

        populateValues(request, bindingMap, validationMap, resolver);

        validateModels(request, validationMap);

        RequestHandler.setValidationBindingMap(request, validationMap, formContext);

        return validationMap.isValid();

    }

    private static void populateValues(HttpServletRequest request, PropertyBindingMap bindingMap, ValidationBindingMap validationBindingMap, ModelXPathResolver resolver) throws ServletException {

        for (Iterator itr = bindingMap.getModelXPaths().iterator(); itr.hasNext();) {
            String modelXPath = (String)itr.next();


            Object model = resolver.getModel(modelXPath);
            IModelAdapter modelAdapter = RequestHandler.getModelAdapter(request, model);

            if (model == null) {
                throw new ServletException("Could not find a model for xpath '" + modelXPath + "'");
            }
            List boundPropertValues = bindingMap.getBoundPropertyValues(modelXPath);
            for (Iterator itrBoundValue = boundPropertValues.iterator(); itrBoundValue.hasNext();) {
                BoundPropertyValue boundValue = (BoundPropertyValue)itrBoundValue.next();
                ValidationResult result = null;
                try {
                    result = modelAdapter.setPropertyValueAsString(model, boundValue.getPropertyName(),
                            boundValue.getPropertyValue(), RequestHandler.getLocale(request));
                } catch (IOException e) {
                    throw new ServletExceptionWrapper(e);
                }
                if (!result.isValid()) {
                    validationBindingMap.bindValidationResult(modelXPath, model,
                            boundValue.getPropertyName(), boundValue.getPropertyValue(), result);
                }
            }

        }

    }

    private static void validateModels(HttpServletRequest request, ValidationBindingMap validationMap) {
        for (Iterator itr = validationMap.getModelValidationBindings().iterator(); itr.hasNext();) {
            ModelValidationBinding binding = (ModelValidationBinding)itr.next();
            if (!binding.hasModelLevelResult()) {
                IModelAdapter modelAdapter = RequestHandler.getModelAdapter(request, binding.getModel());
                ValidationResult result = modelAdapter.validateModel(binding.getModel());
                binding.setModelLevelResult(result);
            }
        }
    }

    public static PropertyBindingMap buildBindingMap(ServletRequest request, String formContext) {
        PropertyBindingMap bindingMap = new PropertyBindingMap(formContext);
        List checkBoxMarkers = new ArrayList();
        for (Enumeration enumeration= request.getParameterNames(); enumeration.hasMoreElements();) {
            String paramName = (String)enumeration.nextElement();
            if (BindingUrlFormatter.isPropertyBindingUrl(paramName)) {
                String paramFormContext = BindingUrlFormatter.extractFormContextFromUrl(paramName);
                if (isSameFormContext(formContext, paramFormContext)) {
                    String modelXPath = BindingUrlFormatter.extractModelXPathFromPropertyBindingUrl(paramName);
                    String propertyName = BindingUrlFormatter.extractPropertyNameFromUrl(paramName);
                    bindingMap.addBoundPropertyValue(modelXPath, propertyName, request.getParameter(paramName));
                }
            } else if (BindingUrlFormatter.isCheckBoxMarker(paramName)) {
                checkBoxMarkers.add(paramName);
            }
        }

        processCheckBoxValues(request, checkBoxMarkers, formContext, bindingMap);

        return bindingMap;
    }

     private static boolean isSameFormContext(String formContext, String paramFormContext) {
        boolean result = formContext != null && paramFormContext != null && paramFormContext.equals(formContext);
        if (formContext == null && paramFormContext == null) {
            result = true;
        }
        return result;
    }

    /**
     * we put hidden fields on the form to make sure we know that a check box was put on the form for a given
     * property.
     * <p/>
     * We can then go through these to identify the condition where a check box is on the page but the user has "unchecked"
     * it. We need to set "false" into the domain for this case.
     *
     * @param request
     * @param checkBoxMarkers
     * @param bindingMap
     */
    private static void processCheckBoxValues(ServletRequest request, List checkBoxMarkers, String formContext, PropertyBindingMap bindingMap) {
        for (Iterator itr = checkBoxMarkers.iterator(); itr.hasNext();) {
            String paramName = (String)itr.next();
            String checkBoxBindingUrl = request.getParameter(paramName);

            String paramFormContext = BindingUrlFormatter.extractFormContextFromUrl(checkBoxBindingUrl);
            if (isSameFormContext(formContext, paramFormContext)) {
                String modelXPath = BindingUrlFormatter.extractModelXPathFromPropertyBindingUrl(checkBoxBindingUrl);
                String propertyName = BindingUrlFormatter.extractPropertyNameFromUrl(checkBoxBindingUrl);
                if (!bindingMap.hasBoundValue(modelXPath, propertyName)) {
                    bindingMap.addBoundPropertyValue(modelXPath, propertyName, "false");
                }
            }
        }
    }
}
