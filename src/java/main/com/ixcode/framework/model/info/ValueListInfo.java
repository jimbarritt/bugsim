/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import java.util.ArrayList;
import java.util.List;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.4 $
 *          $Id: ValueListInfo.java,v 1.4 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class ValueListInfo {

    public ValueListInfo() {
    }

    public void addElement(ValueListElementInfo element) {
        _elements.add(element);
    }
    public void addElement(Object elementValue) {
        addElement(new ValueListElementInfo(elementValue));
    }

    /**
     *
     * @return a list of java.lang.String elements.
     */
    public List getElements() {
        return _elements;
    }

    public String toString() {
        return _elements.toString();
    }

    private List _elements = new ArrayList();
    public static final ValueListInfo NULL_LIST = new ValueListInfo();
}
