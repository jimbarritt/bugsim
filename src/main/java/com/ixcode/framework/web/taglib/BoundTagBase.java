/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Locale;

/**
 * @todo may be able to make this the base class for input tag ?
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: BoundTagBase.java,v 1.4 2004/09/17 13:35:58 rdjbarri Exp $
 */
public abstract class BoundTagBase extends TagSupport {

    public int doStartTag() throws JspException {
        _modelBinding = PageContextHandler.getModelBinding(pageContext, _model, this);

        IModelAdapter modelAdapter = PageContextHandler.getModelAdapter(pageContext, _modelBinding.getModel());

        StringBuffer sb = new StringBuffer();
        int returnAction = writeTagContent(sb, _modelBinding, modelAdapter);
        PageContextHandler.printOut(pageContext, sb);

        return returnAction;
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public Locale getLocale() {
        return PageContextHandler.getLocale(pageContext);
    }

    public String getPropertyName() throws JspException {
        return PageContextHandler.resolvePropertyName(_property, this);
    }

    protected abstract int writeTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException;


    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

    public void setModel(String model) {
        _model = model;
    }

    public void setProperty(String property) {
        _property = property;
    }

    public String getModel() {
        return _model;
    }

    public ModelBinding getModelBinding() {
        return _modelBinding;
    }
    private String _model;
    private String _property;

    private ModelBinding _modelBinding;
}
