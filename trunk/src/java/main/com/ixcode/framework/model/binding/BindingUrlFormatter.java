/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.binding;

/**
 * Class or Interface description.
 *
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: BindingUrlFormatter.java,v 1.1 2004/08/11 12:08:23 rdjbarri Exp $
 */
public class BindingUrlFormatter {

    private static final String PARAM_SEPERATOR = ":";
    private static final String PROPERTY_BINDING_PREFIX = "bind" + PARAM_SEPERATOR;
    private static final String SCRIPT_BINDING_PREFIX = "script" + PARAM_SEPERATOR;
    private static final String SELECTION_BINDING_PREFIX = "select" + PARAM_SEPERATOR;
    private static final String FOLDING_BINDING_PREFIX = "folding" + PARAM_SEPERATOR;

    private static final String PARAM_FORM_CONTEXT_SEPERATOR = "#";

    /**
     * The format of the url is PROPERTY_BINDING_PREFIX + [${formContext} + PARAM_FORM_CONTEXT_SEPERATOR] +  ${xpath} + PARAM_SEPERATOR + ${propertyName}
     * <p/>
     * We only use XPath to return the domain objects, not to get and set property values because we
     * need to be able to get and set them as strings, which is going to be different for different models
     * so we use an implementation of {@link com.ixcode.framework.model.IModelAdapter} to do this.
     *
     * @param modelBinding
     * @param propertyName
     * @return
     */
    public static String createPropertyBindingUrl(ModelBinding modelBinding, String propertyName) {
        return createBindingUrl(PROPERTY_BINDING_PREFIX, modelBinding, propertyName);
    }

    public static String createPropertyScriptIdUrl(ModelBinding modelBinding, String propertyName) {
        return createBindingUrl(SCRIPT_BINDING_PREFIX, modelBinding, propertyName);
    }

    public static String createFoldingBindingUrl(ModelBinding binding) {
        return createBindingUrl(FOLDING_BINDING_PREFIX, binding, null);
    }

    public static String createSelectionBindingUrl(ModelBinding binding) {
        return createBindingUrl(SELECTION_BINDING_PREFIX, binding, null);
    }

    public static String createPropertyBindingUrl(String formContext, String modelXPath, String propertyName) {
        return createBindingUrl(PROPERTY_BINDING_PREFIX, modelXPath, formContext, propertyName);
    }

    private static String createBindingUrl(String prefix, ModelBinding modelBinding, String propertyName) {
        return createBindingUrl(prefix, modelBinding.getXpath(), modelBinding.getFormContext(), propertyName);
    }

    private static String createBindingUrl(String prefix, String modelXPath, String formContext, String propertyName) {
        String formContextSection = formContext == null ? "" : formContext + PARAM_FORM_CONTEXT_SEPERATOR;
        String xpath = modelXPath.equals("/") ? "" : modelXPath;
        String propertySection = "";
        if (xpath.length() >0 && propertyName != null) {
            propertySection += PARAM_SEPERATOR;
        }
        propertySection  += (propertyName != null) ? propertyName : "";
        return prefix + formContextSection + xpath + propertySection;
    }

    public static String extractFormContextFromUrl(String url) {
        String withoutPrefix = stripPrefix(url);
        int pos = withoutPrefix.indexOf(PARAM_FORM_CONTEXT_SEPERATOR);
        String result = null;
        if (pos != -1) {
            result = withoutPrefix.substring(0, pos);
        }
        return result;
    }



    public static boolean isPropertyBindingUrl(String url) {
        return url.startsWith(PROPERTY_BINDING_PREFIX);
    }


    public static String extractPropertyNameFromUrl(String url) {
        String withoutPrefix = stripPrefix(url);
        int posFormContext = withoutPrefix.indexOf(PARAM_FORM_CONTEXT_SEPERATOR);
        String withoutFormContext = (posFormContext == -1) ? withoutPrefix : withoutPrefix.substring(posFormContext + 1);
        return withoutFormContext.substring(withoutFormContext.lastIndexOf(':') + 1);
    }

    /**
     * @param xpath of the form : /rootObject/someCollection[1]myProperty
     * @returns /rootObject/someCollection
     * @todo not sure if this does the right thing for object which dont live in a collection ... (i.e. no [] ) mayb just need to return the xpath directly.
     */
    public static String extractParentCollectionXPath(String xpath) {
        if (xpath.indexOf('[') == -1) {
            return xpath.substring(xpath.lastIndexOf('/'));
        }
        return xpath.substring(0, xpath.indexOf('['));
    }

