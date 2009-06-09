/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.ModelBinding;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: ForEachPropertyTag.java,v 1.4 2004/09/07 16:10:56 rdjbarri Exp $
 */
public class ForEachPropertyTag extends BoundBodyTagBase {

    public static final String PROPERTY_NAME = "propertyName";

    /**
     * This tag has 2 modes - one where we are bound to a model and one where there is no model, but
     * the developer knows the model type of the model but has no instances.
     */
    public int doStartTag() throws JspException {
        resolvePropertyHeaderInfo();
        int retVal;
        if ((_modelType == null) && (_modelClass == null)) {
            retVal = super.doStartTag();
        } else {
            retVal = initialiseFromModelType();
        }
        return retVal;
    }


    private int initialiseFromModelType() {
        if (_modelType == null) {
            _modelType = _modelClass.getName();
        }

        Class modelClass = _modelClass;
        IModelAdapter modelAdapter = PageContextHandler.getModelAdapter(pageContext, modelClass);
        Collection propertyNames = modelAdapter.getPropertyNames(_modelType);
        int retVal;
        if (propertyNames.isEmpty()) {
            retVal = SKIP_BODY;
        } else {
            _iterator = modelAdapter.getPropertyNames(_modelType).iterator();
            retVal = EVAL_BODY_BUFFERED;
        }
        return retVal;

    }

    protected int appendStartTagContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        Collection propertyNames = _matchesList;
        if (propertyNames == null) {

            String modelType = modelAdapter.getModelType(binding.getModel());
            propertyNames = modelAdapter.getPropertyNames(modelType);
        }
        if (propertyNames.isEmpty()) {
            return SKIP_BODY;
        } else {
            _iterator = propertyNames.iterator();
            return EVAL_BODY_BUFFERED;
        }
    }

    private void resolvePropertyHeaderInfo() {
        PropertyHeaderTag parent = (PropertyHeaderTag)TagSupport.findAncestorWithClass(this, PropertyHeaderTag.class);
        if (parent != null) {
            _modelClass = (_modelClass == null) ? parent.getModelClass() : _modelClass;
            _modelType = (_modelType == null) ? parent.getModelType() : _modelType;
        }
    }

    protected int appendAfterBodyContent(StringBuffer sb, ModelBinding binding, IModelAdapter modelAdapter) throws JspException {
        if (_iterator.hasNext()) {
            iterationStep();
            return EVAL_BODY_BUFFERED;
        } else {
            iterationStop();
            return SKIP_BODY;
        }
    }


    public void doInitBody() throws JspException {
        iterationStep();
    }


    private void iterationStop() throws JspException {
        try {
            getBodyContent().writeOut(getPreviousOut());
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    protected void iterationStep() {
        _currentPropertyName = (String)_iterator.next();
        pageContext.setAttribute(PROPERTY_NAME, _currentPropertyName);
    }


    public String getParentModel() {
        return getModel();
    }

    public void setParentModel(String parentModel) {
        super.setModel(parentModel);
    }

    public boolean hasNext() {
        return (_iterator == null) ? false : _iterator.hasNext();
    }


    public String getCurrentPropertyName() {
        return _currentPropertyName;
    }

    public void setMatchesList(String matchesList) {
        _matchesList = new ArrayList();
        StringTokenizer st = new StringTokenizer(matchesList, ",");
        while (st.hasMoreTokens()) {
            _matchesList.add(st.nextToken());
        }
    }

    public static String tokenizeList(List propertyNames) {
        StringBuffer sb = new StringBuffer();
        for (Iterator itr = propertyNames.iterator(); itr.hasNext();) {
            String s = (String)itr.next();
            sb.append(s);
            if (itr.hasNext()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    public void setModelClass(Class modelClass) {
        _modelClass = modelClass;
    }

    public void setModelType(String modelType) {
        _modelType = modelType;
    }

    private Iterator _iterator;
    private String _currentPropertyName;
    private List _matchesList;


    private Class _modelClass;
    private String _modelType;
}
