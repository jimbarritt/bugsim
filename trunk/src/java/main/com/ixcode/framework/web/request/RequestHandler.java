/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.request;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.message.IMessageSource;
import com.ixcode.framework.message.NullMessageSource;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.folding.FoldingModel;
import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.model.selection.SelectionModel;
import com.ixcode.framework.model.validation.ValidationBindingMap;
import com.ixcode.framework.security.ISecurityService;
import com.ixcode.framework.security.NullSecurityService;
import com.ixcode.framework.web.action.ActionModel;
import com.ixcode.framework.web.action.IActionInfo;
import com.ixcode.framework.web.servlet.ServletExceptionWrapper;
import com.ixcode.framework.web.session.SessionHandler;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


/**
 * Utility class that helps you set up the environment from the controller to use the domain tags.
 *
 * @author Jim Barritt
 * @version $Revision: 1.8 $
 *          $Id: RequestHandler.java,v 1.8 2004/09/08 10:11:55 rdjbarri Exp $
 */
public class RequestHandler {

    public static void turnOffDefaultModelAdapter() {
        USE_DEFAULT_MODEL_ADAPTER = false;
    }

    public static void setDefaultModelAdapter(IModelAdapter modelAdapter) {
        List adapters = new ArrayList();
        adapters.add(modelAdapter);
        setDefaultModelAdapters(adapters);
    }

    public static void setDefaultMessageSource(IMessageSource messageSource) {
        DEFAULT_MESSAGE_SOURCE = messageSource;
    }
    public static IMessageSource getDefaultMessageSource() {
        return DEFAULT_MESSAGE_SOURCE;
    }

    /**
     * Sets the model adapters to be used by default (i.e. if you dont put some in the request)
     *
     * @param modelAdapters NOte the ORDER is very important, put more specifc ones first.
     */
    public static void setDefaultModelAdapters(List modelAdapters) {
        DEFAULT_MODEL_ADAPTERS = modelAdapters;
    }


    /**
     * Just return the first one.      
     */
    public static IModelAdapter getDefaultModelAdapter() {
        return (IModelAdapter)getDefaultModelAdapters().get(0);
    }

    public static List getDefaultModelAdapters() {
        return DEFAULT_MODEL_ADAPTERS;
    }
    /**
     * So you can configure specific subclasses.
     *
     * for example the {@link JavaBeanModelAdapter } allows you to configure formatters and metadata.
     *
     */
    public static IModelAdapter getDefaultModelAdapter(Class modelKlass) {
        IModelAdapter found = null;
        for (Iterator itr = DEFAULT_MODEL_ADAPTERS.iterator(); itr.hasNext();) {
            IModelAdapter adapter = (IModelAdapter)itr.next();
            if (adapter.canAdapt(modelKlass)) {
                found = adapter;
                break;
            }
        }
        if (found == null) {
            throw new IllegalStateException("No default model adapter found for class '" + modelKlass.getName() + "'");
        }
        return found;
    }

    /**
     * Call this method in the controller code to set up the request with the root domain
     * to which you want to bind using the tag library
     * @todo consider moving this to ModelHandler
     */
    public static void setModel(ServletRequest request, Object model) {
        request.setAttribute(ATTR_DISPLAY_MODEL, model);
    }

    public static Object getModel(ServletRequest request) {
        return getAttribute(request, ATTR_DISPLAY_MODEL);
    }

    public static void setSelectionModel(ServletRequest request, SelectionModel selectionModel) {
        request.setAttribute(ATTR_SELECTION_MODEL, selectionModel);
    }

    public static SelectionModel getSelectionModel(ServletRequest request) {
        return (SelectionModel)getAttribute(request, ATTR_SELECTION_MODEL);
    }

    public static void setFoldingModel(ServletRequest request, FoldingModel foldingModel) {
        request.setAttribute(ATTR_FOLDING_MODEL, foldingModel);
    }

    public static FoldingModel getFoldingModel(ServletRequest request) {
        return (FoldingModel)getAttribute(request, ATTR_FOLDING_MODEL);
    }

    /**
     * @todo consider moving to PagedQueryHandler     
     */
    public static void setPagedQuery(ServletRequest request, IPagedQuery pagedQuery) {
        request.setAttribute(ATTR_PAGED_QUERY, pagedQuery);
    }

    public static IPagedQuery getPagedQuery(ServletRequest request) {
        return (IPagedQuery)getAttribute(request, ATTR_PAGED_QUERY);
    }


    /**
     * This allows you to have more than one model stored in the request if for exapmple you have multiple forms on a single page.
     */
    public static void setModel(ServletRequest request, String formContext, Object model) {
        request.setAttribute(ATTR_DISPLAY_MODEL + "/" + formContext, model);
    }

