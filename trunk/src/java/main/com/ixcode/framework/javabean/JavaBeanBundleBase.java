/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.javabean;

import com.ixcode.framework.model.info.ModelBundle;
import com.ixcode.framework.model.info.PropertyBundle;
import com.ixcode.framework.model.info.ValueListBundle;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.1 $
 *          $Id: JavaBeanBundleBase.java,v 1.1 2004/08/11 12:08:26 rdjbarri Exp $
 */
public class JavaBeanBundleBase implements IJavaBeanBundle{



    public JavaBeanBundleBase() {
    }

    public ModelBundle getModelBundle(Locale locale) {
        return (ModelBundle)_modelBundles.get(locale);
    }

    public PropertyBundle getPropertyBundle(String propertyName, Locale locale) {
        Map localisedPropertyBundles = (Map)_propertyBundles.get(locale);
        if (localisedPropertyBundles == null) {
            return null;
        }
        return (PropertyBundle)localisedPropertyBundles.get(propertyName);
    }

    public ValueListBundle getValueListBundle(String propertyName, Locale locale) {
        Map localisedValueListBundles = (Map)_valueListBundles.get(locale);
        if (localisedValueListBundles == null) {
            return null;
        }
        return (ValueListBundle)localisedValueListBundles.get(propertyName);
    }

    protected void registerModelBundle(Locale locale, ModelBundle modelBundle) {
        if (!_modelBundles.containsKey(locale)) {
            _modelBundles.put(locale, modelBundle);
        }
    }

    protected void registerPropertyBundle(Locale locale, String propertyName, PropertyBundle propertyBundle) {
        if (!_propertyBundles.containsKey(locale)) {
            _propertyBundles.put(locale, new HashMap());
        }
        Map localisedPropertyBundles = (Map)_propertyBundles.get(locale);
        if (!localisedPropertyBundles.containsKey(propertyName)) {
            localisedPropertyBundles.put(propertyName, propertyBundle);
        }
    }

    protected void registerValueListBundle(Locale locale, String propertyName, ValueListBundle bundle) {
        if (!_valueListBundles.containsKey(locale)) {
            _valueListBundles.put(locale, new HashMap());
        }
        Map localisedValueListBundles = (Map)_valueListBundles.get(locale);
        if (!localisedValueListBundles.containsKey(propertyName)) {
            localisedValueListBundles.put(propertyName, bundle);
        }
    }





    private Map _modelBundles = new HashMap();
    private Map _propertyBundles= new HashMap();
    private Map _valueListBundles = new HashMap();
}
