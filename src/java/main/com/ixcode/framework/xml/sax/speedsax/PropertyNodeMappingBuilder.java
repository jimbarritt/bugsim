package com.ixcode.framework.xml.sax.speedsax;


import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 */
public class PropertyNodeMappingBuilder {

    public PropertyNodeMappingBuilder(Class mappedClass) {
        _mappedClass = mappedClass;
    }


    /**
     * Creates a mapping and defaults the name of the property to the same as the node and the type to be a String.
     */
    public NodeMapping addNodeMapping(String nodeName) {
        return addNodeMapping(nodeName, nodeName);
    }

    public NodeMapping addEmptyNodeMapping(String nodeName) {
        NodeMapping mapping = new NodeMapping(nodeName);
        _mappings.put(mapping.getNodeName(), mapping);
        return mapping;
    }

    public NodeMapping addNodeMapping(String nodeName, String propertyName) {
        NodeMapping mapping = createNodeMapping(nodeName, _mappedClass, String.class, propertyName);
        _mappings.put(mapping.getNodeName(), mapping);
        return mapping;
    }

    public PropertyAttributeMapping addAttributeMapping(String nodeName, String attributeName) {
        return addAttributeMapping(nodeName, attributeName, attributeName, String.class);
    }

    public PropertyAttributeMapping addAttributeMapping(String nodeName, String attributeName, String propertyName, Class propertyType) {
        NodeMapping nodeMapping = getNodeMapping(nodeName);
        if (nodeMapping == null) {
            nodeMapping = addEmptyNodeMapping(nodeName);
        }
        PropertyAttributeMapping mapping = createAttributeMapping(_mappedClass, propertyType, attributeName, propertyName);
        nodeMapping.addAttributeMapping(mapping);
        return mapping;
    }

    public PropertyAttributeMapping addValueAttributeMapping(String nodeName, String propertyName, String valueAttributeName, String typeAttributeName) {
        Method setter = findSetterMethod(_mappedClass, Object.class, propertyName);
        PropertyValueAttributeMapping attributeMapping = new PropertyValueAttributeMapping(propertyName, valueAttributeName, typeAttributeName, setter);

        NodeMapping nodeMapping = getNodeMapping(nodeName);
        if (nodeMapping == null) {
            nodeMapping = addEmptyNodeMapping(nodeName);
        }
        nodeMapping.addAttributeMapping(attributeMapping);
        return attributeMapping;
    }


    private static NodeMapping createNodeMapping(String nodeName) {
        return new NodeMapping(nodeName);
    }

    private static NodeMapping createNodeMapping(String nodeName, Class mappedClass, Class propertyType, String propertyName) {
        Method setter = findSetterMethod(mappedClass, propertyType, propertyName);
        return new NodeMapping(nodeName, propertyName, propertyType, setter);
    }

    /**
     * factory method for creating property mappigns that map from an xml attribute to a property.
     */
    private static PropertyAttributeMapping createAttributeMapping(Class mappedClass, Class propertyType, String elementName, String propertyName) {
        Method setter = findSetterMethod(mappedClass, propertyType, propertyName);
        return new PropertyAttributeMapping(propertyName, elementName, propertyType, setter);
    }

    private static Method findSetterMethod(Class mappedClass, Class propertyType, String propertyName) throws NodeProcessingException {
        String methodName = "set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
        try {
            return mappedClass.getMethod(methodName, new Class[]{propertyType});
        } catch (NoSuchMethodException e) {
            throw new NodeProcessingException("Could not find a setter for property name '" + propertyName + "' on class '" + mappedClass + " - looking for " + methodName, e);
        }

    }

    public Map getNodeMappings() {
        return _mappings;
    }

    /**
     * @param nodeName
     * @return
     * @todo implement XPATH pased names INCLUDING atttribute values so you can map a mapping on both element name and attribute - e.g. <parameter type="derived"> would map to a different node than just <parameter>
     */
    private NodeMapping getNodeMapping(String nodeName) {
        return (NodeMapping)_mappings.get(nodeName);
    }

    

    private Class _mappedClass;
    private Map _mappings = new HashMap();
}