    public static Object getModel(ServletRequest request, String formContext) {
        if (formContext == null) {
            return getModel(request);
        }
        String key = ATTR_DISPLAY_MODEL + "/" + formContext;
        return getAttribute(request, key);
    }

    /**
     * Use this method if you only ever use a single model "type" ie all of your model classes
     * can be adapted via a single model adapter.
     *
     * If you have more than one "type" you can call {@link setModelAdapters(ServletRequest, List) } instead.
     */
    public static void setModelAdapter(ServletRequest request, IModelAdapter modelAdapter) {
        List adapters = new ArrayList();
        adapters.add(modelAdapter);
        setModelAdapters(request, adapters);
    }

    /**
     * Use this method where you have more than one model "type", see
     * {@link setModelAdapter(ServletRequest, IModelAdapter)
     * for details.
     */
    public static void setModelAdapters(ServletRequest request, List modelAdapters) {
        request.setAttribute(ATTR_MODEL_ADAPTERS, modelAdapters);
    }


    public static String getParameter(HttpServletRequest request, String paramName) {
        if (request.getParameter(paramName) == null) {
            throw new IllegalStateException("Could not locate parameter '" + paramName + "' in request.");
        }
        return request.getParameter(paramName);
    }

    /**
     * @see {@link getModelAdapter(HttpServletRequest, Class) }
     */
    public static IModelAdapter getModelAdapter(HttpServletRequest request, Object model) {
         return getModelAdapter(request, model.getClass());
    }

    /**
     * This method checks all the currently registered model adapters to see if there is one
     * which can adapt your model type, by calling {@link IModelAdapter#canAdapt(Class)}
     *
     * First checks in the request to see if it should use the default model adapter (ie. did someone put in
     * another set sepecifically for this request ?
     *
     * If not, uses the ones in the request.
     *
     * note that this is a replacement for the previosu method which did NOT take a class because there was only 1 model adapter.
     *
     * @throws IllegalStateException if no adapters can be found (this is a coding error).
     */
    public static IModelAdapter getModelAdapter(HttpServletRequest request, Class modelKlass) {
        IModelAdapter modelAdapter = null;

        if (useDefaultModelAdapter(request)) {
            modelAdapter = getDefaultModelAdapter(modelKlass);
        } else {
            List adapters = (List)request.getAttribute(ATTR_MODEL_ADAPTERS);
            for (Iterator itr = adapters.iterator(); itr.hasNext();) {
                IModelAdapter adapter = (IModelAdapter)itr.next();
                if (adapter.canAdapt(modelKlass)) {
                    modelAdapter = adapter;
                    break;
                }
            }
            if (modelAdapter == null) {
                throw new IllegalStateException("Could not find a model adapter to adapt model of class '" + modelKlass.getName() +"'");
            }
        }

        return modelAdapter;
    }


    /**
     * Tells you wether or not you should use the defaul model. It also verifies that
     * there is actually some model adapters present - because there is a situation where you
     * can ask it NOT to use the default and then you dont provide one which is a coding error.
     */
    private static boolean useDefaultModelAdapter(ServletRequest request) {
        boolean useDefault = false;
        if (request.getAttribute(ATTR_MODEL_ADAPTERS) == null) {
            if (!USE_DEFAULT_MODEL_ADAPTER) {
                throw new IllegalStateException("No " + IModelAdapter.class.getName() + " found in request at '" + ATTR_MODEL_ADAPTERS + "' use " + RequestHandler.class.getName() + " to put it there in your controller");
            }
            useDefault = true;
        }
        return useDefault;
    }

//    public static String getLookupSourceContext(ServletRequest request) {
//        if (request.getAttribute(ATTR_LOOKUP_SOURCE_CONTEXT) == null) {
//            throw new IllegalStateException("No lookup source context found in request at '" + ATTR_LOOKUP_SOURCE_CONTEXT + "' use " + RequestHandler.class.getName() + " to put it there in your controller");
//        }
//        return (String)request.getAttribute(ATTR_LOOKUP_SOURCE_CONTEXT);
//    }

//    public static boolean hasLookupSourceContext(HttpServletRequest request) {
//        return request.getAttribute(ATTR_LOOKUP_SOURCE_CONTEXT) != null;
//
//    }
//
//    public static void setLookupSourceContext(ServletRequest request, String sourceContext) {
//        request.setAttribute(ATTR_LOOKUP_SOURCE_CONTEXT, sourceContext);
//    }

    public static void setValidationBindingMap(ServletRequest request, ValidationBindingMap validationMap) {
        setValidationBindingMap(request, validationMap, null);
    }

    public static ValidationBindingMap getValidationBindingMap(ServletRequest request) {
        return getValidationBindingMap(request, null);
    }

    public static boolean hasValidationBindingMap(ServletRequest request) {
        return hasValidationBindingMap(request, null);
    }

