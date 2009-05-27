/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.binding;

import org.apache.commons.jxpath.JXPathContext;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * If you have custom domain object classes (which we hav ein evo for
 * dealing with soft properties) you can register custom handlers for these
 * classes with JXPath.
 * 
 * @author Jim Barritt
  * @version $Revision: 1.1 $
 *          $Id: ModelXPathResolver.java,v 1.1 2004/08/11 12:08:23 rdjbarri Exp $
 */
public class ModelXPathResolver {


    public ModelXPathResolver(Object rootModel) {
        _pathContext = JXPathContext.newContext(rootModel);
        _pathContext.setLenient(true);
        _rootModel = rootModel;
    }

    /**
     * @todo maybe there is already a way to deal witht he root domain being a collection ?
     * @todo this is a nasteee way of doing it - we need to work out how to sort it...
     * /[10]/someCollection[1]
     * @param xpath
     * @return
     */
    public Object getModel(String xpath) {
        Object model;
        if (xpath.length() == 0) {
            model =_pathContext.getValue("/");
        } else if (xpath.startsWith("/[")){ // must be a collection in the root ...
            int index = Integer.parseInt(xpath.substring(2, xpath.indexOf(']')));
            Collection rootCollection = (Collection)_pathContext.getValue("/");
            model = findModel(rootCollection, index);
            String childPath = xpath.substring(xpath.indexOf(']')+1);
            if (childPath.startsWith("/")) {
                JXPathContext childContext = JXPathContext.newContext(model);
                model = childContext.getValue(childPath);
            }
        } else {
            model = _pathContext.getValue(xpath);
        }
        return model;
    }

    private Object findModel(Collection rootCollection, int index) {
        if (rootCollection instanceof List) {
            return ((List)rootCollection).get(index-1);
        }
        int i = 1;
        for (Iterator itr = rootCollection.iterator(); itr.hasNext();) {
            Object o = (Object)itr.next();
            if (i==index) {
                return o;
            }
            ++i;
        }
        throw new IllegalArgumentException("Could not find index [" + index + "] in collection " + rootCollection);
    }

    private JXPathContext _pathContext;
    private Object _rootModel;


}
