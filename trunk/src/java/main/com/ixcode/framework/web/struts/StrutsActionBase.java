/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.struts;

import com.ixcode.framework.error.IErrorHandler;
import com.ixcode.framework.model.ModelHandler;
import com.ixcode.framework.model.folding.FoldingHandler;
import com.ixcode.framework.model.query.EndOfResultsException;
import com.ixcode.framework.model.query.PagedQueryHandler;
import com.ixcode.framework.model.selection.SelectionHandler;
import com.ixcode.framework.model.validation.ValidationBindingMap;
import com.ixcode.framework.web.action.ActionEvent;
import com.ixcode.framework.web.action.IActionInfo;
import com.ixcode.framework.web.request.RequestHandler;
import com.ixcode.framework.web.request.RequestInitialiser;
import com.ixcode.framework.web.servlet.ServletExceptionWrapper;
import com.ixcode.framework.web.session.ISessionContext;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.7 $
 *          $Id: StrutsActionBase.java,v 1.7 2004/09/07 16:10:58 rdjbarri Exp $
 */
public abstract class StrutsActionBase extends Action implements IErrorHandler  {


    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ActionForward forward = execute(actionMapping, httpServletRequest, httpServletResponse);
        if (_exception != null) {
            throw new ServletExceptionWrapper(_exception);
        }
        return forward;
    }

    /**
     * Override this if you want to change the error handling behaviour.
     */
    public void onError(Throwable t) {
        _exception = t;
    }

    protected abstract ActionForward execute(ActionMapping actionMapping, HttpServletRequest request, HttpServletResponse response) throws ServletException;

    protected ActionForward findForward(ActionMapping actionMapping, String forwardName) throws ServletException {
        return StrutsHandler.findForward(actionMapping, forwardName);
    }

    protected ActionForward findForward(ActionMapping actionMapping, IActionInfo actionInfo) throws ServletException {
        if (!(actionInfo instanceof StrutsActionInfo)) {
            throw new IllegalStateException("You passed a non-struts implementation of IActionInfo to the StrutsActionBase findActionMapping. Must pass an instance of StrutsActionInfo ");
        }
        return findForward(actionMapping, (StrutsActionInfo)actionInfo);
    }

    protected ActionForward findForward(ActionMapping actionMapping, StrutsActionInfo  actionInfo) throws ServletException {

        return findForward(actionMapping, actionInfo.getForwardName());
    }

    protected void initialiseRequest(HttpServletRequest request, ISessionContext sessionContext) {
        RequestInitialiser.initialiseRequest(request, sessionContext);
    }



    public void populateModels(HttpServletRequest request, ISessionContext sessionContext) throws ServletException {
        if (sessionContext.getModel() != null) {
            ModelHandler.populateModel(request, sessionContext.getModel());
        }
        if (sessionContext.getFoldingModel() != null  && sessionContext.getModel() != null) {
            FoldingHandler.populateFoldingModel(request, sessionContext.getFoldingModel(), sessionContext.getModel());
        }
        if (sessionContext.getSelectionModel() != null && sessionContext.getModel() != null) {
            SelectionHandler.populateSelectionModel(request, sessionContext.getSelectionModel(), sessionContext.getModel());
        }

    }
     protected String handleActions(HttpServletRequest request, String defaultForwardName) {
         return handleActions(request, defaultForwardName, null);
     }
    /**
     * Only returns the forward if the model is valid.
     * May want to change this behaviour, but this gives you at least an idea of what you might want
     * to do - you pretty much want to do this every time you handle a from submission.
     */
    protected String handleActions(HttpServletRequest request, String defaultForwardName, ActionEvent cancelEvent) {
        String forwardName = defaultForwardName;
        boolean isModelValid = true;
        if (RequestHandler.hasValidationBindingMap(request)) {
            ValidationBindingMap validationBindingMap = RequestHandler.getValidationBindingMap(request);
            isModelValid = validationBindingMap.isValid();
        }

        if (RequestHandler.hasActionInfo(request)) {
            StrutsActionInfo actionInfo = (StrutsActionInfo)RequestHandler.getActionInfo(request);
            if (isModelValid) {
                forwardName = actionInfo.getForwardName();
            } else if (cancelEvent != null && actionInfo.getActionEvent().equals(cancelEvent)) {
                forwardName = actionInfo.getForwardName();
            }
        }
        return forwardName;
    }

    protected void handlePagingEvents(HttpServletRequest request, ISessionContext sessionContext) throws ServletException {
        if (PagedQueryHandler.containsPagingEvents(request)) {
            try {
                PagedQueryHandler.handlePagingEvents(request, sessionContext.getPagedQuery());
            } catch (EndOfResultsException e) { // simply reinitialise it.
                sessionContext.getPagedQuery().firstServerPage();
            }
        }
    }


    private Throwable _exception;
}
