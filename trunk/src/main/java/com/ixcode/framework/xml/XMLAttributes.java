/**
 * (c) planet-ix ltd 2005
 */
package com.ixcode.framework.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Description : ${CLASS_DESCRIPTION}
 */
public class XMLAttributes {

    public XMLAttributes() {
    }

    

    public void add(String name, Object value) {
        _attributes.put(name, value);
        _names.add(name);
    }

    public List getAttributeNames() {
        return _names;

    }


    public Object getValue(String name) {
        return _attributes.get(name);
    }

    

    private Map _attributes = new HashMap();
    private List _names = new ArrayList();
}
