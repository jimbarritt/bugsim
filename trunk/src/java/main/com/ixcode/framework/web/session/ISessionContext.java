package com.ixcode.framework.web.session;

import com.ixcode.framework.model.folding.FoldingModel;
import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.model.selection.SelectionModel;
import com.ixcode.framework.web.action.ActionModel;


public interface ISessionContext {

    Object getModel();

    void setModel(Object model);

    FoldingModel getFoldingModel();

    void setFoldingModel(FoldingModel foldingModel);

    SelectionModel getSelectionModel();

    void setSelectionModel(SelectionModel selectionModel);

    void setPagedQuery(IPagedQuery pagedQuery);

    IPagedQuery getPagedQuery();

    void setActionModel(ActionModel actionModel);

    ActionModel getActionModel();

}
