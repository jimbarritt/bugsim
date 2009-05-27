package com.ixcode.framework.xml.sax.speedsax;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class NodeMapping {

    public NodeMapping(String nodeName) {
        _nodeName = nodeName;
    }

    public NodeMapping(String nodeName, String propertyName, Class propertyType, Method setterMethod) {
        _propertyContentMapping = new PropertyXmlMapping(propertyName, propertyType, setterMethod);
        _nodeName = nodeName;

    }

    public String getNodeName() {
        return _nodeName;
    }


    public void addAttributeMapping(PropertyAttributeMapping attributeMapping) {
        _attributeMappings.put(attributeMapping.getAttributeName(), attributeMapping);
        _attributeMappingList.add(attributeMapping);
    }

    public PropertyAttributeMapping getAttributeMapping(String attributeName) {
        return (PropertyAttributeMapping)_attributeMappings.get(attributeName);
    }

    public boolean hasAttributeMapping(String attributeName) {
        return _attributeMappings.containsKey(attributeName);
    }


    public PropertyXmlMapping getPropertyContentMapping() {
        return _propertyContentMapping;
    }

    public boolean hasPropertyMapping() {
        return _propertyContentMapping != null;
    }

    public List getAttributeMappings() {
        return _attributeMappingList;
    }

    private String _nodeName;
    private Map _attributeMappings = new HashMap();
    private PropertyXmlMapping _propertyContentMapping;
    private List _attributeMappingList = new ArrayList();
}
