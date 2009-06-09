/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.web.taglib;

import com.ixcode.framework.message.IMessageSource;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.binding.BindingUrlFormatter;
import com.ixcode.framework.model.binding.ModelBinding;
import com.ixcode.framework.model.binding.ModelXPathResolver;
import com.ixcode.framework.model.folding.FoldingModel;
import com.ixcode.framework.model.folding.FoldingState;
import com.ixcode.framework.security.ISecurityService;
import com.ixcode.framework.web.action.ActionModel;
import com.ixcode.framework.web.request.RequestHandler;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.*;

/**
 * @author Jim Barritt
 * @version $Revision: 1.5 $
 *          $Id: PageContextHandler.java,v 1.5 2004/09/07 16:10:57 rdjbarri Exp $
 */
public class PageContextHandler {

    private PageContextHandler() {
    }


    public static void appendHiddenInput(StringBuffer sb, String name, String value) {
        sb.append("<input type=\"hidden\" name=\"").append(name).append("\" value=\"");
        sb.append(value).append("\" />\n");
    }

    public static ModelBinding getModelBinding(PageContext pageContext, String modelId, Tag tag) throws JspException {
        if (!isInitialised(pageContext)) {
            initialise(pageContext);
        }

        return getModelBinding(pageContext, resolveModelId(modelId, tag));
    }

    private static void initialise(PageContext pageContext) {
        pageContext.setAttribute(PageContextHandler.ATTR_BINDING_MAP, new HashMap());
        Object model = RequestHandler.getModel(pageContext.getRequest());
        IModelAdapter modelAdapter = RequestHandler.getModelAdapter((HttpServletRequest)pageContext.getRequest(), model);
        addModelBinding(pageContext, null, PageContextHandler.ROOT_MODEL_BINDING_ID, "/", model, modelAdapter.getModelType(model));
    }

    public static void addModelBinding(PageContext pageContext, String formContext, String aliasName, String xpath) {
        Object model = getModelXPathResolver(pageContext, formContext).getModel(xpath);
        IModelAdapter modelAdapter = RequestHandler.getModelAdapter((HttpServletRequest)pageContext.getRequest(), model);
        addModelBinding(pageContext, formContext, aliasName, xpath, model, modelAdapter.getModelType(model));
    }

    /**
     * If you already know the model no point in looking it up via xpath again
     * We also put it in the page context so it should be available to other tag libraries.
     */
    public static void addModelBinding(PageContext pageContext, String formContext, String aliasName, String xpath, Object model, String modelType) {
        if (!isInitialised(pageContext)) {
            initialise(pageContext);
        }
        ModelBinding binding = new ModelBinding(aliasName, formContext, xpath, model, modelType);
        getModelBindingMap(pageContext).put(aliasName, binding);
        pageContext.setAttribute(aliasName, model);
    }

    public static ModelXPathResolver getModelXPathResolver(PageContext pageContext, String formContext) {
        String formContextKey = (formContext == null) ? "" : formContext + "/";
        if (pageContext.getAttribute(formContextKey + PageContextHandler.ATTR_XPATH_RESOLVER) == null) {
            Object rootModel = RequestHandler.getModel(pageContext.getRequest(), formContext);
            pageContext.setAttribute(formContextKey + PageContextHandler.ATTR_XPATH_RESOLVER, new ModelXPathResolver(rootModel));
        }
        return (ModelXPathResolver)pageContext.getAttribute(formContextKey + PageContextHandler.ATTR_XPATH_RESOLVER);
    }

    private static Map getModelBindingMap(PageContext pageContext) {
        return (Map)pageContext.getAttribute(PageContextHandler.ATTR_BINDING_MAP);
    }

    private static boolean isInitialised(PageContext pageContext) {
        return pageContext.getAttribute(ATTR_BINDING_MAP) != null;
    }

    public static String getModelScriptId(PageContext pageContext, String modelId, Tag tag) throws JspException {
        ModelBinding binding = getModelBinding(pageContext, modelId, tag);
        return BindingUrlFormatter.createPropertyScriptIdUrl(binding, null);
    }

