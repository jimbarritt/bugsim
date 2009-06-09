/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.swing.property;

import com.ixcode.framework.javabean.JavaBeanModelAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class PropertySheetFactory {
    public void registerPropertySheets(Class beanClass,List propertySheetClassNames) throws ClassNotFoundException {
        for (Iterator itr = propertySheetClassNames.iterator(); itr.hasNext();) {
            String name = (String)itr.next();
            Class sheetClass = Thread.currentThread().getContextClassLoader().loadClass(name);
            registerPropertySheet(beanClass, sheetClass);
        }
    }

    public static final PropertySheetFactory INSTANCE = new PropertySheetFactory();

    private PropertySheetFactory() {

    }

    public void registerPropertySheet(Class beanClass, Class propertySheetClass)  {
        if (!_registry.containsKey(beanClass.getName()))  {
            _registry.put(beanClass.getName(), new ArrayList());
        }

        List sheets = (List)_registry.get(beanClass.getName());
        sheets.add(propertySheetClass);
    }

    public List createPropertySheets(Class beanClass, JavaBeanModelAdapter modelAdapter) {
        List sheetClasses;

        if (!_registry.containsKey(beanClass.getName())) {
            sheetClasses = new ArrayList();
        } else {
            sheetClasses =  (List)_registry.get(beanClass.getName());
        }

        List sheets = new ArrayList();
        for (Iterator itr = sheetClasses.iterator(); itr.hasNext();) {
            Class sheetClass = (Class)itr.next();
            try {
                Constructor c = sheetClass.getConstructor(new Class[]{JavaBeanModelAdapter.class});
                sheets.add(c.newInstance(new Object[]{modelAdapter}));
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);                
            }
        }
        return sheets;
    }

    private Map _registry = new HashMap();
}