    private static String getValidationBindingMapKey(String formContext) {
        return (formContext == null) ? ATTR_VALIDATION_BINDING_MAP : ATTR_VALIDATION_BINDING_MAP + "/" + formContext;
    }

    public static void setValidationBindingMap(ServletRequest request, ValidationBindingMap validationMap, String formContext) {
        request.setAttribute(getValidationBindingMapKey(formContext), validationMap);
    }

    public static ValidationBindingMap getValidationBindingMap(ServletRequest request, String formContext) {
        return (ValidationBindingMap)getAttribute(request, getValidationBindingMapKey(formContext));
    }

    public static boolean hasValidationBindingMap(ServletRequest request, String formContext) {
        return hasAttribute(request, getValidationBindingMapKey(formContext));
    }




    public static Object getAttribute(ServletRequest request, String name) {
        if (!hasAttribute(request, name)) {
            throw new IllegalArgumentException("Could not locate attribute '" + name + "' in request");
        }
        return request.getAttribute(name);
    }

    public static boolean hasAttribute(ServletRequest request, String name) {
        return request.getAttribute(name) != null;
    }


    private RequestHandler() {
    }

    public static Locale getLocale(ServletRequest request) {
        if (!hasAttribute(request, ATTR_LOCALE)) {
            return request.getLocale();
        }
        return (Locale)getAttribute(request, ATTR_LOCALE);
    }

    public static void setLocale(ServletRequest request, Locale locale) {
        request.setAttribute(ATTR_LOCALE, locale);
    }

    public static IMessageSource getMessageSource(ServletRequest request) {
        IMessageSource messageSource = DEFAULT_MESSAGE_SOURCE;

        if (hasAttribute(request, ATTR_MESSAGE_SOURCE)) {
            messageSource = (IMessageSource)getAttribute(request, ATTR_MESSAGE_SOURCE);
        }
        return messageSource;
    }

    /**
     * @param url - note it doesnt have to have the context added to it because it will be executed as a forward.
     * @todo maybe we should keep this in the session ? infact we could keep all the defaults in the session.
     */
    public static void registerDefaultExpiredSessionHandlerUrl(String url) {
        DEFAULT_EXPIRED_SESSION_HANDLER_URL = url;
    }

    public static void setExpiredSessionHandlerUrl(ServletRequest request, String url) {
        request.setAttribute(ATTR_EXPIRED_SESSION_HANDLER_URL, url);
    }

    public static String getExpiredSessionHandlerUrl(ServletRequest request) {
        String url = DEFAULT_EXPIRED_SESSION_HANDLER_URL;
        if (hasAttribute(request, ATTR_EXPIRED_SESSION_HANDLER_URL)) {
            url = (String)getAttribute(request, ATTR_EXPIRED_SESSION_HANDLER_URL);
        }
        return url;
    }

    public static String getExpiredSessionReferrerUrl(HttpServletRequest request) {
        return request.getParameter(PARAM_EXPIRED_SESSION_REFERRER);
    }

    public static boolean validateSession(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        boolean isValid = true;
        HttpSession session = request.getSession();
        String sessionIdFromRequest = request.getParameter(PARAM_SESSION_ID);
        if (sessionIdFromRequest != null && !sessionIdFromRequest.equals(session.getId())) {
            isValid = false;
            redirectResponse(request, response, getExpiredSessionHandlerUrl(request));
        }
        return isValid;
    }

    public static String getExpiredFormReferrerUrl(HttpServletRequest request) {
        return request.getParameter(PARAM_FORM_EXPIRED_REFERRER);
    }

    public static String getExpiredFormHandlerUrl(ServletRequest request) {
        String url = DEFAULT_EXPIRED_FORM_HANDLER_URL;
        if (hasAttribute(request, ATTR_EXPIRED_FORM_HANDLER_URL)) {
            url = (String)getAttribute(request, ATTR_EXPIRED_SESSION_HANDLER_URL);
        }
        return url;
    }


    public static void setMessageSource(ServletRequest request, IMessageSource messageSource) {
        request.setAttribute(ATTR_MESSAGE_SOURCE, messageSource);
    }

    public static ISecurityService getSecurityservice(ServletRequest request) {
        if (!hasAttribute(request, ATTR_SECURITY_SERVICE)) {
            request.setAttribute(ATTR_SECURITY_SERVICE, NullSecurityService.INSTANCE);
        }
        return (ISecurityService)getAttribute(request, ATTR_SECURITY_SERVICE);
    }

    public static void setSecurityService(ServletRequest request, ISecurityService securityService) {
        request.setAttribute(ATTR_SECURITY_SERVICE, securityService);
    }

    public static String createContextUrl(HttpServletRequest request, String relativeUrl, HttpServletResponse response) {
        String context = (relativeUrl.startsWith(request.getContextPath())) ? "" : request.getContextPath();
        return context + ((relativeUrl.startsWith("/")) ? relativeUrl : "/" + relativeUrl);
    }

