/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;
import com.ixcode.framework.model.info.PropertyBundle;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 *  Description : Generic class that introspects a bean to find all properties you pass it.
 */
public class JavaBeanPropertyGroupPanel extends PropertyGroupPanel {

    public JavaBeanPropertyGroupPanel(JavaBeanModelAdapter modelAdapter,  Class beanClass, List propertyNames) {
           this(modelAdapter,  null, beanClass, propertyNames) ;
       }

    public JavaBeanPropertyGroupPanel(JavaBeanModelAdapter modelAdapter, String groupName, Class beanClass, List propertyNames) {
        super(groupName);
        _modelAdapter = modelAdapter;

        initProperties(beanClass, propertyNames);
    }

    private void initProperties(Class beanClass, List propertyNames) {
        for (Iterator itr = propertyNames.iterator(); itr.hasNext();) {
            String propertyName = (String)itr.next();
            String modelType = _modelAdapter.getModelType(beanClass);
            PropertyBundle bundle = _modelAdapter.getPropertyBundle(modelType, propertyName, Locale.getDefault());

            super.addPropertyEditor(propertyName, bundle.getLabel(), bundle.getDisplayCharacterCount(), bundle.getTextAlignment(), 40);
        }

    }



    private JavaBeanModelAdapter _modelAdapter;
}
