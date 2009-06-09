/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.selection;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelXPathResolver;
import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Handles everything you need to do with the selection model.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: SelectionHandler.java,v 1.4 2004/08/30 11:29:46 rdjbarri Exp $
 */
public class SelectionHandler {

    public static void populateSelectionModel(HttpServletRequest request, SelectionModel selectionModel, Object boundModel) throws ServletException {
        populateSelectionModel(request, selectionModel, boundModel, null);
    }

    /**
     * @todo take into account the form context ?
     */
    public static void populateSelectionModel(HttpServletRequest request, SelectionModel selectionModel, Object boundModel, String formContext) throws ServletException{

        ModelXPathResolver resolver = new ModelXPathResolver(boundModel);

        Map  bindings = buildSelectionBindings(request, resolver);

        for (Iterator itr = bindings.values().iterator(); itr.hasNext();) {
            SelectionBinding binding = (SelectionBinding)itr.next();
            if (binding.isSelected()) {
                selectionModel.addModelToSelection(binding.getModelId(), binding.getModel());
            } else if (selectionModel.isModelSelected(binding.getModelId())) {
                selectionModel.removeModelFromSelection(binding.getModelId());
            }
        }
    }

    /**
     * Works out which model ids and models should be marked as selected / unselected.
     * @todo work out how this gets dealt with using form contexts.
     */
    private static Map buildSelectionBindings(HttpServletRequest request, ModelXPathResolver resolver) throws ServletException {
        Map bindings = new HashMap();

        preProcessCheckBoxMarkers(request, bindings, resolver);

        for (Enumeration enumeration = request.getParameterNames(); enumeration.hasMoreElements();) {
            String selectionUrl = (String)enumeration.nextElement();
            if (BindingUrlFormatter.isSelectionBindingUrl(selectionUrl)) {
                String modelXPath = BindingUrlFormatter.extractModelXPathFromSelectionUrl(selectionUrl);
                Object model = resolver.getModel(modelXPath);
                IModelAdapter modelAdapter = RequestHandler.getModelAdapter(request, model);
                String modelId = modelAdapter.getModelId(model);
                if (!bindings.containsKey(modelId)) {
                    throw new IllegalStateException("Cannot find a selectionbinding for model id '" + modelId + "' maybe there is no hidden checkbox marker for this model. xpath  " + modelXPath);
                }
                SelectionBinding binding = (SelectionBinding)bindings.get(modelId);
                binding.setSelected(true);
            }
        }

        return bindings;
    }

    private static void preProcessCheckBoxMarkers(HttpServletRequest request, Map selectionBindings, ModelXPathResolver resolver)  {
        for (Enumeration enumeration= request.getParameterNames(); enumeration.hasMoreElements();) {
            String paramName = (String)enumeration.nextElement();
            if (BindingUrlFormatter.isSelectionCheckBoxMarker(paramName)) {
                String selectionUrl = request.getParameter(paramName);
                String modelXPath = BindingUrlFormatter.extractModelXPathFromSelectionUrl(selectionUrl);

                Object model = resolver.getModel(modelXPath);
                IModelAdapter modelAdapter = RequestHandler.getModelAdapter(request, model);
                String modelId = modelAdapter.getModelId(model);
                SelectionBinding binding = new SelectionBinding(modelId, false, model);
                selectionBindings.put(modelId, binding);
            }
        }
    }


}
