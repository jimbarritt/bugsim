/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.lookup;

import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelXPathResolver;
import com.ixcode.framework.model.info.LookupInfo;
import com.ixcode.framework.model.info.LookupPropertyMappingInfo;
import com.ixcode.framework.model.query.IPagedQuery;
import com.ixcode.framework.model.query.IServerQuery;
import com.ixcode.framework.model.query.IServerQueryParameters;
import com.ixcode.framework.model.query.PagedQuery;
import com.ixcode.framework.web.request.RequestHandler;
import com.ixcode.framework.web.servlet.ServletExceptionWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.12 $
 *          $Id: LookupHandler.java,v 1.12 2004/09/17 10:58:09 rdjbarri Exp $
 */
public class LookupHandler {


    public static String formatLookupRequestParameter(int lookupId) {
        return PARAM_INVOKE_LOOKUP + "-" + lookupId;
    }


    /**
     * Cleans out the session ready for some new lookups to be defined.
     */
    public static void initialseLookupContext(HttpSession session) {
        if (log.isDebugEnabled()) {
            log.debug("<initialseLookupContext> : Created a new lookup context");
        }
        session.removeAttribute(SESSION_ATTR_LOOKUP_CONTEXT);
        session.setAttribute(SESSION_ATTR_LOOKUP_CONTEXT, new LookupContext());
    }

    public static LookupContext getLookupContext(HttpSession session) {
        if (session.getAttribute(SESSION_ATTR_LOOKUP_CONTEXT) == null) {
            throw new IllegalStateException("Could not locate attribute '" + SESSION_ATTR_LOOKUP_CONTEXT + "' in session");
        }
        return (LookupContext)session.getAttribute(SESSION_ATTR_LOOKUP_CONTEXT);
    }

    public static int getLookupIdParameter(HttpServletRequest request) {
        return Integer.parseInt(RequestHandler.getParameter(request, PARAM_LOOKUP_ID));
    }

    public static boolean hasLookupIdParameter(HttpServletRequest request) {
        return RequestHandler.hasParameter(request, PARAM_LOOKUP_ID);
    }


    /**
     * If you dont choose to provide a server query parameters, one will be created for you which is a
     * standard lookup one containing the lookup information which should give you most of the context
     * you need.
     *
     * Unfortunately PR now uses the server query parameters to pass some of its context - this should really be refactored
     * so that it passes its context via the LookupInfo.getLookupParameters.
     */
    public static IPagedQuery createPagedQuery(Lookup lookup, int serverPageSize, int displayPageSize) throws ServletException {
        Class serverQueryClass = lookup.getLookupInfo().getQueryClass();
        IServerQueryParameters serverQueryParameters = lookup.getLookupInfo().getQueryParameters();
        if (serverQueryParameters == null) {
            serverQueryParameters = new LookupServerQueryParameters(lookup);
        }
        return instantiateQuery(serverQueryClass, serverQueryParameters, serverPageSize, displayPageSize);
    }


    private static IPagedQuery instantiateQuery(Class serverQueryClass, IServerQueryParameters serverQueryParameters, int serverPageSize, int displayPageSize) throws ServletException {
        try {
            IServerQuery serverQuery = (IServerQuery)serverQueryClass.newInstance();
            return new PagedQuery(serverQuery, serverPageSize, displayPageSize, serverQueryParameters);
        } catch (IllegalAccessException e) {
            throw new ServletExceptionWrapper(e);
        } catch (InstantiationException e) {
            throw new ServletExceptionWrapper(e);
        }
    }

    public static void handleLookupSubmission(HttpServletRequest request, List currentPage, String lookupModelXPath, Lookup lookup) throws ServletException, IOException {

        LookupInfo lookupInfo = lookup.getLookupInfo();
        ModelXPathResolver resolver = new ModelXPathResolver(currentPage);
        Object sourceModel = lookup.getSourceModel();
        Object lookupModel = resolver.getModel(lookupModelXPath);


        if (log.isDebugEnabled()) {
            log.debug("<handleLookupSubmission> : Handling lookup submission for selected model " + lookupModelXPath + ", selected model is " + lookupModel);
        }
        mapLookupValuesBackToSourceModel(request, lookup.getSourceFormContext(), lookupInfo, lookupModel, sourceModel, lookup.getSourceModelXPath());

        setLookupCompleted(request);
    }

    public static void setLookupCompleted(HttpServletRequest request) {
        request.setAttribute(REQUEST_ATTR_LOOKUP_COMPLETED, Boolean.TRUE);
    }

    public static boolean isLookupCompleted(ServletRequest request) {
        boolean result = false;
        if (RequestHandler.hasAttribute(request, REQUEST_ATTR_LOOKUP_COMPLETED)) {
            result = true;
        }
        return result;
    }

    public static void setLookupCancelled(HttpServletRequest request) {
        request.setAttribute(REQUEST_ATTR_LOOKUP_CANCELLED, Boolean.TRUE);
    }

    public static boolean isLookupCancelled(ServletRequest request) {
        boolean result = false;
        if (RequestHandler.hasAttribute(request, REQUEST_ATTR_LOOKUP_CANCELLED)) {
            result = true;
        }
        return result;
    }


