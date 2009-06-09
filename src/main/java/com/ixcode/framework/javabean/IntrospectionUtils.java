/**
 * (c)Copyright 2003-2004 Systems Union Ltd
 */
package com.ixcode.framework.javabean;

import org.apache.commons.beanutils.PropertyUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

import com.ixcode.framework.javabean.convert.*;


public class IntrospectionUtils {

    private IntrospectionUtils() {
    }

    /**
     * NOTE: it does not include system properties
     */
    public static List extractSimpleProperties(PropertyDescriptor[] propertyDescriptors, Set systemPropertyNames) {
        List simpleProperties = new ArrayList();
        for (int i = 0; i < propertyDescriptors.length; ++i) {
            PropertyDescriptor p = propertyDescriptors[i];
            // AAC: Hmmm - indexed properties blow this up ...
            if ((p.getPropertyType() != null) && (!isSystemProperty(p, systemPropertyNames)) && (!isAssociation(p))) {
                simpleProperties.add(p);
            }
        }
        return simpleProperties;
    }

    public static boolean isSystemProperty(PropertyDescriptor p, Set systemPropertyNames) {
        if (p.getName().equals("class") || systemPropertyNames.contains(p.getName())) {
            return true;
        }
        return false;
    }

    /**
     * If the type is either a collection or extends DomainObject, it is an association.
     *
     * @param p
     * @return
     */
    public static boolean isAssociation(PropertyDescriptor p) {
        return (Collection.class.isAssignableFrom(p.getPropertyType()));
    }

