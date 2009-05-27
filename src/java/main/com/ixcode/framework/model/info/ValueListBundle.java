/*
 * Copyright 2003 Systems Union Holdings Ltd. All rights reserved.
 * Systems Union Proprietary / Confidential. Use is subject to license terms.
 */
package com.ixcode.framework.model.info;

import java.util.HashMap;
import java.util.Map;

/**
 * Class or Interface description.
 * 
 * @author Jim Barritt
 * @version $Revision: 1.2 $
 *          $Id: ValueListBundle.java,v 1.2 2004/09/17 13:36:01 rdjbarri Exp $
 */
public class ValueListBundle {

    public void addLabel(Object value, String label) {
        if (!_labels.containsKey(value)) {
            _labels.put(value, label);
        }
    }

    public String getLabel(Object value) {
        if (!_labels.containsKey(value)) {
            throw new IllegalStateException("Could not find a label for value list value " + value);
        }
        return (String)_labels.get(value);
    }

    private Map _labels = new HashMap();
}