    public static List getLookupValueMappings(ServletRequest request) {
        return (List)RequestHandler.getAttribute(request, REQUEST_ATTR_LOOKUP_VALUE_MAPPINGS);
    }


    /**
     * Does 2 things :
     * <p/>
     * 1) actually populates the source model with values from the lookup result model.
     * <p/>
     * 2) creates a list of lookup value mappings which can later be used to generate stuff like javascript
     * to allow us to populate the forms on the client without doing a round trip to the server.
     *
     * @todo No record of the ValidationResults is made!! allthough we assume that they are valid.
     */
    private static void mapLookupValuesBackToSourceModel(HttpServletRequest request, String sourceFormContext, LookupInfo lookupInfo, Object lookupModel, Object sourceModel, String sourceModelXPath) throws ServletException, IOException {
        Locale locale = RequestHandler.getLocale(request);
        List propertyMappings = lookupInfo.getPropertyMappingInfos();
        List valueMappings = new ArrayList();
        IModelAdapter sourceAdapter = RequestHandler.getModelAdapter(request, sourceModel);
        IModelAdapter lookupAdapter = RequestHandler.getModelAdapter(request, lookupModel);


        if (log.isDebugEnabled()) {
            log.debug("<mapLookupValuesBackToSourceModel> : Found sourceAdapter " + sourceAdapter + ", lookupAdapter " + lookupAdapter + " count of property mappings is " + propertyMappings.size());
        }

        if (lookupInfo.hasLookupAssociation()) {
            if (log.isDebugEnabled()) {
                log.debug("<mapLookupValuesBackToSourceModel> : Populating Lookup association '" + lookupInfo.getLookupAssociationName() + "' with model " + lookupModel);
            }
            sourceAdapter.setAssociatedModel(sourceModel,  lookupInfo.getLookupAssociationName(), lookupModel, locale);
        }

        for (Iterator itr = propertyMappings.iterator(); itr.hasNext();) {
            LookupPropertyMappingInfo mapping = (LookupPropertyMappingInfo)itr.next();
            String sourcePropertyName = mapping.getSourcePropertyName();

            Object loookupValue = null;
            try {
                loookupValue = lookupAdapter.getPropertyValue(lookupModel, mapping.getLookupPropertyName());
                if (mapping.getLookupPropertyName() != null) {
                    loookupValue = lookupAdapter.getPropertyValue(lookupModel, mapping.getLookupPropertyName());
                    String propertyBindingUrl = BindingUrlFormatter.createPropertyBindingUrl(sourceFormContext, sourceModelXPath, sourcePropertyName);
                    String lookupStringValue = lookupAdapter.getPropertyValueAsString(lookupModel, mapping.getLookupPropertyName(), locale);
                    valueMappings.add(new LookupValueMapping(propertyBindingUrl, lookupStringValue));
                } else {
                    loookupValue = lookupModel;
                }
            } catch (IOException e) {              // @todo - sort out exceptions - give the model adapter its own exceptions
                throw new ServletExceptionWrapper(e);
            }

            sourceAdapter.setPropertyValue(sourceModel, sourcePropertyName, loookupValue, locale);

            if (log.isDebugEnabled()) {
                log.debug("<mapLookupValuesBackToSourceModel> : Set Value '" + loookupValue + "' fom lookup property '" + mapping.getLookupPropertyName() + "' back to source property '" + mapping.getSourcePropertyName() + "'");
            }
        }

        request.setAttribute(REQUEST_ATTR_LOOKUP_VALUE_MAPPINGS, valueMappings);

    }

    public static boolean isLookupSubmission(HttpServletRequest request) {
        String command = request.getParameter(PARAM_LOOKUP_COMMAND);
        return (command != null) && (command.length() > 0);
    }

    public static boolean isLookupInvokedRequest(HttpServletRequest request) {
        return request.getParameter(PARAM_INVOKE_LOOKUP) != null;
    }



    public static String getSelectedItemParameter(HttpServletRequest request) {
        return RequestHandler.getParameter(request, PARAM_SELECTED_ITEM);
    }


    public static final String REQUEST_ATTR_LOOKUP_COMPLETED = "com.systemsunion.webflow.lookup.COMPLETED";
    public static final String REQUEST_ATTR_LOOKUP_CANCELLED = "com.systemsunion.webflow.lookup.CANCELLED";
    public static final String REQUEST_ATTR_LOOKUP_VALUE_MAPPINGS = "com.systemsunion.webflow.loookup.VALUE_MAPPINGS";
    public static final String REQUEST_ATTR_LOOKUP_ID = "com.systemsunion.webflow.loookup.LOOKUP_ID";

    private static final String SESSION_ATTR_LOOKUP_CONTEXT = "com.systemsunion.webflow.lookup.CONTEXT";

    public static final String PARAM_SELECTED_ITEM = "selected-item";
    public static final String PARAM_LOOKUP_COMMAND = "lookup-command";
    public static final String COMMAND_SUBMIT = "submit";

    public static final String PARAM_LOOKUP_ID = "lookup-id";
    public static final String PARAM_INVOKE_LOOKUP = "invoke-lookup";

    private static Log log = LogFactory.getLog(LookupHandler.class);


}
