/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.3 $
 *          $Id: ForEachModelTag.java,v 1.3 2004/08/27 11:13:26 rdjbarri Exp $
 */
public class ForEachModelTag extends BodyTagSupport {

    public static final String PROPERTY_ITERATOR_ALIAS = "iteratorAlias";

    /**
     * Notice that we dont have to specify an iterator alias. however, we need to create a binding so that
     * nested child tags can find it and use it. SO as a default we use the name of our parent plus the ame of
     * the association, seperated by a "." e.g.
     *
     * parent = "ROOT"
     *
     * our current model = "ROOT.children"
     *
     * This will be exposed in the JSP but if the developer hasnt specified an iterator alias then they are unlikely to
     * want to use it in this way.      
     */
    public int doStartTag() throws JspException {

        _parentModelBinding = PageContextHandler.getModelBinding(pageContext, _parentModelName, this);

        if (_iteratorAlias == null) {
            _iteratorAlias = _parentModelBinding.getAliasName() + "." + _association;
        }

        IModelAdapter modelAdapter = PageContextHandler.getModelAdapter(pageContext, _parentModelBinding.getModel());
        Collection associatedModels = null;
        try {
            associatedModels = modelAdapter.getAssociatedModels(_parentModelBinding.getModel(), _association);
        } catch (IOException e) {
            throw new JspException(e);
        }
        if ((associatedModels == null) || (associatedModels.isEmpty())) {
            return SKIP_BODY;
        } else {
            _iterator = associatedModels.iterator();
            return EVAL_BODY_BUFFERED;
        }
    }

    /**
     * Gets called once at the start of the iteration.
     */ 
    public void doInitBody() throws JspException {
        _xpathIndex = 1;
        iterationStep();
        _firstModel = true;
    }




    public int doAfterBody() throws JspException {
        _firstModel = false;
        if (_iterator.hasNext()) {
            _xpathIndex++;
            iterationStep();
            return EVAL_BODY_BUFFERED;
        } else {
            iterationStop();
            return SKIP_BODY;
        }
    }

    private void iterationStop() throws JspException {
        try {
            getBodyContent().writeOut(getPreviousOut());
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    protected void iterationStep() {
        String childXPath = BindingUrlFormatter.createAssociatedModelXPath(_parentModelBinding.getXpath(), _association, _xpathIndex);
        _currentModel = _iterator.next();
        IModelAdapter modelAdapter = PageContextHandler.getModelAdapter(pageContext, _currentModel);
        PageContextHandler.addModelBinding(pageContext, _parentModelBinding.getFormContext(), _iteratorAlias, childXPath, _currentModel, modelAdapter.getModelType(_currentModel));
        pageContext.setAttribute(_iteratorAlias, _currentModel);
    }

    public String getAssociation() {
        return _association;
    }

    public void setAssociation(String association) {
        _association = association;
    }

    public String getIteratorAlias() {
        return _iteratorAlias;
    }

    public void setIteratorAlias(String iteratorAlias) {
        _iteratorAlias = iteratorAlias;
    }

    public String getParentModel() {
        return _parentModelName;
    }

    public void setParentModel(String parentModel) {
        _parentModelName = parentModel;
    }

    public boolean hasNext() {
        return (_iterator == null) ? false : _iterator.hasNext();
    }

    public Object getCurrentModel() {
        return _currentModel;
    }

    public String getFormContext() {
        return _parentModelBinding.getFormContext();
    }

    public boolean isFirstModel() {
        return _firstModel;
    }

    public int getXPathIndex() {
        return _xpathIndex;
    }

    public void release() {
        super.release();
        _firstModel = false;
        _iterator = null;
        _parentModelBinding = null;
        _currentModel = null;        
    }

    private boolean _firstModel = false;
    private String _association;
    private String _iteratorAlias;
    private String _parentModelName;
    private Iterator _iterator;
    private ModelBinding _parentModelBinding;
    private int _xpathIndex;
    private Object _currentModel;



}