    public static Object getPropertyValue(Object javaBean, String propertyName) throws JavaBeanException {
        try {
            return PropertyUtils.getProperty(javaBean, propertyName);
        } catch (IllegalAccessException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e);
        } catch (InvocationTargetException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e.getTargetException());
        } catch (NoSuchMethodException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e);
        }
    }

    public static Class getPropertyType(Object javaBean, String propertyName) throws JavaBeanException {
        try {
            return PropertyUtils.getPropertyType(javaBean, propertyName);
        } catch (IllegalAccessException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e);
        } catch (InvocationTargetException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e);
        } catch (NoSuchMethodException e) {
            throw new JavaBeanException(getJavaBeanName(javaBean), e);
        }
    }

    public static Class getPropertyType(Class beanClass, String propertyName) throws JavaBeanException {
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
        PropertyDescriptor propertyDescriptor = null;
        for (int i = 0; i < propertyDescriptors.length; ++i) {
            if (propertyDescriptors[i].getName().equals(propertyName)) {
                propertyDescriptor = propertyDescriptors[i];
            }
        }
        if (propertyDescriptor == null) {
            throw new JavaBeanException(getJavaBeanName(beanClass), new IllegalArgumentException("No property called '" + propertyName + "' on class '" + beanClass.getName() + "'"));
        }
        return propertyDescriptor.getPropertyType();

    }

    public static void setPropertyValue(Object javaBean, String sourcePropertyName, Object value) throws JavaBeanException {
        try {
            PropertyUtils.setProperty(javaBean, sourcePropertyName, value);
        } catch (IllegalAccessException e) {
            throw new JavaBeanException(e);
        } catch (InvocationTargetException e) {
            throw new JavaBeanException(e);
        } catch (NoSuchMethodException e) {
            throw new JavaBeanException(e);
        } catch (IllegalArgumentException e) {
            throw new JavaBeanException(e);
        }
    }

    public static List getPropertyNames(String domainObjectClassName, Set systemPropertyNames) {
        try {
            Class domainObjectClass = Thread.currentThread().getContextClassLoader().loadClass(domainObjectClassName);
            return getPropertyNames(domainObjectClass, systemPropertyNames);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Class not found : '" + domainObjectClassName + "'");
        }
    }

    public static List getPropertyNames(Class domainObjectClass, Set systemPropertyNames) {
        PropertyDescriptor[] allProperties = PropertyUtils.getPropertyDescriptors(domainObjectClass);
        List simpleProperties = extractSimpleProperties(allProperties, systemPropertyNames);
        List propertyNames = new ArrayList();
        for (Iterator itr = simpleProperties.iterator(); itr.hasNext();) {
            PropertyDescriptor p = (PropertyDescriptor)itr.next();
            propertyNames.add(p.getName());
        }
        return propertyNames;
    }

    public static boolean isDate(Class propertyType) {
        return (java.util.Date.class.isAssignableFrom(propertyType));
    }

    public static boolean isLong(Class propertyType) {
        return long.class.isAssignableFrom(propertyType) || Long.class.isAssignableFrom(propertyType);
    }

    public static boolean isInteger(Class propertyType) {
        return int.class.isAssignableFrom(propertyType) || Integer.class.isAssignableFrom(propertyType);
    }

    public static String getJavaBeanName(Object bean) {
        return getJavaBeanName(bean.getClass());
    }

    public static String getJavaBeanName(Class beanClass) {
        String fullName = beanClass.getName();
        return fullName.substring(fullName.lastIndexOf('.') + 1);
    }

    public static boolean isBoolean(Class propertyType) {
        return boolean.class.isAssignableFrom(propertyType) || Boolean.class.isAssignableFrom(propertyType);
    }

    public static boolean isFloat(Class propertyType) {
        return float.class.isAssignableFrom(propertyType) || Float.class.isAssignableFrom(propertyType);
    }

    public static boolean isBigDecimal(Class propertyType) {
        return BigDecimal.class.isAssignableFrom(propertyType);
    }


    public static boolean isDouble(Class propertyType) {
        return double.class.isAssignableFrom(propertyType) || Double.class.isAssignableFrom(propertyType);
    }

    public static boolean isNumeric(Class propertyType) {
        return isDouble(propertyType) || isLong(propertyType) || isBigDecimal(propertyType) || isFloat(propertyType) || isInteger(propertyType);
    }

    public static String createDefaultLabelFromPropertyName(String propertyName) {
        char[] chars = propertyName.toCharArray();
        StringBuffer sb = new StringBuffer();

        sb.append(Character.toUpperCase(chars[0]));
        for (int i = 1; i < chars.length; ++i) {
            if (Character.isUpperCase(chars[i])) {
                sb.append(" ");
            }
            sb.append(chars[i]);
        }
        return sb.toString();

    }

    public static int getDefaultDisplayWidthForPropertyType(Class propertyType) {

        int length = 10;
        if (isNumeric(propertyType)) {
            length = 8;
        }
        return length;

    }

    public static TextAlignment getDefaultTextAlignmentForType(Class propertyType) {

        TextAlignment align = TextAlignment.LEFT;
        if (isNumeric(propertyType)) {
            align = TextAlignment.RIGHT;
        }
        return align;

    }

    public static void debugProperties(Class beanClass) {


        List propertyDescriptors = Arrays.asList(PropertyUtils.getPropertyDescriptors(beanClass));

        for (Iterator itr = propertyDescriptors.iterator(); itr.hasNext();) {
            PropertyDescriptor property = (PropertyDescriptor)itr.next();

            System.out.println("Property : " + property.getName() + ", " + property.getReadMethod().getName() + ", type " + property.getPropertyType());

        }
    }

    public static Method getMethod(String parentClass, String methodName, Class[] parameterClasses) throws ClassNotFoundException, NoSuchMethodException {
        Class parent = Thread.currentThread().getContextClassLoader().loadClass(parentClass);

        return parent.getMethod(methodName, parameterClasses);
    }

    public static String getObjectId(Object object) {
        String s = "#null#";
        if (object != null) {
            s = Integer.toHexString(((Object)object).hashCode());
        }
        return s;
    }

    public static String getShortClassName(Class aClass) {
        String longName = aClass.getName();
        return longName.substring(longName.lastIndexOf(".")+1);

    }
                                                                   
    public static IValueConverter getValueConverter(Class type) {
        return (IValueConverter)CONVERTER_REGISTRY.get(type.getName());
    }

    private static Map CONVERTER_REGISTRY = new HashMap();

    static {
        CONVERTER_REGISTRY.put(Double.class.getName(),  new DoubleConverter());
        CONVERTER_REGISTRY.put(Long.class.getName(),  new LongConverter());
        CONVERTER_REGISTRY.put(Integer.class.getName(),  new IntegerConverter());
        CONVERTER_REGISTRY.put(Boolean.class.getName(),  new BooleanConverter());
        CONVERTER_REGISTRY.put(String.class.getName(),  new StringConverter());
    }

}