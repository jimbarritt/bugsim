/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.session;

import com.ixcode.framework.model.folding.FoldingModel;
import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.model.selection.SelectionModel;
import com.ixcode.framework.web.action.ActionModel;


public abstract class SessionContextBase implements ISessionContext {

    public Object getModel() {
        return _model;
    }

    public void setModel(Object model) {
        _model = model;
    }

    public FoldingModel getFoldingModel() {
        return _foldingModel;
    }

    public void setFoldingModel(FoldingModel foldingModel) {
        _foldingModel = foldingModel;
    }

    public SelectionModel getSelectionModel() {
        return _selectionModel;
    }

    public void setSelectionModel(SelectionModel selectionModel) {
        _selectionModel = selectionModel;
    }

    public IPagedQuery getPagedQuery() {
        return _pagedQuery;
    }

    public void setPagedQuery(IPagedQuery pagedQuery) {
        _pagedQuery = pagedQuery;
    }

    public void setActionModel(ActionModel actionModel) {
        _actionModel = actionModel;
    }

    public ActionModel getActionModel() {
        return _actionModel;
    }

    private Object _model;
    private FoldingModel _foldingModel = new FoldingModel();
    private SelectionModel _selectionModel = new SelectionModel();
    private IPagedQuery _pagedQuery;
    private ActionModel _actionModel;
}
