/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.util.Collection;

/**
 * Only executes the body if the association is empty.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: IsEmptyTag.java,v 1.2 2004/08/25 12:04:09 rdacomle Exp $
 */
public class IsEmptyTag extends BoundBodyTagBase {

    protected int appendStartTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        Collection associatedModels = null;
        try {
            associatedModels = modelAdapter.getAssociatedModels(binding.getModel(), _association);
        } catch (IOException e) {
            throw new JspException(e);
        }
        if ((associatedModels == null) || (associatedModels.isEmpty())) {
            return EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    protected int appendAfterBodyContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) {
        return EVAL_PAGE;
    }

    public String getAssociation() {
        return _association;
    }

    public void setAssociation(String association) {
        _association = association;
    }


    public String getParentModel() {
        return super.getModel();
    }

    public void setParentModel(String parentModel) {
        super.setModel(parentModel);
    }

    private String _association;


}