    /**
     * @param request
     * @param formId
     * @return
     * @todo consider having a SessionUtils
     */
    public static int getNextFormSubmissionId(HttpServletRequest request, String formId) {
        return SessionHandler.getNextFormSumbissionToken(request.getSession(), formId);
    }

    public static boolean validateFormSubmission(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        boolean isValid = true;


        String tokenParamValue = request.getParameter(PARAM_FORM_SUBMISSION_TOKEN);
        if (tokenParamValue != null) {
            int formToken = Integer.parseInt(tokenParamValue);
            String formId = getParameter(request, PARAM_FORM_ID);
            int currentSubmissionId = SessionHandler.getCurrentFormSubmissionToken(request.getSession(), formId);


            if ((formToken != currentSubmissionId)) {
                isValid = false;
                redirectResponse(request, response, getExpiredFormHandlerUrl(request));
            }
        }

        return isValid;

    }

    private static void redirectResponse(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException {
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (IOException e) {
            throw new ServletExceptionWrapper(e);
        }
    }

    public static boolean hasParameter(HttpServletRequest request, String name) {
        return request.getParameter(name) != null;
    }

    public static void setActionInfo(HttpServletRequest request, IActionInfo event) {
        request.setAttribute(ATTR_ACTION_INFO, event);
    }

    public static IActionInfo getActionInfo(HttpServletRequest request) {
        return (IActionInfo)getAttribute(request, ATTR_ACTION_INFO);
    }

    public static boolean hasActionInfo(HttpServletRequest request) {
        return hasAttribute(request, ATTR_ACTION_INFO);
    }


    public static boolean hasActionModel(HttpServletRequest request) {
        return hasAttribute(request, ATTR_ACTION_MODEL);
    }
    public static ActionModel getActionModel(HttpServletRequest request) {
        return (ActionModel)getAttribute(request, ATTR_ACTION_MODEL);
    }

    public static void setActionModel(HttpServletRequest request, ActionModel actionModel) {
        request.setAttribute(ATTR_ACTION_MODEL, actionModel);
    }

    

    private static final String PREFIX = "com.systemsunion.webflow.binding.";

    private static final String ATTR_ACTION_INFO = PREFIX + "ACTION_INFO";
    private static final String ATTR_ACTION_MODEL = PREFIX + "ACTION_MODEL";
    private static final String ATTR_DISPLAY_MODEL = PREFIX + "DISPLAY_MODEL";
    private static final String ATTR_SELECTION_MODEL = PREFIX + "SELECTION_MODEL";
    private static final String ATTR_FOLDING_MODEL = PREFIX + "FOLDING_MODEL";
    private static final String ATTR_VALIDATION_BINDING_MAP = PREFIX + "VALIDATION_BINDING_MAP";
    private static final String ATTR_PAGED_QUERY = PREFIX + "PAGED_QUERY";
    private static final String ATTR_LOCALE = PREFIX + "LOCALE";
    private static final String ATTR_MESSAGE_SOURCE = PREFIX + "MESSAGE_SOURCE";
    private static final String ATTR_MODEL_ADAPTERS = PREFIX + "MODEL_ADAPTER";
    private static final String ATTR_SECURITY_SERVICE = PREFIX + "SECURITY_SERVICE";
    private static final String ATTR_EXPIRED_SESSION_HANDLER_URL = PREFIX + "EXPIRED_SESSION_HANDLER_URL";
    private static final String ATTR_EXPIRED_FORM_HANDLER_URL = PREFIX + "EXPIRED_FORM_HANDLER_URL";

    private static boolean USE_DEFAULT_MODEL_ADAPTER = true;
    private static List DEFAULT_MODEL_ADAPTERS;

    static {
        DEFAULT_MODEL_ADAPTERS = new ArrayList();
        DEFAULT_MODEL_ADAPTERS.add(JavaBeanModelAdapter.INSTANCE);
    }


    public static final String PARAM_SESSION_ID = PREFIX + "SESSION_ID";
    private static String DEFAULT_EXPIRED_SESSION_HANDLER_URL = "session_expired.jsp";
    public static final String PARAM_EXPIRED_SESSION_REFERRER = "session-expired-referrer";

    public static final String PARAM_FORM_ID = PREFIX + "FORM_ID";
    public static final String PARAM_FORM_SUBMISSION_TOKEN = PREFIX + "FORM_SUBMISSION_TOKEN";
    private static String DEFAULT_EXPIRED_FORM_HANDLER_URL = "form_expired.jsp";
    public static final String PARAM_FORM_EXPIRED_REFERRER = "form-expired-referrer";
    private static  IMessageSource DEFAULT_MESSAGE_SOURCE = NullMessageSource.INSTANCE;
}

