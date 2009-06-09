/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: BoundBodyTagBase.java,v 1.2 2004/08/27 11:13:25 rdjbarri Exp $
 */
public abstract class BoundBodyTagBase extends BodyTagSupport {

    public BoundBodyTagBase() {
    }

    public int doStartTag() throws JspException {
        _binding = PageContextHandler.getModelBinding(pageContext, _model, this);
        _modelAdapter = PageContextHandler.getModelAdapter(pageContext, _binding.getModel());
        
        StringBuffer sb = new StringBuffer();

        int retVal = appendStartTagContent(sb, _binding, _modelAdapter);

        PageContextHandler.printOut(pageContext, sb);

        return retVal;
    }

    public int doAfterBody() throws JspException {
        StringBuffer sb = new StringBuffer();

        int retVal = appendAfterBodyContent(sb, _binding, _modelAdapter);

        PageContextHandler.printOut(pageContext, sb);

        return retVal;
    }


    protected abstract int appendStartTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException;

    protected abstract int appendAfterBodyContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException;

    public String getModel() {
        return _model;
    }

    public void setModel(String model) {
        _model = model;
    }

    public void release() {
        _binding = null;
        _modelAdapter = null;
        super.release();
    }

    private ModelBinding _binding;
    private IModelAdapter _modelAdapter;
    private String _model;
}