    /**
     * @param xpath of the form : /rootObject/someCollection[1]myProperty
     * @return 1
     */
    public static String extractCollectionIndex(String xpath) {
        int pos = xpath.indexOf('[');
        String collectionIndex = "-1";
        if (pos != -1) {
            collectionIndex = xpath.substring(pos + 1, pos + 2);
        }
        return collectionIndex;
    }


    private BindingUrlFormatter() {
    }

    public static String createAssociatedModelXPath(String parentXPath, String associationName, int i) {
        String associationPath = (associationName == null) ? "" : associationName;
        if (parentXPath.endsWith("/")) {
            return parentXPath + associationPath + "[" + i + "]";
        } else {
            return parentXPath + "/" + associationPath + "[" + i + "]";
        }
    }

    public static boolean isCheckBoxMarker(String paramName) {
        return paramName.startsWith(getCheckBoxMarkerId());
    }

    public static String getCheckBoxMarkerId() {
        return "checkbox-marker";
    }

    public static boolean isSelectionCheckBoxMarker(String paramName) {
        return paramName.startsWith(getSelectionCheckBoxMarkerId());
    }

    public static String getSelectionCheckBoxMarkerId() {
        return "selection-checkbox-marker";
    }

    public static String extractModelXPathFromSelectionUrl(String url) {
        String withoutPrefix = stripSelectionPrefix(url);
        int formContextPos = withoutPrefix.indexOf(PARAM_FORM_CONTEXT_SEPERATOR);
        int startPos = (formContextPos == -1) ? 0 : formContextPos + 1;

        String urlWithoutFormContext = withoutPrefix.substring(startPos);

        return urlWithoutFormContext;
    }

    public static String extractModelXPathFromFoldingUrl(String url) {
        String withoutPrefix = stripFoldingPrefix(url);
        int formContextPos = withoutPrefix.indexOf(PARAM_FORM_CONTEXT_SEPERATOR);
        int startPos = (formContextPos == -1) ? 0 : formContextPos + 1;

        String urlWithoutFormContext = withoutPrefix.substring(startPos);

        return urlWithoutFormContext;
    }

    private static String stripSelectionPrefix(String url) {
        if (!isSelectionBindingUrl(url)) {
            throw new IllegalStateException("Tried to process a binding url a that was not a valid url '" + url + "'");
        }
        return url.substring(SELECTION_BINDING_PREFIX.length());
    }

    private static String stripFoldingPrefix(String url) {
        if (!isFoldingBindingUrl(url)) {
            throw new IllegalStateException("Tried to process a binding url a that was not a valid url '" + url + "'");
        }
        return url.substring(FOLDING_BINDING_PREFIX.length());
    }

    public static boolean isSelectionBindingUrl(String url) {
        return url.startsWith(SELECTION_BINDING_PREFIX);
    }

    public static boolean isFoldingBindingUrl(String url) {
        return url.startsWith(FOLDING_BINDING_PREFIX);
    }

    /**
     * This is different to extractModelXPathFrom selection binding url because selection bindings never have properties
     * so you cannot use the propertyseperator to determine the xpath bit
     * @param url
     * @return
     */
    public static String extractModelXPathFromPropertyBindingUrl(String url) {
        String withoutPrefix = stripPrefix(url);
        int formContextPos = withoutPrefix.indexOf(PARAM_FORM_CONTEXT_SEPERATOR);
        int startPos = (formContextPos == -1) ? 0 : formContextPos + 1;

        String urlWithoutFormContext = withoutPrefix.substring(startPos);

        int propertySeperatorPos = urlWithoutFormContext.indexOf(PARAM_SEPERATOR);

        String xpath = (propertySeperatorPos == -1) ? "/" : urlWithoutFormContext.substring(0, propertySeperatorPos);
        return xpath;
    }

    private static String stripPrefix(String url) {
        if (!isPropertyBindingUrl(url)) {
            throw new IllegalStateException("Tried to process a binding url a that was not a valid url '" + url + "'");
        }
        return url.substring(PROPERTY_BINDING_PREFIX.length());
    }




}
