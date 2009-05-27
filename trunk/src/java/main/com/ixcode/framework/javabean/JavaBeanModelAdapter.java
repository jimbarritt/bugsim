/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

import com.ixcode.framework.io.IOExceptionWithCause;
import com.ixcode.framework.javabean.format.JavaBeanFormatter;
import com.ixcode.framework.javabean.format.JavaBeanParseException;
import com.ixcode.framework.javabean.message.JavaBeanMessageSource;
import com.ixcode.framework.message.IMessageSource;
import com.ixcode.framework.model.IModelAdapter;
import com.ixcode.framework.model.ModelAdapterBase;
import com.ixcode.framework.model.ModelAdapterRuntimeException;
import com.ixcode.framework.model.info.*;
import com.ixcode.framework.model.validation.ValidationFailure;
import com.ixcode.framework.model.validation.ValidationResult;
import com.ixcode.framework.web.taglib.html.CheckBoxTagHtmlWriter;
import com.ixcode.framework.web.taglib.html.SelectTagHtmlWriter;
import com.ixcode.framework.web.taglib.html.TextBoxTagHtmlWriter;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Uses standard (well beanutils) introspection to expose a java bean to the binding framework
 *
 * @author Jim Barritt
 * @version $Revision: 1.14 $
 *          $Id: JavaBeanModelAdapter.java,v 1.14 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class JavaBeanModelAdapter extends ModelAdapterBase {

    public JavaBeanModelAdapter() {
        _messageSource = JavaBeanMessageSource.INSTANCE;
    }

    public boolean canAdapt(Class model) {
        return true;
    }


    /**
     * @todo not sure it should throw a JspException  - it does it because Jsp's are the only place it is used.
     */
    public Collection getAssociatedModels(Object model, String associationName) throws IOException {
        if ((model instanceof Collection) && associationName == null) {
            return (Collection)model;
        }
        return (Collection)getPropertyValue(model, associationName);
    }

    public Object getAssociatedModel(Object model, String associationName) throws IOException {
        return getPropertyValue(model, associationName);
    }

    public ValidationResult setAssociatedModel(Object model, String associationName, Object associatedModel, Locale locale) throws IOException {
        return setPropertyValue(model, associationName, associatedModel, null);
    }

    public ValidationResult setAssociatedModels(Object model, String associationName, Collection models, Locale locale) throws IOException{
        return setPropertyValue(model, associationName, models, locale);
    }


    public void registerInfo(Class beanClass, IModelInfo info) {
        _infos.put(beanClass.getName(), info);
    }

    public void registeBundle(Class beanClass, IJavaBeanBundle javaBeanBundle) {
        _bundles.put(beanClass.getName(), javaBeanBundle);
    }

    public void registerSystemPropertyNames(Set systemPropertyNames) {
        _systemPropertyNames = systemPropertyNames;
    }


    public String getPropertyStyleClasses(Object model, String propertyName) {
        return null;
    }

    public String getPropertyStyle(Object model, String propertyName) {
        return null;
    }


    /**
     * Stupidly simple method that does toString(). Could easily use the ConvertUtils to
     * sort this out.
     */
    public String getPropertyValueAsString(Object model, String propertyName, Locale locale) {
        try {
            return _formatter.getPropertyValueAsString(model, propertyName, locale);
        } catch (FormatterException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * @todo tidy up exception handling!
     * @param model
     * @param propertyName
     * @param value
     * @param locale
     * @return
     */
    public ValidationResult setPropertyValueAsString(Object model, String propertyName, String value, Locale locale) {
        ValidationResult result = ValidationResult.VALID_RESULT;
        try {
            Object parsedValue = _formatter.parseStringToPropertyValue(model, propertyName, locale, value);
            result = setPropertyValue(model, propertyName, parsedValue, null);
            return result;
        } catch (FormatterException e) {
            throw new RuntimeException(e);
        } catch (JavaBeanParseException e) {
            try {
                result = createParseValidationResult(locale, _messageSource, model, propertyName, value, e.getMessageKey());
            } catch (IOException e1) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public static ValidationResult createParseValidationResult(Locale locale, IMessageSource messageSource, Object model, String propertyName, String value, String messageKey) throws IOException {
        ValidationResult result;
        String pattern = messageSource.getMessage(locale, messageKey);
        Class propertyType;
        try {
            propertyType = IntrospectionUtils.getPropertyType(model, propertyName);
        } catch (JavaBeanException e1) {
            throw new IOExceptionWithCause(e1);
        }
        String message = (pattern == null) ? messageKey : MessageFormat.format(pattern, new Object[]{value});
        result = new ValidationResult(new ValidationFailure(message));
        return result;
    }

    public Class getPropertyType(Object model, String propertyName)  {
        try {
            return IntrospectionUtils.getPropertyType(model, propertyName);
        } catch (JavaBeanException e) {
            throw new RuntimeException(e);
        }
    }

    public Class getPropertyType(String modelType, String propertyname) {
        try {
            Class modelClass =Thread.currentThread().getContextClassLoader().loadClass(modelType);
            return IntrospectionUtils.getPropertyType(modelClass, propertyname);
        } catch (JavaBeanException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPropertyNumeric(Object model, String propertyName) {
        try {
            Class propertyType = IntrospectionUtils.getPropertyType(model, propertyName);
            return IntrospectionUtils.isNumeric(propertyType);
        } catch (JavaBeanException e) {
            throw new JavaBeanRuntimeException(e);
        }
    }

    public boolean isInfoRegistered(Class modelClass) {
        return _infos.containsKey(modelClass.getName());
    }


    public ValidationResult validateModel(Object model) {
        return ValidationResult.VALID_RESULT;
    }


    /**
     * Without some extra metadat there is no way we can answer this question.
     */
    public boolean isPropertyLookup(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return getModelInfo(model).isPropertyLookup(model, propertyName);
        }
        return false;
    }

    public LookupInfo getLookupInfo(Object model, String propertyName) {
        if (!hasModelInfo(model)) {
            throw new IllegalArgumentException("Could not find lookup info for property '" + propertyName + "' on model " + model);
        }
        return getModelInfo(model).getLookupInfo(model, propertyName);
    }

    public boolean isPropertyReadOnly(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return getModelInfo(model).isPropertyReadOnly(model, propertyName);
        }
        return false;
    }


    /**
     * Without some extra metadat there is no way we can answer this question.
     */
    public boolean isPropertyMandatory(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return getModelInfo(model).isPropertyMandatory(model, propertyName);
        }
        return false;
    }

    /**
     * Without some extra metadat there is no way we can answer this question.
     */
    public boolean isPropertyHidden(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return getModelInfo(model).isPropertyHidden(model, propertyName);
        }
        return false;
    }


    public ValueListInfo getValueListInfo(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return getModelInfo(model).getValueListInfo(model, propertyName);
        }
        return ValueListInfo.NULL_LIST;
    }


    public boolean isPropertyValueList(Object model, String propertyName) {
        if (hasModelInfo(model)) {
            return ((IModelInfo)_infos.get(model.getClass().getName())).isPropertyValueList(model, propertyName);
        }
        return false;
    }


    public Object getPropertyValue(Object model, String propertyName) throws IOException {
        try {
            return IntrospectionUtils.getPropertyValue(model, propertyName);
        } catch (JavaBeanException e) {
            throw new IOExceptionWithCause(e);
        }
    }

    public ValidationResult setPropertyValue(Object model, String propertyName, Object value, Locale locale) throws IOException {
        try {
            IntrospectionUtils.setPropertyValue(model, propertyName, value);
        } catch (JavaBeanException e) {
            throw new IOExceptionWithCause(e);
        }
        return ValidationResult.VALID_RESULT;
    }

    public List getPropertyNames(String modelType) {
        if (hasModelInfo(modelType)) {
            return getModelInfo(modelType).getPropertyNames(modelType);
        }
        return IntrospectionUtils.getPropertyNames(modelType, _systemPropertyNames);
    }


    private boolean hasModelInfo(Object model) {
        return hasModelInfo(model.getClass().getName());
    }

    private boolean hasModelInfo(String modelType) {
        return _infos.containsKey(modelType);
    }

    private IModelInfo getModelInfo(Object model) {
        return getModelInfo(model.getClass().getName());
    }

    private IModelInfo getModelInfo(String modelType) {
        return ((IModelInfo)_infos.get(modelType));
    }

    public String getModelId(Object model) {
        try {
            Object idValue = IntrospectionUtils.getPropertyValue(model, _idPropertyName);
            return "" + idValue;
        } catch (JavaBeanException e) {
            throw new IllegalStateException("JavaBeanModelAdapter.getModelId() - Could not find property '" + _idPropertyName + " on javabean of class '" + model.getClass().getName() + "'");
        }
    }

    public void setIdPropertyName(String idPropertyName) {
        _idPropertyName = idPropertyName;
    }

    public ModelBundle getModelBundle(String modelType, Locale locale) {
        ModelBundle modelBundle = null;
        if (hasBundle(modelType)) {
            modelBundle = getBundle(modelType).getModelBundle(locale);
        } else if (hasModelInfo(modelType)) {
            IModelInfo info = getModelInfo(modelType);
            modelBundle = info.getModelBundle(modelType, locale);
        }
        if (modelBundle == null) {
            modelBundle = new ModelBundle(modelType);
        }
        return modelBundle;
    }

    public PropertyBundle getPropertyBundle(String modelType, String propertyName, Locale locale) {
        PropertyBundle propertyBundle = null;

        if (hasBundle(modelType)) {
            propertyBundle = getBundle(modelType).getPropertyBundle(propertyName, locale);
        } else if (hasModelInfo(modelType)) {
            IModelInfo info = getModelInfo(modelType);
            propertyBundle = info.getPropertyBundle(modelType, propertyName, locale);
        }

        if (propertyBundle == null) {
            propertyBundle = createDefaultPropertyBundle(modelType,propertyName);
        }
        return propertyBundle;
    }

    private PropertyBundle createDefaultPropertyBundle(String modelType, String propertyName) {
        try {
            Class beanClass = Thread.currentThread().getContextClassLoader().loadClass(modelType);

            Class propertyType = IntrospectionUtils.getPropertyType(beanClass, propertyName);
            String label = IntrospectionUtils.createDefaultLabelFromPropertyName(propertyName);
            int size= IntrospectionUtils.getDefaultDisplayWidthForPropertyType(propertyType);
            TextAlignment textAlign = IntrospectionUtils.getDefaultTextAlignmentForType(propertyType);
            return new PropertyBundle(label, label, size, textAlign);

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (JavaBeanException e) {
            throw new RuntimeException(e);
        }


    }

    public ValueListBundle getValueListBundle(Object model, String propertyName, Locale locale) {
        ValueListBundle vlBundle = null;
        String modelType = getModelType(model);
        if (hasBundle(model.getClass().getName())) {
            IJavaBeanBundle bundle = getBundle(model.getClass().getName());
            vlBundle = bundle.getValueListBundle(propertyName, locale);
        } else if (hasModelInfo(modelType)) {
            IModelInfo info = getModelInfo(modelType);
            vlBundle = info.getValueListBundle(modelType, propertyName, locale);
        }

        return vlBundle;
    }

    private IJavaBeanBundle getBundle(String modelType) {
        return (IJavaBeanBundle)_bundles.get(modelType);
    }

    private boolean hasBundle(String modelType) {
        return _bundles.containsKey(modelType);
    }



    public String getModelType(Class beanClass) {
        return beanClass.getName();
    }
    public String getModelType(Object model) {
        if (model == null) {
            throw new IllegalStateException("Tried to pass null to getModelType");
        }
        return model.getClass().getName();
    }

    public void setMessageSource(IMessageSource messageSource) {
        _messageSource = messageSource;
    }

    public JavaBeanFormatter getFormatter() {
        return _formatter;
    }



    /**
     * Yeah this code is pretty rough, but should do the trick!:)
     */
    public Class getPropertyHtmlWriter(Object model, String propertyName) {

        Class htmlWriterClass = null;
        if (hasModelInfo(model)) {
            htmlWriterClass = getModelInfo(model).getPropertyHtmlWriter(model, propertyName);
        }
        if (htmlWriterClass == null) {
            htmlWriterClass = getHtmlWriterClassForPropertyType(model, propertyName, this);
        }
        return htmlWriterClass;
    }

    public static Class getHtmlWriterClassForPropertyType(Object model, String propertyName, IModelAdapter modelAdapter) {
        Class htmlWriterClass = null;
        try {
            if (htmlWriterClass == null) {
                htmlWriterClass = (Class)HTML_WRITERS.get(IntrospectionUtils.getPropertyType(model, propertyName).getName());
            }

            if (htmlWriterClass == null && modelAdapter.isPropertyValueList(model, propertyName)) {
                htmlWriterClass = SelectTagHtmlWriter.class;
            }

            if (htmlWriterClass == null) {
                htmlWriterClass = TextBoxTagHtmlWriter.class;
            }
            return htmlWriterClass;
        } catch (JavaBeanException e) {
            throw new ModelAdapterRuntimeException(e);
        }
    }



    private static final Map HTML_WRITERS = new HashMap();

    static {
        HTML_WRITERS.put(Boolean.class.getName(), CheckBoxTagHtmlWriter.class);
        HTML_WRITERS.put(boolean.class.getName(), CheckBoxTagHtmlWriter.class);
    }

    private JavaBeanFormatter _formatter = new JavaBeanFormatter();
    public static final JavaBeanModelAdapter INSTANCE = new JavaBeanModelAdapter();


    private Map _infos = new HashMap();
    private String _idPropertyName = "id";
    private Map _bundles = new HashMap();
    private IMessageSource _messageSource;

    private Set _systemPropertyNames = new HashSet();
}