    public static String getModelXPath(PageContext pageContext, String modelId, Tag tag) throws JspException {
        ModelBinding binding = getModelBinding(pageContext, modelId, tag);
        return binding.getXpath();
    }

    public static void printOut(PageContext pageContext, StringBuffer sb) throws JspException {
        printOut(pageContext, sb.toString());
    }

    public static void printOut(PageContext pageContext, String s) throws JspException {
        try {
            pageContext.getOut().print(s);
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

    public static String resolveModelId(String specifiedModelId, Tag tag) throws JspException {
        String modelId = specifiedModelId;
        if (modelId == null) {
            ForEachModelTag parent = (ForEachModelTag)TagSupport.findAncestorWithClass(tag, ForEachModelTag.class);
            if (parent == null) {
                UseModelTag useModelParent = (UseModelTag)TagSupport.findAncestorWithClass(tag, UseModelTag.class);
                if (useModelParent != null) {
                    modelId = useModelParent.getAlias();
                } else {
                    modelId = ROOT_MODEL_BINDING_ID; // use the root model by default.
                }
            } else {
                modelId = parent.getIteratorAlias();

            }

        }
        return modelId;
    }

    public static String resolvePropertyName(String specifiedPropertyName, Tag tag) throws JspException {
        String propertyName = specifiedPropertyName;
        if (propertyName == null) {
            ForEachPropertyTag parent = (ForEachPropertyTag)TagSupport.findAncestorWithClass(tag, ForEachPropertyTag.class);
            if (parent == null) {
                throw new JspException("Could not find parent iterator tag for property '" + specifiedPropertyName + "'");
            }
            propertyName = parent.getCurrentPropertyName();
        }
        return propertyName;
    }


    public static BrowserType getBrowserType(ServletRequest request) {
        String s = ((HttpServletRequest)request).getHeader("user-agent");
        BrowserType type = BrowserType.UNKNOWN;
        if (s != null) {
            if (s.indexOf("MSIE") > -1) {
                type = BrowserType.IE;
            } else if (s.indexOf("Mozilla") > -1) {
                type = BrowserType.MOZILLA;
            }
        }
        return type;
    }

    public static void appendParam(StringBuffer sb, String name, String value) {
        sb.append(" ").append(name).append("='").append(value).append("'");
    }

    public static String getFormId(Tag tag) throws JspException {
        FormTag parent = (FormTag)TagSupport.findAncestorWithClass(tag, FormTag.class);
        if (parent == null) {
            throw new JspException("The tag " + tag + " was not placed nested in a webflow form tag - it requires a webflow form tag as a parent");
        }
        return parent.getId();
    }


    public static void appendln(StringBuffer sb, String content) {
        sb.append(content).append("\n");

    }

    public static void appendln(StringBuffer sb, int indent, String content) {
        for (int i = 0; i < (indent * 4); ++i) {
            sb.append(" ");
        }
        sb.append(content).append("\n");

    }

    public static FoldingState getFoldingState(PageContext pageContext, ModelBinding binding) {
        FoldingModel foldingModel = RequestHandler.getFoldingModel(pageContext.getRequest());
        IModelAdapter modelAdapter = RequestHandler.getModelAdapter((HttpServletRequest)pageContext.getRequest(), binding.getModel());
        String modelId = modelAdapter.getModelId(binding.getModel());
        FoldingState foldingState = foldingModel.getFoldingState(modelId);       // going to get this from the folding model in the request.
        return foldingState;
    }


    public static void registerPropertyAsUsed(PageContext pageContext, ModelBinding binding, String propertyName) {
        if (!hasAttribute(pageContext, ATTR_USED_PROPERTIES)) {
            pageContext.setAttribute(ATTR_USED_PROPERTIES, new HashSet());
        }

        String url = BindingUrlFormatter.createPropertyBindingUrl(binding, propertyName);
        Set usedProperties = (Set)getAttribute(pageContext, ATTR_USED_PROPERTIES);
        usedProperties.add(url);
    }

    public static boolean isPropertyUsed(PageContext pageContext, ModelBinding binding, String propertyName) {
        boolean isUsed = false;
        if (hasAttribute(pageContext, ATTR_USED_PROPERTIES)) {
            Set usedProperties = (Set)getAttribute(pageContext, ATTR_USED_PROPERTIES);
            String url = BindingUrlFormatter.createPropertyBindingUrl(binding, propertyName);
            isUsed = usedProperties.contains(url);
        }
        return isUsed;
    }

    public static boolean hasAttribute(PageContext pageContext, String key) {
        return pageContext.getAttribute(key) != null;
    }

    public static Object getAttribute(PageContext pageContext, String key) {
        if (!hasAttribute(pageContext, key)) {
            throw new IllegalStateException("Could not find attribute '" + key + "' in page context.");
        }
        return pageContext.getAttribute(key);
    }

    private static ModelBinding getModelBinding(PageContext pageContext, String modelBindingId) throws JspException {
        if (!getModelBindingMap(pageContext).containsKey(modelBindingId)) {
            throw new JspException("Could not find the model binding with modelBindingId '" + modelBindingId + "' in the page context, maybe no-one registered it.");
        }
        return (ModelBinding)getModelBindingMap(pageContext).get(modelBindingId);
    }

    public static IModelAdapter getModelAdapter(PageContext pageContext, Object model) {
        return RequestHandler.getModelAdapter((HttpServletRequest)pageContext.getRequest(), model);
    }
    public static IModelAdapter getModelAdapter(PageContext pageContext, Class modelKlass) {
        return RequestHandler.getModelAdapter((HttpServletRequest)pageContext.getRequest(), modelKlass);
    }


    public static int getNextLookupId(PageContext pageContext) {
        if (pageContext.getAttribute(PageContextHandler.ATTR_NEXT_LOOKUP_ID) == null) {
            pageContext.setAttribute(PageContextHandler.ATTR_NEXT_LOOKUP_ID, new Integer(0));
        }
        Integer nextId = (Integer)pageContext.getAttribute(PageContextHandler.ATTR_NEXT_LOOKUP_ID);
        pageContext.setAttribute(PageContextHandler.ATTR_NEXT_LOOKUP_ID, new Integer(nextId.intValue() + 1));
        return nextId.intValue();
    }


    public static Locale getLocale(PageContext pageContext) {
        return RequestHandler.getLocale(pageContext.getRequest());

    }

    public static IMessageSource getMessageSource(PageContext pageContext) {
        return RequestHandler.getMessageSource(pageContext.getRequest());
    }

    public static ISecurityService getSecurityService(PageContext pageContext) {
        return RequestHandler.getSecurityservice(pageContext.getRequest());
    }

    /**
     * @param pageContext
     * @param relativeUrl
     * @todo consider making these in a seperate class, maybe "URLUtils" ? (sounds horrible though:)
     */
    public static String createContextUrl(PageContext pageContext, String relativeUrl) {
        HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
        HttpServletRequest request = ((HttpServletRequest)pageContext.getRequest());

        return RequestHandler.createContextUrl(request, relativeUrl, response);
    }

    /**
     * Allows us to prevent duplicate form submissions - its a bit like a version number on a form.
     */
    public static int getNextFormSubmissionId(PageContext pageContext, String formId) {
        return RequestHandler.getNextFormSubmissionId((HttpServletRequest)pageContext.getRequest(), formId);
    }

    public static Class loadClass(String modelClassName) throws JspException {
        try {
            return Thread.currentThread().getContextClassLoader().loadClass(modelClassName);
        } catch (ClassNotFoundException e) {
            throw new JspException(e);
        }
    }

    public static ActionModel getActionModel(PageContext pageContext) {
        return RequestHandler.getActionModel((HttpServletRequest)pageContext.getRequest());        
    }

   
    public static final String PREFIX = "com.ixcode.framework.web.taglib.";
    public static final String ROOT_MODEL_BINDING_ID = PREFIX + "ROOT_MODEL";
    public static final String ATTR_BINDING_MAP = PREFIX + "BINDING_MAP";
    public static final String ATTR_XPATH_RESOLVER = PREFIX + "XPATH_RESOLVER";
    public static final String ATTR_NEXT_LOOKUP_ID = PREFIX + "NEXT_LOOKUP_ID";
    public static final String ATTR_USED_PROPERTIES = PREFIX + "USED_PROPERTIES";


}
