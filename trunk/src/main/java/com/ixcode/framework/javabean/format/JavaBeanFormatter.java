/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean.format;

import com.ixcode.framework.javabean.FormatterException;
import com.ixcode.framework.javabean.IntrospectionUtils;
import com.ixcode.framework.javabean.JavaBeanException;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This containsCoord some basic formatting based on type of property.
 * which is how the javabean stuff works,
 *
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: JavaBeanFormatter.java,v 1.4 2004/09/10 13:33:04 rdjbarri Exp $
 */
public class JavaBeanFormatter {

    public JavaBeanFormatter() {
        initialiseBasicFormats();        
    }




    /**
     * This needs a map of parsers to type so we dont have to have this big if statement.
     */
    public Object parseStringToPropertyValue(Object model, String propertyName, Locale locale, String sourceValue) throws JavaBeanParseException, FormatterException {
        try {
            PropertyDescriptor propertyDescriptor = PropertyUtils.getPropertyDescriptor(model, propertyName);
            if (propertyDescriptor == null) {
                throw new IllegalStateException("Could not find a property in model " + model.getClass().getName() + " for property name '" + propertyName + "'");
            }

            Class propertyType = propertyDescriptor.getPropertyType();

            IJavaBeanValueFormat format = getFormat(locale, propertyType);

            return format.parse(sourceValue);

        } catch (IllegalAccessException e) {
            throw new FormatterException(e);
        } catch (InvocationTargetException e) {
            throw new FormatterException(e);
        } catch (NoSuchMethodException e) {
            throw new FormatterException(e);
        }

    }

    public String getPropertyValueAsString(Object javaBean, String propertyName, Locale locale) throws FormatterException {
        try {
            Object value = PropertyUtils.getProperty(javaBean, propertyName);

            String formatted;

            if (value instanceof String) {
                formatted = (String)value;
            } else if (value == null) {
                formatted = "";
            } else {
                Class propertyType = IntrospectionUtils.getPropertyType(javaBean, propertyName);
                IJavaBeanValueFormat format = getFormat(locale, propertyType);
                formatted = format.format(value);

            }

            return formatted;

        } catch (IllegalAccessException e) {
            throw new FormatterException(IntrospectionUtils.getJavaBeanName(javaBean), e);
        } catch (InvocationTargetException e) {
            throw new FormatterException(IntrospectionUtils.getJavaBeanName(javaBean), e.getCause());
        } catch (NoSuchMethodException e) {
            throw new FormatterException(IntrospectionUtils.getJavaBeanName(javaBean), e);
        } catch (JavaBeanException e) {
            throw new FormatterException(IntrospectionUtils.getJavaBeanName(javaBean), e);
        }
    }


    public IJavaBeanValueFormat getFormat(Locale locale, Class valueClass) {

        IJavaBeanValueFormat format = null;
        format = searchClasses(valueClass, locale);

        if (format == null) {
            format = new ToStringFormat(valueClass);
            _formats.put(new ValueFormatKey(locale, valueClass), format);
        }
        return format;
    }


    private IJavaBeanValueFormat searchClasses(Class valueClass, Locale locale) {
        IJavaBeanValueFormat format = null;
        Class currentClass = valueClass;
        while (currentClass != null) {
            ValueFormatKey key = new ValueFormatKey(locale, currentClass);
            if (_formats.containsKey(key)) {
                format = (IJavaBeanValueFormat)_formats.get(key);
            }  else {
                key = new ValueFormatKey(DEFAULT_LOCALE, currentClass);
                if (_formats.containsKey(key)) {
                   format = (IJavaBeanValueFormat)_formats.get(key);
                } else {
                    format = searchInterfaces(currentClass, locale);
                }
            }

            if (format == null) {
               currentClass = currentClass.getSuperclass();
            } else {
                currentClass = null;
            }

        }

        return format;
    }


    private IJavaBeanValueFormat searchInterfaces(Class valueClass, Locale locale) {
        Class[] ifcs = valueClass.getInterfaces();
        IJavaBeanValueFormat format = null;
        for (int i=0;(i<ifcs.length) && (format==null);++i) {
            Class ifc = ifcs[i];
            format = searchClasses(ifc, locale);
        }
        return format;
    }

    public void registerFormatForDefaultLocale(Class valueClass, IJavaBeanValueFormat valueFormat) {
        registerFormat(DEFAULT_LOCALE, valueClass, valueFormat);
    }

    public boolean isFormatRegisteredForDefaultLocale(Class valueClass) {
        return isFormatRegistered(DEFAULT_LOCALE, valueClass);
    }

    private boolean isFormatRegistered(Locale locale, Class valueClass) {
        ValueFormatKey key = new ValueFormatKey(locale, valueClass);
        return _formats.containsKey(key);
    }

    public void registerFormat(Locale locale, Class valueClass, IJavaBeanValueFormat valueFormat) {
        ValueFormatKey key = new ValueFormatKey(locale, valueClass);
        if (_formats.containsKey(key)) {
            throw new IllegalStateException("Trying to register a duplicate format for locale " + locale + ", valueClass " + valueClass.getName());
        }
        _formats.put(key, valueFormat);
    }

    private void initialiseBasicFormats() {
        registerFormat(DEFAULT_LOCALE, Double.class, new DoubleFormat());
        registerFormat(DEFAULT_LOCALE, double.class, new DoubleFormat());
        registerFormat(DEFAULT_LOCALE, Long.class, new LongFormat());
        registerFormat(DEFAULT_LOCALE, long.class, new LongFormat());
        registerFormat(DEFAULT_LOCALE, Integer.class, new IntegerFormat());
        registerFormat(DEFAULT_LOCALE, int.class, new IntegerFormat());
        registerFormat(DEFAULT_LOCALE, Short.class, new ShortFormat());
        registerFormat(DEFAULT_LOCALE, short.class, new ShortFormat());
        registerFormat(DEFAULT_LOCALE, Boolean.class, new BooleanFormat());
        registerFormat(DEFAULT_LOCALE, boolean.class, new BooleanFormat());
        registerFormat(DEFAULT_LOCALE, Date.class, new SimpleDateFormat(DEFAULT_LOCALE));
        registerFormat(DEFAULT_LOCALE, Collection.class, new SimpleCollectionFormat());
        _formats.putAll(EXTENSION_FORMATS);
    }

    public static boolean isExtensionFormatRegistered(Locale locale, Class valueClass) {
        ValueFormatKey key = new ValueFormatKey(locale, valueClass);
        return EXTENSION_FORMATS.containsKey(key);
    }

    public static void registerExtensionFormat(Locale locale, Class valueClass, IJavaBeanValueFormat format) {
        ValueFormatKey key = new ValueFormatKey(locale, valueClass);
        if (EXTENSION_FORMATS.containsKey(key)) {
            throw new IllegalStateException("Trying to register a duplicate format for locale " + locale + ", valueClass " + valueClass.getName());
        }
        EXTENSION_FORMATS.put(key, format);
    }

    private static final Map EXTENSION_FORMATS = new HashMap();


    private static final Locale DEFAULT_LOCALE = Locale.UK;
    private Map _formats = new HashMap();


}
