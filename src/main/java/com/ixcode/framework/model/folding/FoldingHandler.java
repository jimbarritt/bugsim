/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.folding;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelXPathResolver;
import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: FoldingHandler.java,v 1.4 2004/08/30 11:29:44 rdjbarri Exp $
 */
public class FoldingHandler {


    public static void populateFoldingModel(HttpServletRequest request, FoldingModel foldingModel, Object boundModel) {
        populateFoldingModel(request, foldingModel, boundModel, null);
    }

    /**
     * @todo make it work with multiple form contexts!!!
     */
    public static void populateFoldingModel(HttpServletRequest request, FoldingModel foldingModel, Object boundModel, String formContext) {
        ModelXPathResolver resolver = new ModelXPathResolver(boundModel);

        for (Enumeration enumeration= request.getParameterNames(); enumeration.hasMoreElements();) {
            String paramName = (String)enumeration.nextElement();
            if (BindingUrlFormatter.isFoldingBindingUrl(paramName)) {
                String modelXPath = BindingUrlFormatter.extractModelXPathFromFoldingUrl(paramName);
                Object model = resolver.getModel(modelXPath);
                IModelAdapter modelAdapter = RequestHandler.getModelAdapter(request, model);
                String modelId = modelAdapter.getModelId(model);

                FoldingState foldingState = FoldingState.parse(request.getParameter(paramName));
                if (foldingState == FoldingState.FOLDED) {
                    foldingModel.setFolded(modelId);
                } else if (foldingState == FoldingState.UNFOLDED) {
                    foldingModel.setUnfolded(modelId);
                } else {
                    throw new IllegalStateException("Unrecognised folding state " + foldingState);
                }
            }
        }

    }


}
